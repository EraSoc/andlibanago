package com.link2loyalty.bwigomdlib.models2;

import android.content.Context;
import android.content.DialogInterface;
import androidx.appcompat.app.AlertDialog;
import android.util.Log;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.google.gson.Gson;
import com.link2loyalty.bwigomdlib.api.Api;
import com.link2loyalty.bwigomdlib.api.ServerCallback;
import com.link2loyalty.bwigomdlib.models2.canje.ResCanje;
import com.link2loyalty.bwigomdlib.models2.login.Valor;
import com.link2loyalty.bwigomdlib.models2.test.AnswerList;
import com.link2loyalty.bwigomdlib.models2.test.QuestionList;
import com.link2loyalty.bwigomdlib.models2.test.ResTest;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;


public class Test {

    private Context mContext;
    private User mUser;
    private ArrayList answers = new ArrayList();

    private int questionPosition = 0;
    private ArrayList<QuestionList> mQuestions = new ArrayList<>();

    private boolean ban = false;
    private int banId = 0;
    private boolean cancelBan = false;



    public Test(Context mContext) {
        this.mContext = mContext;
        mUser = new User(mContext);
    }


    public void runTest(){

        answers.clear();

        questionPosition = 0;
        mQuestions.clear();

        ban = false;
        banId = 0;
        cancelBan = false;


        if(doTest()){
            getListTest(new ServerCallback() {
                @Override
                public void onSuccess(String result) {
                    Gson mGson = new Gson();
                    ResTest mResTest = mGson.fromJson(result, ResTest.class);

                    if(mResTest.getErr() == 0){
                        mQuestions = mResTest.getPreguntas();
                        executeQuestions();

                    }else{
                        Toast.makeText(mContext, mResTest.getMen(), Toast.LENGTH_SHORT).show();
                    }

                }

                @Override
                public void onError(VolleyError err) {
                    Toast.makeText(mContext, "Error de conexión D:", Toast.LENGTH_SHORT).show();
                }
            });

        }else{
            canje(new ServerCallback() {
                @Override
                public void onSuccess(String result) {
                    answers.clear();
                    ban=false;
                    banId=0;
                    questionPosition = 0;
                    Gson mGson = new Gson();
                    ResCanje mResCanje = mGson.fromJson(result, ResCanje.class);
                    Toast.makeText(mContext, mResCanje.getMen(), Toast.LENGTH_SHORT).show();
                }

                @Override
                public void onError(VolleyError err) {
                    Toast.makeText(mContext, "Error al canjear!", Toast.LENGTH_SHORT).show();
                }
            });
        }
    }

    private void executeQuestions(){

        if( questionPosition >= mQuestions.size()){
            if( !cancelBan ){
                String answerSanetize = String.valueOf(answers).replace("[", "");
                answerSanetize = answerSanetize.replace("]", "");

                Log.d("answers", answerSanetize);


                sendAnswers(answerSanetize, new ServerCallback() {
                    @Override
                    public void onSuccess(String result) {
                        canje(new ServerCallback() {
                            @Override
                            public void onSuccess(String result) {
                                answers.clear();
                                ban=false;
                                banId=0;
                                questionPosition = 0;
                                Gson mGson = new Gson();
                                ResCanje mResCanje = mGson.fromJson(result, ResCanje.class);
                                Toast.makeText(mContext, mResCanje.getMen(), Toast.LENGTH_SHORT).show();
                            }

                            @Override
                            public void onError(VolleyError err) {

                            }
                        });
                    }

                    @Override
                    public void onError(VolleyError err) {

                    }
                });
            }

        }else{
            QuestionList mQuestion = mQuestions.get(questionPosition);
            executeQuestion(mQuestion);
        }


    }



    private void executeQuestion(QuestionList mQuestion){
        //si hay bandera activada checar si muestro o no la pregunta
        if(ban){
            if( mQuestion.getIdp() == banId ){
                selectType(mQuestion);
            }else{
                answers.add("0");
                questionPosition ++;
                executeQuestions();
            }
        }else{
            selectType(mQuestion);
        }
    }

