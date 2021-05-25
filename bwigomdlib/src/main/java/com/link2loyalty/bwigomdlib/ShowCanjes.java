package com.link2loyalty.bwigomdlib;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Build;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.appcompat.widget.Toolbar;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

import com.android.volley.VolleyError;
import com.google.gson.Gson;
import com.link2loyalty.bwigomdlib.R;
import com.link2loyalty.bwigomdlib.adapters.CanjesAdapter;
import com.link2loyalty.bwigomdlib.api.ServerCallback;
import com.link2loyalty.bwigomdlib.interfaces.RewardsRecyclerViewOnItemClickListener;
import com.link2loyalty.bwigomdlib.models2.User;
import com.link2loyalty.bwigomdlib.models2.canje.LovBoletos;
import com.link2loyalty.bwigomdlib.models2.canje.ResLogBoletos;
import com.link2loyalty.bwigomdlib.models2.user.ValorMultitenancy;

import java.util.ArrayList;

public class ShowCanjes extends AppCompatActivity {

    private Context mContext;
    private User mUser;

    private ValorMultitenancy mMultitenancy;

    private Toolbar tbCanjes;
    private RecyclerView rvCanjes;
    private RecyclerView.LayoutManager layoutManager;
    private RecyclerView.Adapter adapterRewards;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_canjes);

        mContext = ShowCanjes.this;

        rvCanjes = findViewById(R.id.rv_canjes);
        tbCanjes = findViewById(R.id.tb_canjes);
        setSupportActionBar(tbCanjes);
        getSupportActionBar().setTitle("Canjes");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        mUser = new User(mContext);
        mMultitenancy = mUser.getMultitenancy();

        //Config multitenancy
        if( mMultitenancy.getColpri() != null ){
            tbCanjes.setBackgroundColor(Color.parseColor( "#"+mMultitenancy.getColpri() ));
            //myToolbar.setTitle( mMultitenancy.getDes() );
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                Window window = getWindow();
                window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
                window.setStatusBarColor(Color.parseColor( "#"+mMultitenancy.getColosc() ));
            }
        }
        loadCanjes();
    }

    private void loadCanjes(){

        mUser.logBoletos(mUser.getSes(), new ServerCallback() {
            @Override
            public void onSuccess(String result) {

                Gson mGson = new Gson();
                ResLogBoletos mRes = mGson.fromJson(result, ResLogBoletos.class);
                if( mRes.getErr() == 0 ){
                    final ArrayList<LovBoletos> canjes = mRes.getLov();
                    // use this setting to improve performance if you know that changes
                    // in content do not change the layout size of the RecyclerView
                    rvCanjes.setHasFixedSize(true);
                    // use a linear layout manager
                    layoutManager = new GridLayoutManager(ShowCanjes.this, 2);
                    rvCanjes.setLayoutManager(layoutManager);
                    // specify an adapter (see also next example)
                    adapterRewards = new CanjesAdapter(canjes, ShowCanjes.this , new RewardsRecyclerViewOnItemClickListener() {
                        @Override
                        public void onClick(View v, int position) {
                            //Toast.makeText(mContext, "Clikeado in position: "+position, Toast.LENGTH_SHORT).show();
                            showDetail(canjes.get(position));
                        }
                    });
                    rvCanjes.setAdapter( adapterRewards );

                }



            }

            @Override
            public void onError(VolleyError err) {

            }
        });

    }

    private void showDetail(LovBoletos boleto){

        Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("http://"+boleto.getPdf()));
        startActivity(browserIntent);

        /*
        Bundle bundle = new Bundle();
        bundle.putSerializable("boleto", boleto);

        FragmentManager fragmentManager = getSupportFragmentManager();
        ShowCanjeFragment newFragment = new ShowCanjeFragment();
        newFragment.setArguments(bundle);
        // The device is smaller, so show the fragment fullscreen
        android.support.v4.app.FragmentTransaction transaction = fragmentManager.beginTransaction();
        // For a little polish, specify a transition animation
        transaction.setTransition(android.app.FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
        // To make it fullscreen, use the 'content' root view as the container
        // for the fragment, which is always the root view for the activity

        newFragment.show(fragmentManager, "Hola");
        */
    }


}
