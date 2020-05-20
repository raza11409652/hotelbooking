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

import androidx.appcompat.app.AppCompatActivity;

import com.flatsondemand.user.R;
import com.flatsondemand.user.utils.ErrorAlert;
import com.flatsondemand.user.utils.ShowAlert;
import com.flatsondemand.user.utils.StringHandler;

public class Login extends AppCompatActivity {
    EditText mobileInput;
    Button loginBtn;
    String mobileNumber;
    String TAG = Login.class.getSimpleName();
    ShowAlert alert;
    ErrorAlert errorAlert;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        /**
         * @view init
         *
         */

        mobileInput = findViewById(R.id.mobile);
        loginBtn = findViewById(R.id.login);
        alert = new ShowAlert(this, this);
        errorAlert = new ErrorAlert(this);

        loginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                errorAlert.show(getString(R.string.moblie_required_error));
//                return;
                mobileNumber = mobileInput.getText().toString();
                if (StringHandler.isEmpty(mobileNumber)) {
                    errorAlert.show(getString(R.string.moblie_required_error));
                    return;
                }
                if (StringHandler.isNumber(mobileNumber) == false) {
                    errorAlert.show(getString(R.string.moblie_required_error));
                    return;
                }
                Log.d(TAG, "onClick: all OK to login .. ");

                Intent otpScreen = new Intent(getApplicationContext(), OtpVerify.class);
                otpScreen.putExtra("mobile", mobileNumber);
                otpScreen.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(otpScreen);
                overridePendingTransition(R.anim.left_to_right, R.anim.right_to_left);


            }
        });

    }
}
