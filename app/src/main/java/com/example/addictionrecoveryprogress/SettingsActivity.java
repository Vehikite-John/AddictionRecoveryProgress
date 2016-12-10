package com.example.addictionrecoveryprogress;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class SettingsActivity extends AppCompatActivity {
    EditText et_newPin;
    EditText et_confirmPin;
    Button b_updatePin;
    TextView tv_settingsPinError;
    SharedPreferences sp;

    // SharedPreferences will be saved in a file named MyPrefsFile
    public static final String PREFS_NAME = "MyPrefsFile";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
        Toolbar mainToolbar = (Toolbar) findViewById(R.id.main_toolbar);
        setSupportActionBar(mainToolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        tv_settingsPinError = (TextView) findViewById(R.id.tv_settingsPinError);
        et_newPin = (EditText) findViewById(R.id.et_newPin);
        et_confirmPin = (EditText) findViewById(R.id.et_confirmPin);
        et_confirmPin.addTextChangedListener(pinWatcher);
        b_updatePin = (Button) findViewById(R.id.b_updatePin);
        b_updatePin.setEnabled(false);

        b_updatePin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // save int to SharedPreferences
                int pinInt = Integer.parseInt(et_newPin.getText().toString());
                sp = getSharedPreferences(PREFS_NAME, 0);
                SharedPreferences.Editor editor = sp.edit();
                editor.putInt("savedPin", pinInt);
                editor.commit();
                Toast t = Toast.makeText(SettingsActivity.this, "Pin updated.", Toast.LENGTH_SHORT);
                t.show();
                Intent i = new Intent(SettingsActivity.this, DashboardActivity.class);
                startActivity(i);
            }
        });
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

    private final TextWatcher pinWatcher = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

        }

        @Override
        public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

        }

        @Override
        public void afterTextChanged(Editable editable) {
            String pin = et_newPin.getText().toString();
            String confirmPin = et_confirmPin.getText().toString();

            if (confirmPin.length() < 4 && tv_settingsPinError.getVisibility() == View.VISIBLE) {
                tv_settingsPinError.setVisibility(View.INVISIBLE);
            }

            if (confirmPin.length() == 4 && !confirmPin.equals(pin)) {
                // display error if incorrect pin is entered
                tv_settingsPinError.setVisibility(View.VISIBLE);
                b_updatePin.setEnabled(false);
            }
            if (confirmPin.equals(pin)) {
                int pinInt = Integer.parseInt(pin);
                b_updatePin.setEnabled(true);
            }
        }
    };
}