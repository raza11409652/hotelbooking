/*
 * Hackdroid Tech Author raza11409652 github www.github.com/raza11409652 Copyright (c)  2020
 */

package com.flatsondemand.user.listener;

import com.flatsondemand.user.model.Property;

public interface OnPropertyClick {
    void onHeartIconClick(Property property);

    void onItemClick(Property property);
}