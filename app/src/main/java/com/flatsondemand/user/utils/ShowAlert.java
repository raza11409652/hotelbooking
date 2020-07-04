/*
 * Hackdroid Tech Author raza11409652 github www.github.com/raza11409652 Copyright (c)  2020
 */

package com.flatsondemand.user.utils;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;

import com.flatsondemand.user.MainActivity;
import com.flatsondemand.user.R;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import com.google.firebase.auth.FirebaseAuth;

public class ShowAlert {
    Activity activity;
    Context context;
    FirebaseAuth firebaseAuth;


    public ShowAlert(Activity activity, Context context) {
        this.activity = activity;
        this.context = context;


    }

    public void alert(String msg) {
        new MaterialAlertDialogBuilder(context, R.style.alert).setMessage(msg)
                .setPositiveButton("OK", null)
                .show();

    }

    public void alertBackToHome(String msg) {
        new MaterialAlertDialogBuilder(context, R.style.alert).setMessage(msg)
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        activity.finish();
                    }
                }).setCancelable(false)
                .show();

    }

    public void alertLogout() {
        firebaseAuth = firebaseAuth.getInstance();
        new MaterialAlertDialogBuilder(context, R.style.alert).setMessage("Are you sure ? You will be logout from this device.")
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        firebaseAuth.signOut();
                        Intent main = new Intent(context, MainActivity.class);
                        main.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                        context.startActivity(main);
                        activity.finish();
                    }
                }).setCancelable(true).setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                    
            }
        })
                .show();

    }

}
