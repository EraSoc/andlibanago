package com.link2loyalty.bwigomdlib;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import com.google.android.material.appbar.CollapsingToolbarLayout;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.appcompat.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.android.volley.VolleyError;
import com.google.gson.Gson;
import com.link2loyalty.bwigomdlib.R;
import com.link2loyalty.bwigomdlib.adapters.CouponAdapter;
import com.link2loyalty.bwigomdlib.adapters.HighlightCouponAdapter;
import com.link2loyalty.bwigomdlib.api.ServerCallback;
import com.link2loyalty.bwigomdlib.fragments.FilterFragment;
import com.link2loyalty.bwigomdlib.interfaces.CouponsRecyclerViewOnItemClickListener;
import com.link2loyalty.bwigomdlib.models.CategoryModel;
import com.link2loyalty.bwigomdlib.models2.Coupon;
import com.link2loyalty.bwigomdlib.models2.User;
import com.link2loyalty.bwigomdlib.models2.coupon.RecomendadosRes;

import java.util.List;

public class CategoryActivity extends AppCompatActivity implements FilterFragment.OnNewsItemSelectedListener{

    private String title = "El título";
    private Toolbar toolbar;
    private CategoryModel category;
    private Menu menu;
    private boolean isFavorite = false;
    private LinearLayout llHeader;
    private ImageView ivHeader;
    private CollapsingToolbarLayout collapsingToolbar;

    private RecyclerView rvRecomendos, rvDestacados;
    private Context mContext;
    private User mUser;
    private Coupon mCoupon;

    private int itemsRecomendadosCount = 0;
    private int itemsDestacadosCount = 0;

    CouponAdapter couponAdapterRecomendados;
    HighlightCouponAdapter couponAdapterDestacados;

