package com.link2loyalty.bwigomdlib.services;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import com.link2loyalty.bwigomdlib.MyLocationService;

public class BootBroadcast extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        context.startService(new Intent(context, MyLocationService.class));
    }
}
