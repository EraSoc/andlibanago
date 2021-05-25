package com.link2loyalty.bwigomdlib.models2;

import android.content.Context;
import android.net.Uri;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.google.android.gms.maps.model.LatLng;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.link2loyalty.bwigomdlib.api.Api;
import com.link2loyalty.bwigomdlib.api.ServerCallback;
import com.link2loyalty.bwigomdlib.api.models.LoginValue;
import com.link2loyalty.bwigomdlib.api.models.ResponseModel;
import com.link2loyalty.bwigomdlib.models2.login.LoginRes;
import com.link2loyalty.bwigomdlib.models2.login.Valor;
import com.link2loyalty.bwigomdlib.models2.user.UserContacto;
import com.link2loyalty.bwigomdlib.models2.user.UserDireccion;
import com.link2loyalty.bwigomdlib.models2.user.UserGeneral;
import com.link2loyalty.bwigomdlib.models2.user.UserLov;
import com.link2loyalty.bwigomdlib.models2.user.ValorMultitenancy;
import com.link2loyalty.bwigomdlib.models2.usrFacebook.ValorFacebook;
import com.snappydb.DB;
import com.snappydb.DBFactory;
import com.snappydb.SnappydbException;

import java.net.URI;
import java.util.HashMap;
import java.util.Map;

public class User {

    private Context mCtx;
    private int numRepAnuncio = 0;

    public void registrarVisualizacion() {
        try {
            numRepAnuncio += 1;
            DB snappydb = DBFactory.open(mCtx);
            snappydb.putInt("numRepAnuncio", numRepAnuncio);
            snappydb.close();
        } catch (SnappydbException e) {
            e.printStackTrace();
        }
    }

    public void registrarIdVisualizacion(int id) {
        try {
            numRepAnuncio += 1;
            DB snappydb = DBFactory.open(mCtx);
            snappydb.putInt("idAnuncio", id);
            snappydb.close();
        } catch (SnappydbException e) {
            e.printStackTrace();
        }
    }

    public void resetIdAnuncio() {
        try {
            DB snappydb = DBFactory.open(mCtx);
            if(snappydb.exists("idAnuncio")){
                snappydb.del("idAnuncio");
            }
        }catch (SnappydbException e) {
            e.printStackTrace();
        }
    }

    public int getIdAnuncio() {
        int idAnuncio = 0;
        try {
            DB snappydb = DBFactory.open(mCtx);
            if ( snappydb.exists("idAnuncio") ){
                idAnuncio = snappydb.getInt("idAnuncio");
            }
            snappydb.close();
        } catch (SnappydbException e) {
            e.printStackTrace();
        }
        return idAnuncio;
    }


    public int getVisualizacionesAnuncio() {
        try {
            DB snappydb = DBFactory.open(mCtx);
            if ( snappydb.exists("numRepAnuncio") ){
                numRepAnuncio = snappydb.getInt("numRepAnuncio");
            } else {
                numRepAnuncio = 0;
            }

            snappydb.close();
        } catch (SnappydbException e) {
            e.printStackTrace();
        }
        return numRepAnuncio;
    }


