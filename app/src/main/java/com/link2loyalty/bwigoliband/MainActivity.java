package com.link2loyalty.bwigoliband;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.link2loyalty.bwigomdlib.HomeActivity;
import com.link2loyalty.bwigomdlib.MyLocationService;
import com.link2loyalty.bwigomdlib.StartCls;
import com.link2loyalty.bwigomdlib.models2.CheckGpsPromo;

public class MainActivity extends AppCompatActivity {
Button btnx;
String ses,mttn;
private SharedPreferences session,multiten;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        btnx=(Button) findViewById(R.id.btnx);

        session=getSharedPreferences("session",MODE_PRIVATE);
        ses=session.getString("ses","");

        multiten=getSharedPreferences("multitenancy",MODE_PRIVATE);
        mttn=multiten.getString("data","");

        if(ses!=""){

            btnx.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(getApplicationContext(), HomeActivity.class);
//                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    startActivity(intent);
                }
                });

        }else {
            btnx.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    StartCls stx = new StartCls(getApplicationContext());
                    stx.startevent("arm8701", "mtest");

                }
            });
        }


    }

}
