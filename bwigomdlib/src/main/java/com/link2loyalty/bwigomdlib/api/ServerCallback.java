package com.link2loyalty.bwigomdlib.api;

import com.android.volley.VolleyError;

public interface ServerCallback{
    void onSuccess(String result);
    void onError(VolleyError err);
}
