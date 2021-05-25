package com.link2loyalty.bwigomdlib.helpers;


import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class HttpConn {
    private String urx;
    private Context context;
    boolean isonline;
    public HttpConn(Context context){
        this.context=context;
        isonline= isOnline(context);
    }
    public boolean isOnline(Context context) {

        ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo netInfo = cm.getActiveNetworkInfo();
        //should check null because in airplane mode it will be null
        return (netInfo != null && netInfo.isConnected());
    }
    public String getResposeRequest(String[] strings){
        String a="noconnected";
        urx=strings[0];
        InputStream in=null;

        if(isonline==true){

            try {
                URL urk=new URL(urx);
                if(String.valueOf(urk).length()>=1) {
                    HttpURLConnection conn = (HttpURLConnection) urk.openConnection();
                    conn.setReadTimeout(15000);
                    conn.setConnectTimeout(15000);
                    conn.setRequestMethod("POST");
                    conn.setDoInput(true);
                    conn.setDoOutput(true);
                    conn.connect();

                    DataOutputStream dos = new DataOutputStream(conn.getOutputStream());
                    dos.writeBytes(strings[1]);
                    dos.flush();
                    dos.close();

                    int response = conn.getResponseCode();
                    if (response == HttpURLConnection.HTTP_OK) {
                        new BufferedInputStream(conn.getInputStream());
                        in = conn.getInputStream();
                        a = readStream(in);
                    }
                    conn.disconnect();
                }
            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }

            return a;
        }else{
            return "noconnected";
        }
    }
    public String getResponseSimpleRequest(String urls){
        String a="noconnected";
        InputStream in=null;

        try{
            URL urk=new URL(urls);

            if(isonline==true) {

                HttpURLConnection conn = (HttpURLConnection) urk.openConnection();
                conn.setReadTimeout(15000);
                conn.setConnectTimeout(15000);
                conn.setRequestMethod("POST");
                conn.setDoInput(true);
                conn.setDoOutput(true);
                conn.connect();

            /*DataOutputStream dos=new DataOutputStream(conn.getOutputStream());
            dos.writeBytes(strings[1]);
            dos.flush();
            dos.close();*/

                int response = conn.getResponseCode();
                if (response == HttpURLConnection.HTTP_OK) {
                    new BufferedInputStream(conn.getInputStream());
                    in = conn.getInputStream();
                    a = readStream(in);
                }

            }
        }catch (MalformedURLException e) {
            e.printStackTrace();
        }catch (IOException e) {
            e.printStackTrace();
        }
        return a;

    }
    private String readStream(InputStream is) throws IOException {
        StringBuilder sb = new StringBuilder();
        BufferedReader r = new BufferedReader(new InputStreamReader(is),1000);
        for (String line = r.readLine(); line != null; line =r.readLine()){
            sb.append(line);
        }
        is.close();
        return sb.toString();
    }
}
