package com.link2loyalty.bwigomdlib.helpers;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.os.Build;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.core.app.NotificationCompat;

import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;
import com.link2loyalty.bwigomdlib.R;
import com.link2loyalty.bwigomdlib.ArticleDetailActivity;
import com.link2loyalty.bwigomdlib.ShowCouponActivity;
import com.snappydb.DB;
import com.snappydb.DBFactory;
import com.snappydb.SnappydbException;

import java.util.Map;

public class MyFirebaseMessagingService extends FirebaseMessagingService {

    public static final String TAG = "NOTICIAS";

    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {
        super.onMessageReceived(remoteMessage);
        Log.i("receiver...","message:::"+remoteMessage.getData().get("couponId"));
        if (remoteMessage.getData().size() > 0) {
            mostrarNotificacion(remoteMessage.getNotification().getTitle(), remoteMessage.getNotification().getBody(), remoteMessage.getData());
        }else{
            /*
            //mostrarNotificacion(remoteMessage.getNotification().getTitle(), remoteMessage.getNotification().getBody());*/
        }


    }

    private void mostrarNotificacion(String title, String body, Map<String, String> data) {
        Intent intent = new Intent(this, ShowCouponActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        intent.putExtra( "title", title);
        intent.putExtra( "body", body);
        intent.putExtra("fromact","home");
        Log.i("check----","show..."+data.get("couponId"));
        if( data.get("couponId") != null ) {
            intent.putExtra("couponId", data.get("couponId"));


            PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, intent, PendingIntent.FLAG_ONE_SHOT);
            String channelId = "Default";
            NotificationCompat.Builder builder = new NotificationCompat.Builder(this, channelId)
                    .setSmallIcon(R.drawable.ic_launcher_background)
                    .setColor(getResources().getColor(R.color.colorAccent))
                    .setContentTitle(title)
                    .setContentText(body).setAutoCancel(true).setContentIntent(pendingIntent);
            ;
            NotificationManager manager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                NotificationChannel channel = new NotificationChannel(channelId, "Default channel", NotificationManager.IMPORTANCE_DEFAULT);
                manager.createNotificationChannel(channel);
            }
            manager.notify(0, builder.build());
        }
    }

/*    private void mostrarNotificacionx(String title, String body) {

        Intent intent = new Intent(this, ArticleDetailActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        intent.putExtra( "title", title);
        intent.putExtra( "body", body);
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, intent, PendingIntent.FLAG_ONE_SHOT);
        String channelId = "Default";
        NotificationCompat.Builder builder = new  NotificationCompat.Builder(this, channelId)
                .setSmallIcon(R.drawable.ic_launcher_background)
                .setColor( getResources().getColor(R.color.colorAccent) )
                .setContentTitle( title )
                .setContentText( body ).setAutoCancel(true).setContentIntent(pendingIntent);;
        NotificationManager manager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel channel = new NotificationChannel(channelId, "Default channel", NotificationManager.IMPORTANCE_DEFAULT);
            manager.createNotificationChannel(channel);
        }
        manager.notify(0, builder.build());

    }*/

    @Override
    public void onNewToken(@NonNull String token) {
        super.onNewToken(token);

        try {
            DB snappydb = DBFactory.open(this); //create or open an existing database using the default name

            snappydb.put("token", token );
            Log.d("Token", "token guardado...");
            Log.d("Token", token);
            snappydb.close();

        } catch (SnappydbException e) {
        }

    }
}