    public static final int DIALOG_FRAGMENT = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_category);
        mContext = getApplicationContext();
        mUser = new User(mContext);
        mCoupon = new Coupon(mContext);
        // To retrieve object in second Activity
        category = (CategoryModel) getIntent().getSerializableExtra("CategoryModel");
        this.title = category.getName();
        configToolbar();
        //Config header
        rvRecomendos = findViewById(R.id.rv_category_recomendados);
        rvDestacados = findViewById(R.id.rv_category_destacados);
        collapsingToolbar = findViewById(R.id.collapsing_toolbar_category);
        collapsingToolbar.setContentScrimColor(Color.parseColor(category.getColor()));
        ivHeader = findViewById(R.id.iv_header);
        llHeader = findViewById(R.id.ll_category_header);
        ivHeader.setImageResource(category.getIcon());
        llHeader.setBackgroundColor(Color.parseColor(category.getColor()));
        toolbar.setBackgroundColor(Color.parseColor(category.getColor()));

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Window window = getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(Color.parseColor(category.getColor()));
        }


        loadHighlightsCoupons();
        loadRecommendedCoupons();


    }

    private void configToolbar() {
        toolbar = (Toolbar) findViewById(R.id.tb_category);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setTitle(title);
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        this.menu = menu;
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_category, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle item selection
        if (item.getItemId() == R.id.menu_filter) {
            FilterFragment newFragment = new FilterFragment();
            newFragment.show(getSupportFragmentManager(), "missiles");
            return true;
        }
        if(item.getItemId()==android.R.id.home){
            Intent home=new Intent(getApplicationContext(),HomeActivity.class);
            startActivity(home);
        }

        return super.onOptionsItemSelected(item);
    }


    private void getRecomendados(String km, String lat, String lon, String fec){

        mCoupon.getAlianzas(mUser.getSes(), "0", String.valueOf(category.getId()),km, mUser.getLat(), mUser.getLng(), fec, new ServerCallback() {
            @Override
            public void onSuccess(String result) {
                Gson mGson = new Gson();
                RecomendadosRes mRecomendadosRes = mGson.fromJson(result, RecomendadosRes.class);
                if( mRecomendadosRes.getErr() == 0 ){
                    final List<com.link2loyalty.bwigomdlib.models2.coupon.LovRecomendados> mItems = mRecomendadosRes.getLov();
                    itemsRecomendadosCount = mItems.size();
                    couponAdapterRecomendados = new CouponAdapter(mItems, mContext,
                            new CouponsRecyclerViewOnItemClickListener() {
                                @Override
                                public void onClick(View v, int position) {
                                    if( v.getId() == R.id.cv_coupon ){
                                        com.link2loyalty.bwigomdlib.models2.coupon.LovRecomendados c = new com.link2loyalty.bwigomdlib.models2.coupon.LovRecomendados();
                                        c = mItems.get(position);
                                        Intent intent = new Intent(CategoryActivity.this, ShowCouponActivity.class);
                                        intent.putExtra("couponId", c.getClvPro() );
                                        intent.putExtra("fromact","cat"+category.getId());
                                        startActivity(intent);
                                        //Toast.makeText(HomeActivity.this, "cupon: "+ recommendedCoupons.get(position).getStore(), Toast.LENGTH_SHORT).show();
                                    }else if(v.getId() == R.id.btn_coupon){
                                        Toast.makeText(CategoryActivity.this, "btn: "+ mItems.get(position).getAli() , Toast.LENGTH_SHORT).show();
                                    }
                                }
                            });
                    if(itemsRecomendadosCount > 0){
                        rvDestacados.removeAllViews();
                    }
                    rvRecomendos.setAdapter(couponAdapterRecomendados);
                    couponAdapterRecomendados.notifyDataSetChanged();
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

    private void getDestacados(String km, String lat, String lon, String fec){
        mCoupon.getDestacados(mUser.getSes(), "0", String.valueOf(category.getId()), km,lat,lon,fec,new ServerCallback() {
            @Override
            public void onSuccess(String result) {
                Gson mGson = new Gson();
                RecomendadosRes mRecomendadosRes = mGson.fromJson(result, RecomendadosRes.class);
                if( mRecomendadosRes.getErr() == 0 ){
                    final List<com.link2loyalty.bwigomdlib.models2.coupon.LovRecomendados> mItems = mRecomendadosRes.getLov();
                    itemsDestacadosCount = mItems.size();
                    couponAdapterDestacados = new HighlightCouponAdapter(mItems, mContext,
                            new CouponsRecyclerViewOnItemClickListener() {
                                @Override
                                public void onClick(View v, int position) {
                                    if( v.getId() == R.id.cv_coupon_des ){
                                        com.link2loyalty.bwigomdlib.models2.coupon.LovRecomendados c = new com.link2loyalty.bwigomdlib.models2.coupon.LovRecomendados();
                                        c = mItems.get(position);
                                        Intent intent = new Intent(CategoryActivity.this, ShowCouponActivity.class);
                                        intent.putExtra("couponId",c.getClvPro() );
                                        intent.putExtra("fromact","cat"+category.getId());
                                        startActivity(intent);
                                        //Toast.makeText(CategoryActivity.this, "cupon: "+ mItems.get(position).getAli(), Toast.LENGTH_SHORT).show();
                                    }else if(v.getId() == R.id.btn_coupon){
                                        Toast.makeText(CategoryActivity.this, "btn: "+ mItems.get(position).getAli() , Toast.LENGTH_SHORT).show();
                                    }
                                }
                            });
                    if(itemsDestacadosCount > 0){
                        rvDestacados.removeAllViews();
                    }
                    rvDestacados.setAdapter(couponAdapterDestacados);
                    couponAdapterDestacados.notifyDataSetChanged();
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
        LinearLayoutManager llm = new LinearLayoutManager(CategoryActivity.this, LinearLayoutManager.VERTICAL, false);
        rvRecomendos.setLayoutManager(llm);
        getRecomendados("0", String.valueOf(mUser.getLat()), String.valueOf(mUser.getLng()),"0");

    }

    private void loadHighlightsCoupons() {
        /* DESTACADOS */
        rvDestacados.setHasFixedSize(true);
        LinearLayoutManager llm = new LinearLayoutManager(CategoryActivity.this, LinearLayoutManager.HORIZONTAL, false);
        rvDestacados.setLayoutManager(llm);
        getDestacados("0", String.valueOf(mUser.getLat()), String.valueOf(mUser.getLng()),"0");

    }




    private void getFiltered(String kms, String days){
        Log.d("pepe", "============================================");
        Log.d("ses", mUser.getSes());
        Log.d("cat", category.getId());
        Log.d("kms", kms);
        Log.d("day", days);
        Log.d("lat", String.valueOf(mUser.getLng()));
        Log.d("lng", String.valueOf(mUser.getLat()));
        Log.d("pepe", "============================================");
        getDestacados(kms, String.valueOf(mUser.getLat()),String.valueOf(mUser.getLng()),days);
        getRecomendados(kms, String.valueOf(mUser.getLat()),String.valueOf(mUser.getLng()),days);
    }



    @Override
    public void onNewsItemPicked(int days, int kms) {
        getFiltered(String.valueOf(kms), String.valueOf(days));
    }
}
