package com.link2loyalty.bwigomdlib;

import android.content.Intent;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.link2loyalty.bwigomdlib.R;

import java.util.regex.Pattern;

public class CheckInActivity extends AppCompatActivity implements View.OnClickListener {

    EditText etEmail, etPassword, etEtPasswordConfirm;
    Button btnCheckIn;
    TextView tvTerminos;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_check_in);
        findViews();
        setListeners();
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        if (id == R.id.btn_check_in) {
            doCheckin();
        } else if (id == R.id.tv_ci_term_cond) {
            goToTerminos();
        }
    }

    private void goToTerminos() {
        Intent intent = new Intent(this, TerminosActivity.class);
        startActivity(intent);
    }

    private void doCheckin() {

        if( validateInputs() ){
            Intent intent = new Intent(this, FormActivity.class);
            startActivity(intent);
        }


    }

    private boolean validateInputs() {
        boolean ok = true;
        //Validate num afiliado
        if( TextUtils.isEmpty(etEmail.getText())  ){
            etEmail.setError("Campo obligatorio");
            ok = false;
        }else if (!validateEmail(String.valueOf(etEmail.getText()))) {
            //Validate email
            etEmail.setError("Email no valido!");
            ok = false;
        }

        /*
         * Insertar validacion de longitud del numero de afiliado
         * */

        if( TextUtils.isEmpty(etPassword.getText())  ){
            etPassword.setError("Campo obligatorio");
            ok = false;
        }
        if( TextUtils.isEmpty(etEtPasswordConfirm.getText())  ){
            etEtPasswordConfirm.setError("Campo obligatorio");
            ok = false;
        }
        if(!etPassword.getText().toString().equals(etEtPasswordConfirm.getText().toString())){
            Toast.makeText(this, "Las contraseñas no coinciden!", Toast.LENGTH_LONG).show();
            ok = false;
        }
        if(etPassword.getText().toString().length() < 6){
            etPassword.setError("Almenos nesesitas 6 digitos alfanuméricos");
            ok = false;
        }
        return ok;
    }

    private boolean validateEmail(String email) {
        Pattern pattern = Patterns.EMAIL_ADDRESS;
        return pattern.matcher(email).matches();
    }


    /**
     * Function to set listeners to all widgets that require
     */
    private void setListeners() {
        btnCheckIn.setOnClickListener(this);
        tvTerminos.setOnClickListener(this);
    }

    /**
     * Function to find views on the XML
     */
    private void findViews() {
        etEmail = findViewById(R.id.et_ci_email);
        etPassword = findViewById(R.id.et_ci_password);
        etEtPasswordConfirm = findViewById(R.id.et_ci_password_confirm);
        btnCheckIn = findViewById(R.id.btn_check_in);
        tvTerminos = findViewById(R.id.tv_ci_term_cond);
    }

}
