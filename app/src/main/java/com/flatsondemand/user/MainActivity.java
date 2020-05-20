
/*
 * Copyright (c) Hackdroid Tech Author raza11409652 github www.github.com/raza11409652 2020
 */
/**
 * Main Activity of App  , routing between session Management
 */

package com.flatsondemand.user;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.flatsondemand.user.activity.Login;
import com.flatsondemand.user.utils.TypefaceUtil;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        try {
            TypefaceUtil.overrideFont(getApplicationContext(), "SERIF", "fonts/Poppins-Regular.ttf");
        } catch (Exception e) {
            e.printStackTrace();
        }
        setContentView(R.layout.activity_main);
        Intent login = new Intent(getApplicationContext(), Login.class);
        startActivity(login);
        finish();
    }

}
