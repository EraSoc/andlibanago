package com.link2loyalty.bwigomdlib;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.appcompat.widget.Toolbar;

import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Toast;

import com.android.volley.VolleyError;
import com.google.gson.Gson;
import com.link2loyalty.bwigomdlib.R;
import com.link2loyalty.bwigomdlib.adapters.RecomendadosFilteredAdapter;
import com.link2loyalty.bwigomdlib.api.ServerCallback;
import com.link2loyalty.bwigomdlib.interfaces.CouponsRecyclerViewOnItemClickListener;
import com.link2loyalty.bwigomdlib.models2.Coupon;
import com.link2loyalty.bwigomdlib.models2.User;
import com.link2loyalty.bwigomdlib.models2.coupon.FilteredRes;
import com.link2loyalty.bwigomdlib.models2.coupon.LovFiltered;
import com.link2loyalty.bwigomdlib.models2.user.ValorMultitenancy;

import java.util.List;

public class SearchedActivity extends AppCompatActivity {

    private Context mContext;
    private User mUser;
    private Coupon mCoupon;
    private String title = "Resultados";
    private RecyclerView rvSearchedCoupons;
    private ValorMultitenancy mMultitenancy;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_searched);
        Intent intent = getIntent();
        String query = intent.getStringExtra("CADENA");
        mContext = getApplicationContext();
        mUser    = new User(mContext);
        mCoupon  = new Coupon(mContext);
        mMultitenancy = mUser.getMultitenancy();
        rvSearchedCoupons = findViewById(R.id.rv_searched_coupons);
        rvSearchedCoupons.setHasFixedSize(true);
        LinearLayoutManager llm = new LinearLayoutManager(mContext, LinearLayoutManager.VERTICAL, false);
        rvSearchedCoupons.setLayoutManager(llm);
        searchCoupons(query);
        configToolbar();

    }



    private void searchCoupons(String q){
        mCoupon.buscar(mUser.getSes(), "","","","","","","","","","","", q,new ServerCallback() {
            @Override
            public void onSuccess(String result) {
                Gson mGson = new Gson();
                FilteredRes mFavoritosRes = mGson.fromJson(result, FilteredRes.class);
                if( mFavoritosRes.getErr() == 0 ){
                    final List<LovFiltered> mItems = mFavoritosRes.getLov();
                    RecomendadosFilteredAdapter couponAdapter = new RecomendadosFilteredAdapter(mItems, mContext,
                            new CouponsRecyclerViewOnItemClickListener() {
                                @Override
                                public void onClick(View v, int position) {

                                    if( v.getId() == R.id.cv_coupon ){
                                        LovFiltered c = new LovFiltered();
                                        c = mItems.get(position);
                                        Intent intent = new Intent(mContext, ShowCouponActivity.class);
                                        intent.putExtra("couponId", c.getClvPro());
                                        startActivity(intent);
                                        //Toast.makeText(HomeActivity.this, "cupon: "+ recommendedCoupons.get(position).getStore(), Toast.LENGTH_SHORT).show();
                                    }else if(v.getId() == R.id.btn_coupon){
                                        Toast.makeText(mContext, "btn: "+ mItems.get(position).getAli(), Toast.LENGTH_SHORT).show();
                                    }
                                }
                            });
                    rvSearchedCoupons.setAdapter(couponAdapter);
                    couponAdapter.notifyDataSetChanged();
                }else{
                    Toast.makeText(mContext, mFavoritosRes.getMen(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onError(VolleyError err) {
                Toast.makeText(mContext, "Error de conexion!", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void configToolbar() {

        Toolbar toolbar = (Toolbar) findViewById(R.id.tb_search);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setTitle(title);
        //Config multitenancy
        if( mMultitenancy.getColpri() != null ){
            toolbar.setBackgroundColor(Color.parseColor( "#"+mMultitenancy.getColpri() ));
            //myToolbar.setTitle( mMultitenancy.getDes() );
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                Window window = getWindow();
                window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
                window.setStatusBarColor(Color.parseColor( "#"+mMultitenancy.getColosc() ));
            }
        }
    }



}
