package com.example.addictionrecoveryprogress;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.app.ListActivity;
import android.widget.ArrayAdapter;

import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;

public class ViewRecordActivity extends ListActivity {

    private RecordOperations recordOps;
    List<ProgressRecord> records;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_record);
        recordOps = new RecordOperations(this);
        recordOps.open();

        // create a dummy record, and add to recordOps
        ProgressRecord record1 = new ProgressRecord();
        Calendar date1 = Calendar.getInstance();
        record1.setDate(date1);
        record1.setVictory(true);
        recordOps.addRecord(record1);

        ProgressRecord record2 = new ProgressRecord();
        Calendar date2 = Calendar.getInstance();
        date2.add(Calendar.DATE, -1);
        record2.setDate(date2);
        record2.setVictory(false);
        recordOps.addRecord(record2);

        records = recordOps.getAllRecords();
        recordOps.close();

        ArrayAdapter<ProgressRecord> adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, records);
        setListAdapter(adapter);
    }

    /** called when the user clicks the 'Add a new Record' button */
    public void startAdd(View view) {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }
}
