package com.link2loyalty.bwigomdlib.models2;

import android.content.Context;
import android.util.Log;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.link2loyalty.bwigomdlib.api.Api;
import com.link2loyalty.bwigomdlib.api.ServerCallback;
import com.link2loyalty.bwigomdlib.models.Article;
import com.snappydb.DB;
import com.snappydb.DBFactory;
import com.snappydb.SnappydbException;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class ArticleService implements Serializable {

    private Context mCtx;

    public ArticleService(Context mCtx) {
        this.mCtx = mCtx;
    }

    public void clearFavorites(){
        try {
            DB snappydb = DBFactory.open(mCtx); //create or open an existing database using the default name
            snappydb.del("ARTICLES_FAVORITES");

            snappydb.close();
        } catch (SnappydbException e) {
        }
    }

    public Boolean isFavorite( Article article ){
        Boolean is = false;
        try {
            DB snappydb = DBFactory.open(mCtx);
            if( snappydb.exists("ARTICLES_FAVORITES") ){
                ArrayList<Article> articles = (ArrayList<Article>) snappydb.getObject("ARTICLES_FAVORITES", ArrayList.class);
                for( int i = 0; i<articles.size(); i++ ){
                    if( article.getId_articulo().equals(articles.get(i).getId_articulo()) ){
                        is=true;
                    }
                }
            }

            snappydb.close();
        } catch (SnappydbException e) {
        }
        return is;
    }

    public void saveFavorites( ArrayList<Article> favorites ){

        Log.d( "favorito", String.valueOf(favorites.size()) );

        if( favorites.size() > 0 ){
            try {
                DB snappydb = DBFactory.open(mCtx); //create or open an existing database using the default name
                snappydb.del("ARTICLES_FAVORITES");

                snappydb.put("ARTICLES_FAVORITES", favorites);
                snappydb.close();
            } catch (SnappydbException e) {
            }
        }else{

        }

    }

    public ArrayList<Article> getFavorites(){
        ArrayList<Article> articles = new ArrayList<>();
        try {
            DB snappydb = DBFactory.open(mCtx); //create or open an existing database using the default name
            if( snappydb.exists("ARTICLES_FAVORITES") ){

                articles = (ArrayList<Article>) snappydb.getObject("ARTICLES_FAVORITES", ArrayList.class);
                //articles.addAll(articles);

            }

            snappydb.close();
        } catch (SnappydbException e) {
        }
        return articles;
    }

    public String toggleFavorite( Article favorite ){
        ArrayList<Article> favorites = getFavorites();
        String msg = "";
        int index = -1;
        for( int i = 0; i < favorites.size(); i++ ){
            if( favorite.getId_articulo().equals(favorites.get(i).getId_articulo()) ){
                index=i;
            }
        }
        if( favorites.size() == 0 ){
            index = 0;
        }
        if(index > -1 && favorites.size() > index){
            favorites.remove( index );
            msg = "Articulo quitado de favoritos!";
        }else{
            favorites.add( favorite );
            msg = "Articulo agregado a favoritos!";
        }
        saveFavorites( favorites );
        return msg;
    }

    public void getArticles(final String ses,  final ServerCallback callback){
        String url = "http://link2loyalty.com/TeleBwMbl/Article/getArticulo";
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
                params.put("flg", "1");
                params.put("idart", "0");

                return params;
            }
        };
        // Add a request (in this example, called stringRequest) to your RequestQueue.
        Api.getInstance(mCtx).addToRequestQueue(stringRequest);
    }




    public void getArticle(final String ses, final String idart,  final ServerCallback callback){
        String url = "http://link2loyalty.com/TeleBwMbl/Article/getArticulo";
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
                params.put("flg", "2");
                params.put("idart", idart);

                return params;
            }
        };
        // Add a request (in this example, called stringRequest) to your RequestQueue.
        Api.getInstance(mCtx).addToRequestQueue(stringRequest);
    }




}

