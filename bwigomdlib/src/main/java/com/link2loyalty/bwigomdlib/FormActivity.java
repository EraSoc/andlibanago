package com.link2loyalty.bwigomdlib;

import android.app.DatePickerDialog;
import android.content.Intent;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import androidx.appcompat.widget.Toolbar;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Toast;

import com.link2loyalty.bwigomdlib.R;

import java.util.Calendar;

public class FormActivity extends AppCompatActivity implements View.OnClickListener {

    private static final String CERO = "0";
    private static final String BARRA = "/";

    //Calendario para obtener fecha & hora
    public final Calendar c = Calendar.getInstance();
    //Variables para obtener la fecha
    final int mes = c.get(Calendar.MONTH);
    final int dia = c.get(Calendar.DAY_OF_MONTH);
    final int anio = c.get(Calendar.YEAR);



    //Widgets that view use
    EditText etName, etPaterno, etMaterno,etBirthday, etPoliza;
    Button btnNext;
    Toolbar myToolbar;
    RadioButton rbMan, rbWoman;

    private String gender = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_form);
        findViews();
        setListeners();
        configToolbar();

    }

    private void configToolbar() {
        setSupportActionBar(myToolbar);
        getSupportActionBar().setTitle("Bienvenido");
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.btn_form_next) {
            goToStart();
        }

    }


    public void onRadioButtonClicked(View view){
        // Is the button now checked?
        boolean checked = ((RadioButton) view).isChecked();
        // hacemos un case con lo que ocurre cada vez que pulsemos un botón
        int id = view.getId();
        if (id == R.id.rb_gender_man) {
            if (checked) {
                gender = "man";
            }
        } else if (id == R.id.rb_gender_woman) {
            if (checked) {
                gender = "woman";
            }
        }
    }

    private void goToStart() {

        if( validateInputs() ){
            //TODO- obtener los campos mandarlos he implementar el registro
            String name, gender, apePat, apeMat, birthday, policeNumber;
            name = etName.getText().toString().trim();
            gender = this.gender;
            apePat = etPaterno.getText().toString().trim();
            apeMat = etMaterno.getText().toString().trim();
            birthday = etBirthday.getText().toString().trim();
            policeNumber = etPoliza.getText().toString().trim();




            Intent intent = new Intent(FormActivity.this, OnboardingActivity.class);
            startActivity(intent);

            finish();

        }
    }

    private boolean validateInputs() {
        boolean ok = true;
        //Validate not null fields
        if( TextUtils.isEmpty(etName.getText())  ){
            etName.setError("Campo obligatorio");
            ok = false;
        }
        if( TextUtils.isEmpty(etPaterno.getText())  ){
            etPaterno.setError("Campo obligatorio");
            ok = false;
        }
        if( TextUtils.isEmpty(etMaterno.getText())  ){
            etMaterno.setError("Campo obligatorio");
            ok = false;
        }
        if( TextUtils.isEmpty(etBirthday.getText())  ){
            etBirthday.setError("Campo obligatorio");
            ok = false;
        }
        if( TextUtils.isEmpty(etPoliza.getText())  ){
            etPoliza.setError("Campo obligatorio");
            ok = false;
        }
        if( gender == null ){
            Toast.makeText(this, "Selecciona tu genero!", Toast.LENGTH_SHORT).show();
            ok = false;
        }
        return ok;
    }

    private void setListeners() {
        btnNext.setOnClickListener(this);

        etBirthday.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                obtenerFecha();
            }
        });
    }

    private void findViews() {
        etName     = findViewById(R.id.et_form_name);
        etPaterno  = findViewById(R.id.et_form_name_paterno);
        etMaterno  = findViewById(R.id.et_form_name_materno);
        etBirthday = findViewById(R.id.et_form_birthday);
        etPoliza   = findViewById(R.id.et_form_poliza);
        btnNext    = findViewById(R.id.btn_form_next);
        myToolbar  =  findViewById(R.id.tb_form);
        rbMan      = findViewById(R.id.rb_gender_man);
        rbWoman    = findViewById(R.id.rb_gender_woman);

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
                etBirthday.setText(diaFormateado + BARRA + mesFormateado + BARRA + year);
            }
            //Estos valores deben ir en ese orden, de lo contrario no mostrara la fecha actual
            /**
             *También puede cargar los valores que usted desee
             */
        }, anio, mes, dia);
        //Muestro el widget
        recogerFecha.show();

    }


}
