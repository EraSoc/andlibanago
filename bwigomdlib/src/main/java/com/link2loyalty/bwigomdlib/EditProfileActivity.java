package com.link2loyalty.bwigomdlib;

import android.app.DatePickerDialog;
import android.content.Context;
import android.graphics.Color;
import android.os.Build;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import androidx.appcompat.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Toast;

import com.link2loyalty.bwigomdlib.R;
import com.link2loyalty.bwigomdlib.models2.User;
import com.link2loyalty.bwigomdlib.models2.user.UserLov;
import com.link2loyalty.bwigomdlib.models2.user.ValorMultitenancy;
import com.link2loyalty.bwigomdlib.models2.usrFacebook.ValorFacebook;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

public class EditProfileActivity extends AppCompatActivity {

    private String title = "Bwigo";
    private Toolbar myToolbar;
    private Context mContext;
    private EditText etName, etPat, etMat, etBirthday, etEmail;
    private EditText etPhone, etMobile;
    private EditText etActualWork;
    private User mUser;
    private UserLov mUserDetails;
    private ValorFacebook valorFacebook;
    private ValorMultitenancy mMultitenancy;

    private EditText etAbout, etOriginCity, etSentimental, etReligion, etPol;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_profile);
        mContext = getApplicationContext();
        myToolbar = findViewById(R.id.tb_edit_profile);
        mUser = new User(mContext);
        mMultitenancy = mUser.getMultitenancy();
        configToolbar();
        renderView();

    }

    private void renderView() {
        //Finds views
        etName = findViewById(R.id.et_edit_profile_name);
        etPat = findViewById(R.id.et_edit_profile_pat);
        etMat = findViewById(R.id.et_edit_profile_mat);
        etBirthday = findViewById(R.id.et_edit_profile_birthday);
        etEmail = findViewById(R.id.et_edit_profile_alternative_email);
        etPhone = findViewById(R.id.et_edit_profile_phone);
        etMobile = findViewById(R.id.et_edit_profile_mobile);
        etActualWork = findViewById(R.id.et_edit_profile_work);
        //etPassword = findViewById(R.id.et_edit_profile_pa);
        etAbout = findViewById(R.id.et_edit_profile_about_my);
        etOriginCity = findViewById(R.id.et_edit_profile_origin_city);
        etSentimental = findViewById(R.id.et_edit_profile_espiritual_sesion);
        etReligion = findViewById(R.id.et_edit_profile_religion);
        etPol = findViewById(R.id.et_edit_profile_political);
        configDataPicker(etBirthday);

        //SET data
        mUserDetails = mUser.getDetails();
        etName.setText(mUserDetails.getNom());
        etPat.setText(mUserDetails.getApa());
        etMat.setText(mUserDetails.getAma());

        if (mUserDetails.getGeneral().getEma().equals("0")) {
            etPhone.setText("");
        } else {
            etEmail.setText(mUserDetails.getGeneral().getEma());
        }


        if (mUserDetails.getContacto().getTel1().equals("0")) {
            etPhone.setText("");
        } else {
            etPhone.setText(mUserDetails.getContacto().getTel1());
        }

        if (mUserDetails.getContacto().getTel2().equals("0")) {
            etMobile.setText("");
        } else {
            etMobile.setText(mUserDetails.getContacto().getTel2());
        }

        if (mUserDetails.getGeneral().getOcu().equals("0")) {
            etActualWork.setText("");
        } else {
            etActualWork.setText(mUserDetails.getGeneral().getOcu());
        }

        String[] splited = mUserDetails.getGeneral().getOno().split("\\s+");
        etBirthday.setText(splited[0]);

        valorFacebook = mUser.getFacebookVal();
        etAbout.setText(valorFacebook.getAbo());
        etOriginCity.setText(valorFacebook.getHomtow());
        etSentimental.setText(valorFacebook.getRelsta());
        etReligion.setText(valorFacebook.getRel());
        etPol.setText(valorFacebook.getPol());

    }

    private void configToolbar() {
        setSupportActionBar(myToolbar);
        getSupportActionBar().setTitle(title);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        //Config multitenancy
        if (mMultitenancy.getColpri() != null) {
            myToolbar.setBackgroundColor(Color.parseColor("#" + mMultitenancy.getColpri()));
            //myToolbar.setTitle( mMultitenancy.getDes() );
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                Window window = getWindow();
                window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
                window.setStatusBarColor(Color.parseColor("#" + mMultitenancy.getColosc()));
            }
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_save_profile, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle item selection
        if (item.getItemId() == R.id.save_profile) {
            saveProfile();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void saveProfile() {
        updFacebook();
        updateUser();

        mUser.updateDetails(mUser.getSes(), mUserDetails);
        mUser.updFacebook( mUser.getSes(), valorFacebook );
        Toast.makeText(this, "Datos actualizados", Toast.LENGTH_SHORT).show();
        finish();
    }

    private void updFacebook(){

        valorFacebook.setAbo(etAbout.getText().toString().trim());
        valorFacebook.setHomtow(etOriginCity.getText().toString().trim());
        valorFacebook.setRelsta(etSentimental.getText().toString().trim());
        valorFacebook.setRel(etReligion.getText().toString().trim());
        valorFacebook.setPol(etPol.getText().toString().trim());

    }

    private void updateUser() {
        mUserDetails.setNom(etName.getText().toString().trim());
        mUserDetails.setApa(etPat.getText().toString().trim());
        mUserDetails.setAma(etMat.getText().toString().trim());
        mUserDetails.getGeneral().setOno(etBirthday.getText().toString().trim());
        mUserDetails.getGeneral().setEma(etEmail.getText().toString().trim());
        mUserDetails.getContacto().setTel1(etPhone.getText().toString().trim());
        mUserDetails.getContacto().setTel2(etMobile.getText().toString().trim());
        mUserDetails.getGeneral().setOcu(etActualWork.getText().toString().trim());
    }

    private void configDataPicker(final EditText et){


        final Calendar myCalendar = Calendar.getInstance();

        final DatePickerDialog.OnDateSetListener date = new DatePickerDialog.OnDateSetListener() {

            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear,
                                  int dayOfMonth) {
                // TODO Auto-generated method stub
                myCalendar.set(Calendar.YEAR, year);
                myCalendar.set(Calendar.MONTH, monthOfYear);
                myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);

                String myFormat = "yyyy-MM-dd"; //In which you need put here
                SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);

                et.setText(sdf.format(myCalendar.getTime()));



            }

        };

        et.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                new DatePickerDialog(EditProfileActivity.this, date, myCalendar
                        .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                        myCalendar.get(Calendar.DAY_OF_MONTH)).show();
            }
        });


    }

}
