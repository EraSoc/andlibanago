package com.link2loyalty.bwigomdlib;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import android.app.DatePickerDialog;
import android.content.Context;
import android.content.Intent;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import androidx.appcompat.widget.Toolbar;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Spinner;
import android.widget.Toast;


import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.google.gson.Gson;
import com.link2loyalty.bwigomdlib.R;
import com.link2loyalty.bwigomdlib.api.Api;
import com.link2loyalty.bwigomdlib.api.models.ActivationModel;
import com.link2loyalty.bwigomdlib.api.models.LoginValue;
import com.link2loyalty.bwigomdlib.models2.User;
import com.snappydb.DB;
import com.snappydb.DBFactory;
import com.snappydb.SnappydbException;

import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

public class ActivateActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    private Toolbar myToolbar;
    private static final String CERO = "0";
    private static final String BARRA = "-";
    //Calendario para obtener fecha & hora
    public final Calendar c = Calendar.getInstance();
    //Variables para obtener la fecha
    final int mes = c.get(Calendar.MONTH);
    final int dia = c.get(Calendar.DAY_OF_MONTH);
    final int anio = c.get(Calendar.YEAR);

    private String gender = null;
    RadioButton rbMan, rbWoman;

    private EditText etName, etApePat, etApeMat, etBirthday, etEmail, etOcupacion;
    private Button btnActivate;
    private CheckBox cbTerminos;

    private Spinner spEciv;

    private Context mContext;

    private User mUser;

    String ses;
    String name;
    String apa;
    String ama;
    String gen;
    String eciv = "S";
    String ono;
    String ema;
    String ocu;
    int ter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_activate);
        mContext = getApplicationContext();
        mUser = new User(mContext);
        findViews();
        configToolbar();
        btnActivate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                doActivation();
            }
        });

        etBirthday.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                obtenerFecha();
            }
        });

    }

    private void doActivation() {
        if( validateInputs() ){

            //Get data to send

            name = etName.getText().toString().trim();
            apa = etApePat.getText().toString().trim();
            ama = etApeMat.getText().toString().trim();
            gen = gender;
            //eciv = etCivil.getText().toString().trim();
            ono = etBirthday.getText().toString().trim();
            ema = etEmail.getText().toString().trim();
            ocu = etOcupacion.getText().toString().trim();
            ter = 1;
            //Get sesion
            Log.d("Activacion", mUser.getSes());
            Log.d("Activacion", mUser.getSes());
            doRequest("_ActivacionBvn");



        }
    }

    public void doRequest(String action){

        String url = Api.baseUrlUsers + action;
        // Request a string response from the provided URL.
        StringRequest stringRequest = new StringRequest(
                Request.Method.POST,
                url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        // Use the response

                        Gson mGson = new Gson();
                        ActivationModel mActivationResponse = mGson.fromJson(response, ActivationModel.class);
                        Toast.makeText(ActivateActivity.this, String.valueOf(mActivationResponse.getMen()),
                                Toast.LENGTH_SHORT).show();

                        if( mActivationResponse.getErr() == 0 ){
                            goHome();
                        }

                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                //pbLogin.setVisibility(View.GONE);

                Toast.makeText(mContext, "Error de conección!", Toast.LENGTH_LONG).show();
            }
        }){
            @Override
            protected Map<String, String> getParams()
            {

                Log.d("Activacion", mUser.getSes());
                Map<String, String>  params = new HashMap<String, String>();
                params.put("ses", mUser.getSes());
                params.put("nom", name);
                params.put("apa", apa);
                params.put("ama", ama);
                params.put("gen", gen);
                params.put("eciv", eciv);
                params.put("ono", ono);
                params.put("ema", ema);
                params.put("ocu", ocu);
                params.put("ter", String.valueOf(ter));

                return params;
            }
        };

        // Add a request (in this example, called stringRequest) to your RequestQueue.
        Api.getInstance(this).addToRequestQueue(stringRequest);
    }

    private void findViews() {
        myToolbar   = findViewById(R.id.tb_activate);
        etName      = findViewById(R.id.et_activate_name);
        etApePat    = findViewById(R.id.et_activate_name_paterno);
        etApeMat    = findViewById(R.id.et_activate_name_materno);
        etBirthday  = findViewById(R.id.et_activate_birthday);
        //etCivil     = findViewById(R.id.et_activate_ecivil);
        etEmail     = findViewById(R.id.et_activate_email);
        etOcupacion = findViewById(R.id.et_activate_ocupacion);
        btnActivate = findViewById(R.id.btn_activate_next);
        cbTerminos  = findViewById(R.id.checkbox_terminos);
        spEciv      = findViewById(R.id.sp_estado_civil);

        // Create an ArrayAdapter using the string array and a default spinner layout
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.eciv_array, android.R.layout.simple_spinner_item);
        // Specify the layout to use when the list of choices appears
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        // Apply the adapter to the spinner
        spEciv.setAdapter(adapter);
        spEciv.setOnItemSelectedListener(this);

    }

    private void goHome() {

        try {
            DB snappydb = DBFactory.open(mContext);

            LoginValue loginValue = new LoginValue();
            loginValue.setSes( ses );
            loginValue.setNom( name );
            loginValue.setAma( ama );
            loginValue.setApa( apa );
            loginValue.setEma( ema );
            loginValue.setTer( ter );

            snappydb.put("loginValue", loginValue);
            snappydb.put("logged", true);
            snappydb.close();

            Intent intent = new Intent(this, OnboardingActivity.class);
            startActivity(intent);
            finish();
        } catch (SnappydbException e) {
            e.printStackTrace();
        }


    }

    private void configToolbar() {
        setSupportActionBar(myToolbar);
        getSupportActionBar().setTitle("Activar cuenta");
    }

    private void obtenerFecha() {
        DatePickerDialog recogerFecha = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                //Esta variable lo que realiza es aumentar en uno el mes ya que comienza desde 0 = enero
                final int mesActual = month + 1;
                //Formateo el día obtenido: antepone el 0 si son menores de 10
                String diaFormateado = (dayOfMonth < 10) ? CERO + String.valueOf(dayOfMonth) : String.valueOf(dayOfMonth);
                //Formateo el mes obtenido: antepone el 0 si son menores de 10
                String mesFormateado = (mesActual < 10) ? CERO + String.valueOf(mesActual) : String.valueOf(mesActual);
                //Muestro la fecha con el formato deseado
                etBirthday.setText( year + BARRA + mesFormateado + BARRA + diaFormateado );
            }
            //Estos valores deben ir en ese orden, de lo contrario no mostrara la fecha actual
            /**
             *También puede cargar los valores que usted desee
             */
        }, anio, mes, dia);
        //Muestro el widget
        recogerFecha.show();

    }

    public void onRadioButtonClicked(View view){
        // Is the button now checked?
        boolean checked = ((RadioButton) view).isChecked();
        // hacemos un case con lo que ocurre cada vez que pulsemos un botón
        int id = view.getId();
        if (id == R.id.rb_gender_man) {
            if (checked) {
                gender = "m";
            }
        } else if (id == R.id.rb_gender_woman) {
            if (checked) {
                gender = "f";
            }
        }
    }

    private boolean validateInputs() {
        boolean ok = true;
        //Validate not null fields
        if( TextUtils.isEmpty(etName.getText())  ){
            etName.setError("Campo obligatorio");
            ok = false;
        }
        if( TextUtils.isEmpty(etApePat.getText())  ){
            etApePat.setError("Campo obligatorio");
            ok = false;
        }
        if( TextUtils.isEmpty(etApeMat.getText())  ){
            etApeMat.setError("Campo obligatorio");
            ok = false;
        }
        if( TextUtils.isEmpty(etBirthday.getText())  ){
            etBirthday.setError("Campo obligatorio");
            ok = false;
        }
        if( TextUtils.isEmpty(etEmail.getText())  ){
            etEmail.setError("Campo obligatorio");
            ok = false;
        }

        if( TextUtils.isEmpty(etOcupacion.getText())  ){
            etOcupacion.setError("Campo obligatorio");
            ok = false;
        }
        if( gender == null ){
            Toast.makeText(this, "Selecciona tu genero!", Toast.LENGTH_SHORT).show();
            ok = false;
        }
        if(!cbTerminos.isChecked()){
            Toast.makeText(this, "Debes aceptar los Términos y condiciones!", Toast.LENGTH_SHORT).show();
            ok = false;
        }
        return ok;
    }

    public void goTerminos(View view){
        Intent intent = new Intent(getApplicationContext(), TerminosActivity.class);
        startActivity(intent);
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {



        if (parent.getItemAtPosition(position) == "Soltero") {
            eciv = "S";
        } else if (parent.getItemAtPosition(position) == "Casado") {
            eciv = "C";
        } else if (parent.getItemAtPosition(position) == "Divorciado") {
            eciv = "D";
        } else if (parent.getItemAtPosition(position) == "Conjubinato") {
            eciv = "C";
        }








    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
}
