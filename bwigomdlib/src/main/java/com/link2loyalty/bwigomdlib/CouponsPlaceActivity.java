package com.link2loyalty.bwigomdlib;

import android.content.Context;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;

import androidx.recyclerview.widget.RecyclerView;
import androidx.appcompat.widget.Toolbar;

import com.link2loyalty.bwigomdlib.R;

import java.io.IOException;
import java.io.InputStream;

public class CouponsPlaceActivity extends AppCompatActivity {

    private RecyclerView rvCoupons;
    private Context ctx;
    private Toolbar myToolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_coupons_place);

        rvCoupons = findViewById( R.id.rv_coupons_places );
        myToolbar = findViewById( R.id.tb_coupons_place );
        ctx = CouponsPlaceActivity.this;
        configToolbar();
        loadCoupons();

    }

    private void configToolbar() {
        setSupportActionBar(myToolbar);
        getSupportActionBar().setTitle("Bwigo mobile");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }


    private void loadCoupons(){
        /*
        LinearLayoutManager llm = new LinearLayoutManager(ctx, LinearLayoutManager.VERTICAL, false);
        rvCoupons.setLayoutManager(llm);
        //load data fom json
        try {
            //--TODO Cambiar loadJSONFromAsset a loadJSONFromServer
            JSONArray jsonCoupons = new JSONArray(loadJSONFromAsset(ctx, "my_coupons"));
            //convert JSONArray to List
            Gson gson = new Gson();
            final List<CouponModel> recommendedCoupons = Arrays.asList(gson.fromJson(String.valueOf(jsonCoupons), CouponModel[].class));
            CouponAdapter couponAdapter = new CouponAdapter(recommendedCoupons,  new CouponsRecyclerViewOnItemClickListener() {
                @Override
                public void onClick(View v, int position) {
                    if( v.getId() == R.id.cv_coupon ){
                        CouponModel c = new CouponModel();
                        c = recommendedCoupons.get(position);
                        Intent intent = new Intent(ctx, ShowCouponActivity.class);
                        intent.putExtra("CouponModel", c );
                        startActivity(intent);
                        //Toast.makeText(ctx, "cupon: "+ recommendedCoupons.get(position).getStore(), Toast.LENGTH_SHORT).show();
                    }else if(v.getId() == R.id.btn_coupon){
                        Toast.makeText(ctx, "btn: "+ recommendedCoupons.get(position).getStore(), Toast.LENGTH_SHORT).show();
                    }
                }
            });
            rvCoupons.setAdapter(couponAdapter);
        } catch (JSONException e) {
            try {
                JSONObject jsonCoupon = new JSONObject(loadJSONFromAsset(ctx, "my_coupons"));
                //convert JSONArray to List
                Gson gson = new Gson();
                final List<CouponModel> recommendedCoupons = Arrays.asList(gson.fromJson(String.valueOf(jsonCoupon), CouponModel.class));
                CouponAdapter couponAdapter = new CouponAdapter(recommendedCoupons,  new CouponsRecyclerViewOnItemClickListener() {
                    @Override
                    public void onClick(View v, int position) {
                        if( v.getId() == R.id.cv_descuentos ){
                            CouponModel c = new CouponModel();
                            c = recommendedCoupons.get(position);
                            Intent intent = new Intent(ctx, ShowCouponActivity.class);
                            intent.putExtra("CouponModel", c );
                            startActivity(intent);
                            //Toast.makeText(ctx, "cupon: "+ recommendedCoupons.get(position).getStore(), Toast.LENGTH_SHORT).show();
                        }else if(v.getId() == R.id.btn_destacados){
                            Toast.makeText(ctx, "btn: "+ recommendedCoupons.get(position).getStore(), Toast.LENGTH_SHORT).show();
                        }
                    }
                });
                rvCoupons.setAdapter(couponAdapter);
            } catch (JSONException e1) {
                e1.printStackTrace();
            }
        }
        */
    }

    public String loadJSONFromAsset(Context context, String fileName) {
        String json = null;
        try {
            InputStream is = context.getAssets().open(fileName);
            int size = is.available();
            byte[] buffer = new byte[size];
            is.read(buffer);
            is.close();
            json = new String(buffer, "UTF-8");
        } catch (IOException ex) {
            ex.printStackTrace();
            return null;
        }
        return json;

    }

}
