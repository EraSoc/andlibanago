package com.link2loyalty.bwigomdlib;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.location.Location;
import android.net.Uri;
import android.os.Build;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.model.BitmapDescriptor;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.appbar.AppBarLayout;
import com.google.android.material.appbar.CollapsingToolbarLayout;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import androidx.appcompat.widget.Toolbar;

import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.VolleyError;
import com.bumptech.glide.Glide;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.gson.Gson;
import com.link2loyalty.bwigomdlib.R;
import com.link2loyalty.bwigomdlib.api.ServerCallback;
import com.link2loyalty.bwigomdlib.api.models.AddFav;
import com.link2loyalty.bwigomdlib.helpers.GPSTracker;
import com.link2loyalty.bwigomdlib.models.CategoryModel;
import com.link2loyalty.bwigomdlib.models2.Coupon;
import com.link2loyalty.bwigomdlib.models2.User;
import com.link2loyalty.bwigomdlib.models2.coupon.DetailRes;
import com.link2loyalty.bwigomdlib.models2.coupon.Valor;
import com.link2loyalty.bwigomdlib.models2.sucursal.ResSucCupon;
import com.link2loyalty.bwigomdlib.models2.sucursal.SucCupon;
import com.link2loyalty.bwigomdlib.models2.user.ValorMultitenancy;
import com.snappydb.DB;
import com.snappydb.DBFactory;
import com.snappydb.SnappydbException;

import java.util.ArrayList;
import java.util.List;

public class ShowCouponActivity extends AppCompatActivity implements OnMapReadyCallback {

    private String title = "";
    private Valor coupon;

    private Menu menu;
    private boolean isFavorite = false;
    private TextView tvDiscountShort, tvDiscount, tvConditions;
    private FloatingActionButton fabRedimir;

    private ImageView ivCoupon;

    private ListView lvAddresses;

    private GoogleMap mMap;

    private int latlontienda;

    private int couponId;
    private String fromact;
    private Context mContext;

    private User mUser;
    private Coupon mCoupon;

    private List<SucCupon> mSucursales = new ArrayList<>();

    private ValorMultitenancy mMultitenancy;

    private AppBarLayout mAppBar;
    private CollapsingToolbarLayout mCollapsingToolbar;

    private String latx, lngx;
    private double mylat, mylng;
    private Uri intentmap;
    private SupportMapFragment mapFragment;

    private Bundle mybndl;

    @Override
    protected void onResume() {
        super.onResume();
        try {
            DB snappydb = DBFactory.open(mContext);
            couponId = snappydb.getInt("couponId");

            getCoupon();
        } catch (SnappydbException e) {
            e.printStackTrace();
        }

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_coupon);
        // To retrieve object in second Activity
        //coupon = (CouponModel) getIntent().getSerializableExtra("CouponModel");
        mybndl=getIntent().getExtras();

        mContext = getApplicationContext();
        couponId =Integer.parseInt(mybndl.getString("couponId")); //getIntent().getIntExtra("couponId", -1);
        Log.i("fromact....",""+mybndl.getString("fromact"));
        if(mybndl.getString("fromact")!=null){
            fromact = mybndl.getString("fromact");
        }else{
            fromact="home";
        }


        mCoupon = new Coupon(mContext);
        mUser = new User(mContext);
        mMultitenancy = mUser.getMultitenancy();

