/*
 * Hackdroid Tech Author raza11409652 github www.github.com/raza11409652 Copyright (c)  2020
 */

package com.flatsondemand.user.listener;

import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.ResolveInfo;

public interface UpiListener {
    void onUpiItemClick(ResolveInfo resolveInfo , Intent intent);
}
