package com.example.addictionrecoveryprogress;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.LinearLayout;
import android.widget.TextView;

public class TrendsActivity extends AppCompatActivity {

    private RecordOperations recordOps;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_trends);
        Toolbar mainToolbar = (Toolbar) findViewById(R.id.main_toolbar);
        setSupportActionBar(mainToolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);

        recordOps = new RecordOperations(this);
        recordOps.open();

        ProgressRecord.Mood commonMood = recordOps.getMostFrequentMood();
        ProgressRecord.Location commonLocation = recordOps.getMostFrequentLocation();
        ProgressRecord.TimePeriod commonTime = recordOps.getMostFrequentTime();

        recordOps.close();

        LinearLayout lView = (LinearLayout) findViewById(R.id.trendsLinearlayout);

        if (commonMood == null || commonLocation == null || commonTime == null) {
            TextView noCommon = new TextView(this);
            noCommon.setPadding(40, 40, 40, 40);
            noCommon.setTextSize(16);
            noCommon.setText("No Trends, check back after more data has been submitted");
            lView.addView(noCommon);
        } else {
            TextView commonMoodText = new TextView(this);
            commonMoodText.setPadding(40, 40, 40, 40);
            commonMoodText.setTextSize(16);
            commonMoodText.setText("Most Common Mood: " + commonMood.toString());
            lView.addView(commonMoodText);

            TextView commonLocationText = new TextView(this);
            commonLocationText.setPadding(40, 40, 40, 40);
            commonLocationText.setTextSize(16);
            commonLocationText.setText("Most Common Location: " + commonLocation.toString());
            lView.addView(commonLocationText);

            TextView commonTimeText = new TextView(this);
            commonTimeText.setPadding(40, 40, 40, 40);
            commonTimeText.setTextSize(16);
            commonTimeText.setText("Most Common Time: " + commonTime.toString());
            lView.addView(commonTimeText);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        Intent intent;
        switch (item.getItemId()) {
            case R.id.action_home:
                // User chose the "home" item, show the app home UI...
                intent = new Intent(this, DashboardActivity.class);
                startActivity(intent);
                return true;

            case R.id.action_trends:
                // User chose the "home" item, show the app home UI...
                intent = new Intent(this, TrendsActivity.class);
                startActivity(intent);
                return true;

            case R.id.action_add:
                // User chose the "home" item, show the app home UI...
                intent = new Intent(this, AddRecordActivity.class);
                startActivity(intent);
                return true;

            case R.id.action_settings:
                // User chose the "home" item, show the app home UI...
                intent = new Intent(this, SettingsActivity.class);
                startActivity(intent);
                return true;

            default:
                // If we got here, the user's action was not recognized.
                // Invoke the superclass to handle it.
                return super.onOptionsItemSelected(item);
        }
    }
}