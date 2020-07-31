/*
 * Hackdroid Tech Author raza11409652 github www.github.com/raza11409652 Copyright (c)  2020
 */

package com.hotel.user.listener;

import com.hotel.user.model.Nearby;

import java.util.ArrayList;

public interface NearBySelector {
    void onItemClick(int position, String title , ArrayList<Nearby>arrayList);
}