    private void selectType(QuestionList mQuestion){
        switch (Integer.valueOf(mQuestion.getTpr())){
            case 1:
                break;
            case 2:
                break;
            case 3:
                excluyenteAlert(mQuestion);
                break;
            case 4:
                break;
            case 5:
                break;
        }
    }




    private boolean doTest() {
        Valor loginDetails = mUser.getLoginDetails();
        if(loginDetails.getTes() > 0){
            return false;
        }else{
            return true;
        }
    }



    private void excluyenteAlert(final QuestionList mQuestion){
        ArrayList itemsA = new ArrayList<String>();
        for (AnswerList answer : mQuestion.getRespuestas()) {
            itemsA.add(answer.getRes());
        }
        final CharSequence[] items = (CharSequence[]) itemsA.toArray(new CharSequence[itemsA.size()]);
        final AlertDialog.Builder builder = new AlertDialog.Builder(mContext);
        builder.setTitle(mQuestion.getPre());

        builder.setItems(items, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int item) {
                // Do something with the selection
                Log.d("pepe", "=======================");
                Log.d("pepe", String.valueOf(item));
                Log.d("pepe", String.valueOf(items[item]));
                Log.d("pepe", "=======================");
                dialog.dismiss();
                questionPosition ++;
                answers.add(item);
                ban = false;
                banId = 0;
                for( AnswerList respuesta: mQuestion.getRespuestas() ){
                    if( respuesta.getBan() != 0 ){
                        ban=true;
                        banId = mQuestion.getRespuestas().get(item).getBan();
                    }
                }

                executeQuestions();
            }
        });

        builder.setNegativeButton("cancelar", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                cancelBan = true;
                questionPosition = mQuestions.size() + 1;
            }
        });

        builder.show();
    }


    private void canje( final ServerCallback callback ){
        String url = "http://link2loyalty.com/CertificadoBoleto/api/CanjecAsis";
        // Request a string response from the provided URL.
        StringRequest stringRequest = new StringRequest(
                Request.Method.POST,
                url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        callback.onSuccess(response);
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                callback.onError(error);
            }
        }) {
            @Override
            protected Map<String, String> getParams() {
                String cam = mUser.getLoginDetails().getCambol();
                Map<String, String> params = new HashMap<String, String>();
                params.put("cod", "0");
                params.put("cam", mUser.getLoginDetails().getCambol());
                params.put("cin", "2");
                params.put("fol", mUser.getLoginDetails().getFol());
                return params;
            }
        };
        // Add a request (in this example, called stringRequest) to your RequestQueue.
        Api.getInstance(mContext).addToRequestQueue(stringRequest);
    }

    private void getListTest(final ServerCallback callback) {
        String url = "http://link2loyalty.com/AsistenciasL2L/api/_ListaTest";
        // Request a string response from the provided URL.
        StringRequest stringRequest = new StringRequest(
                Request.Method.POST,
                url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        callback.onSuccess(response);
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                callback.onError(error);
            }
        }) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<String, String>();
                params.put("ses", mUser.getSes());
                params.put("idt", String.valueOf(mUser.getLoginDetails().getTes()));
                return params;
            }
        };
        // Add a request (in this example, called stringRequest) to your RequestQueue.
        Api.getInstance(mContext).addToRequestQueue(stringRequest);
    }

    private void sendAnswers(final String mAnswers, final ServerCallback callback) {
        String url = "http://www.link2loyalty.com/AsistenciasL2L/api/_GuardaTest";
        // Request a string response from the provided URL.
        StringRequest stringRequest = new StringRequest(
                Request.Method.POST,
                url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        callback.onSuccess(response);
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                callback.onError(error);
            }
        }) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<String, String>();
                params.put("ses", mUser.getSes());
                params.put("res", mAnswers);
                params.put("idp", "0");
                params.put("idt", String.valueOf(mUser.getLoginDetails().getTes()));
                return params;
            }
        };
        // Add a request (in this example, called stringRequest) to your RequestQueue.
        Api.getInstance(mContext).addToRequestQueue(stringRequest);
    }


}
