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
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.VolleyError;
import com.google.gson.Gson;
import com.link2loyalty.bwigomdlib.R;
import com.link2loyalty.bwigomdlib.adapters.RewardsAdapter;
import com.link2loyalty.bwigomdlib.api.ServerCallback;
import com.link2loyalty.bwigomdlib.interfaces.RewardsRecyclerViewOnItemClickListener;
import com.link2loyalty.bwigomdlib.models2.Test;
import com.link2loyalty.bwigomdlib.models2.User;
import com.link2loyalty.bwigomdlib.models2.user.LovReward;
import com.link2loyalty.bwigomdlib.models2.user.ResRewards;
import com.link2loyalty.bwigomdlib.models2.user.ValorMultitenancy;

import java.util.ArrayList;

public class RewardsActivity extends AppCompatActivity {

    private String title = "Recompensas";
    private Toolbar myToolbar;

    private Context mContext;
    private User mUser;
    private Test mTest;

    private ValorMultitenancy mMultitenancy;

    private RecyclerView rvRewards;
    private RecyclerView.LayoutManager layoutManager;
    private RecyclerView.Adapter adapterRewards;

    private ProgressBar pbRewards;

    TextView tvLastCanje;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rewards);
        mContext = RewardsActivity.this;

        mUser = new User(mContext);
        mTest = new Test(mContext);
        mMultitenancy = mUser.getMultitenancy();
        findViews();
        configToolbar();
        pbRewards.setVisibility(View.INVISIBLE);
        tvLastCanje.setVisibility(View.INVISIBLE);
        getRewards();
    }

    private void getRewards(){
        pbRewards.setVisibility(View.VISIBLE);
        mUser.getRewards(mUser.getSes(), new ServerCallback() {
            @Override
            public void onSuccess(String result) {
                Gson mGson = new Gson();
                ResRewards mResRewards = mGson.fromJson(result, ResRewards.class);
                if( mResRewards.getErr() == 0 ){


                    ArrayList<LovReward> mRewards = mResRewards.getLov();

                    // use this setting to improve performance if you know that changes
                    // in content do not change the layout size of the RecyclerView
                    rvRewards.setHasFixedSize(true);
                    // use a linear layout manager
                    layoutManager = new LinearLayoutManager(RewardsActivity.this);
                    rvRewards.setLayoutManager(layoutManager);
                    // specify an adapter (see also next example)
                    adapterRewards = new RewardsAdapter(mContext, mRewards, new RewardsRecyclerViewOnItemClickListener() {
                        @Override
                        public void onClick(View v, int position) {
                            //Toast.makeText(RewardsActivity.this, "Clikeado in position: "+position, Toast.LENGTH_SHORT).show();
                            doTest();
                        }
                    });
                    rvRewards.setAdapter( adapterRewards );
                    if( mResRewards.getLov().size() == 0 ){
                        String ultCan = mUser.getLoginDetails().getUltcan();
                        tvLastCanje.setText( ultCan );
                        rvRewards.setVisibility(View.INVISIBLE);
                        tvLastCanje.setVisibility(View.VISIBLE);

                    }
                    pbRewards.setVisibility(View.INVISIBLE);
                }else{
                    Toast.makeText(mContext, mResRewards.getMen(), Toast.LENGTH_SHORT).show();
                    pbRewards.setVisibility(View.INVISIBLE);
                }
            }
            @Override
            public void onError(VolleyError err) {
                Toast.makeText(mContext, "Error de conexion!", Toast.LENGTH_SHORT).show();
                pbRewards.setVisibility(View.INVISIBLE);
            }
        });
    }

    private void configToolbar() {
        setSupportActionBar(myToolbar);
        getSupportActionBar().setTitle(title);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        //Config multitenancy
        if( mMultitenancy.getColpri() != null ){
            myToolbar.setBackgroundColor(Color.parseColor( "#"+mMultitenancy.getColpri() ));
            //myToolbar.setTitle( mMultitenancy.getDes() );
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                Window window = getWindow();
                window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
                window.setStatusBarColor(Color.parseColor( "#"+mMultitenancy.getColosc() ));
            }
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_rewards, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.menu_rewards_canjes) {
            showCanjes();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void findViews(){
        myToolbar = findViewById(R.id.tb_rewards);
        rvRewards = findViewById(R.id.rv_rewards);
        pbRewards = findViewById(R.id.pb_rewards);
        tvLastCanje = findViewById(R.id.tv_last_canje);
    }

    private void doTest(){

        mTest.runTest();

    }

    private void showCanjes(){
        Intent intent = new Intent(this, ShowCanjes.class);


        startActivity(intent);
    }

}
