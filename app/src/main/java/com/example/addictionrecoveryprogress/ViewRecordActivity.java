package com.example.addictionrecoveryprogress;

import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;

import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ViewRecordActivity extends ListActivity {

    private RecordOperations recordOps;
    List<ProgressRecord> records;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_record);
        recordOps = new RecordOperations(this);

        try {
            recordOps.open();
            records = recordOps.getAllRecords();
            recordOps.close();
        } catch (Exception ex) {
            Logger.getLogger(ViewRecordActivity.class.getName()).log(Level.SEVERE, null, ex);
        }

        try {
            recordOps.open();
            records = recordOps.getAllRecords();
            recordOps.close();
        } catch (Exception ex) {
            Logger.getLogger(ViewRecordActivity.class.getName()).log(Level.SEVERE, null, ex);
        }

        ArrayAdapter<ProgressRecord> adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, records);
        setListAdapter(adapter);
    }

    /** called when the user clicks the 'Add a new Record' button */
    public void startAdd(View view) {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }
}
