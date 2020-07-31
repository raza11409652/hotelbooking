
/*
 * Copyright (c) Hackdroid Tech Author raza11409652 github www.github.com/raza11409652 2020
 */
/**
 * Main Activity of App  , routing between session Management
 */

package com.hotel.user;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.hotel.user.activity.Home;
import com.hotel.user.activity.Login;
import com.hotel.user.utils.TypefaceUtil;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class MainActivity extends AppCompatActivity {
    FirebaseAuth auth;
    FirebaseUser user;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        try {
            TypefaceUtil.overrideFont(getApplicationContext(), "SERIF", "fonts/Poppins-Regular.ttf");
        } catch (Exception e) {
            e.printStackTrace();
        }
        auth = FirebaseAuth.getInstance();
        user = auth.getCurrentUser();
        if (user != null) {
            Intent home = new Intent(getApplicationContext(), Home.class);
            updateUi(home);
        } else {
            Intent login = new Intent(getApplicationContext(), Login.class);
            updateUi(login);
        }


//        startActivity(login);
//        finish();
    }

    private void updateUi(Intent intent) {
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
        finish();
    }

}
