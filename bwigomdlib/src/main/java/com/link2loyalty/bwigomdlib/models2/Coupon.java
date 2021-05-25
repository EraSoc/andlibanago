package com.link2loyalty.bwigomdlib.models2;

import android.content.Context;
import android.util.Log;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.link2loyalty.bwigomdlib.api.Api;
import com.link2loyalty.bwigomdlib.api.ServerCallback;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

public class Coupon implements Serializable {

    private Context mCtx;

    public Coupon(Context mCtx) {
        this.mCtx = mCtx;
    }

    public void getRecomendados(final String ses, final String top, final String idc, final String km, final String lat, final String lon, final String fec, final ServerCallback callback){
        String url = "http://www.link2loyalty.com/BwMobile/api/LovRecomendados";
        // Request a string response from the provided URL.
        StringRequest stringRequest = new StringRequest(
                Request.Method.POST,
                url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        callback.onSuccess(response); // call call back function here
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                callback.onError(error); // call call back function here
            }
        }){
            @Override
            protected Map<String, String> getParams()
            {
                Map<String, String>  params = new HashMap<String, String>();
                params.put("ses", ses);
                params.put("top", top);
                params.put("idc", idc);
                params.put("km", km);
                params.put("lat", lat);
                params.put("lon", lon);
                params.put("fec", fec);

                return params;
            }
        };
        // Add a request (in this example, called stringRequest) to your RequestQueue.
        Api.getInstance(mCtx).addToRequestQueue(stringRequest);
    }

    public void getAlianzas(final String ses, final String top, final String idc, final String km, final Double lat, final Double lon, final String fec, final ServerCallback callback){
        String url = "http://www.link2loyalty.com/BwMobile/api/LovAlianzas";
        // Request a string response from the provided URL.
        StringRequest stringRequest = new StringRequest(
                Request.Method.POST,
                url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        callback.onSuccess(response); // call call back function here
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                callback.onError(error); // call call back function here
            }
        }){
            @Override
            protected Map<String, String> getParams()
            {
                Map<String, String>  params = new HashMap<String, String>();
                params.put("ses", ses);
                params.put("top", top);
                params.put("idc", idc);
                params.put("km", km);
                params.put("lat", lat.toString());
                params.put("lon", lon.toString());
                params.put("fec", fec);

                return params;
            }
        };
        // Add a request (in this example, called stringRequest) to your RequestQueue.
        Api.getInstance(mCtx).addToRequestQueue(stringRequest);
    }

    public void getSucursales(final String ses, final String clvpro,final Double lat,final Double lon, final ServerCallback callback){
        String url = "http://www.link2loyalty.com/BwMobile/api/LovSucCupon";
        // Request a string response from the provided URL.
        StringRequest stringRequest = new StringRequest(
                Request.Method.POST,
                url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        callback.onSuccess(response); // call call back function here
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                callback.onError(error); // call call back function here
            }
        }){
            @Override
            protected Map<String, String> getParams()
            {
                Map<String, String>  params = new HashMap<String, String>();
                params.put("ses", ses);
                params.put("clvpro", clvpro);
                params.put("lat",lat.toString());
                params.put("lon",lon.toString());
                return params;
            }
        };
        // Add a request (in this example, called stringRequest) to your RequestQueue.
        Api.getInstance(mCtx).addToRequestQueue(stringRequest);
    }

    public void getDestacados(final String ses, final String top, final String idc, final String km, final String lat, final String lon, final String fec, final ServerCallback callback){
        String url = "http://www.link2loyalty.com/BwMobile/api/LovDestacados";
        // Request a string response from the provided URL.
        StringRequest stringRequest = new StringRequest(
                Request.Method.POST,
                url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        callback.onSuccess(response); // call call back function here
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                callback.onError(error); // call call back function here
            }
        }){
            @Override
            protected Map<String, String> getParams()
            {
                Map<String, String>  params = new HashMap<String, String>();
                params.put("ses", ses);
                params.put("top", top);
                params.put("idc", idc);
                params.put("km", km);
                params.put("lat", lat);
                params.put("lon", lon);
                params.put("fec", fec);
                return params;
            }
        };
        // Add a request (in this example, called stringRequest) to your RequestQueue.
        Api.getInstance(mCtx).addToRequestQueue(stringRequest);
    }

    public void getFavorites(final String ses, final ServerCallback callback){
        String url = "http://www.link2loyalty.com/BwMobile/api/MisFavoritos";
        // Request a string response from the provided URL.
        StringRequest stringRequest = new StringRequest(
                Request.Method.POST,
                url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        callback.onSuccess(response); // call call back function here
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                callback.onError(error); // call call back function here
            }
        }){
            @Override
            protected Map<String, String> getParams()
            {
                Map<String, String>  params = new HashMap<String, String>();
                params.put("ses", ses);
                return params;
            }
        };
        // Add a request (in this example, called stringRequest) to your RequestQueue.
        Api.getInstance(mCtx).addToRequestQueue(stringRequest);
    }

    public void addFavorite(final String ses, final String idpro, final ServerCallback callback){
        String url = "http://www.link2loyalty.com/BwMobile/api/AddFav";
        // Request a string response from the provided URL.
        StringRequest stringRequest = new StringRequest(
                Request.Method.POST,
                url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        callback.onSuccess(response); // call call back function here
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                callback.onError(error); // call call back function here
            }
        }){
            @Override
            protected Map<String, String> getParams()
            {
                Map<String, String>  params = new HashMap<String, String>();
                params.put("ses", ses);
                params.put("idpro", idpro);
                return params;
            }
        };
        // Add a request (in this example, called stringRequest) to your RequestQueue.
        Api.getInstance(mCtx).addToRequestQueue(stringRequest);
    }

    public void getDetail(final String ses, final String idpro, final ServerCallback callback){
        String url = "http://www.link2loyalty.com/BwMobile/api/DetCupon";
        // Request a string response from the provided URL.
        StringRequest stringRequest = new StringRequest(
                Request.Method.POST,
                url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        callback.onSuccess(response); // call call back function here
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                callback.onError(error); // call call back function here
            }
        }){
            @Override
            protected Map<String, String> getParams()
            {
                Map<String, String>  params = new HashMap<String, String>();
                params.put("ses", ses);
                params.put("idpro", idpro);
                return params;
            }
        };
        // Add a request (in this example, called stringRequest) to your RequestQueue.
        Api.getInstance(mCtx).addToRequestQueue(stringRequest);
    }

    public void buscar(final String ses,
                       final String edefe,
                       final String idm,
                       final String idc,
                       final String idsc,
                       final String ida,
                       final String des,
                       final String top,
                       final String km,
                       final String fe,
                       final String lon,
                       final String lat,
                       final String cad,
                       final ServerCallback callback){
        String url = "http://www.link2loyalty.com/BwMobile/api/BuscaCupon";
        // Request a string response from the provided URL.
        StringRequest stringRequest = new StringRequest(
                Request.Method.POST,
                url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        callback.onSuccess(response); // call call back function here
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                callback.onError(error); // call call back function here
            }
        }){
            @Override
            protected Map<String, String> getParams()
            {
                Map<String, String>  params = new HashMap<String, String>();
                params.put("ses", ses);
                params.put("edefe", edefe);
                params.put("idm", idm);
                params.put("idc", idc);
                params.put("idsc", idsc);
                params.put("ida", ida);
                params.put("des", des);
                params.put("top", top);
                params.put("km", km);
                params.put("fe", fe);
                params.put("lon", lon);
                params.put("lat", lat);
                params.put("cad", cad);
                return params;
            }
        };
        // Add a request (in this example, called stringRequest) to your RequestQueue.
        Api.getInstance(mCtx).addToRequestQueue(stringRequest);
    }

    public void redimir(final String ses, final String idpro, final String idsuc, final String lat, final String lon,final String sidesc, final ServerCallback callback){
        String url = "http://www.link2loyalty.com/BwMobile/api/Redencion";
        // Request a string response from the provided URL.
        StringRequest stringRequest = new StringRequest(
                Request.Method.POST,
                url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.i("response_redimir...",""+response);
                        callback.onSuccess(response); // call call back function here
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                callback.onError(error); // call call back function here
            }
        }){
            @Override
            protected Map<String, String> getParams()
            {
                String idsucx;
                if(idsuc!="0"){
                    idsucx=idsuc;
                }else{
                    idsucx="0";
                }
                Map<String, String>  params = new HashMap<String, String>();
                params.put("ses", ses);
                params.put("idpro", idpro);
                params.put("idsuc", idsucx);
                params.put("lat", lat);
                params.put("lon", lon);
                params.put("canok",sidesc);
                Log.i("params","ses=>"+ses+"___idpro:::"+idpro+"___idsuc"+idsucx+"___lat:::"+lat+"___lon:::"+lon+"___canok"+sidesc);

                return params;
            }
        };
        // Add a request (in this example, called stringRequest) to your RequestQueue.
        Api.getInstance(mCtx).addToRequestQueue(stringRequest);
    }

    public void getRedimidos(final String ses, final ServerCallback callback){
        String url = "http://www.link2loyalty.com/BwMobile/api/LovRedimidos";
        // Request a string response from the provided URL.
        StringRequest stringRequest = new StringRequest(
                Request.Method.POST,
                url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        callback.onSuccess(response); // call call back function here
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                callback.onError(error); // call call back function here
            }
        }){
            @Override
            protected Map<String, String> getParams()
            {
                Map<String, String>  params = new HashMap<String, String>();
                params.put("ses", ses);
                return params;
            }
        };
        // Add a request (in this example, called stringRequest) to your RequestQueue.
        Api.getInstance(mCtx).addToRequestQueue(stringRequest);
    }


}

