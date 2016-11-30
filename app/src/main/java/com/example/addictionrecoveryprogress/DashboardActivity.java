package com.example.addictionrecoveryprogress;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.logging.Level;
import java.util.logging.Logger;

public class DashboardActivity extends AppCompatActivity {

    private RecordOperations recordOps;
    private int longestStreak;
    private int currentStreak;
    private int totalVictories;
    private int totalVictoriesPercent;
    private int monthVictories;
    private int monthVictoriesPercent;

    private TextView totalVictoriesText = null;
    private TextView totalVictoriesPercentText = null;
    private TextView monthVictoriesText = null;
    private TextView monthVictoriesPercentText = null;
    private TextView longestStreakText = null;
    private TextView currentSreakText = null;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_record);
        Toolbar mainToolbar = (Toolbar) findViewById(R.id.main_toolbar);
        setSupportActionBar(mainToolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);

        recordOps = new RecordOperations(this);

        try {
            recordOps.open();
            longestStreak = recordOps.getLongestStreak();
            currentStreak = recordOps.getCurrentStreak();
            totalVictories = recordOps.getTotalVictories();
            totalVictoriesPercent = recordOps.getTotalVictoriesPercent();
            monthVictories = recordOps.getMonthVictories();
            monthVictoriesPercent = recordOps.getMonthVictoriesPercent();
            recordOps.close();
        } catch (Exception ex) {
            Logger.getLogger(DashboardActivity.class.getName()).log(Level.SEVERE, null, ex);
        }

        LinearLayout lView = (LinearLayout) findViewById(R.id.statisticsLinearlayout);

        totalVictoriesText = new TextView(this);
        totalVictoriesText.setText("Total Victories: " + totalVictories);
        lView.addView(totalVictoriesText);

        totalVictoriesPercentText = new TextView(this);
        totalVictoriesPercentText.setText("Total Victories Percent: " + totalVictoriesPercent);
        lView.addView(totalVictoriesPercentText);

        monthVictoriesText = new TextView(this);
        monthVictoriesText.setText("Month Victories: " + monthVictories);
        lView.addView(monthVictoriesText);

        monthVictoriesPercentText = new TextView(this);
        monthVictoriesPercentText.setText("Month Victories Percent: " + monthVictoriesPercent);
        lView.addView(monthVictoriesPercentText);

        longestStreakText = new TextView(this);
        longestStreakText.setText("Longest Streak: " + longestStreak);
        lView.addView(longestStreakText);

        currentSreakText = new TextView(this);
        currentSreakText.setText("Current Streak: " + currentStreak);
        lView.addView(currentSreakText);
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
