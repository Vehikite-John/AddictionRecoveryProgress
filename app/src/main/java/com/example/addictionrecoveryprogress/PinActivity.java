package com.example.addictionrecoveryprogress;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

public class PinActivity extends AppCompatActivity {
    private EditText editText;
    private TextView validPinMessage;
    int pin;
    SharedPreferences sp;

    // SharedPreferences will be saved in a file named MyPrefsFile
    public static final String PREFS_NAME = "MyPrefsFile";

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pin);

        editText = (EditText) findViewById(R.id.et_pinEntry);
        editText.addTextChangedListener(pinWatcher);
        validPinMessage = (TextView) findViewById(R.id.tv_validPinMessage);

        // get SharedPreferences from file named MyPrefsFile
        sp = getSharedPreferences(PREFS_NAME, 0);
        // set pin to savedPin if SharedPreferences exists
        // if not, set pin to 1111
        pin = sp.getInt("savedPin", 1111);

        // attempt to bypass pin if not set (meaning if set to 1111
        if (pin == 1111) {
            Intent i = new Intent(PinActivity.this, DashboardActivity.class);
            startActivity(i);
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
            String input = editText.getText().toString();

            if (input.length() != 4) {
                validPinMessage.setText("Please enter a valid pin");
                validPinMessage.setVisibility(View.VISIBLE);
            }
            else {
                int inputInt = Integer.parseInt(input);
                if (inputInt != pin) {
                    validPinMessage.setText("Invalid pin. Please try again.");
                    validPinMessage.setVisibility(View.VISIBLE);
                }
                if (inputInt == pin) {
                    Intent i = new Intent(PinActivity.this, DashboardActivity.class);
                    startActivity(i);
                }
            }
        }
    };
}
