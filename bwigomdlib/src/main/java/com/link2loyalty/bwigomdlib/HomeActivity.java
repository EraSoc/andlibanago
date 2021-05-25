package com.link2loyalty.bwigomdlib;

import android.Manifest;
import android.app.Activity;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.location.LocationManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.navigation.NavigationView;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AlertDialog;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.core.view.GravityCompat;
import androidx.core.view.MenuItemCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.appcompat.widget.PopupMenu;
import androidx.recyclerview.widget.RecyclerView;
import androidx.appcompat.widget.SearchView;
import androidx.appcompat.widget.Toolbar;

import android.provider.Settings;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.VolleyError;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.google.android.gms.maps.model.LatLng;
import com.google.gson.Gson;
import com.link2loyalty.bwigomdlib.R;
import com.link2loyalty.bwigomdlib.adapters.CouponAdapter;
import com.link2loyalty.bwigomdlib.adapters.HighlightCouponAdapter;
import com.link2loyalty.bwigomdlib.api.ServerCallback;
import com.link2loyalty.bwigomdlib.fragments.LinkFacebookFragment;
import com.link2loyalty.bwigomdlib.helpers.AnuncioDialog;
import com.link2loyalty.bwigomdlib.helpers.GPSTracker;
import com.link2loyalty.bwigomdlib.helpers.GpsServiceTracker;
import com.link2loyalty.bwigomdlib.interfaces.CouponsRecyclerViewOnItemClickListener;
import com.link2loyalty.bwigomdlib.models.CategoryModel;
import com.link2loyalty.bwigomdlib.models2.Anuncio;
import com.link2loyalty.bwigomdlib.models2.Coupon;
import com.link2loyalty.bwigomdlib.models2.ResAnuncios;
import com.link2loyalty.bwigomdlib.models2.User;
import com.link2loyalty.bwigomdlib.models2.coupon.RecomendadosRes;
import com.link2loyalty.bwigomdlib.models2.login.Valor;
import com.link2loyalty.bwigomdlib.models2.user.UserDetRes;
import com.link2loyalty.bwigomdlib.models2.user.UserLov;
import com.link2loyalty.bwigomdlib.models2.user.ValorMultitenancy;
import com.snappydb.DB;
import com.snappydb.DBFactory;
import com.snappydb.SnappydbException;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.List;

public class HomeActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    private String title = "Bwigo mobile";
    private ImageView ivProfile;
    private TextView tvCoupons, tvName, tvBasico;
    private FloatingActionButton fabAlimentos, fabSalud, fabCasa, fabEntretenimiento, fabEspecialidad,
                                fabRopa, fabViajes, fabEducacion;
    public static Context mContext;
    private RecyclerView rvRecomendos, rvDestacados;
    private User mUser;
    private Coupon mCoupon;
    private GPSTracker mGps;
    LocationManager locationManager;

    private ValorMultitenancy mMultitenancy;

    private AnuncioDialog anuncioDialog = null;

    public static final int MY_PERMISSIONS_REQUEST_LOCATION = 99;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle(title);
        mContext = getApplicationContext();
        mUser = new User(mContext);
        mCoupon = new Coupon(mContext);
        Intent intent2 = getIntent();
        boolean showFace = intent2.getBooleanExtra("SHOW_FACEBOOK", false);
        anuncioDialog = new AnuncioDialog();
        if(showFace){
            showDialog();
        }
        locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        checkGPS();


        mMultitenancy = mUser.getMultitenancy();
        Log.i("tenancy...","usr:::"+mMultitenancy.getColpri());
        //Config multitenancy
        if( mMultitenancy != null ){
            toolbar.setBackgroundColor(Color.parseColor( "#"+mMultitenancy.getColpri() ));
            toolbar.setTitle( mMultitenancy.getDes() );
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                Window window = getWindow();
                window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
                window.setStatusBarColor(Color.parseColor( "#"+mMultitenancy.getColosc() ));
            }
        }

        toolbar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                HomeActivity.super.onBackPressed();
            }
        });
        //findViews
        rvRecomendos = findViewById(R.id.rv_recomendados);
        rvDestacados = findViewById(R.id.rv_destacados);

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        navigationView.setItemIconTintList(null);


        Valor valor = mUser.getLoginDetails();
        Menu nav_menu = navigationView.getMenu();
        if( valor.getSart() != 1 ){
            nav_menu.findItem( R.id.nav_art ).setVisible( false );
        }





        //Config the header
        View header = navigationView.getHeaderView(0);
        ivProfile = header.findViewById(R.id.iv_profile);

        ivProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                /*anago modificaciones*/
