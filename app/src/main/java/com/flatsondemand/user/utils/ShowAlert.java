/*
 * Hackdroid Tech Author raza11409652 github www.github.com/raza11409652 Copyright (c)  2020
 */

package com.flatsondemand.user.utils;

import android.app.Activity;
import android.content.Context;

import com.flatsondemand.user.R;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;

public class ShowAlert {
    Activity activity;
    Context context;


    public ShowAlert(Activity activity, Context context) {
        this.activity = activity;
        this.context = context;

    }

    public void alert(String msg) {
        new MaterialAlertDialogBuilder(context, R.style.alert).setMessage(msg)
                .setPositiveButton("OK", null)
                .show();

    }
}
