package com.link2loyalty.bwigomdlib;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.IBinder;
import android.os.Looper;
import android.util.Log;

import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.core.app.NotificationCompat;
import androidx.core.content.ContextCompat;

import com.android.volley.VolleyError;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;
import com.google.gson.Gson;
import com.link2loyalty.bwigomdlib.api.ServerCallback;
import com.link2loyalty.bwigomdlib.models2.CheckGpsPromo;
import com.link2loyalty.bwigomdlib.models2.User;
import com.link2loyalty.bwigomdlib.models2.login.LoginRes;
import com.link2loyalty.bwigomdlib.models2.notification.Notification;

import java.lang.reflect.Type;

public class MyLocationService extends Service {
    public User mUser;
    public Context context;
    static final int LOCATION_SERVICE_ID = 175;
    static final String ACTION_START_LOCATION_SERVICE = "startLocationService";
//    static final String ACTION_STOP_LOCATION_SERVICE = "stopLocationService";


    private LocationCallback locationCallback = new LocationCallback() {
        @Override
        public void onLocationResult(LocationResult locationResult) {
            super.onLocationResult(locationResult);
            if (locationResult != null && locationResult.getLastLocation() != null) {
                double latitude = locationResult.getLastLocation().getLatitude();
                double longitude = locationResult.getLastLocation().getLongitude();

                mUser=new User(getApplicationContext());
                Log.d("LOCATION_UPDATE", latitude + ", " + longitude+"---USER::::"+mUser.getSes());
                /**/
                CheckGpsPromo x=new CheckGpsPromo(getBaseContext());
                x.doCallGpsPromo(mUser.getSes(), "" + latitude, "" + longitude, new ServerCallback() {
                    @Override
                    public void onSuccess(String result) {
  //                      Gson mGson=new Gson();
//                        final LoginRes mRes = mGson.fromJson(result, (Type) getApplicationContext());
                        Log.d("ResponseFrom PushCupon",""+ result);
                        Log.i("success...yyyy.",""+result);
                    }

                    @Override
                    public void onError(VolleyError err) {
                        Log.i("error..xxx.",""+err);
                    }
                });

                /**/

            }
        }
    };

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        throw new UnsupportedOperationException("Not yet implemented");

    }

    private void startLocationService() {
        Intent intent = new Intent(this, ArticleDetailActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        intent.putExtra( "title", "BwigoMobile");
        intent.putExtra( "body", "BwigoMobile");
        //PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, intent, PendingIntent.FLAG_ONE_SHOT);
        String channelId = "Default";
        NotificationCompat.Builder builder = new  NotificationCompat.Builder(this, channelId)
                .setSmallIcon(R.drawable.ic_not_bwigo)
                .setColor( getResources().getColor(R.color.colorAccent) )
                .setContentTitle( "Bwigo Mobile Conectado" );
//                .setContentText( "Bwigo Mobile").setAutoCancel(true).setContentIntent(pendingIntent)
        NotificationManager manager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel channel = new NotificationChannel(channelId, "Default channel", NotificationManager.IMPORTANCE_DEFAULT);
            manager.createNotificationChannel(channel);
        }
        LocationRequest locationRequest = new LocationRequest();
        locationRequest.setInterval(10000);
        //locationRequest.setFastestInterval(2000);
        locationRequest.setSmallestDisplacement(100); //329
        locationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        LocationServices.getFusedLocationProviderClient(this)
                .requestLocationUpdates(locationRequest, locationCallback, Looper.getMainLooper());
            startForeground(LOCATION_SERVICE_ID,builder.build());
    }
    public void stopLocationService(){
        LocationServices.getFusedLocationProviderClient(this)
                .removeLocationUpdates(locationCallback);
        stopForeground(true);
        stopSelf();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
/*        if(intent!=null){
            String action=intent.getAction();
            if(action!=null){
                if(action.equals(ACTION_START_LOCATION_SERVICE)){
                    startLocationService();
                }else if(action.equals(ACTION_STOP_LOCATION_SERVICE)){
                    stopLocationService();
                }
            }
        }*/
        //startLocationService();
        return START_STICKY; //super.onStartCommand(intent, flags, startId);
    }
}