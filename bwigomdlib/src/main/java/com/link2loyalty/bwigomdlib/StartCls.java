package com.link2loyalty.bwigomdlib;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.util.Log;
import android.widget.Toast;


import com.android.volley.VolleyError;
import com.google.gson.Gson;
import com.link2loyalty.bwigomdlib.api.ServerCallback;
import com.link2loyalty.bwigomdlib.models2.login.LoginRes;
import com.link2loyalty.bwigomdlib.models2.user.ResMultitenancy;
import com.link2loyalty.bwigomdlib.models2.User;



public class StartCls {
    Context context;
    private User mUser;
    private SharedPreferences.Editor session,multiten,activityx;
    String backact=null;

    public StartCls(Context context){
        this.context=context;
        mUser = new User(context);

    }
    public void startevent(String usrx,String psswd){

        doRequest(usrx,psswd);


/*        Intent inc=new Intent(context,SplashActivity.class);
        inc.addFlags(FLAG_ACTIVITY_NEW_TASK);
        //info base64 encode
        context.startActivity(inc);*/
    }

    public void doRequest(String usrx,String psswd) {
        mUser.doLogin(usrx,psswd, new ServerCallback() {
            @Override
            public void onSuccess(final String response) {
                Gson mGson = new Gson();
                final LoginRes mRes = mGson.fromJson(response, LoginRes.class);

                if (mRes.getErr() == 0) {
                    mUser.setSes(mRes.getValor().ses);
                    mUser.setLoginDetails(mRes.getValor());
                    mUser.multitenancy(mUser.getSes(), new ServerCallback() {
                        @Override
                        public void onSuccess(String result) {
                            Gson mGson = new Gson();
                            ResMultitenancy mResM = mGson.fromJson(result, ResMultitenancy.class);
                            Log.i("Session","....."+mRes.getValor().ses);
                            Log.i("multit","..."+mResM.getValor().getColpri());
                            if (mResM.getErr() == 0) {
                                mUser.setMultitenancy(mResM.getValor());
                                if (Integer.valueOf(mRes.getValor().getTer()) == 0) {
                                    //Ir a activar cuenta
                                    //goToActivate();

                                } else {
                                    session=context.getSharedPreferences("session",context.MODE_PRIVATE).edit();
                                    session.putString("ses",""+mRes.getValor().ses);
                                    session.commit();

                                    multiten=context.getSharedPreferences("multitenancy",context.MODE_PRIVATE).edit();
                                    multiten.putString("data",result);
                                    multiten.commit();

                                    //Ir al HOME
                                    Intent intent = new Intent(context, HomeActivity.class);
                                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                                    context.startActivity(intent);


                                }
                            }else {
                                Toast.makeText(context, mResM.getMen(), Toast.LENGTH_SHORT).show();
                            }
                        }
                        @Override
                        public void onError(VolleyError err) {
                            Toast.makeText(context, "Error de conexion!", Toast.LENGTH_SHORT).show();
                        }
                    });
                } else if(mRes.getErr() == 5) {
                    mUser.setSes(mRes.getSes());
                    mUser.setLoginDetails(mRes.getValor());
                    mUser.multitenancy(mUser.getSes(), new ServerCallback() {
                        @Override
                        public void onSuccess(String result) {

                            Gson mGson = new Gson();
                            ResMultitenancy mResM = mGson.fromJson(result, ResMultitenancy.class);
                            if (mResM.getErr() == 0) {
                                mUser.setMultitenancy(mResM.getValor());
                                //goToActivate();
                            }else {
                                Toast.makeText(context, mResM.getMen(), Toast.LENGTH_SHORT).show();
                            }


                        }

                        @Override
                        public void onError(VolleyError err) {
                            Toast.makeText(context, "Error de conexion!", Toast.LENGTH_SHORT).show();
                        }
                    });

                } else {
                    Toast.makeText(context, mRes.getMen(), Toast.LENGTH_SHORT).show();
                    // pbLogin.setVisibility(View.GONE);
                }
            }

            @Override
            public void onError(VolleyError error) {
                // do stuff here
                Toast.makeText(context, "Error de conexion!", Toast.LENGTH_SHORT).show();
                //pbLogin.setVisibility(View.GONE);
            }
        });

    }
}
