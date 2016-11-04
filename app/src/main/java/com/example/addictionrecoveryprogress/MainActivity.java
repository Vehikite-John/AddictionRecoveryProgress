package com.example.addictionrecoveryprogress;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
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

    recordOps = new RecordOperations(this);
    recordOps.open();

    radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
      @Override
      public void onCheckedChanged(RadioGroup group, int checkedId) {
        // find which radio button is selected
        if (checkedId == R.id.radio_isVictory) {
          newRecord.setVictory(true);
        } else if (checkedId == R.id.radio_isSetback) {
          newRecord.setVictory(false);
        }
      }
    });

    addUpdateButton.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        try {
          Calendar reportDate = formatDate(reportDateEditText.getText().toString());
          newRecord.setDate(reportDate);
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
