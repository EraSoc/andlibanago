package com.link2loyalty.bwigomdlib.helpers;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.IBinder;

import android.util.Log;

import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;


public class GpsServiceTracker extends Service {

    private static final String TAG = "GpsServiceTracker";
    private LocationManager mLocationManager = null;
    private static final int LOCATION_INTERVAL = 1000;
    private static final float LOCATION_DISTANCE = 0f;

    public class LocationListenerx implements android.location.LocationListener{
        Location mLastLocation;

        public LocationListenerx(String provider){
            Log.e(TAG,"LocationListenerx "+provider);
            mLastLocation=new Location(provider);
        }

        @Override
        public void onLocationChanged(Location location){
            Log.i(TAG,"onLocationChanged====> lat:::::"+location.getLatitude()+" ----lng :::"+location.getLongitude());
            try{
                String[] paramx=new String[]{""+location.getLatitude(),""+location.getLongitude()};
               // new AsyncLocation(getBaseContext()).execute(paramx);

            }catch (Exception e){
                Log.e(TAG,"error "+e.getMessage());
            }
            mLastLocation.set(location);
        }

        @Override
        public void onStatusChanged(String provider, int status, Bundle extras) {

        }

        @Override
        public void onProviderEnabled(String provider) {
            Log.e(TAG,"onProviderEnabled: "+provider);
        }

        @Override
        public void onProviderDisabled(String provider) {
            Log.e(TAG,"onProviderDisabled: "+provider);
        }
    }

    LocationListenerx[] mLocationListeners=new LocationListenerx[]{
            new LocationListenerx(LocationManager.PASSIVE_PROVIDER)
    };


    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.e(TAG,"onStartCommand");
        super.onStartCommand(intent,flags,startId);
        return START_STICKY;
//        return super.onStartCommand(intent, flags, startId);
    }


    @Override
    public void onCreate() {
        Log.e(TAG,"onCreate");
        initializeLocationManager();
        try {
            mLocationManager.requestLocationUpdates(
                    LocationManager.PASSIVE_PROVIDER,
                    LOCATION_INTERVAL,
                    LOCATION_DISTANCE,
                    mLocationListeners[0]
            );
        } catch (java.lang.SecurityException ex) {
            Log.i(TAG, "fail to request location update, ignore", ex);
        } catch (IllegalArgumentException ex) {
            Log.d(TAG, "network provider does not exist, " + ex.getMessage());
        }
    }
    @Override
    public void onDestroy() {
        Log.e(TAG, "onDestroy");
        super.onDestroy();
        if (mLocationManager != null) {
            for (int i = 0; i < mLocationListeners.length; i++) {
                try {
                    if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                        return;
                    }
                    mLocationManager.removeUpdates(mLocationListeners[i]);
                } catch (Exception ex) {
                    Log.i(TAG, "fail to remove location listener, ignore", ex);
                }
            }
        }
    }

    private void initializeLocationManager() {
        Log.e(TAG, "initializeLocationManager - LOCATION_INTERVAL: "+ LOCATION_INTERVAL + " LOCATION_DISTANCE: " + LOCATION_DISTANCE);
        if (mLocationManager == null) {
            mLocationManager = (LocationManager) getApplicationContext().getSystemService(Context.LOCATION_SERVICE);
        }
    }

}
