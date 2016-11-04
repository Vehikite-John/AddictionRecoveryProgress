package com.example.addictionrecoveryprogress;

import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;

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
