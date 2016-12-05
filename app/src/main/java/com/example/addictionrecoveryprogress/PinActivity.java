package com.example.addictionrecoveryprogress;

import android.content.Intent;
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
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pin);

        editText = (EditText) findViewById(R.id.et_pinEntry);
        editText.addTextChangedListener(pinWatcher);
        validPinMessage = (TextView) findViewById(R.id.tv_validPinMessage);
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
            String pin = editText.getText().toString();

            if (pin.length() != 4) {
                validPinMessage.setText("Please enter a valid pin");
                validPinMessage.setVisibility(View.VISIBLE);
            }
            else {
                if (!pin.equals("1111")) {
                    validPinMessage.setText("Invalid pin. Please try again.");
                    validPinMessage.setVisibility(View.VISIBLE);
                }
                if (pin.equals("1111")) {
                    Intent i = new Intent(PinActivity.this, DashboardActivity.class);
                    startActivity(i);
                }
            }
        }
    };
}
