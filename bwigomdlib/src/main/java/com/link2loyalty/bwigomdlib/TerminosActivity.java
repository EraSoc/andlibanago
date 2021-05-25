package com.link2loyalty.bwigomdlib;

import android.content.Context;
import android.graphics.Color;
import android.os.Build;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import androidx.appcompat.widget.Toolbar;
import android.text.Html;
import android.text.Spanned;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;

import com.link2loyalty.bwigomdlib.R;
import com.link2loyalty.bwigomdlib.models2.User;
import com.link2loyalty.bwigomdlib.models2.user.ValorMultitenancy;

public class TerminosActivity extends AppCompatActivity {

    private Toolbar myToolbar;
    private String title = "Términos y condiciones";
    private Context mContext;

    private User mUser;
    private ValorMultitenancy mMultitenancy;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_terminos);
        mContext = getApplicationContext();
        mUser = new User(mContext);
        mMultitenancy = mUser.getMultitenancy();
        // get our html content
        String htmlAsString = getString(R.string.html);      // used by WebView
        Spanned htmlAsSpanned = Html.fromHtml(htmlAsString); // used by TextView
        // set the html content on a TextView
        TextView textView = (TextView) findViewById(R.id.textView);
        textView.setText(htmlAsSpanned);
        configToolbar();

    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }


    private void configToolbar() {
        myToolbar = findViewById(R.id.tb_terminos);
        setSupportActionBar(myToolbar);
        getSupportActionBar().setTitle(title);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
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



}
