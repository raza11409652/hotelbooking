/*
 * Hackdroid Tech Author raza11409652 github www.github.com/raza11409652 Copyright (c)  2020
 */

package com.flatsondemand.user.activity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.flatsondemand.user.R;
import com.flatsondemand.user.utils.ErrorAlert;
import com.flatsondemand.user.utils.StringHandler;

public class OtpVerify extends AppCompatActivity {
    String mobileNumber = null;
    String TAG = OtpVerify.class.getSimpleName();
    Toolbar toolbar;
    TextView mobileNumberHint;
    Button verify;
    EditText otpInput;
    String otp;
    ErrorAlert errorAlert;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_otp_verify);

        Log.d(TAG, "onCreate: " + mobileNumber);
        toolbar = findViewById(R.id.include);
        setSupportActionBar(toolbar);
        ActionBar actionbar = getSupportActionBar();
        actionbar.setDisplayHomeAsUpEnabled(true);
        actionbar.setHomeButtonEnabled(true);
        mobileNumber = "+91" + getIntent().getStringExtra("mobile");
        mobileNumberHint = findViewById(R.id.otp_screen_mobile_hint);
        mobileNumberHint.setText(getString(R.string.otp_screen_hint_text) + " " + mobileNumber);
        otpInput = findViewById(R.id.otpInput);
        verify = findViewById(R.id.verify);
        errorAlert = new ErrorAlert(this);
        verify.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(TAG, "onClick: Verify button click");
                otp = otpInput.getText().toString();
                if (StringHandler.isEmpty(otp)) {
                    errorAlert.show(getString(R.string.otp_required_error));
                    return;
                }
                Intent dash = new Intent(getApplicationContext(), Home.class);
                startActivity(dash);
                dash.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
                finish();

            }
        });
    }

}
