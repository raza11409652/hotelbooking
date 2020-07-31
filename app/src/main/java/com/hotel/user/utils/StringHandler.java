/*
 * Hackdroid Tech Author raza11409652 github www.github.com/raza11409652 Copyright (c)  2020
 */

package com.hotel.user.utils;

import android.text.TextUtils;

public class StringHandler {


    /**
     * long millis=System.currentTimeMillis();
     * java.sql.Date date=new java.sql.Date(millis);
     * System.out.println(date);
     */
    public StringHandler() {
    }



    public static boolean isEmpty(String str) {
        if (TextUtils.isEmpty(str)) return true;
        return false;
    }

    public static String getString(CharSequence s) {
        final StringBuilder sb = new StringBuilder(s.length());
        sb.append(s);
        return sb.toString();
    }
    public  static  boolean isNumber(String string){
        if(TextUtils.isDigitsOnly(string)) return  true  ;
        return  false   ;
    }


}
