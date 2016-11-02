package com.example.addictionrecoveryprogress;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class ViewRecordActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_record);
    }

    /** called when the user clicks the 'Add a new Record' button */
    public void startAdd(View view) {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }
}
