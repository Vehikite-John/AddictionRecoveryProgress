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

public class SettingsActivity extends AppCompatActivity {
    EditText et_newPin;
    EditText et_confirmPin;
    Button b_updatePin;

    // SharedPreferences will be saved in a file named MyPrefsFile
    public static final String PREFS_NAME = "MyPrefsFile";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
        Toolbar mainToolbar = (Toolbar) findViewById(R.id.main_toolbar);
        setSupportActionBar(mainToolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        et_newPin = (EditText) findViewById(R.id.et_newPin);
        et_confirmPin = (EditText) findViewById(R.id.et_confirmPin);
        et_confirmPin.addTextChangedListener(pinWatcher);
        b_updatePin = (Button) findViewById(R.id.b_updatePin);
        b_updatePin.setEnabled(false);
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
            int pin = Integer.parseInt(et_newPin.getText().toString());
            int confirmPin = Integer.parseInt(et_confirmPin.getText().toString());

            if (pin != confirmPin) {
//                Toast t = Toast.makeText(SettingsActivity.this, "This pin doesn't match the pin entered above. Please reenter pin.",
//                        Toast.LENGTH_SHORT);
//                t.show();
                b_updatePin.setEnabled(false);
            }
            if (pin == confirmPin) {
                b_updatePin.setEnabled(true);
                // Code to save int to SharedPreferences
                SharedPreferences sp = getSharedPreferences(PREFS_NAME, 0);
                SharedPreferences.Editor editor = sp.edit();
                editor.putInt("savedPin", pin);
                editor.commit();
            }
        }
    };

    public void saveNumber(View view) {

    }
}