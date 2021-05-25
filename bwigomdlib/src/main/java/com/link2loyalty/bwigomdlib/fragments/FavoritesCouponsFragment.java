package com.link2loyalty.bwigomdlib.fragments;


import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.android.volley.VolleyError;
import com.google.gson.Gson;
import com.link2loyalty.bwigomdlib.R;
import com.link2loyalty.bwigomdlib.ShowCouponActivity;
import com.link2loyalty.bwigomdlib.adapters.FavoriteCouponAdapter;
import com.link2loyalty.bwigomdlib.api.ServerCallback;
import com.link2loyalty.bwigomdlib.interfaces.CouponsRecyclerViewOnItemClickListener;
import com.link2loyalty.bwigomdlib.models2.Coupon;
import com.link2loyalty.bwigomdlib.models2.User;
import com.link2loyalty.bwigomdlib.models2.coupon.RecomendadosRes;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;


public class FavoritesCouponsFragment extends Fragment {

    private Context ctx;
    RecyclerView rv;

    private User mUser;
    private Coupon mCoupon;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View view = inflater.inflate(R.layout.fragment_favorites_coupons, container, false);
        ctx = getActivity();
        mUser = new User(ctx);
        mCoupon = new Coupon(ctx);
        loadMyCoupons(view);
        return view;
    }

    private void loadMyCoupons(View view) {
        rv = (RecyclerView)view.findViewById(R.id.rv_favorites_coupons);
        /* RECOMENDADOS */
        rv.setHasFixedSize(true);
        LinearLayoutManager llm = new LinearLayoutManager(ctx, LinearLayoutManager.VERTICAL, false);
        rv.setLayoutManager(llm);
        //load data fom json
        getRecomendados();
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

    private void getRecomendados(){

        mCoupon.getFavorites(mUser.getSes(), new ServerCallback() {
            @Override
            public void onSuccess(String result) {
                Gson mGson = new Gson();
                RecomendadosRes mFavoritosRes = mGson.fromJson(result, RecomendadosRes.class);
                if( mFavoritosRes.getErr() == 0 ){
                    final List<com.link2loyalty.bwigomdlib.models2.coupon.LovRecomendados> mItems = mFavoritosRes.getLov();
                    FavoriteCouponAdapter couponAdapter = new FavoriteCouponAdapter(mItems, ctx,
                            new CouponsRecyclerViewOnItemClickListener() {
                                @Override
                                public void onClick(View v, int position) {

                                    if( v.getId() == R.id.cv_coupon ){
                                        com.link2loyalty.bwigomdlib.models2.coupon.LovRecomendados c = new com.link2loyalty.bwigomdlib.models2.coupon.LovRecomendados();
                                        c = mItems.get(position);
                                        Intent intent = new Intent(ctx, ShowCouponActivity.class);
                                        intent.putExtra("couponId", c.getClvPro());
                                        startActivity(intent);
                                        //Toast.makeText(HomeActivity.this, "cupon: "+ recommendedCoupons.get(position).getStore(), Toast.LENGTH_SHORT).show();
                                    }else if(v.getId() == R.id.btn_coupon){
                                        Toast.makeText(ctx, "btn: "+ mItems.get(position).getAli(), Toast.LENGTH_SHORT).show();
                                    }
                                }
                            });
                    rv.setAdapter(couponAdapter);
                    couponAdapter.notifyDataSetChanged();

                }else{
                    Toast.makeText(ctx, mFavoritosRes.getMen(), Toast.LENGTH_SHORT).show();
                }
            }
            @Override
            public void onError(VolleyError err) {
                Toast.makeText(ctx, "Error de conexion!", Toast.LENGTH_SHORT).show();
            }
        });

    }



}
