package com.link2loyalty.bwigomdlib;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import androidx.appcompat.widget.Toolbar;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;

import com.link2loyalty.bwigomdlib.R;
import com.link2loyalty.bwigomdlib.models2.User;
import com.link2loyalty.bwigomdlib.models2.user.ValorMultitenancy;

public class AnswerActivity extends AppCompatActivity {

    private static final String THE_ANSWER = "com.link2loyalty.bwigo.activities.THE_ANSWER";
    private String title = "Respuesta";
    private Toolbar myToolbar;
    private TextView tvAnswer;

    private Context mContext;
    private User mUser;

    private ValorMultitenancy mMultitenancy;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_answer);
        myToolbar = findViewById(R.id.tb_answer);
        tvAnswer = findViewById(R.id.tv_answer);
        mContext = getApplicationContext();

        mUser = new User(mContext);
        mMultitenancy = mUser.getMultitenancy();
        configToolbar();
        // Get the Intent that started this activity and extract the string
        Intent intent = getIntent();
        String answer = intent.getStringExtra(THE_ANSWER);
        tvAnswer.setText(answer);
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
}
