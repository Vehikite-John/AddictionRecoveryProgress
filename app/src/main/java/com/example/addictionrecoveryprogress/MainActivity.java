package com.example.addictionrecoveryprogress;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class MainActivity extends AppCompatActivity {

  private RadioGroup radioGroup;
  private RadioButton isVictoryRadioButton, isSetbackRadiobutton;
  private EditText reportDateEditText;
  private Button addUpdateButton;
  private ProgressRecord newRecord;
  private RecordOperations recordOps;
  private ImageView calImage;
  private TextView validDateMessage;

  // spinners if setback is selected
  private Spinner sp_mood;
  private Spinner sp_location;
  private Spinner sp_time;

  // Linear Layout that will be hidden until Setback is chosen
  LinearLayout ll_setback;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);

    newRecord = new ProgressRecord();
    newRecord.setVictory(true);
    radioGroup = (RadioGroup) findViewById(R.id.radio_victory);
    isVictoryRadioButton = (RadioButton) findViewById(R.id.radio_isVictory);
    isSetbackRadiobutton = (RadioButton) findViewById(R.id.radio_isSetback);
    reportDateEditText = (EditText) findViewById(R.id.edit_text_report_date);
    // add textwatcher to edittext
    reportDateEditText.addTextChangedListener(dateWatcher);
    validDateMessage = (TextView) findViewById(R.id.tv_validDateMessage);

    // creates date picker
    // appears when user selects calendar icon
    createDatePicker();

    addUpdateButton = (Button) findViewById(R.id.button_add_update_record);

    // Create container for setback fields
    ll_setback = (LinearLayout) findViewById(R.id.ll_setback);

    // spinners
    sp_mood = (Spinner) findViewById(R.id.sp_mood);
    sp_location = (Spinner) findViewById(R.id.sp_location);
    sp_time = (Spinner) findViewById(R.id.sp_time);

    // add values to spinners
    sp_mood.setAdapter(new ArrayAdapter<>(this, R.layout.support_simple_spinner_dropdown_item, ProgressRecord.Mood.values()));
    sp_location.setAdapter(new ArrayAdapter<>(this, R.layout.support_simple_spinner_dropdown_item, ProgressRecord.Location.values()));
    sp_time.setAdapter(new ArrayAdapter<>(this, R.layout.support_simple_spinner_dropdown_item, ProgressRecord.TimePeriod.values()));

    // hide setback Linear Layout
    ll_setback.setVisibility(LinearLayout.GONE);

    recordOps = new RecordOperations(this);
    recordOps.open();

    // startEditTextListener();

    radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
      @Override
      public void onCheckedChanged(RadioGroup group, int checkedId) {
        // find which radio button is selected
        if (checkedId == R.id.radio_isVictory) {
          newRecord.setVictory(true);

          // hide setback Linear Layout
          ll_setback.setVisibility(LinearLayout.GONE);

        } else if (checkedId == R.id.radio_isSetback) {
          newRecord.setVictory(false);

          // make setback Linear Layout visible
          ll_setback.setVisibility(LinearLayout.VISIBLE);
        }
      }
    });

    addUpdateButton.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        try {
          Calendar reportDate = formatDate(reportDateEditText.getText().toString());
          newRecord.setDate(reportDate);
          newRecord.setMood((ProgressRecord.Mood)sp_mood.getSelectedItem());
          newRecord.setLocation((ProgressRecord.Location)sp_location.getSelectedItem());
          newRecord.setTimePeriod((ProgressRecord.TimePeriod)sp_time.getSelectedItem());
        } catch (ParseException e) {
          e.printStackTrace();
        }
        //TODO: figure out why record isn't updating
        if (addUpdateButton.getText().equals("Update")) {
          recordOps.updateRecord(newRecord);
        }
        else {
          recordOps.addRecord(newRecord);
        }
        Toast t = Toast.makeText(MainActivity.this, newRecord.toString(), Toast.LENGTH_SHORT);
        t.show();
        Intent i = new Intent(MainActivity.this,ViewRecordActivity.class);
        startActivity(i);
      }
    });
  }

  // validate date as text is entered in edittext
  // http://stacktips.com/tutorials/android/android-textwatcher-example
  private final TextWatcher dateWatcher = new TextWatcher() {
    @Override
    public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

    }

    @Override
    public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

    }

    @Override
    public void afterTextChanged(Editable editable) {
      String date = reportDateEditText.getText().toString();
      // show error message if invalid date is entered
      if (!isValidFormat(date)) {
        validDateMessage.setText("Invalid date format");
        validDateMessage.setVisibility(View.VISIBLE);
      }
      else {
        try {
          Calendar cal = formatDate(date);

          // remove error message and activate button when valid date is entered
          validDateMessage.setVisibility(View.INVISIBLE);
          addUpdateButton.setEnabled(true);

          // attempt to get record based on inputted date
          ProgressRecord tempRecord;
          tempRecord = recordOps.getRecord(cal);
          Log.i("Test", "id = " + tempRecord.getId());
          // new records have an id of 0
          // if record exists (i.e. id != 0), restore record
          if (tempRecord.getId() != 0) {
            restoreRecord(tempRecord);
          }
        } catch (ParseException e) {
          e.printStackTrace();
        }
      }
    }
  };

  // restore record based from inputted date
  public void restoreRecord(ProgressRecord record) {
    // update Button text to Update
    addUpdateButton.setText("Update");
    Toast t = Toast.makeText(MainActivity.this, "Record for this date exists. Record restored.", Toast.LENGTH_SHORT);
    t.show();

    // set newRecord = record
    newRecord = record;

    // restore values
    if(record.isVictory()) {
      isVictoryRadioButton.setChecked(true);
    }
    else {
      isVictoryRadioButton.setChecked(false);
      isSetbackRadiobutton.setChecked(true);
      // source: http://stackoverflow.com/questions/11072576/set-selected-item-of-spinner-programmatically
      sp_mood.setSelection(((ArrayAdapter) sp_mood.getAdapter()).getPosition(record.getMood()));
      sp_time.setSelection(((ArrayAdapter) sp_time.getAdapter()).getPosition(record.getTimePeriod()));
      sp_location.setSelection(((ArrayAdapter) sp_location.getAdapter()).getPosition(record.getLocation()));
    }
  }

  // check if date is valid
  // source: http://stackoverflow.com/questions/20231539/java-check-the-date-format-of-current-string-is-according-to-required-format-or
  public boolean isValidFormat(String value) {
    Date date = null;
    try {
      SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy", Locale.US);
      date = sdf.parse(value);
      if (!value.equals(sdf.format(date))) {
        date = null;
      }
    } catch (ParseException ex) {
      ex.printStackTrace();
    }
    return date != null;
  }

  /** called when the user clicks the 'Add a new Record' button */
  public void startView(View view) {
    Intent intent = new Intent(this, ViewRecordActivity.class);
    startActivity(intent);
  }

  public Calendar formatDate(String date) throws ParseException {
    SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy", Locale.US);
    Calendar cal = Calendar.getInstance();
    cal.setTime(sdf.parse(date));
    return cal;
  }

  // Source: http://stackoverflow.com/questions/14933330/datepicker-how-to-popup-datepicker-when-click-on-edittext
  public void createDatePicker() {
    final Calendar myCalendar = Calendar.getInstance();
    final DatePickerDialog.OnDateSetListener date = new DatePickerDialog.OnDateSetListener() {
      @Override
      public void onDateSet(DatePicker view, int year, int monthOfYear,
                            int dayOfMonth) {
        myCalendar.set(Calendar.YEAR, year);
        myCalendar.set(Calendar.MONTH, monthOfYear);
        myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
        updateLabel(myCalendar);
      }
    };
    // Date picker appears when user clicks on calendar icon
    calImage = (ImageView) findViewById(R.id.image_view_report_date);
    calImage.setOnClickListener(new View.OnClickListener() {

      @Override
      public void onClick(View v) {
        new DatePickerDialog(MainActivity.this, date, myCalendar
                .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                myCalendar.get(Calendar.DAY_OF_MONTH)).show();
      }
    });
  }

  // update reportDateEditText with date selected on date picker
  private void updateLabel(Calendar myCalendar) {

    String myFormat = "dd/MM/yyyy"; //In which you need put here
    SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);

    reportDateEditText.setText(sdf.format(myCalendar.getTime()));
  }
}
