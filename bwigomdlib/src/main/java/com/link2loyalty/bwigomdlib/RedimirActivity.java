package com.link2loyalty.bwigomdlib;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
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
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.VolleyError;
import com.google.gson.Gson;
import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;
import com.link2loyalty.bwigomdlib.R;
import com.link2loyalty.bwigomdlib.api.ServerCallback;
import com.link2loyalty.bwigomdlib.models2.Coupon;
import com.link2loyalty.bwigomdlib.models2.User;
import com.link2loyalty.bwigomdlib.models2.coupon.ResRedimir;
import com.link2loyalty.bwigomdlib.models2.user.ValorMultitenancy;

public class RedimirActivity extends AppCompatActivity {
    private Toolbar myToolbar;
    private Button btnScanCode, btnRedimir;
    private EditText etCode;
    private Context mContext;
    private int couponId = 0;
    private int sucId = 0;
    private Coupon mCoupon;
    private User mUser;
    private ValorMultitenancy mMultitenancy;
    /*nuevas funciones libreria integra*/
    private CheckBox sidescuento;
    private String sidesc="no";
    private Bundle mybndl;
    private Menu menu;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_redimir);

        mybndl=getIntent().getExtras();

        couponId =Integer.parseInt(mybndl.getString("couponId")); //getIntent().getIntExtra("couponId", -1);
        mContext = getApplicationContext();

        mUser = new User(mContext);
        mCoupon = new Coupon(mContext);
        mMultitenancy = mUser.getMultitenancy();

        findViews();
        configToolbar();

        btnScanCode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                scanCode();
            }
        });

        btnRedimir.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if( etCode.getText().toString().trim().length() <1 ){
                    if( sucId != 0 ){
                        redimirCode();
                    }else{
                        sucId=0;
                        redimirCode();
                        Toast.makeText(mContext, "Ingresa un código alfanumérico, o escanea un QR!", Toast.LENGTH_SHORT).show();
                    }

                }else{
                    sucId = Integer.parseInt(etCode.getText().toString().trim());
                    redimirCode();
                    //Toast.makeText(mContext, "Redimir", Toast.LENGTH_SHORT).show();
                }
            }
        });

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch(item.getItemId()){
            case android.R.id.home:
                /*Intent intent = new Intent(RedimirActivity.this, ShowCouponActivity.class);
                intent.putExtra("couponId",mybndl.getString("couponId"));
                startActivity(intent);*/
                RedimirActivity.super.onBackPressed();
                break;
        }
        return super.onOptionsItemSelected(item);

    }

    private void scanCode(){
        IntentIntegrator intent = new IntentIntegrator(this);
        intent.setDesiredBarcodeFormats(IntentIntegrator.QR_CODE_TYPES);

        intent.setPrompt("");
        intent.setCameraId(0);
        intent.setBeepEnabled(true);
        intent.setBarcodeImageEnabled(false);
        intent.setOrientationLocked(true);

        intent.initiateScan();
    }

    private void redimirCode(){
        Log.d("pepe", "=============================");
        Log.d("idSuc", String.valueOf(sucId));
        Log.d("idpro", String.valueOf(couponId));
        Log.d("pepe", "=============================");
        if(sidescuento.isChecked()){
            sidesc="1";
        }else{
            sidesc="0";
        }
        mCoupon.redimir(
                mUser.getSes(),
                String.valueOf(couponId),
                String.valueOf(sucId),
                String.valueOf(mUser.getLat()),
                String.valueOf(mUser.getLng()),
                String.valueOf(sidesc),
                new ServerCallback() {
                    @Override
                    public void onSuccess(String result) {
                        Gson mGson = new Gson();
                        ResRedimir mRes = mGson.fromJson(result, ResRedimir.class);
                        Toast.makeText(mContext, mRes.getMen(), Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onError(VolleyError err) {

                    }
                });

        sidescuento.setChecked(false);
    }


    /** Find all the views in the layout **/
    private void findViews() {
        myToolbar   = findViewById(R.id.tb_redimir);
        btnRedimir  = findViewById(R.id.btn_redimir);
        btnScanCode = findViewById(R.id.btn_scan_code);
        etCode      = findViewById(R.id.et_code);
        sidescuento = findViewById(R.id.sidescuento);

    }


    /** Config th toolbar **/
    private void configToolbar() {
        setSupportActionBar(myToolbar);
        //getSupportActionBar().setTitle("Bwigo mobile");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        //Config multitenancy
        if( mMultitenancy.getColpri() != null ){
            myToolbar.setBackgroundColor(Color.parseColor( "#"+mMultitenancy.getColpri() ));
            myToolbar.setTitle( mMultitenancy.getDes() );
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                Window window = getWindow();
                window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
                window.setStatusBarColor(Color.parseColor( "#"+mMultitenancy.getColosc() ));
            }
            btnRedimir.setBackgroundColor(Color.parseColor( "#"+mMultitenancy.getColosc() ));
            btnScanCode.setTextColor(Color.parseColor( "#"+mMultitenancy.getColosc() ));

        }
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        IntentResult result = IntentIntegrator.parseActivityResult(requestCode, resultCode, data);
        if (result != null){
            if (result.getContents() == null){
                Toast.makeText(this, "Cancelaste el escaneo", Toast.LENGTH_LONG).show();
            }
            else{
                Toast.makeText(this, result.getContents(), Toast.LENGTH_LONG).show();
                sucId = Integer.parseInt(result.getContents());
                this.redimirCode();
            }
        }
        else
        {
            super.onActivityResult(requestCode, resultCode, data);
        }
    }
}