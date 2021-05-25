package com.link2loyalty.bwigomdlib.models2;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Build;
import android.util.Log;

import androidx.core.app.NotificationCompat;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.RetryPolicy;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.google.gson.Gson;
import com.link2loyalty.bwigomdlib.R;
import com.link2loyalty.bwigomdlib.ShowCouponActivity;
import com.link2loyalty.bwigomdlib.api.Api;
import com.link2loyalty.bwigomdlib.api.ServerCallback;
import com.link2loyalty.bwigomdlib.helpers.HttpConn;
import com.link2loyalty.bwigomdlib.models2.login.LoginRes;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import static android.content.Context.NOTIFICATION_SERVICE;

public class CheckGpsPromo {
    Context context;
    public CheckGpsPromo(Context context){
        this.context=context;

    }

    public void onSuccessPush(String jsona){
        try {
            JSONObject jsonx=new JSONObject(jsona);
            if(jsonx.getInt("err")==0){
                JSONArray arre=jsonx.getJSONArray("Lov");
                for(int i=0; i<arre.length(); i++){
                    JSONObject obj=arre.getJSONObject(i);
                    mostrarNotificacion(obj.getString("ali"),obj.getString("descor"),obj.getString("clvpro"));
                }
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

    }
    public void doCallGpsPromo(final String sesx, final String latx, final String lonx,final ServerCallback callback) {
        String url = "https://bwigo.com/anago/class.api.php"; //"http://www.link2loyalty.com/BwMobile/api/PushCupon";

        try{
            String[] paramx=new String[]{sesx,latx,lonx,url};
            new AsyncLocation().execute(paramx);

        }catch (Exception e){
            Log.e("LocationServiceRequest","error "+e.getMessage());
        }

/*        // Request a string response from the provided URL.
       // Log.i("recibiendodatos...",""+sesx+"---lat...."+latx+"....lonx="+lonx);
        StringRequest stringRequest = new StringRequest(
                Request.Method.POST,
                url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
//                        onSuccessPush(response);
//                        mostrarNotificacion("titulofromapp","mensajefromapp","6283");
                        callback.onSuccess(response); // call call back function here
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d("checkGpsError", String.valueOf(error));
                callback.onError(error); // call call back function here
            }
        }) {
            @Override
            protected Map<String, String> getParams() {
                String ses = sesx;
                String lat = latx;
                String lon =lonx;
                Map<String, String> params = new HashMap<String, String>();
                params.put("ses", ses);
                params.put("lat", lat);
                params.put("lon", lon);
                return params;
            }
        };
        // Add a request (in this example, called stringRequest) to your RequestQueue.
        Api.getInstance(context).addToRequestQueue(stringRequest);*/

  /*      StringRequest stringRequest = new StringRequest(
                Request.Method.POST,
                url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.d("response::::GEO", response);
                        callback.onSuccess(response); // call call back function here
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d("ResponseGeoError", String.valueOf(error));
                callback.onError(error); // call call back function here
            }
        }) {
            @Override
            protected Map<String, String> getParams() {
                String ses=sesx;
                String lat = latx;
                String lon = lonx;
                Map<String, String> params = new HashMap<String, String>();
                params.put("ses", ses);
                params.put("lat", lat);
                params.put("lon", lon);
                return params;
            }
        };
        stringRequest.setRetryPolicy(new RetryPolicy() {
            @Override
            public int getCurrentTimeout() {
                return 10000;
            }

            @Override
            public int getCurrentRetryCount() {
                return 10000;
            }

            @Override
            public void retry(VolleyError error) throws VolleyError {

            }
        });
        // Add a request (in this example, called stringRequest) to your RequestQueue.
        Api.getInstance(context).addToRequestQueue(stringRequest);*/
    }

    private void mostrarNotificacion(String title, String body, String cuponId) {
        Intent intent = new Intent(context, ShowCouponActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        intent.putExtra( "title", title);
        intent.putExtra( "body", body);
        intent.putExtra( "couponId", cuponId);

        PendingIntent pendingIntent = PendingIntent.getActivity(context, 0, intent, PendingIntent.FLAG_ONE_SHOT);
        String channelId = "Default";
        NotificationCompat.Builder builder = new  NotificationCompat.Builder(context, channelId)
                .setSmallIcon(R.drawable.bwigo_logo_2)
                .setColor( context.getResources().getColor(R.color.colorAccent) )
                .setContentTitle( title )
                .setContentText( body ).setAutoCancel(true).setContentIntent(pendingIntent);;
        NotificationManager manager = (NotificationManager) context.getSystemService(NOTIFICATION_SERVICE);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel channel = new NotificationChannel(channelId, "Default channel", NotificationManager.IMPORTANCE_DEFAULT);
            manager.createNotificationChannel(channel);
        }
        manager.notify(0, builder.build());
    }

    public class AsyncLocation extends AsyncTask<String,Integer,String> {

        @Override
        protected String doInBackground(String... strings) {

            String a="123/*setcurrentLocation";
            String ap=null;
            String lat=null;
            String lng=null;
                    String params="ses="+strings[0]+"&lat="+strings[1]+"&lon="+strings[2];
                    Log.i("params tracker...",""+params);
                    String[] urlx=new String[]{strings[3],params};
                    HttpConn conn = new HttpConn(context);
                    String resp = conn.getResposeRequest(urlx);
                    Log.i("response insert","..."+resp);
                    onSuccessPush(resp);
            return null;
        }
    }
}
