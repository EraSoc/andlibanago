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
import com.link2loyalty.bwigomdlib.adapters.UsedCouponAdapter;
import com.link2loyalty.bwigomdlib.api.ServerCallback;
import com.link2loyalty.bwigomdlib.interfaces.CouponsRecyclerViewOnItemClickListener;
import com.link2loyalty.bwigomdlib.models2.Coupon;
import com.link2loyalty.bwigomdlib.models2.User;
import com.link2loyalty.bwigomdlib.models2.coupon.LovRedimido;
import com.link2loyalty.bwigomdlib.models2.coupon.ResRedimidos;

import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class UsedCouponsFragment extends Fragment {

    private Context ctx;
    private RecyclerView rv;
    private User mUser;
    private Coupon mCoupon;

    public UsedCouponsFragment() {
        // Required empty public constructor

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_used_coupons, container, false);
        ctx = getActivity();
        mUser = new User(ctx);
        mCoupon = new Coupon(ctx);
        loadUsedCoupons(view);
        return view;
    }


    private void loadUsedCoupons(View view) {

        rv = (RecyclerView)view.findViewById(R.id.rv_used_coupons);
        rv.setHasFixedSize(true);
        LinearLayoutManager llm = new LinearLayoutManager(ctx, LinearLayoutManager.VERTICAL, false);
        rv.setLayoutManager(llm);
        getRedimidos();
    }

    private void getRedimidos(){
        mCoupon.getRedimidos(mUser.getSes(), new ServerCallback() {
            @Override
            public void onSuccess(String result) {
                Gson mGson = new Gson();
                ResRedimidos mFavoritosRes = mGson.fromJson(result, ResRedimidos.class);
                if( mFavoritosRes.getErr() == 0 ){
                    final List<LovRedimido> mItems = mFavoritosRes.getLov();
                    UsedCouponAdapter couponAdapter = new UsedCouponAdapter(mItems, ctx,
                            new CouponsRecyclerViewOnItemClickListener() {
                                @Override
                                public void onClick(View v, int position) {

                                    if( v.getId() == R.id.cv_coupon ){
                                        LovRedimido c = new LovRedimido();
                                        c = mItems.get(position);
                                        Intent intent = new Intent(ctx, ShowCouponActivity.class);
                                        intent.putExtra("couponId",c.getClvpro());
                                        //intent.putExtra("couponId", Integer.valueOf(c.getClvpro()));
                                        startActivity(intent);
                                        //Toast.makeText(HomeActivity.this, "cupon: "+ recommendedCoupons.get(position).getStore(), Toast.LENGTH_SHORT).show();
                                    }else if(v.getId() == R.id.btn_coupon){
                                        //Toast.makeText(ctx, "btn: "+ mItems.get(position).getAli(), Toast.LENGTH_SHORT).show();
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
