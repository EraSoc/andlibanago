package com.link2loyalty.bwigomdlib;

import android.Manifest;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.LocationManager;
import android.os.Bundle;
import android.provider.Settings;
import android.view.View;
import android.widget.Toast;

import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.VolleyError;
import com.google.android.gms.maps.model.LatLng;
import com.google.gson.Gson;
import com.link2loyalty.bwigomdlib.R;
import com.link2loyalty.bwigomdlib.adapters.FavoriteCouponAdapter;
import com.link2loyalty.bwigomdlib.api.ServerCallback;
import com.link2loyalty.bwigomdlib.helpers.GPSTracker;
import com.link2loyalty.bwigomdlib.interfaces.CouponsRecyclerViewOnItemClickListener;
import com.link2loyalty.bwigomdlib.models2.Coupon;
import com.link2loyalty.bwigomdlib.models2.User;
import com.link2loyalty.bwigomdlib.models2.coupon.LovRecomendados;
import com.link2loyalty.bwigomdlib.models2.coupon.RecomendadosRes;

import java.util.List;

public class SplashActivity extends AppCompatActivity {

    Context mContext;

    public static final int MY_PERMISSIONS_REQUEST_LOCATION = 99;
    private GPSTracker mGps;

    private User mUser;
    private Coupon mCoupon;


    LocationManager locationManager;

    @Override
    protected void onDestroy() {
        super.onDestroy();
//        mGps.stopUsingGPS();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        mContext = getApplicationContext();
        mUser = new User(mContext);
        mCoupon = new Coupon(mContext);
        locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);




        checkGPS();







        /*


         */


    }

    private void checkGPS(){
        if( isGPSEnabled() ){


            if( !checkLocationPermission() ){
                showAlertPermission();
                return;
            }

            mGps = new GPSTracker(mContext);


            if ( !isOnlineNet() ){
                notInternet();
                return;
            }

            if( isLogged() ){

                mCoupon.getFavorites(mUser.getSes(), new ServerCallback() {
                    @Override
                    public void onSuccess(String result) {
                        Gson mGson = new Gson();
                        RecomendadosRes mFavoritosRes = mGson.fromJson(result, RecomendadosRes.class);
                        if( mFavoritosRes.getErr() == 0 ){
                            Intent intent = new Intent(mContext, HomeActivity.class);
                            startActivity(intent);
                            finish();
                        }else{
                            Intent intent = new Intent(mContext, LoginActivity.class);
                            startActivity(intent);
                            finish();
                        }
                    }
                    @Override
                    public void onError(VolleyError err) {
                        Toast.makeText(mContext, "Error de conexion!", Toast.LENGTH_SHORT).show();
                    }
                });
            }else{
                Intent intent = new Intent(mContext, LoginActivity.class);
                startActivity(intent);
                finish();
            }


        }else{
            showAlert();
        }
    }

    private boolean isGPSEnabled(){
        return locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER) ||
                locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER);
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        checkGPS();
    }

    private void showAlert() {
        final AlertDialog.Builder dialog = new AlertDialog.Builder(this);
        dialog.setTitle("Activar la ubicación!")
                .setMessage("Su ubicación esta desactivada.\npor favor active su ubicación " +
                        "para que BwigoMobile le muestre los canjes que estan cercanos a usted.")
                .setPositiveButton("Configuración de ubicación", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface paramDialogInterface, int paramInt) {
                        Intent myIntent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                        startActivity(myIntent);
                    }
                });
        dialog.show();
    }

    private void notInternet(){
        final AlertDialog.Builder dialog = new AlertDialog.Builder(this);
        dialog.setTitle("Upps no hay internet!")
                .setCancelable(false)
                .setMessage("Al parecer no estas conectado a una coneccion de internet estable," +
                        " porfavor conoectate a un WI-FI o enciende tus datos móviles e intenta ingresar" +
                        "una vez más.")
                .setPositiveButton("ok", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface paramDialogInterface, int paramInt) {
                        finish();
                    }
                });
        dialog.show();
    }


    public Boolean isOnlineNet() {

        try {
            Process p = java.lang.Runtime.getRuntime().exec("ping -c 1 www.google.es");

            int val           = p.waitFor();
            boolean reachable = (val == 0);
            return reachable;

        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return false;
    }



    private boolean isLogged(){
        if(mUser.isLoged()){




            LatLng mLocation = new LatLng(mGps.getLatitude(), mGps.getLongitude());
            mUser.setLocation(mLocation);
            return true;
        }else{
            return false;
        }
    }


    private void showAlertPermission(){
        // Should we show an explanation?
        if (ActivityCompat.shouldShowRequestPermissionRationale(this,
                Manifest.permission.ACCESS_FINE_LOCATION)) {

            // Show an explanation to the user *asynchronously* -- don't block
            // this thread waiting for the user's response! After the user
            // sees the explanation, try again to request the permission.
            new AlertDialog.Builder(this)
                    .setTitle("Permiso para usar el GPS")
                    .setMessage("Permitenos hacer uso del GPS de tu smartphone para poderte brindar una mejor experiencia de usuario.")
                    .setPositiveButton("ok", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            //Prompt the user once explanation has been shown
                            ActivityCompat.requestPermissions(SplashActivity.this,
                                    new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                                    MY_PERMISSIONS_REQUEST_LOCATION);
                        }
                    })
                    .create()
                    .show();


        } else {
            // No explanation needed, we can request the permission.
            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                    MY_PERMISSIONS_REQUEST_LOCATION);
        }
    }

    public boolean checkLocationPermission() {
        if (ContextCompat.checkSelfPermission(this,
                Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {


            return false;
        } else {
            return true;
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           String permissions[], int[] grantResults) {
        switch (requestCode) {
            case MY_PERMISSIONS_REQUEST_LOCATION: {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                    // permission was granted, yay! Do the
                    // location-related task you need to do.
                    if (ContextCompat.checkSelfPermission(this,
                            Manifest.permission.ACCESS_FINE_LOCATION)
                            == PackageManager.PERMISSION_GRANTED) {

                        mGps = new GPSTracker(mContext);


                        if ( !isOnlineNet() ){
                            notInternet();
                            return;
                        }

                        if( isLogged() ){
                            Intent intent = new Intent(mContext, HomeActivity.class);
                            startActivity(intent);
                            finish();
                        }else{
                            Intent intent = new Intent(mContext, LoginActivity.class);
                            startActivity(intent);
                            finish();
                        }
                    }

                } else {

                    checkLocationPermission();

                }
                return;
            }

        }
    }

}