    public User(Context mCtx) {
        this.mCtx = mCtx;
    }
    public void saveDevIdFirebase(final String ses,final String token, final ServerCallback callback){
        String url = "http://www.link2loyalty.com/BwMobile/api/TokenCel";
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
        }) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<String, String>();
                params.put("ses", ses);
                params.put("tokencel",token);
                return params;
            }
        };
        // Add a request (in this example, called stringRequest) to your RequestQueue.
        Api.getInstance(mCtx).addToRequestQueue(stringRequest);
    }
    public void getAnuncios(final String ses, final ServerCallback callback) {
        String url = "http://www.link2loyalty.com/BwMobile/api/LovAnuncios";
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
        }) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<String, String>();
                params.put("ses", ses);
                return params;
            }
        };
        // Add a request (in this example, called stringRequest) to your RequestQueue.
        Api.getInstance(mCtx).addToRequestQueue(stringRequest);
    }


    public void regUsr(final String ses, final String nom, final String apa, final String ama, final String ema,
                       final String token, final ServerCallback callback) {
        String url = "http://link2loyalty.com/TeleBwMbl/UserApp/setRegUserApp";
        // Request a string response from the provided URL.
        StringRequest stringRequest = new StringRequest(
                Request.Method.POST,
                url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.d("pepe", response);
                        callback.onSuccess(response); // call call back function here
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d("pepe", String.valueOf(error));
                callback.onError(error); // call call back function here
            }
        }) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<String, String>();
                params.put("flg", "1");
                params.put("ses", ses);
                params.put("nom", nom);
                params.put("apa", apa);
                params.put("ama", ama);
                params.put("ema", ema);
                params.put("start", "1");
                params.put("spus", "1");
                params.put("devid", token);
                return params;
            }
        };
        // Add a request (in this example, called stringRequest) to your RequestQueue.
        Api.getInstance(mCtx).addToRequestQueue(stringRequest);
    }


    public void doLogin(final String user, final String password, final ServerCallback callback) {
        String url ="http://www.link2loyalty.com/AsistenciasL2L/api/ValidaAnaGo";/*"http://www.link2loyalty.com/AsistenciasL2L/api/ValidaMasServicios";*/ //"http://link2loyalty.com/AsistenciasL2L/api/_LoginAsis";*/
        // Request a string response from the provided URL.
        StringRequest stringRequest = new StringRequest(
                Request.Method.POST,
                url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.d("loginResponse", response);
                        callback.onSuccess(response); // call call back function here
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d("loginResponse", String.valueOf(error));
                callback.onError(error); // call call back function here
            }
        }) {
            @Override
            protected Map<String, String> getParams() {
                String usr = user;
                String psw = password;
                Map<String, String> params = new HashMap<String, String>();
                params.put("usr", usr);
                params.put("psw", psw);
                params.put("idf", "0");
                params.put("cli", "0");
                params.put("asis", "1821");
                return params;
            }
        };
        // Add a request (in this example, called stringRequest) to your RequestQueue.
        Api.getInstance(mCtx).addToRequestQueue(stringRequest);
    }

    public void recoverPass(final String ema, final ServerCallback callback) {
        String url = "http://www.link2loyalty.com/AsistenciasL2L/api/_RecuperaPsw";
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
        }) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<String, String>();
                params.put("ema", ema);
                return params;
            }
        };
        // Add a request (in this example, called stringRequest) to your RequestQueue.
        Api.getInstance(mCtx).addToRequestQueue(stringRequest);
    }

    public void getDetailsRes(final String ses, final ServerCallback callback) {
        String url = "http://www.link2loyalty.com/BwMobile/api/DetUsuario";
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
        }) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<String, String>();
                params.put("ses", ses);
                return params;
            }
        };
        // Add a request (in this example, called stringRequest) to your RequestQueue.
        Api.getInstance(mCtx).addToRequestQueue(stringRequest);
    }

    public void getFacebookRes(final String ses, final ServerCallback callback) {
        String url = "http://www.link2loyalty.com/BwMobile/api/DetUsuFacebook";
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
        }) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<String, String>();
                params.put("ses", ses);
                return params;
            }
        };
        // Add a request (in this example, called stringRequest) to your RequestQueue.
        Api.getInstance(mCtx).addToRequestQueue(stringRequest);
    }

    public void setDetails(UserLov usrDetails) {
        try {
            DB snappydb = DBFactory.open(mCtx);
            snappydb.put("usrDetails", usrDetails);
            snappydb.close();
        } catch (SnappydbException e) {
            e.printStackTrace();
        }
    }

    public void setLoginDetails(Valor usrLoginDetails) {
        try {
            DB snappydb = DBFactory.open(mCtx);
            snappydb.put("usrLoginDetails", usrLoginDetails);
            snappydb.close();
        } catch (SnappydbException e) {
            e.printStackTrace();
        }
    }

    public Valor getLoginDetails() {
        Valor mLoginRes = null;
        try {
            DB snappydb = DBFactory.open(mCtx);
            mLoginRes = snappydb.get("usrLoginDetails", Valor.class);
            snappydb.close();
        } catch (SnappydbException e) {
            e.printStackTrace();
        }
        return mLoginRes;
    }

    public void dropDetails() {
        try {
            DB snappydb = DBFactory.open(mCtx);
            snappydb.del("usrDetails");
            snappydb.close();
        } catch (SnappydbException e) {
            e.printStackTrace();
        }
    }

    public UserLov getDetails() {
        UserLov ud = null;
        try {
            DB snappydb = DBFactory.open(mCtx);
            ud = snappydb.get("usrDetails", UserLov.class);
            snappydb.close();
        } catch (SnappydbException e) {
            e.printStackTrace();
        }
        return ud;
    }

    public void setLat(Double lat) {
        try {
            DB snappydb = DBFactory.open(mCtx);
            snappydb.putDouble("lat", lat);
            snappydb.close();
        } catch (SnappydbException e) {
            e.printStackTrace();
        }
    }

    public void setLng(Double lng) {
        try {
            DB snappydb = DBFactory.open(mCtx);
            snappydb.putDouble("lng", lng);
            snappydb.close();
        } catch (SnappydbException e) {
            e.printStackTrace();
        }
    }

    public void setLocation(LatLng myLocation) {
        Log.d("pepe", "==================SET LOCATION======================");
        Log.d("lat", String.valueOf(myLocation.latitude));
        Log.d("lng", String.valueOf(myLocation.longitude));
        Log.d("pepe", "============================================");
        this.setLat(myLocation.latitude);
        this.setLng(myLocation.longitude);
    }

    public Double getLat() {
        try {
            DB snappydb = DBFactory.open(mCtx);
            Double lat = snappydb.getDouble("lat");
            snappydb.close();
            return lat;
        } catch (SnappydbException e) {
            e.printStackTrace();
            return 0.0;
        }
    }

    public Double getLng() {
        try {
            DB snappydb = DBFactory.open(mCtx);
            Double lng = snappydb.getDouble("lng");
            snappydb.close();
            return lng;
        } catch (SnappydbException e) {
            e.printStackTrace();
            return 0.0;
        }
    }

    public void setSes(String ses) {
        try {
            DB snappydb = DBFactory.open(mCtx);
            snappydb.put("ses", ses);
            snappydb.close();
        } catch (SnappydbException e) {
            e.printStackTrace();
        }
    }

    public String getSes() {
        String mSes = "";
        try {
            DB snappydb = DBFactory.open(mCtx);
            mSes = snappydb.get("ses");
            snappydb.close();
        } catch (SnappydbException e) {
            e.printStackTrace();
        }
        return mSes;
    }

    public void setImage(Uri image) {
        try {
            DB snappydb = DBFactory.open(mCtx);
            snappydb.put("image", image.toString());
            snappydb.close();
        } catch (SnappydbException e) {
            e.printStackTrace();
        }
    }

    public Uri getImage() {
        try {
            DB snappydb = DBFactory.open(mCtx);
            Uri image = Uri.parse(snappydb.get("image"));
            snappydb.close();
            return image;
        } catch (SnappydbException e) {
            e.printStackTrace();
            return null;
        }
    }

    public boolean isLoged() {
        boolean ok = false;
        try {
            DB snappydb = DBFactory.open(mCtx);
            ok = snappydb.exists("ses");
            snappydb.close();
            return ok;
        } catch (SnappydbException e) {
            e.printStackTrace();
            return ok;
        }
    }

    public void doLogout() {
        try {
            DB snappydb = DBFactory.open(mCtx);

            if (snappydb.exists("ses"))
                snappydb.del("ses");
            if (snappydb.exists("multitenancy"))
                snappydb.del("multitenancy");
            if (snappydb.exists("usrDetails"))
                snappydb.del("usrDetails");

            snappydb.close();
        } catch (SnappydbException e) {
            e.printStackTrace();
        }
    }

    public void updateDetails(final String ses, UserLov mUserDetails) {
        this.updateUsrGeneral(ses, mUserDetails);
        this.updateUserContacto(ses, mUserDetails.getContacto());
        this.updateUsrDireccion(ses, mUserDetails.getDireccion());
        this.dropDetails();
        this.setDetails(mUserDetails);
    }

    public void updateUsrGeneral(final String ses, final UserLov usr) {
        String url = "http://www.link2loyalty.com/BwMobile/api/UpdUsuarioGeneral";
        // Request a string response from the provided URL.
        StringRequest stringRequest = new StringRequest(
                Request.Method.POST,
                url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        }) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<String, String>();
                params.put("ses", ses);
                params.put("nom", usr.getNom());
                params.put("apa", usr.getApa());
                params.put("ama", usr.getAma());
                params.put("gen", usr.getGeneral().getGen());
                params.put("edociv", usr.getGeneral().getEdociv());
                params.put("ono", usr.getGeneral().getOno());
                params.put("nemp", usr.getGeneral().getNemp());
                params.put("ocu", usr.getGeneral().getOcu());
                params.put("ter", usr.getGeneral().getTer());
                return params;
            }
        };
        // Add a request (in this example, called stringRequest) to your RequestQueue.
        Api.getInstance(mCtx).addToRequestQueue(stringRequest);
    }

    public void updateUserContacto(final String ses, final UserContacto usr) {
        String url = "http://www.link2loyalty.com/BwMobile/api/UpdUsuarioContacto";
        // Request a string response from the provided URL.
        StringRequest stringRequest = new StringRequest(
                Request.Method.POST,
                url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        }) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<String, String>();
                params.put("ses", ses);
                params.put("idcon", usr.getClvCon());
                params.put("tel1", usr.getTel1());
                params.put("tel2", usr.getTel2());
                params.put("idface", usr.getIdFace());
                params.put("idtwi", usr.getIdTwi());
                params.put("idide", usr.getNoIde());
                params.put("noide", usr.getNoIde());
                params.put("tip", usr.getTip());
                return params;
            }
        };
        // Add a request (in this example, called stringRequest) to your RequestQueue.
        Api.getInstance(mCtx).addToRequestQueue(stringRequest);
    }

    public void updateUsrDireccion(final String ses, final UserDireccion usr) {
        String url = "http://www.link2loyalty.com/BwMobile/api/UpdUsuarioDireccion";
        // Request a string response from the provided URL.
        StringRequest stringRequest = new StringRequest(
                Request.Method.POST,
                url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        }) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<String, String>();
                params.put("ses", ses);
                params.put("iddir", usr.getClvDir());
                params.put("idefe", usr.getIdEfe());
                params.put("idmun", usr.getIdMun());
                params.put("col", usr.getCol());
                params.put("pob", usr.getPob());
                params.put("dom", usr.getDom());
                params.put("nin", usr.getNin());
                params.put("nex", usr.getNex());
                params.put("cp", usr.getCp());
                params.put("tip", usr.getTip());
                return params;
            }
        };
        // Add a request (in this example, called stringRequest) to your RequestQueue.
        Api.getInstance(mCtx).addToRequestQueue(stringRequest);
    }

    public void multitenancy(final String ses, final ServerCallback callback) {
        String url = "http://link2loyalty.com/BwMobile/api/ConfigTenancy";
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
        }) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<String, String>();
                params.put("ses", ses);
                return params;
            }
        };
        // Add a request (in this example, called stringRequest) to your RequestQueue.
        Api.getInstance(mCtx).addToRequestQueue(stringRequest);
    }

    public void getRewards(final String ses, final ServerCallback callback) {
        String url = "http://www.link2loyalty.com/BwMobile/api/LovRecompensas";
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
        }) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<String, String>();
                params.put("ses", ses);
                return params;
            }
        };
        // Add a request (in this example, called stringRequest) to your RequestQueue.
        Api.getInstance(mCtx).addToRequestQueue(stringRequest);
    }

    public void setMultitenancy(ValorMultitenancy multitenancy) {
        try {
            DB snappydb = DBFactory.open(mCtx);
            snappydb.put("multitenancy", multitenancy);
            snappydb.close();
            Log.d("Tenancy", "Tenancy guardado..."+multitenancy);



        } catch (SnappydbException e) {
            e.printStackTrace();
        }
    }

    public ValorMultitenancy getMultitenancy() {
        ValorMultitenancy mMultitenancy = new ValorMultitenancy();
        try {
            DB snappydb = DBFactory.open(mCtx);
            mMultitenancy = snappydb.get("multitenancy", ValorMultitenancy.class);
            snappydb.close();
            return mMultitenancy;
        } catch (SnappydbException e) {
            e.printStackTrace();
            return mMultitenancy;
        }
    }

    public void linkFacebook(final String ses, final ValorFacebook valorFacebook, final ServerCallback callback) {
        String url = "http://www.link2loyalty.com/BwMobile/api/UpdUsuarioFacebook";
        // Request a string response from the provided URL.
        StringRequest stringRequest = new StringRequest(
                Request.Method.POST,
                url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.d("loginFacebook", response);
                        callback.onSuccess(response); // call call back function here
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d("loginFacebook", String.valueOf(error));
                callback.onError(error); // call call back function here
            }
        }) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<String, String>();
                params.put("ses", ses);
                params.put("uid", valorFacebook.getUID());
                params.put("idfb", valorFacebook.getIdfac());
                params.put("pic", valorFacebook.getPic());
                params.put("lin", valorFacebook.getLin());
                params.put("clvlisami", valorFacebook.getClvlisami());
                params.put("firnam", valorFacebook.getFirnam());
                params.put("lasnam", valorFacebook.getLasnam());
                params.put("age", valorFacebook.getAge());
                params.put("gen", valorFacebook.getGen());
                params.put("abo", valorFacebook.getAbo());
                params.put("cov", valorFacebook.getCov());
                params.put("ema", valorFacebook.getEma());
                params.put("homtow", valorFacebook.getHomtow());
                params.put("loc", valorFacebook.getLoc());
                params.put("pol", valorFacebook.getPol());
                params.put("relsta", valorFacebook.getRelsta());
                params.put("rel", valorFacebook.getRel());
                params.put("timzon", valorFacebook.getTimzon());
                params.put("updtim", valorFacebook.getUpdtim());
                params.put("ver", valorFacebook.getVer());
                params.put("wor", valorFacebook.getWor());



                return params;
            }
        };
        // Add a request (in this example, called stringRequest) to your RequestQueue.
        Api.getInstance(mCtx).addToRequestQueue(stringRequest);
    }

    public void setLoginFacebook(Boolean flag) {
        try {
            DB snappydb = DBFactory.open(mCtx);
            snappydb.putBoolean("loginFacebook", flag);
            snappydb.close();
        } catch (SnappydbException e) {
            e.printStackTrace();
        }
    }

    public Boolean getLoginFacebook() {
        Boolean flag = false;
        try {
            DB snappydb = DBFactory.open(mCtx);
            flag = snappydb.getBoolean("loginFacebook");
            snappydb.close();
        } catch (SnappydbException e) {
            e.printStackTrace();
        }
        return flag;
    }

    public int getLoginFacebookCount() {

        int count = 0;
        try {
            DB snappydb = DBFactory.open(mCtx);
            count = snappydb.getInt("loginFacebookCount");
            snappydb.close();
        } catch (SnappydbException e) {
            e.printStackTrace();
        }

        return count;


    }

    public void setLoginFacebookCount(int count) {
        try {
            DB snappydb = DBFactory.open(mCtx);
            snappydb.putInt("loginFacebookCount", count);
            snappydb.close();
        } catch (SnappydbException e) {
            e.printStackTrace();
        }
    }

    public void loginFacebook( final String facebookId, final ServerCallback callback) {
        String url = "http://link2loyalty.com/AsistenciasL2L/api/_LoginAsis";
        // Request a string response from the provided URL.
        StringRequest stringRequest = new StringRequest(
                Request.Method.POST,
                url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.d("pepe", response);
                        callback.onSuccess(response); // call call back function here
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d("pepe", String.valueOf(error));
                callback.onError(error); // call call back function here
            }
        }) {
            @Override
            protected Map<String, String> getParams() {

                Map<String, String> params = new HashMap<String, String>();
                params.put("usr", "0");
                params.put("psw", "0");
                params.put("idf", facebookId);
                params.put("cli", "0");
                params.put("asis", "1821");
                return params;
            }
        };
        // Add a request (in this example, called stringRequest) to your RequestQueue.
        Api.getInstance(mCtx).addToRequestQueue(stringRequest);
    }

    public void logBoletos(final String ses, final ServerCallback callback) {
        String url = "http://www.link2loyalty.com/CertificadoBoleto/api/logBoletoUsuario";
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
        }) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<String, String>();
                params.put("ses", ses);
                return params;
            }
        };
        // Add a request (in this example, called stringRequest) to your RequestQueue.
        Api.getInstance(mCtx).addToRequestQueue(stringRequest);
    }

    public void setFacebook(ValorFacebook facebookVal) {
        try {
            DB snappydb = DBFactory.open(mCtx);
            snappydb.put("usrFacebook", facebookVal);
            snappydb.close();
        } catch (SnappydbException e) {
            e.printStackTrace();
        }
    }

    public ValorFacebook getFacebookVal() {
        ValorFacebook fv = null;
        try {
            DB snappydb = DBFactory.open(mCtx);
            fv = snappydb.get("usrFacebook", ValorFacebook.class);
            snappydb.close();
        } catch (SnappydbException e) {
            e.printStackTrace();
        }
        return fv;
    }

    public void updFacebook(final String ses, final ValorFacebook valorFacebook) {
        String url = "http://www.link2loyalty.com/BwMobile/api/UpdUsuarioFacebook";
        // Request a string response from the provided URL.
        StringRequest stringRequest = new StringRequest(
                Request.Method.POST,
                url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.d("resp", response);
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        }) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<String, String>();
                params.put("ses", ses);
                params.put("uid", valorFacebook.getUID());
                params.put("idfb", valorFacebook.getIdfac());
                params.put("pic", valorFacebook.getPic());
                params.put("lin", valorFacebook.getLin());
                params.put("clvlisami", valorFacebook.getClvlisami());
                params.put("firnam", valorFacebook.getFirnam());
                params.put("lasnam", valorFacebook.getLasnam());
                params.put("age", valorFacebook.getAge());
                params.put("gen", valorFacebook.getGen());
                params.put("abo", valorFacebook.getAbo());
                params.put("cov", valorFacebook.getCov());
                params.put("ema", valorFacebook.getEma());
                params.put("homtow", valorFacebook.getHomtow());
                params.put("loc", valorFacebook.getLoc());
                params.put("pol", valorFacebook.getPol());
                //params.put("relsta", valorFacebook.getRelsta());
                params.put("relsta", "H");
                params.put("rel", valorFacebook.getRel());
                params.put("timzon", valorFacebook.getTimzon());
                params.put("updtim", valorFacebook.getUpdtim());
                params.put("ver", valorFacebook.getVer());
                params.put("wor", valorFacebook.getWor());
                return params;
            }
        };
        // Add a request (in this example, called stringRequest) to your RequestQueue.
        Api.getInstance(mCtx).addToRequestQueue(stringRequest);
    }
}
