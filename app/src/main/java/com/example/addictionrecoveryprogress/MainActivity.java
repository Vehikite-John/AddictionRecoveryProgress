package com.example.addictionrecoveryprogress;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutCompat;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

public class MainActivity extends AppCompatActivity {

  private RadioGroup radioGroup;
  private RadioButton isVictoryRadioButton, isSetbackRadiobutton;
  private EditText reportDateEditText;
  private Button addUpdateButton;
  private ProgressRecord newRecord;
  private RecordOperations recordOps;

  // text views and spinners if setback is selected
  private TextView tv_mood;
  private TextView tv_location;
  private TextView tv_time;
  private Spinner sp_mood;
  private Spinner sp_location;
  private Spinner sp_time;

  // Linear Layout that will be hidden until Setback is chosen
//  LinearLayout ll_mood;
//  LinearLayout ll_location;
//  LinearLayout ll_time;
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
    addUpdateButton = (Button) findViewById(R.id.button_add_update_record);

    // Create containers for setback fiels
    ll_setback = (LinearLayout) findViewById(R.id.ll_setback);

    tv_mood = (TextView) findViewById(R.id.tv_mood);
    sp_mood = (Spinner) findViewById(R.id.sp_mood);

    tv_location = (TextView) findViewById(R.id.tv_location);
    sp_location = (Spinner) findViewById(R.id.sp_location);

    tv_time = (TextView) findViewById(R.id.tv_time);
    sp_time = (Spinner) findViewById(R.id.sp_time);

    // add values to spinners
    sp_mood.setAdapter(new ArrayAdapter<ProgressRecord.Mood>(this, R.layout.support_simple_spinner_dropdown_item, ProgressRecord.Mood.values()));
    sp_location.setAdapter(new ArrayAdapter<ProgressRecord.Location>(this, R.layout.support_simple_spinner_dropdown_item, ProgressRecord.Location.values()));
    sp_time.setAdapter(new ArrayAdapter<ProgressRecord.TimePeriod>(this, R.layout.support_simple_spinner_dropdown_item, ProgressRecord.TimePeriod.values()));

    // hide setback Linear Layout
    ll_setback.setVisibility(LinearLayout.GONE);

    recordOps = new RecordOperations(this);
    recordOps.open();

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
        recordOps.addRecord(newRecord);
        Toast t = Toast.makeText(MainActivity.this, newRecord.toString(), Toast.LENGTH_SHORT);
        t.show();
        Intent i = new Intent(MainActivity.this,ViewRecordActivity.class);
        startActivity(i);
      }
    });
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
}
