package com.link2loyalty.bwigomdlib;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ProgressBar;

import com.link2loyalty.bwigomdlib.R;
import com.link2loyalty.bwigomdlib.adapters.NotificationsListAdapter;
import com.link2loyalty.bwigomdlib.models2.User;
import com.link2loyalty.bwigomdlib.models2.notification.Notification;
import com.link2loyalty.bwigomdlib.models2.user.ValorMultitenancy;

import java.util.ArrayList;

public class NotificationsActivity extends AppCompatActivity {

    private String title = "Notificaciones";
    private Toolbar myToolbar;

    ProgressBar progressBar;
    RecyclerView rvNotifications;
    ArrayList<Notification> notifications = new ArrayList();
    NotificationsListAdapter adapter;

    public Context mContext;
    User mUser;
    private ValorMultitenancy mMultitenancy;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notifications);
        mContext = getApplicationContext();
        mUser = new User(mContext);
        mMultitenancy = mUser.getMultitenancy();
        myToolbar = findViewById(R.id.tb_notifications);
        progressBar = findViewById(R.id.pb_notifications);
        configToolbar();

        rvNotifications = findViewById(R.id.rv_notifications);
        adapter = new NotificationsListAdapter( this, notifications );
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        rvNotifications.setLayoutManager( layoutManager );
        rvNotifications.setAdapter( adapter );

        notifications.add( new Notification("Primera notificación", "El contenidod de la primera notificacion", "20-Feb-2020") );
        notifications.add( new Notification("Segunda notificación", "El contenidod de la second notificacion", "21-Feb-2020") );
        notifications.add( new Notification("TErcera notificación", "El contenidod de la third notificacion", "22-Feb-2020") );
        notifications.add( new Notification("Última notificación", "El contenidod de la last notificacion", "23-Feb-2020") );


        adapter.notifyDataSetChanged();
        progressBar.setVisibility(View.INVISIBLE );


    }


    void configToolbar(){
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
