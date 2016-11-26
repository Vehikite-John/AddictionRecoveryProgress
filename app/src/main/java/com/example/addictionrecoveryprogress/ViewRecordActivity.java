package com.example.addictionrecoveryprogress;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.logging.Level;
import java.util.logging.Logger;

public class ViewRecordActivity extends AppCompatActivity {

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
            Logger.getLogger(ViewRecordActivity.class.getName()).log(Level.SEVERE, null, ex);
        }

        LinearLayout lView = (LinearLayout)findViewById(R.id.statisticsLinearlayout);

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

    /**
     * called when the user clicks the 'Add a new Record' button
     */
    public void startAdd(View view) {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }
}
