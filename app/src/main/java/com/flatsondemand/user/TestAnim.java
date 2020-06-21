/*
 * Hackdroid Tech Author raza11409652 github www.github.com/raza11409652 Copyright (c)  2020
 */

package com.flatsondemand.user;

import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class TestAnim extends AppCompatActivity {
    Button fade;
    TextView test;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test_anim);

        fade = findViewById(R.id.fade);
        test = findViewById(R.id.text); // TextView
        fade.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                /**
                 * Load Animation
                 */
                fade();
            }
        });


    }

    private void fade() {
        Animation anim = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.right_to_left);
        test.startAnimation(anim);
    }


}