/*                Intent intent = new Intent(HomeActivity.this, ProfileActivity.class);
                startActivity(intent);*/
                /*end anago modificacion*/
            }
        });

        Log.d("pepe", "=======SET IMAGE======");
        Log.d("pepe", "==================");

        /*if( mUser.getImage() != null ){

            Glide.with(HomeActivity.this)
                    .load(mUser.getImage())
                    .apply(RequestOptions.circleCropTransform())
                    .into(ivProfile);
        }*/



        tvCoupons = header.findViewById(R.id.tv_header_coupons);
        tvName    = header.findViewById(R.id.tv_header_name);
        tvBasico  = header.findViewById(R.id.tv_header_basico);

        Valor loginValue = mUser.getLoginDetails();

        if (loginValue.getCupred() != 0) {
            tvCoupons.setText(loginValue.getCupred() +" cupones" );
        }else{
            tvCoupons.setText("");
            tvBasico.setText("");
        }





        //-- TODO: Mostrar la pantalla de ligar cuenta a facebook
        /*if( mUser.getLoginFacebookCount() < 3 ) {
            int count = mUser.getLoginFacebookCount();
            count  = count +1;
            mUser.setLoginFacebookCount(count);
            showDialog();
        }*/
        //startfirebasetoken();

/*
        Intent intent=new Intent(getBaseContext(), MyLocationService.class);
        startService(intent);*/



        /*Intent servx=new Intent(getBaseContext(), GpsServiceTracker.class);
        startService(servx);*/

    }

    /*start firebase token*/
    public void startfirebasetoken(){

        try {
            DB snappydb = DBFactory.open( getApplicationContext() ); //create or open an existing database using the default name

            String 	 token   =  snappydb.get("token");
            snappydb.close();
            Log.i("login success....","ok"+token);
            String nom = mUser.getLoginDetails().nom;
            String apa = mUser.getLoginDetails().apa;
            String ama = mUser.getLoginDetails().ama;
            String ema = mUser.getLoginDetails().ema;

                mUser.saveDevIdFirebase(mUser.getSes(),token,new ServerCallback() {
                    @Override
                    public void onSuccess(String result) {
                        Log.i("saved token success....","ok"+mUser.getSes());
                    }

                    @Override
                    public void onError(VolleyError err) {
                        Toast.makeText(HomeActivity.this, "Error de red token!", Toast.LENGTH_LONG).show();
                    }
                });



        } catch (SnappydbException e) {
        }

    }

    /*permission for gps 12/20*/
    private boolean isGPSEnabled(){
        return locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER) ||
                locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER);
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
                            ActivityCompat.requestPermissions(HomeActivity.this,
                                    new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                                    MY_PERMISSIONS_REQUEST_LOCATION);
                        }
                    })
                    .create()
                    .show();

            doothershits();

        } else {
            // No explanation needed, we can request the permission.
            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                    MY_PERMISSIONS_REQUEST_LOCATION);
        }
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
    private boolean isLogged(){
        if(mUser.isLoged()){




            LatLng mLocation = new LatLng(mGps.getLatitude(), mGps.getLongitude());
            mUser.setLocation(mLocation);
            return true;
        }else{
            return false;
        }
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

    private void doothershits(){

        mUser.getDetailsRes(mUser.getSes(), new ServerCallback() {
            @Override
            public void onSuccess(String response) {

                Gson mGson = new Gson();
                UserDetRes mUserDetailRes = mGson.fromJson(response, UserDetRes.class);

                Log.d("pepe", "=======SET IMAGE======");
                Log.d("pepe", String.valueOf(mUserDetailRes.getErr()));
                Log.d("pepe", "==================");

                if( mUserDetailRes.getErr() == 0 ){
                    UserLov mUserDetail = mUserDetailRes.getLov().get(0);
                    mUser.setDetails(mUserDetail);
                    UserLov mUserDetails = mUser.getDetails();

                    tvName.setText(mUserDetails.getNom());


                    configFabs();
                    mGps = new GPSTracker(mContext);
                    LatLng mLocation = new LatLng(mGps.getLatitude(), mGps.getLongitude());
                    mUser.setLocation(mLocation);
                    loadHighlightsCoupons();
                    loadRecommendedCoupons();
                }else{
                    mUser.doLogout();
                    Intent intent = new Intent(HomeActivity.this, LoginActivity.class);
                    startActivity(intent);
                    finish();
                }


            }
            @Override
            public void onError(VolleyError error) {
                // do stuff here
                //Toast.makeText(mContext, "Error de conexion!", Toast.LENGTH_SHORT).show();
                mUser.doLogout();
                Intent intent = new Intent(HomeActivity.this, LoginActivity.class);
                startActivity(intent);
                finish();
            }
        });

        mUser.getAnuncios( mUser.getSes(), new ServerCallback() {
            @Override
            public void onSuccess(String result) {
                Gson mGson = new Gson();
                ResAnuncios mResAnuncios = mGson.fromJson(result, ResAnuncios.class);

                if( mResAnuncios.getLov().size() > 0 ){
                    final Anuncio anuncio = mResAnuncios.getLov().get(0);
                    if( mUser.getIdAnuncio() != anuncio.getId() ){
                        mUser.resetIdAnuncio();
                    }
                    if( mUser.getVisualizacionesAnuncio() <= anuncio.getRep() ){
                        // if( mUser.getVisualizacionesAnuncio() <= anuncio.getRep() ){
                        Log.d("numRep (al memento): ", String.valueOf(mUser.getVisualizacionesAnuncio()));
                        Log.d("numRep (por hacer): ", String.valueOf(anuncio.getRep()));
                        anuncioDialog.showAnuncio(HomeActivity.this, anuncio, new AnuncioDialog.OnAnuncioClicked() {
                            @Override
                            public void goCoupon() {
                                // Intent intent = new Intent(HomeActivity.this, ShowCouponActivity.class);
                                // intent.putExtra("couponId", Integer.valueOf(anuncio.getIdPro()));
                                // startActivity(intent);
                                String url = anuncio.getUrl();
                                if (!url.startsWith("http://") && !url.startsWith("https://"))
                                    url = "http://" + url;
                                Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
                                startActivity(browserIntent);
                                anuncioDialog.hideAnuncio();
                            }
                        });
                        mUser.registrarVisualizacion();
                        mUser.registrarIdVisualizacion(anuncio.getId());
                    }
                }

            }

            @Override
            public void onError(VolleyError err) {

            }
        });

    }
    private void checkGPS(){
        if( isGPSEnabled() ){


            if( !checkLocationPermission() ){
                showAlertPermission();
                return;
            }

            mGps = new GPSTracker(mContext);

            doothershits();

            if ( !isOnlineNet() ){
                notInternet();
                return;
            }


        }else{
            showAlert();
        }
    }
    /**/

    public void showDialog() {

        FragmentManager fragmentManager = getSupportFragmentManager();
        LinkFacebookFragment newFragment = new LinkFacebookFragment();

        // The device is smaller, so show the fragment fullscreen
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        // For a little polish, specify a transition animation
        transaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
        // To make it fullscreen, use the 'content' root view as the container
        // for the fragment, which is always the root view for the activity
        transaction.add(R.id.main_home, newFragment)
                .addToBackStack(null).commit();
    }

    /**
     * Este es el de alianzas O.o
     * */

    private void getRecomendados(){

        mCoupon.getAlianzas(mUser.getSes(), "0", "0","0", mUser.getLat(), mUser.getLng(),"0", new ServerCallback() {
            @Override
            public void onSuccess(String result) {
                Gson mGson = new Gson();
                RecomendadosRes mRecomendadosRes = mGson.fromJson(result, RecomendadosRes.class);
                if( mRecomendadosRes.getErr() == 0 ){
                    final List<com.link2loyalty.bwigomdlib.models2.coupon.LovRecomendados> mItems = mRecomendadosRes.getLov();
                    CouponAdapter couponAdapter = new CouponAdapter(mItems, mContext,
                            new CouponsRecyclerViewOnItemClickListener() {
                                @Override
                                public void onClick(View v, int position) {
                                    if( v.getId() == R.id.cv_coupon ){
                                        com.link2loyalty.bwigomdlib.models2.coupon.LovRecomendados c = new com.link2loyalty.bwigomdlib.models2.coupon.LovRecomendados();
                                        c = mItems.get(position);
                                        Intent intent = new Intent(HomeActivity.this, ShowCouponActivity.class);
                                        intent.putExtra("couponId",c.getClvPro());
                                        intent.putExtra("fromact","home");
                                        startActivity(intent);
                                        //Toast.makeText(HomeActivity.this, "cupon: "+ recommendedCoupons.get(position).getStore(), Toast.LENGTH_SHORT).show();
                                    }else if(v.getId() == R.id.btn_coupon){
                                        Toast.makeText(HomeActivity.this, "btn: "+ mItems.get(position).getAli() , Toast.LENGTH_SHORT).show();
                                    }
                                }
                            });
                    rvRecomendos.setAdapter(couponAdapter);
                }else{
                    Toast.makeText(mContext, mRecomendadosRes.getMen(), Toast.LENGTH_SHORT).show();
                }
            }
            @Override
            public void onError(VolleyError err) {
                Toast.makeText(mContext, "Error de conexion!", Toast.LENGTH_SHORT).show();
            }
        });

    }

    /**
     * Este es el de recomendados o.O
     * */
    private void getDestacados(){

        mCoupon.getRecomendados(mUser.getSes(), "0", "0", "0", String.valueOf(mUser.getLat()), String.valueOf(mUser.getLng()),"0",new ServerCallback() {
            @Override
            public void onSuccess(String result) {
                Gson mGson = new Gson();
                RecomendadosRes mRecomendadosRes = mGson.fromJson(result, RecomendadosRes.class);
                if( mRecomendadosRes.getErr() == 0 ){
                    final List<com.link2loyalty.bwigomdlib.models2.coupon.LovRecomendados> mItems = mRecomendadosRes.getLov();
                    HighlightCouponAdapter couponAdapter = new HighlightCouponAdapter(mItems, mContext,
                            new CouponsRecyclerViewOnItemClickListener() {
                                @Override
                                public void onClick(View v, int position) {
                                    if( v.getId() == R.id.cv_coupon_des ){
                                        com.link2loyalty.bwigomdlib.models2.coupon.LovRecomendados c = new com.link2loyalty.bwigomdlib.models2.coupon.LovRecomendados();
                                        c = mItems.get(position);
                                        Intent intent = new Intent(HomeActivity.this, ShowCouponActivity.class);
                                        intent.putExtra("couponId",c.getClvPro() );
                                        intent.putExtra("fromact","home");
                                        startActivity(intent);
                                        //Toast.makeText(HomeActivity.this, "cupon: "+ recommendedCoupons.get(position).getStore(), Toast.LENGTH_SHORT).show();
                                    }else if(v.getId() == R.id.btn_coupon){
                                        Toast.makeText(HomeActivity.this, "btn: "+ mItems.get(position).getAli() , Toast.LENGTH_SHORT).show();
                                    }
                                }
                            });
                    rvDestacados.setAdapter(couponAdapter);
                }else{
                    Toast.makeText(mContext, mRecomendadosRes.getMen(), Toast.LENGTH_SHORT).show();
                }
            }
            @Override
            public void onError(VolleyError err) {
                Toast.makeText(mContext, "Error de conexion!", Toast.LENGTH_SHORT).show();
            }
        });

    }


    private void loadRecommendedCoupons() {
        /* RECOMENDADOS */
        rvRecomendos.setHasFixedSize(true);
        LinearLayoutManager llm = new LinearLayoutManager(HomeActivity.this, LinearLayoutManager.VERTICAL, false);
        rvRecomendos.setLayoutManager(llm);
        getRecomendados();

    }

    private void loadHighlightsCoupons() {
        /* DESTACADOS */
        rvDestacados.setHasFixedSize(true);
        LinearLayoutManager llm = new LinearLayoutManager(HomeActivity.this, LinearLayoutManager.HORIZONTAL, false);
        rvDestacados.setLayoutManager(llm);
        getDestacados();
    }


    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    public String loadJSONFromAsset(Context context, String fileName) {
        String json = null;
        try {
            InputStream is = context.getAssets().open(fileName);
            int size = is.available();
            byte[] buffer = new byte[size];
            is.read(buffer);
            is.close();
            json = new String(buffer, StandardCharsets.UTF_8);
        } catch (IOException ex) {
            ex.printStackTrace();
            return null;
        }
        return json;

    }

    private void showPopupMenu(View view,int position) {
        // inflate menu
        PopupMenu popup = new PopupMenu(view.getContext(),view );
        MenuInflater inflater = popup.getMenuInflater();
        inflater.inflate(R.menu.menu_popup, popup.getMenu());
        popup.setOnMenuItemClickListener(new MyMenuItemClickListener(position));
        popup.show();
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.home, menu);
        MenuItem searchViewItem = menu.findItem(R.id.action_search);
        final SearchView searchViewAndroidActionBar = (SearchView) MenuItemCompat.getActionView(searchViewItem);
        searchViewAndroidActionBar.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                searchViewAndroidActionBar.clearFocus();
                //Toast.makeText(HomeActivity.this, query, Toast.LENGTH_LONG).show();


                Intent intent = new Intent(HomeActivity.this, SearchedActivity.class);
                intent.putExtra("CADENA", query);
                startActivity(intent);





                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        //noinspection SimplifiableIfStatement
        if (id == R.id.action_search) {
            //Toast.makeText(this, "Hacer la busqueda...", Toast.LENGTH_SHORT).show();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_home) {
            //Intent intent = new Intent(this, HomeActivity.class);
            //startActivity(intent);
        } else if (id == R.id.nav_coupons) {
            Intent intent = new Intent(this, CouponActivity.class);
            startActivity(intent);
        } else if (id == R.id.nav_map) {
            Intent intent = new Intent(this, MapActivity.class);
            startActivity(intent);
        /*} else if (id == R.id.nav_rewards) {
            Intent intent = new Intent(this, RewardsActivity.class);
            startActivity(intent);*/
        } else if (id == R.id.nav_fac) {
            Intent intent = new Intent(this, FaqActivity.class);
            startActivity(intent);
        } else if( id == R.id.nav_settings ){
            Intent intent = new Intent(this, SettingsActivity.class);
            startActivity(intent);
        /*} else if( id == R.id.nav_facebook ){
            goToFacebook();
        }else if( id == R.id.nav_twitter ){
            goToTwitter();
        }else if( id == R.id.nav_instagram ){
            goToInstagram();*/
        }else if( id == R.id.nav_art ){
            Intent intent = new Intent(this, ArticleActivity.class);
            startActivity(intent);
        }else if(id == R.id.nav_back){

            HomeActivity.super.onBackPressed();
        }

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    private void goToFacebook(){
        String FACEBOOK_URL = "https://www.facebook.com/BWIGO";
        String FACEBOOK_PAGE_ID = "BWIGO";
        String correctUrl = "";

        PackageManager packageManager = this.getPackageManager();
        try {
            int versionCode = packageManager.getPackageInfo("com.facebook.katana", 0).versionCode;
            if (versionCode >= 3002850) { //newer versions of fb app
                correctUrl = "fb://facewebmodal/f?href=" + FACEBOOK_URL;
            } else { //older versions of fb app
                correctUrl = "fb://page/" + FACEBOOK_PAGE_ID;
            }
        } catch (PackageManager.NameNotFoundException e) {
            correctUrl = FACEBOOK_URL; //normal web url
        }

        Intent facebookIntent = new Intent(Intent.ACTION_VIEW);
        String facebookUrl = correctUrl;
        facebookIntent.setData(Uri.parse(facebookUrl));
        startActivity(facebookIntent);

    }

    private void goToInstagram(){
        Uri uri = Uri.parse("http://instagram.com/_u/bwigomobile");
        Intent likeIng = new Intent(Intent.ACTION_VIEW, uri);

        likeIng.setPackage("com.instagram.android");

        try {
            startActivity(likeIng);
        } catch (ActivityNotFoundException e) {
            startActivity(new Intent(Intent.ACTION_VIEW,
                    Uri.parse("http://instagram.com/bwigomobile")));
        }

    }

    private void goToTwitter(){
        Intent intent = null;
        try {
            // get the Twitter app if possible
            this.getPackageManager().getPackageInfo("com.twitter.android", 0);
            intent = new Intent(Intent.ACTION_VIEW, Uri.parse("twitter://user?user_id=819902539"));
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        } catch (Exception e) {
            // no Twitter app, revert to browser
            intent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://twitter.com/Bwigo"));
        }
        this.startActivity(intent);

    }

    private void configFabs(){
        //find views in the xml
        fabAlimentos = findViewById(R.id.fab_alimentos);
        fabSalud = findViewById(R.id.fab_salud);
        fabCasa = findViewById(R.id.fab_casa);
        fabEntretenimiento = findViewById(R.id.fab_entretenimiento);
        fabEspecialidad = findViewById(R.id.fab_especialidad);
        fabRopa = findViewById(R.id.fab_ropa);
        fabViajes = findViewById(R.id.fab_viajes);
        fabEducacion = findViewById(R.id.fab_educacion);

        //set listeners
        fabAlimentos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                CategoryModel category = new CategoryModel();
                category.setName("ALIMENTOS Y BEBIDAS");
                category.setId("1");
                category.setColor("#F4511E");
                category.setIcon(R.drawable.ic_alimentos);
                Intent intent = new Intent(HomeActivity.this, CategoryActivity.class);
                intent.putExtra("CategoryModel", category );
                startActivity(intent);

            }
        });
        fabSalud.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                CategoryModel category = new CategoryModel();
                category.setName("SALUD Y BELLEZA");
                category.setId("2");
                category.setColor("#43A047");
                category.setIcon(R.drawable.ic_salud);
                Intent intent = new Intent(HomeActivity.this, CategoryActivity.class);
                intent.putExtra("CategoryModel", category );
                startActivity(intent);

            }
        });
        fabCasa.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                CategoryModel category = new CategoryModel();
                category.setName("CASA Y HOGAR");
                category.setId("3");
                category.setColor("#6D4C41");
                category.setIcon(R.drawable.ic_hogar);
                Intent intent = new Intent(HomeActivity.this, CategoryActivity.class);
                intent.putExtra("CategoryModel", category );
                startActivity(intent);

            }
        });
        fabEntretenimiento.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                CategoryModel category = new CategoryModel();
                category.setName("ENTRETENIMIENTO");
                category.setId("4");
                category.setColor("#FFD600");
                category.setIcon(R.drawable.ic_entretenimiento);
                Intent intent = new Intent(HomeActivity.this, CategoryActivity.class);
                intent.putExtra("CategoryModel", category );
                startActivity(intent);

            }
        });
        fabEspecialidad.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                CategoryModel category = new CategoryModel();
                category.setName("ESPECIALIDAD");
                category.setId("5");
                category.setColor("#5E35B1");
                category.setIcon(R.drawable.ic_especialidad);
                Intent intent = new Intent(HomeActivity.this, CategoryActivity.class);
                intent.putExtra("CategoryModel", category );
                startActivity(intent);

            }
        });
        fabRopa.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                CategoryModel category = new CategoryModel();
                category.setName("ROPA Y ACCESORIOS");
                category.setId("6");
                category.setColor("#D81B60");
                category.setIcon(R.drawable.ic_ropa);
                Intent intent = new Intent(HomeActivity.this, CategoryActivity.class);
                intent.putExtra("CategoryModel", category );
                startActivity(intent);

            }
        });
        fabViajes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                CategoryModel category = new CategoryModel();
                category.setName("VIAJES");
                category.setId("7");
                category.setColor("#00BCD4");
                category.setIcon(R.drawable.ic_viajes);
                Intent intent = new Intent(HomeActivity.this, CategoryActivity.class);
                intent.putExtra("CategoryModel", category );
                startActivity(intent);

            }
        });
        fabEducacion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                CategoryModel category = new CategoryModel();
                category.setName("EDUCACION");
                category.setId("8");
                category.setColor("#43A047");
                category.setIcon(R.drawable.ic_educacion);
                Intent intent = new Intent(HomeActivity.this, CategoryActivity.class);
                intent.putExtra("CategoryModel", category );
                startActivity(intent);

            }
        });

    }




    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        //callbackManager.onActivityResult(requestCode, resultCode, data);

        for (Fragment fragment : getSupportFragmentManager().getFragments()) {
            //System.out.println("@#@");
            fragment.onActivityResult(requestCode, resultCode, data);
        }

        super.onActivityResult(requestCode, resultCode, data);
    }

    private class MyMenuItemClickListener implements PopupMenu.OnMenuItemClickListener {

        private int position;
        public MyMenuItemClickListener(int positon) {
            this.position=positon;
        }

        @Override
        public boolean onMenuItemClick(MenuItem menuItem) {
            int itemId = menuItem.getItemId();
            if (itemId == R.id.Not_interasted_catugury) {
                Toast.makeText(HomeActivity.this, "Add to favourite", Toast.LENGTH_SHORT).show();
                return true;
            } else if (itemId == R.id.No_interasted) {
                Toast.makeText(HomeActivity.this, "Done for now", Toast.LENGTH_SHORT).show();
                return true;
            }
            return false;
        }
    }
}
