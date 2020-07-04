/*
 * Hackdroid Tech Author raza11409652 github www.github.com/raza11409652 Copyright (c)  2020
 */

package com.flatsondemand.user.activity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.flatsondemand.user.R;
import com.flatsondemand.user.service.UserService;
import com.flatsondemand.user.utils.ErrorAlert;
import com.flatsondemand.user.utils.ShowProgress;
import com.flatsondemand.user.utils.StringHandler;
import com.google.android.gms.tasks.OnCanceledListener;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.android.gms.tasks.TaskExecutors;
import com.google.firebase.FirebaseException;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthProvider;

import java.util.concurrent.TimeUnit;

public class OtpVerify extends AppCompatActivity {
    String mobileNumber = null;
    String TAG = OtpVerify.class.getSimpleName();
    Toolbar toolbar;
    TextView mobileNumberHint;
    Button verify;
    EditText otpInput;
    String otp;
    ErrorAlert errorAlert;
    String verification = null;
    FirebaseAuth firebaseAuth;
    FirebaseUser firebaseUser;
    ShowProgress showProgress;
    UserService userService;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_otp_verify);
        firebaseAuth = FirebaseAuth.getInstance();


        toolbar = findViewById(R.id.include);
        setSupportActionBar(toolbar);
        ActionBar actionbar = getSupportActionBar();
        actionbar.setDisplayHomeAsUpEnabled(true);
        actionbar.setHomeButtonEnabled(true);
        mobileNumber = "+91" + getIntent().getStringExtra("mobile");
        Log.d(TAG, "onCreate: " + mobileNumber);
        mobileNumberHint = findViewById(R.id.otp_screen_mobile_hint);
        mobileNumberHint.setText(getString(R.string.otp_screen_hint_text) + mobileNumber);
        otpInput = findViewById(R.id.otpInput);
        verify = findViewById(R.id.verify);
        errorAlert = new ErrorAlert(this);
        showProgress = new ShowProgress(this);
        userService = new UserService(OtpVerify.this, this);
        /***
         * Check anyy user is logged in or not
         */
        firebaseUser = firebaseAuth.getCurrentUser();
        if (firebaseUser != null) {
            Log.d(TAG, "onCreate: " + firebaseUser.getUid());
        }


        sendVerificationCode(mobileNumber);

        verify.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(TAG, "onClick: Verify button click");
                otp = otpInput.getText().toString();
                if (StringHandler.isEmpty(otp)) {
                    errorAlert.show(getString(R.string.otp_required_error));
                    return;
                }
                verifyCode(otp);

            }
        });
    }

    private void sendVerificationCode(String mobileNumber) {
        PhoneAuthProvider.getInstance().verifyPhoneNumber(mobileNumber, 60, TimeUnit.SECONDS, TaskExecutors.MAIN_THREAD, mCallBacks);
    }

    private PhoneAuthProvider.OnVerificationStateChangedCallbacks mCallBacks = new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
        @Override
        public void onCodeSent(@NonNull String s, @NonNull PhoneAuthProvider.ForceResendingToken forceResendingToken) {
            super.onCodeSent(s, forceResendingToken);
            verification = s;
        }

        @Override
        public void onVerificationCompleted(@NonNull PhoneAuthCredential phoneAuthCredential) {
            String code = phoneAuthCredential.getSmsCode();
            if (code != null) {
//                editText.setText(code);
//                progressBar.setVisibility(View.VISIBLE);
                otpInput.setText(code);
                verifyCode(code);
            }
        }

        @Override
        public void onVerificationFailed(@NonNull FirebaseException e) {
            Log.d(TAG, "onVerificationFailed: " + e.getLocalizedMessage());
            errorAlert.show(e.getLocalizedMessage());
            showProgress.dismiss();

        }
    };

    private void verifyCode(String code) {
        showProgress.show("Please wait while OTP is being verified");

        try {
            PhoneAuthCredential phoneAuthCredential = PhoneAuthProvider.getCredential(verification, code);
            signInWithCredential(phoneAuthCredential);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void signInWithCredential(PhoneAuthCredential phoneAuthCredential) {
        firebaseAuth.signInWithCredential(phoneAuthCredential).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                showProgress.dismiss();
                if (task.isSuccessful()) {
                    Log.d(TAG, "onComplete: Login success");
                    userService.userVerification(task.getResult().getUser().getUid(), mobileNumber);
//                    Intent home = new Intent(getApplicationContext(), Home.class);
//                    home.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK);
//                    startActivity(home);
//                    finish();
                } else {
                    Log.d(TAG, "onComplete: Login not success");
                }
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                showProgress.dismiss();
                Log.d(TAG, "onFailure: " + e.getMessage());
                errorAlert.show(e.getLocalizedMessage());
            }
        }).addOnCanceledListener(new OnCanceledListener() {
            @Override
            public void onCanceled() {
                showProgress.dismiss();
                Log.d(TAG, "onCanceled: cancled from user end");
            }
        });

    }
}