        try {
            DB snappydb = DBFactory.open(mContext);
            if (couponId != -1) {
                snappydb.putInt("couponId", couponId);
            }
           // Log.i("El coupon",""+couponId+"___idcupon_extras..."+mybndl.getInt("couponId",-1));

            getCoupon();
            //Find views
            mAppBar = findViewById(R.id.app_bar_layout);
            mCollapsingToolbar = findViewById(R.id.collapsing_toolbar);
            tvConditions = findViewById(R.id.tv_conditions);
            tvDiscount = findViewById(R.id.tv_discount);
            tvDiscountShort = findViewById(R.id.tv_discount_short);
            lvAddresses = findViewById(R.id.lv_addresses);
            fabRedimir = findViewById(R.id.fab_redimir);
            ivCoupon = findViewById(R.id.iv_coupon_detail);

            //config map
            mapFragment = (SupportMapFragment) getSupportFragmentManager()
                    .findFragmentById(R.id.map_coupon);
            mapFragment.getMapAsync(this);

            fabRedimir.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //Toast.makeText(ShowCouponActivity.this,"Redimir boton!", Toast.LENGTH_LONG).show();


                    Intent intent = new Intent(ShowCouponActivity.this, RedimirActivity.class);
                    intent.putExtra("couponId", coupon.getClvpro());
                    startActivity(intent);

                }
            });

        } catch (SnappydbException e) {
            e.printStackTrace();
        }

        /*intent maps*/

                    mylat=mUser.getLat();
                    mylng=mUser.getLng();





        /**/

    }

    private void execBtngoto(){
        //intentmap=Uri.parse("google.streetview:cbll="+latx+","+lngx);+latx+","+lngx
        intentmap=Uri.parse("https://maps.google.com/maps?saddr="+mylat+","+mylng+"&daddr="+latx+","+lngx);
        Intent mapint=new Intent(Intent.ACTION_VIEW,intentmap);
        mapint.setPackage("com.google.android.apps.maps");
        startActivity(mapint);
    }

    private void getCoupon(){

        mCoupon.getDetail(mUser.getSes(), String.valueOf(couponId) , new ServerCallback() {
            @Override
            public void onSuccess(String response) {
                Log.i("detalle de cupon","detail::::"+response);
                Gson mGson = new Gson();
                DetailRes mResponse = mGson.fromJson(response, DetailRes.class);
                coupon = mResponse.getValor();
                //title  = coupon.getLimusu();
                Glide.with(ShowCouponActivity.this).load(coupon.getCup()).placeholder(R.drawable.default_pleaceholder).into(ivCoupon);
                getSucCoupon(coupon.getClvpro());

            }
            @Override
            public void onError(VolleyError error) {
                // do stuff here
                Toast.makeText(mContext, "Error de conexion!", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void getSucCoupon(String coupinId){

        Log.i("recibiendo","cupon:::"+coupinId);
        mCoupon.getSucursales(mUser.getSes(), coupinId,mUser.getLat(),mUser.getLng(), new ServerCallback() {
            @Override
            public void onSuccess(String result) {
                Gson mGson = new Gson();
                ResSucCupon mResponse = mGson.fromJson(result, ResSucCupon.class);
                mSucursales = mResponse.getLov();
                //Log.i("itm....","tienda..."+mResponse.getLov().get(0).getLat()+"---"+mResponse.getLov().get(0).getLon());
                Log.i("itms.....","tiendas"+result);

                if(mResponse.getLov().size()<=0){
                    latlontienda=0;
                }else{
                    latlontienda=1;
                }


                mMap.clear();
                if( mResponse.getErr() != 0 ){
                    mSucursales.clear();
                }
                renderView();
                configToolbar();

            }

            @Override
            public void onError(VolleyError err) {

            }
        });


    }

    private void renderView() {

        //Config the list view
        final List<String> addressesList = new ArrayList<String>();
        //final ArrayList<Sucursal> sucursales = coupon.getSucursales();

        //Render coupon in view
        tvDiscountShort.setText(coupon.getDescor());
        tvDiscount.setText(coupon.getDeslar());
        tvConditions.setText(coupon.getTer());

        for (final SucCupon s : mSucursales) {
            addressesList.add(s.getDir());
        }
        if (latlontienda >= 1) {
            renderMapPosition(mSucursales.get(0));
        }
        // This is the array adapter, it takes the context of the activity as a
        // first parameter, the type of list view as a second parameter and your
        // array as a third parameter.
        if(latlontienda>=1){
        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(
                this,
                R.layout.addresses_show_coupon,
                addressesList);
        lvAddresses.setAdapter(arrayAdapter);
        // register onClickListener to handle click events on each item
        lvAddresses.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            // argument position gives the index of item which is clicked
            public void onItemClick(AdapterView<?> arg0, View v, int position, long arg3) {
                String selectedAddress = addressesList.get(position);
                SucCupon mSucursal = mSucursales.get(position);
                renderMapPosition(mSucursal);

//                Log.i("latlng on select event..",""+mylat+"::::lng:::--"+mylng);

                latx = mSucursal.getLat();
                lngx = mSucursal.getLon();
            }


        });
    }

    }



    private void configToolbar() {

        Toolbar toolbar = (Toolbar) findViewById(R.id.tb_show_coupon);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setTitle(title);
        //Config multitenancy
        if( mMultitenancy.getColpri() != null ){
            mAppBar.setBackgroundColor(Color.parseColor( "#"+mMultitenancy.getColpri() ));
            //myToolbar.setTitle( mMultitenancy.getDes() );
            mCollapsingToolbar.setBackgroundColor(Color.parseColor( "#"+mMultitenancy.getColpri() ));
            mCollapsingToolbar.setContentScrim( new ColorDrawable(Color.parseColor( "#"+mMultitenancy.getColpri() )) );

            toolbar.setBackgroundColor( Color.TRANSPARENT );
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                Window window = getWindow();
                window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
                window.setStatusBarColor(Color.parseColor( "#"+mMultitenancy.getColosc() ));
            }
        }

    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        try {
            DB snappydb = DBFactory.open(mContext);
            snappydb.del("couponId");
        } catch (SnappydbException e) {
            e.printStackTrace();
        }
        return true;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        this.menu = menu;
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_show_coupon, menu);

        //si es favorito
        if(coupon.getFav() == 0){
            menu.getItem(0).setIcon(ContextCompat.getDrawable(this, R.drawable.ic_heart_outline));
        }else{
            menu.getItem(0).setIcon(ContextCompat.getDrawable(this, R.drawable.ic_heart));

        }

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle item selection
        if (item.getItemId() == R.id.menu_add_favourite) {
            switchFavourite();
            return true;
        }
        if(item.getItemId()==android.R.id.home){
            ShowCouponActivity.super.onBackPressed();
           /* switch(fromact){
                case "home":
                    Intent home=new Intent(getApplicationContext(),HomeActivity.class);
                    home.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                    startActivity(home);
                    break;
                case "mapa":
                    Intent mapax=new Intent(getApplicationContext(),MapActivity.class);
                    mapax.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                    startActivity(mapax);
                    break;
                case "micupon":
                    Intent mcupon=new Intent(getApplicationContext(),CouponActivity.class);
                    mcupon.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                    startActivity(mcupon);
                    break;
                case "cat1":
                    Intent cat1=new Intent(getApplicationContext(),CategoryActivity.class);
                    CategoryModel category = new CategoryModel();
                    category.setName("ALIMENTOS Y BEBIDAS");
                    category.setId("1");
                    category.setColor("#F4511E");
                    category.setIcon(R.drawable.ic_alimentos);
                    cat1.putExtra("CategoryModel", category );
                    cat1.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                    startActivity(cat1);
                    break;
                case "cat2":
                    Intent cat2=new Intent(getApplicationContext(),CategoryActivity.class);
                    CategoryModel category2 = new CategoryModel();
                    category2.setName("SALUD Y BELLEZA");
                    category2.setId("2");
                    category2.setColor("#43A047");
                    category2.setIcon(R.drawable.ic_salud);
                    cat2.putExtra("CategoryModel", category2 );
                    cat2.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                    startActivity(cat2);
                    break;
                case "cat3":
                    Intent cat3=new Intent(getApplicationContext(),CategoryActivity.class);
                    CategoryModel category3 = new CategoryModel();
                    category3.setName("CASA Y HOGAR");
                    category3.setId("3");
                    category3.setColor("#6D4C41");
                    category3.setIcon(R.drawable.ic_hogar);
                    cat3.putExtra("CategoryModel", category3 );
                    cat3.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                    startActivity(cat3);
                    break;
                case "cat4":
                    Intent cat4=new Intent(getApplicationContext(),CategoryActivity.class);
                    CategoryModel category4 = new CategoryModel();
                    category4.setName("ENTRETENIMIENTO");
                    category4.setId("4");
                    category4.setColor("#FFD600");
                    category4.setIcon(R.drawable.ic_entretenimiento);
                    cat4.putExtra("CategoryModel", category4 );
                    cat4.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                    startActivity(cat4);
                    break;
                case "cat5":
                    Intent cat5=new Intent(getApplicationContext(),CategoryActivity.class);
                    CategoryModel category5 = new CategoryModel();
                    category5.setName("ESPECIALIDAD");
                    category5.setId("5");
                    category5.setColor("#5E35B1");
                    category5.setIcon(R.drawable.ic_especialidad);
                    cat5.putExtra("CategoryModel", category5 );
                    cat5.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                    startActivity(cat5);
                    break;
                case "cat6":
                    Intent cat6=new Intent(getApplicationContext(),CategoryActivity.class);
                    CategoryModel category6 = new CategoryModel();
                    category6.setName("ROPA Y ACCESORIOS");
                    category6.setId("6");
                    category6.setColor("#D81B60");
                    category6.setIcon(R.drawable.ic_ropa);
                    cat6.putExtra("CategoryModel", category6 );
                    cat6.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                    startActivity(cat6);
                    break;
                case "cat7":
                    Intent cat7=new Intent(getApplicationContext(),CategoryActivity.class);
                    CategoryModel category7 = new CategoryModel();
                    category7.setName("VIAJES");
                    category7.setId("7");
                    category7.setColor("#00BCD4");
                    category7.setIcon(R.drawable.ic_viajes);
                    cat7.putExtra("CategoryModel", category7 );
                    cat7.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                    startActivity(cat7);
                    break;
                case "cat8":
                    Intent cat8=new Intent(getApplicationContext(),CategoryActivity.class);
                    CategoryModel category8 = new CategoryModel();
                    category8.setName("EDUCACION");
                    category8.setId("8");
                    category8.setColor("#43A047");
                    category8.setIcon(R.drawable.ic_educacion);
                    cat8.putExtra("CategoryModel", category8 );
                    cat8.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                    startActivity(cat8);
                    break;
                default:
                    Intent deflt=new Intent(getApplicationContext(),HomeActivity.class);
                    deflt.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                    startActivity(deflt);
            }*/

        }

        return super.onOptionsItemSelected(item);
    }

    private void switchFavourite(){
        if( isFavorite ){
            mCoupon.addFavorite(mUser.getSes(), String.valueOf(coupon.getClvpro()), new ServerCallback() {
                @Override
                public void onSuccess(String result) {
                    Gson mGson = new Gson();
                    AddFav mAddFav = mGson.fromJson(result, AddFav.class);
                    if (mAddFav.getErr() == 0) {
                        Toast.makeText(mContext, mAddFav.getMen(), Toast.LENGTH_LONG).show();
                        menu.getItem(0).setIcon(ContextCompat.getDrawable(ShowCouponActivity.this, R.drawable.ic_heart_outline));
                        isFavorite = false;
                        Log.i("favoritos","agregado....addFavorite");
                    } else {
                        Toast.makeText(mContext, mAddFav.getMen(), Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onError(VolleyError err) {
                    Toast.makeText(mContext, "Error de conexion!", Toast.LENGTH_SHORT).show();
                }
            });

        }else{
            mCoupon.addFavorite(mUser.getSes(), String.valueOf(coupon.getClvpro()), new ServerCallback() {
                @Override
                public void onSuccess(String result) {
                    Gson mGson = new Gson();
                    AddFav mAddFav = mGson.fromJson(result, AddFav.class);
                    if (mAddFav.getErr() == 0) {
                        Toast.makeText(mContext, mAddFav.getMen(), Toast.LENGTH_LONG).show();
                        menu.getItem(0).setIcon(ContextCompat.getDrawable(ShowCouponActivity.this, R.drawable.ic_heart));
                        isFavorite = true;
                    } else {
                        Toast.makeText(mContext, mAddFav.getMen(), Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onError(VolleyError err) {
                    Toast.makeText(mContext, "Error de conexion!", Toast.LENGTH_SHORT).show();
                }
            });

        }
    }




    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        //Get the first sucursal
        //ArrayList<Sucursal> sucursales = coupon.getSucursales();
        //Sucursal sucursal = sucursales.get(0);
        // Add a marker in Sydney and move the camera
        //LatLng firstSucursal = new LatLng(Double.valueOf(sucursal.getLat()), Double.valueOf(sucursal.getLng()));
        //mMap.addMarker(new MarkerOptions().position(firstSucursal).title(coupon.getStore()));
        //mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(firstSucursal, 15));



    }

    private void renderMapPosition(SucCupon sucursal){
        Log.i("afterlat","----"+latlontienda);
        if(latlontienda==0){
            mapFragment.getView().setVisibility(View.INVISIBLE);
         }else {
            mapFragment.getView().setVisibility(View.VISIBLE);
            LatLng updateSucursal = new LatLng(Double.valueOf(sucursal.getLat()), Double.valueOf(sucursal.getLon()));

            BitmapDescriptor icon = BitmapDescriptorFactory.fromResource(R.drawable.ic_marker2);

            MarkerOptions markerOptions = new MarkerOptions().position(updateSucursal)

                    .icon(icon);


            mMap.addMarker(markerOptions); //(new MarkerOptions().position(updateSucursal).title( sucursal.getAli()  ));
            mMap.setOnMarkerClickListener(new GoogleMap.OnMarkerClickListener() {
                @Override
                public boolean onMarkerClick(Marker marker) {

                    execBtngoto();

                    return false;
                }
            });
            mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(updateSucursal, 15));
        }
    }

}
