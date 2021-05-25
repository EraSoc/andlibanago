package com.link2loyalty.bwigomdlib;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.location.Location;
import android.location.LocationManager;
import android.os.Build;

import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import androidx.appcompat.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.android.volley.VolleyError;
import com.google.android.gms.common.api.GoogleApiClient;


import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.gson.Gson;
import com.link2loyalty.bwigomdlib.R;
import com.link2loyalty.bwigomdlib.api.ServerCallback;
import com.link2loyalty.bwigomdlib.models2.Coupon;
import com.link2loyalty.bwigomdlib.models2.User;
import com.link2loyalty.bwigomdlib.models2.coupon.FilteredRes;
import com.link2loyalty.bwigomdlib.models2.coupon.LovFiltered;
import com.link2loyalty.bwigomdlib.models2.user.ValorMultitenancy;

import java.util.ArrayList;
import java.util.List;

public class MapActivity extends AppCompatActivity implements OnMapReadyCallback, GoogleMap.OnMarkerClickListener {


    private TextView tvCategory, tvDiscount, tvAddress, tvDistance;
    private Button btnViewMore;
    private LinearLayout llInfo;

    private String title = "Mapa";
    private Toolbar myToolbar;
    private GoogleMap mMap;

    public static final int MY_PERMISSIONS_REQUEST_LOCATION = 99;
    LocationManager locationManager;
    String provider;
    private GoogleApiClient googleApiClient;

    private User mUser;
    private Coupon mCoupon;
    private Context mContext;
    private List<LovFiltered> mSucursales = new ArrayList<>();
    private int selectedCouponId = 0;

