package com.link2loyalty.bwigomdlib.api;

import android.content.Context;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.Volley;

public class Api {
    private static Api mInstance;
    private RequestQueue mRequestQueue;
    private static Context mCtx;

    public static String baseUrlUsers = "http://link2loyalty.com/AsistenciasL2L/api/";
    public static String baseUrlActions = "http://link2loyalty.com/Bwmobile/api/";

    private Api(Context context) {
        mCtx = context;
        mRequestQueue = getRequestQueue();

    }

    public static synchronized Api getInstance(Context context) {
        if (mInstance == null) {
            mInstance = new Api(context);
        }
        return mInstance;
    }

    public RequestQueue getRequestQueue() {
        if (mRequestQueue == null) {
            // getApplicationContext() is key, it keeps you from leaking the
            // Activity or BroadcastReceiver if someone passes one in.
            mRequestQueue = Volley.newRequestQueue(mCtx.getApplicationContext());
        }
        return mRequestQueue;
    }

    public <T> void addToRequestQueue(Request<T> req) {
        getRequestQueue().add(req);
    }

}
