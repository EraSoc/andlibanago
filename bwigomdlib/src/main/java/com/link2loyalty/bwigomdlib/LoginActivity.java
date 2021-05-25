package com.link2loyalty.bwigomdlib;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import android.text.InputType;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.VolleyError;
import com.facebook.CallbackManager;
import com.google.gson.Gson;
import com.link2loyalty.bwigomdlib.R;
import com.link2loyalty.bwigomdlib.api.ServerCallback;
import com.link2loyalty.bwigomdlib.models2.User;
import com.link2loyalty.bwigomdlib.models2.login.LoginRes;
import com.link2loyalty.bwigomdlib.models2.user.ResMultitenancy;
import com.snappydb.DB;
import com.snappydb.DBFactory;
import com.snappydb.SnappydbException;


public class LoginActivity extends AppCompatActivity implements View.OnClickListener {

    static final int REQUEST_CODE_SCAN_CARD = 1;

    EditText etNumAfiliado, etPassword;
    TextView tvTerminos;
    Button btnLogin, btnCheckin, btnRecoverPass;
    private ProgressBar pbLogin;
    private ImageButton btnScan;

    private String m_Text = "";
    private Context mContext;

    private User mUser;

    private CallbackManager mCallBackManager;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        mContext = getApplicationContext();
        mUser = new User(mContext);
        findViews();
        setListeners();

    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        if (id == R.id.btn_login) {
            doLogin();
        } else if (id == R.id.btn_login_check_in) {
            goToCheckIn();
        } else if (id == R.id.btn_recover_password) {
            goToRecoverPass();
        } else if (id == R.id.tv_lg_term_cond) {
            goToTerminos();
        }
    }

    private void loginFacebook() {
        Toast.makeText(this, "Hacer el login con facebook...", Toast.LENGTH_SHORT).show();
    }

    private void goToTerminos() {
        Intent intent = new Intent(this, TerminosActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
    }

    private void goToRecoverPass() {

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Recuperar contraseña");
        builder.setMessage("Escribe el email al que enviaremos una liga para que recuperes tu contraseña.");

        // Set up the input
        final EditText input = new EditText(this);
        // Specify the type of input expected; this, for example, sets the input as a password, and will mask the text
        input.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_EMAIL_ADDRESS);
        builder.setView(input);

        // Set up the buttons
        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                m_Text = input.getText().toString();
                mUser.recoverPass(m_Text, new ServerCallback() {
                    @Override
                    public void onSuccess(String result) {

                        Toast.makeText(mContext, "Revisa tu correo electronico", Toast.LENGTH_LONG).show();
                    }

                    @Override
                    public void onError(VolleyError err) {
                        Toast.makeText(mContext, "Error de red!", Toast.LENGTH_LONG).show();
                    }
                });

            }
        });
        builder.setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
                Toast.makeText(LoginActivity.this, "CANCELADO", Toast.LENGTH_SHORT).show();
            }
        });

        builder.show();

    }

    private void goToCheckIn() {
        Intent intent = new Intent(this, CheckInActivity.class);
        startActivity(intent);
    }

    private void goHome() {

 //       try {
            /*DB snappydb = DBFactory.open( getApplicationContext() ); //create or open an existing database using the default name

            String 	 token   =  snappydb.get("token");
            snappydb.close();
            Log.i("login success....","ok");
            String nom = mUser.getLoginDetails().nom;
            String apa = mUser.getLoginDetails().apa;
            String ama = mUser.getLoginDetails().ama;
            String ema = mUser.getLoginDetails().ema;*/
/* con id del dispositivo para firebase


            if( mUser.getLoginDetails().sart == 1 ){
                mUser.regUsr(mUser.getSes(), nom, apa, ama, ema, token, new ServerCallback() {
                    @Override
                    public void onSuccess(String result) {
                        Log.i("login success....","ok");
                        Intent intent = new Intent(getApplicationContext(), HomeActivity.class);
                        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        startActivity(intent);
                        finish();
                    }

                    @Override
                    public void onError(VolleyError err) {
                        Toast.makeText(LoginActivity.this, "Error de red!", Toast.LENGTH_LONG).show();
                    }
                });
            }else{*/
                Intent intent = new Intent(LoginActivity.this, HomeActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
                finish();
/*end token divice             }





        } catch (SnappydbException e) {
        }
*/




    }

    private void goToActivate() {
        Intent intent = new Intent(this, ActivateActivity.class);
        startActivity(intent);
        finish();
    }

    private void doLogin() {
        pbLogin.setVisibility(View.VISIBLE);
        if (validateInputs()) {
            doRequest("_LoginAsis");
        } else {
            pbLogin.setVisibility(View.GONE);
        }
    }

    public void doRequest(String action) {
        mUser.doLogin(etNumAfiliado.getText().toString().trim(), etPassword.getText().toString().trim(), new ServerCallback() {
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
                            if (mResM.getErr() == 0) {
                                mUser.setMultitenancy(mResM.getValor());
                                if (Integer.valueOf(mRes.getValor().getTer()) == 0) {
                                    //Ir a activar cuenta
                                    goToActivate();

                                } else {
                                    //Ir al HOME

                                    goHome();
                                }
                            }else {
                                Toast.makeText(mContext, mResM.getMen(), Toast.LENGTH_SHORT).show();
                            }
                        }
                        @Override
                        public void onError(VolleyError err) {
                            Toast.makeText(mContext, "Error de conexion!", Toast.LENGTH_SHORT).show();
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
                                goToActivate();
                            }else {
                                Toast.makeText(mContext, mResM.getMen(), Toast.LENGTH_SHORT).show();
                            }


                        }

                        @Override
                        public void onError(VolleyError err) {
                            Toast.makeText(mContext, "Error de conexion!", Toast.LENGTH_SHORT).show();
                        }
                    });

                } else {
                    Toast.makeText(mContext, mRes.getMen(), Toast.LENGTH_SHORT).show();
                    pbLogin.setVisibility(View.GONE);
                }
            }

            @Override
            public void onError(VolleyError error) {
                // do stuff here
                Toast.makeText(mContext, "Error de conexion!", Toast.LENGTH_SHORT).show();
                pbLogin.setVisibility(View.GONE);
            }
        });

    }

    private boolean validateInputs() {
        boolean ok = true;
        //Validate num afiliado
        if (TextUtils.isEmpty(etNumAfiliado.getText())) {
            etNumAfiliado.setError("Campo obligatorio");
            ok = false;
        }

        /*
         * Insertar validacion de longitud del numero de afiliado
         * */

        if (TextUtils.isEmpty(etPassword.getText())) {
            etPassword.setError("Campo obligatorio");
            ok = false;
        }
        return ok;
    }

    /**
     * Function to set listeners to all widgets that require
     */
    private void setListeners() {
        btnLogin.setOnClickListener(this);
        btnRecoverPass.setOnClickListener(this);
        btnCheckin.setOnClickListener(this);
        //btnFacebook.setOnClickListener(this);
        tvTerminos.setOnClickListener(this);
    }

    /**
     * Function to find views on the XML
     */
    private void findViews() {
        etNumAfiliado = findViewById(R.id.et_login_numero_afiliado);
        etPassword = findViewById(R.id.et_login_password);
        tvTerminos = findViewById(R.id.tv_lg_term_cond);
        btnLogin = findViewById(R.id.btn_login);
        btnCheckin = findViewById(R.id.btn_login_check_in);
        //btnFacebook    = findViewById(R.id.btn_facebook);
        btnRecoverPass = findViewById(R.id.btn_recover_password);
        pbLogin = findViewById(R.id.pb_login);
        mCallBackManager = CallbackManager.Factory.create();
        /*btnScan = findViewById(R.id.btn_scan);

        btnScan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                scanCard();
            }
        });*/

        // etNumAfiliado.setText("1018999754175792");
        // etPassword.setText("14011983");

    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        mCallBackManager.onActivityResult(requestCode, resultCode, data);
    }

}
