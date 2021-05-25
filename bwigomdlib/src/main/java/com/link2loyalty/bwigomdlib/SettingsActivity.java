package com.link2loyalty.bwigomdlib;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import androidx.appcompat.widget.Toolbar;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;

import com.facebook.login.LoginManager;
import com.link2loyalty.bwigomdlib.R;
import com.link2loyalty.bwigomdlib.fragments.LinkFacebookFragment;
import com.link2loyalty.bwigomdlib.models2.User;
import com.link2loyalty.bwigomdlib.models2.user.ValorMultitenancy;

public class SettingsActivity extends AppCompatActivity implements View.OnClickListener {

    private String title = "Ajustes";
    private Toolbar myToolbar;
    private Button btnLogout, btnTerminos, btnAviso;

    private Context mContext;

    private User mUser;
    private ValorMultitenancy mMultitenancy;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
        mContext = getApplicationContext();
        mUser = new User(mContext);
        mMultitenancy = mUser.getMultitenancy();
        findViews();
        configToolbar();
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

    private void findViews(){
        myToolbar   = findViewById(R.id.tb_settings);
        //btnLogout   = findViewById(R.id.btn_settings_log_out);
        btnTerminos = findViewById(R.id.btn_settings_terminos);
        btnAviso   = findViewById(R.id.btn_settings_aviso);

        //btnLogout.setOnClickListener(this);
        btnTerminos.setOnClickListener(this);
        btnAviso.setOnClickListener(this);

    }

    private void doLogout(){

        mUser.doLogout();
        LoginManager.getInstance().logOut();
        Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        finishAffinity();
        startActivity(intent);
        finish();
    }

    private void goAviso() {
        Intent intent = new Intent(getApplicationContext(), AvisoActivity.class);
        startActivity(intent);
    }

    private void goTerminos() {
        Intent intent = new Intent(getApplicationContext(), TerminosActivity.class);
        startActivity(intent);
    }

    private void linkFacebook(){
        FragmentManager fragmentManager = getSupportFragmentManager();
        LinkFacebookFragment newFragment = new LinkFacebookFragment();

        // The device is smaller, so show the fragment fullscreen
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        // For a little polish, specify a transition animation
        transaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
        // To make it fullscreen, use the 'content' root view as the container
        // for the fragment, which is always the root view for the activity
        transaction.add(android.R.id.content, newFragment)
                .addToBackStack(null).commit();
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        /*if (id == R.id.btn_settings_log_out) {
            doLogout();
        } else*/ if (id == R.id.btn_settings_terminos) {
            goTerminos();
        } else if (id == R.id.btn_settings_aviso) {
            goAviso();
        }
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

}