    private ValorMultitenancy mMultitenancy;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map);
        mContext = getApplicationContext();

        mUser = new User(mContext);
        mCoupon = new Coupon(mContext);

        mMultitenancy = mUser.getMultitenancy();

        findViews();
        configToolbar();


        // Get the SupportMapFragment and request notification
        // when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        //find viewa
        llInfo = findViewById(R.id.ll_info);
        tvCategory = findViewById(R.id.tv_category);
        tvDiscount = findViewById(R.id.tv_discount);
        tvAddress = findViewById(R.id.tv_address);
        tvDistance = findViewById(R.id.tv_distance);

        btnViewMore = findViewById(R.id.btn_view_more);
        btnViewMore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MapActivity.this, ShowCouponActivity.class);
                intent.putExtra("couponId", String.valueOf(selectedCouponId));
                intent.putExtra("fromact","mapa");
                startActivity(intent);
            }
        });


    }

    private void configToolbar() {
        setSupportActionBar(myToolbar);
        getSupportActionBar().setTitle(title);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        //Config multitenancy
        if (mMultitenancy.getColpri() != null) {
            myToolbar.setBackgroundColor(Color.parseColor("#"+mMultitenancy.getColpri()));
            //myToolbar.setTitle( mMultitenancy.getDes() );
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                Window window = getWindow();
                window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
                window.setStatusBarColor(Color.parseColor("#"+mMultitenancy.getColosc()));
            }
        }
    }

    private void findViews() {
        myToolbar = findViewById(R.id.tb_map);
    }


    public static Location getBestLastKnownLocation(Context context) {
        LocationManager locationManager = (LocationManager) context.getSystemService(Context.LOCATION_SERVICE);
        List<String> providers = locationManager.getAllProviders();
        Location bestLocation = null;

        for (String provider : providers) {
            try {
                Location location = locationManager.getLastKnownLocation(provider);
                if (bestLocation == null || location != null
                        && location.getElapsedRealtimeNanos() > bestLocation.getElapsedRealtimeNanos()
                        && location.getAccuracy() > bestLocation.getAccuracy())
                    bestLocation = location;
            } catch (SecurityException ignored) {
            }
        }

        return bestLocation;
    }


    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        //mMap.setMinZoomPreference((float) 12.0);
        //mMap.setMaxZoomPreference((float) 20.0);
        // Add a marker in Sydney, Australia,
        // and move the map's camera to the same location.
        LatLng pos = new LatLng(mUser.getLat(), mUser.getLng());
        //mMap.addMarker(new MarkerOptions().position(pos)
        //        .title("My position"));
        //mMap.moveCamera(CameraUpdateFactory.newLatLng(pos));
        mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(pos, 15.0f));
        mMap.setOnMarkerClickListener(this);
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
        mMap.setMyLocationEnabled(true);


        mMap.setOnMapClickListener(new GoogleMap.OnMapClickListener() {
            @Override
            public void onMapClick(LatLng latLng) {
                llInfo.setVisibility(View.INVISIBLE);
            }
        });


        getSucursales("50000", String.valueOf(mUser.getLat()), String.valueOf(mUser.getLng()));

    }

    void getSucursales(String kms, String lat, String lng) {
        Log.d("pepe", "Hacer el response kms: "+kms);
        mCoupon.buscar(mUser.getSes(), "", "", "", "", "", "", "", kms, "", lng, lat, "", new ServerCallback() {
            @Override
            public void onSuccess(String result) {
                Log.d("pepe", "res: "+result);
                Gson mGson = new Gson();
                FilteredRes mFavoritosRes = mGson.fromJson(result, FilteredRes.class);
                if( mFavoritosRes.getErr() == 0 ){
                    mSucursales = mFavoritosRes.getLov();
                    for (LovFiltered mSucursal : mSucursales) {
                        renderMarker(new LatLng(Double.valueOf(mSucursal.getLat()), Double.valueOf(mSucursal.getLon())), mSucursal.getAli() , mSucursal );
                    }
                }
            }

            @Override
            public void onError(VolleyError err) {

            }
        });




    }

    private void renderMarker(LatLng pos, String title, LovFiltered sucursal) {
        MarkerOptions mMarkerOptions = new MarkerOptions();
        mMarkerOptions.title(title);
        mMarkerOptions.position(pos);

        switch (sucursal.getCat()){
            case "Alimentos y Bebidas":
                mMarkerOptions.icon(BitmapDescriptorFactory.fromResource(R.drawable.ic_1_map));
                break;
            case "Salud y Belleza":
                mMarkerOptions.icon(BitmapDescriptorFactory.fromResource(R.drawable.ic_2_map));
                break;
            case "Casa y Hogar":
                mMarkerOptions.icon(BitmapDescriptorFactory.fromResource(R.drawable.ic_3_map));
                break;
            case "Entretenimiento":
                mMarkerOptions.icon(BitmapDescriptorFactory.fromResource(R.drawable.ic_4_map));
                break;
            case "Especialidad":
                mMarkerOptions.icon(BitmapDescriptorFactory.fromResource(R.drawable.ic_5_map));
                break;
            case "Ropa Accesorios":
                mMarkerOptions.icon(BitmapDescriptorFactory.fromResource(R.drawable.ic_6_map));
                break;
            case "Viajes":
                mMarkerOptions.icon(BitmapDescriptorFactory.fromResource(R.drawable.ic_7_map));
                break;
            case "Educación":
                mMarkerOptions.icon(BitmapDescriptorFactory.fromResource(R.drawable.ic_8_map));
                break;
        }


        mMap.addMarker(mMarkerOptions).setTag(sucursal);
        //Log.d("pepe", "Agregar el marker!!!");

    }



    public void checkLocationPermission() {

        // Here, thisActivity is the current activity
        if (ContextCompat.checkSelfPermission(MapActivity.this,
                Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {

            // Should we show an explanation?
            if (ActivityCompat.shouldShowRequestPermissionRationale(MapActivity.this,
                    Manifest.permission.ACCESS_FINE_LOCATION)) {

                // Show an expanation to the user *asynchronously* -- don't block
                // this thread waiting for the user's response! After the user
                // sees the explanation, try again to request the permission.

            } else {

                // No explanation needed, we can request the permission.

                ActivityCompat.requestPermissions(MapActivity.this,
                        new String[]{Manifest.permission.ACCESS_FINE_LOCATION},1);

                // MY_PERMISSIONS_REQUEST_READ_CONTACTS is an
                // app-defined int constant. The callback method gets the
                // result of the request.
            }
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

                        //Request location updates:
                        //locationManager.requestLocationUpdates(provider, 400, 1, this);
                    }

                } else {

                    // permission denied, boo! Disable the
                    // functionality that depends on this permission.

                }
                return;
            }

        }
    }

    @Override
    public boolean onMarkerClick(Marker marker) {

        llInfo.setVisibility(View.VISIBLE);
        LovFiltered mSucursal = (LovFiltered) marker.getTag();
        tvCategory.setText( mSucursal.getAli() );
        tvAddress.setText( mSucursal.getCat() );
        tvDiscount.setText( mSucursal.getDesCor() );
        tvDistance.setText( mSucursal.getKm() );
        selectedCouponId = Integer.valueOf(mSucursal.getClvPro());


        return false;
    }


}
