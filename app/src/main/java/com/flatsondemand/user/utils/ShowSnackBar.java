/*
 * Hackdroid Tech Author raza11409652 github www.github.com/raza11409652 Copyright (c)  2020
 */

package com.flatsondemand.user.utils;

import android.view.View;

import androidx.constraintlayout.widget.ConstraintLayout;

import com.google.android.material.snackbar.Snackbar;

public class ShowSnackBar {
    public static void show(String str, View view) {
        Snackbar snackbar = Snackbar.make(view, str, Snackbar.LENGTH_SHORT);
        snackbar.show();
    }
    public static void showWithConstraintLayout(String str, ConstraintLayout view) {
        Snackbar snackbar = Snackbar.make(view.getChildAt(0), str, Snackbar.LENGTH_SHORT);
        snackbar.show();
    }
}
