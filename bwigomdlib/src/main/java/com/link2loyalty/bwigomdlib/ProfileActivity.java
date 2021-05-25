package com.link2loyalty.bwigomdlib;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.media.ExifInterface;
import android.media.Image;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.provider.MediaStore;
import com.google.android.material.appbar.AppBarLayout;
import com.google.android.material.appbar.CollapsingToolbarLayout;
import androidx.fragment.app.DialogFragment;
import androidx.core.content.FileProvider;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import androidx.appcompat.widget.Toolbar;

import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.VolleyError;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.google.gson.Gson;
import com.link2loyalty.bwigomdlib.R;
import com.link2loyalty.bwigomdlib.api.ServerCallback;
import com.link2loyalty.bwigomdlib.fragments.PhotoGalleryFragment;
import com.link2loyalty.bwigomdlib.models2.CheckGpsPromo;
import com.link2loyalty.bwigomdlib.models2.Coupon;
import com.link2loyalty.bwigomdlib.models2.User;
import com.link2loyalty.bwigomdlib.models2.user.UserDetRes;
import com.link2loyalty.bwigomdlib.models2.user.UserLov;
import com.link2loyalty.bwigomdlib.models2.user.ValorMultitenancy;
import com.link2loyalty.bwigomdlib.models2.usrFacebook.ResDetFacebook;
import com.link2loyalty.bwigomdlib.models2.usrFacebook.ValorFacebook;
import com.snappydb.DB;
import com.snappydb.DBFactory;
import com.snappydb.SnappydbException;

import org.w3c.dom.Text;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Locale;

public class ProfileActivity extends AppCompatActivity {

    private AppBarLayout mAppBar;
    private CollapsingToolbarLayout mCollapsingTolbarLayout;

    private String title = "Perfil";
    private Toolbar myToolbar;
    private Context mContext;
    private TextView tvName, tvPat, tvMat, tvBirthday, tvEmail, tvPassword;
    private ImageView ivProfile;

    private TextView tvPhone, tvMobile, tvWork;

    private TextView tvAbout, tvOriginCity, tvEmotionalSituation, tvReligion, tvPolitical,
            tvAddress, tvEmailAlternative;

    private String ses;

    static final int REQUEST_IMAGE_CAPTURE = 1;
    static final int REQUEST_IMAGE_GALLERY = 0;
    String mCurrentPhotoPath;

    private User mUser;
    private Coupon mCoupon;
    private LinearLayout imgtar;
    private TextView nommem;
    private TextView emamem;




    SimpleDateFormat formatter6 = new SimpleDateFormat("yyyy-MM-dd", Locale.US);

    private ValorMultitenancy mMultitenancy;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        mContext = getApplicationContext();
        mUser = new User(mContext);
        mCoupon = new Coupon(mContext);
        mMultitenancy = mUser.getMultitenancy();

        mAppBar = findViewById(R.id.appbar);
        mCollapsingTolbarLayout = findViewById(R.id.collapsing_toolbar);

        imgtar=(LinearLayout) findViewById(R.id.bkimgtar);
        nommem=(TextView) findViewById(R.id.nommem);
        emamem=(TextView) findViewById(R.id.emamem);
        configToolbar();
        getDetUsuario();
    }

    @Override
    protected void onResume() {
        super.onResume();
        getDetUsuario();
    }

    private void getDetUsuario() {

        mUser.getDetailsRes(mUser.getSes(), new ServerCallback() {
            @Override
            public void onSuccess(String response) {

                Gson mGson = new Gson();
                UserDetRes mUserDetailRes = mGson.fromJson(response, UserDetRes.class);
                Log.i("response...",""+response);
                UserLov mUserDetail = mUserDetailRes.getLov().get(0);
                mUser.setDetails(mUserDetail);
                renderView();
            }

            @Override
            public void onError(VolleyError error) {
                // do stuff here
                Toast.makeText(mContext, "Error de conexion!", Toast.LENGTH_SHORT).show();
            }
        });

        mUser.getFacebookRes(mUser.getSes(), new ServerCallback() {
            @Override
            public void onSuccess(String response) {

                Gson mGson = new Gson();
                ResDetFacebook resFacebook = mGson.fromJson(response, ResDetFacebook.class);
                ValorFacebook facebookVal = resFacebook.getValor();
                mUser.setFacebook( facebookVal );
                renderViewFacebook();
            }

            @Override
            public void onError(VolleyError error) {
                // do stuff here
                Toast.makeText(mContext, "Error de conexion!", Toast.LENGTH_SHORT).show();
            }
        });

    }

    private void renderViewFacebook(){
        tvAbout = findViewById(R.id.tv_pf_about);
        tvOriginCity = findViewById(R.id.tv_pf_origin_city);
        tvEmotionalSituation = findViewById(R.id.tv_pf_emotional_situation);
        tvReligion = findViewById(R.id.tv_pf_religion);
        tvPolitical = findViewById(R.id.tv_pf_political);
        tvAddress = findViewById(R.id.tv_pf_address);
        tvEmailAlternative = findViewById(R.id.tv_pf_email_alternative);

        //FACEBOOK DATA

        ValorFacebook facebookVal = mUser.getFacebookVal();
        tvAbout.setText(facebookVal.getAbo());
        tvOriginCity.setText(facebookVal.getHomtow());
        tvEmotionalSituation.setText(facebookVal.getRelsta());
        tvReligion.setText(facebookVal.getRel());
        tvPolitical.setText(facebookVal.getPol());
        tvAddress.setText(""); //<---DIRECCIÓN PAPU!!
        //tvEmailAlternative.setText();

        //END FACEBOOK DATA
    }

    private void renderView() {
        //Find views
        tvName = findViewById(R.id.tv_pf_name);
        tvPat = findViewById(R.id.tv_pf_pat);
        tvMat = findViewById(R.id.tv_pf_mat);
        tvBirthday = findViewById(R.id.tv_pf_birthday);
        tvEmail = findViewById(R.id.tv_pf_email);
        tvPassword = findViewById(R.id.tv_pf_password);
        ivProfile = findViewById(R.id.iv_profile_photo);
        tvPhone = findViewById(R.id.tv_pf_phone);
        tvMobile = findViewById(R.id.tv_pf_mobile_2);
        tvWork = findViewById(R.id.tv_pf_work);



        //SET data
        UserLov mUserDetails = mUser.getDetails();
        Log.i("sess tar",""+mUser.getSes());

        if(mUserDetails.getImgtar()!="0"){
            new DownloadImageTask(imgtar).execute(mUserDetails.getImgtar());
            nommem.setText(mUserDetails.getNom()+" "+mUserDetails.getApa());
            emamem.setText(mUserDetails.getFoltar());
        }
        tvName.setText(mUserDetails.getNom());
        tvPat.setText(mUserDetails.getApa());
        tvMat.setText(mUserDetails.getAma());

        tvEmail.setText(mUserDetails.getGeneral().getEma());
        tvPassword.setText("***********");
        tvPhone.setText(mUserDetails.getContacto().getTel1());
        tvMobile.setText(mUserDetails.getContacto().getTel2());

        tvWork.setText(mUserDetails.getGeneral().getOcu());




        String[] splited = mUserDetails.getGeneral().getOno().split("\\s+");
        tvBirthday.setText(splited[0]);


        if (mUser.getImage() != null) {
            Glide.with(ProfileActivity.this)
                    .load(mUser.getImage())
                    .apply(RequestOptions.circleCropTransform())
                    .into(ivProfile);
        }


        ivProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DialogFragment newFragment = new PhotoGalleryFragment();
                newFragment.show(getSupportFragmentManager(), "missiles");
            }
        });

    }

    private void configToolbar() {
        myToolbar = findViewById(R.id.tb_profile);
        setSupportActionBar(myToolbar);
        getSupportActionBar().setTitle(title);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        //Config multitenancy
        if (mMultitenancy.getColpri() != null) {
            mAppBar.setBackgroundColor(Color.parseColor("#" + mMultitenancy.getColpri()));
            //myToolbar.setTitle( mMultitenancy.getDes() );
            mCollapsingTolbarLayout.setBackgroundColor(Color.parseColor("#" + mMultitenancy.getColpri()));
            mCollapsingTolbarLayout.setContentScrim(new ColorDrawable(Color.parseColor("#" + mMultitenancy.getColpri())));

            myToolbar.setBackgroundColor(Color.TRANSPARENT);
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
        inflater.inflate(R.menu.menu_profile, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle item selection
        if (item.getItemId() == R.id.profile_edit) {
            Intent intent = new Intent(ProfileActivity.this, EditProfileActivity.class);
            startActivity(intent);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        getImage();

        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK) {
            //Bundle extras = data.getExtras();
            //Bitmap imageBitmap = (Bitmap) extras.get("data");
            //mImageView.setImageBitmap(imageBitmap);
            File file = new File(mCurrentPhotoPath);
            Uri uri = Uri.fromFile(file);
            mUser.setImage(uri);
            Bitmap bitmap;
            try {
                bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), uri);
                //bitmap = crupAndScale(bitmap, 300); // if you mind scaling
                Toast.makeText(this, "image: " + String.valueOf(bitmap), Toast.LENGTH_LONG).show();
                ExifInterface ei = new ExifInterface(mCurrentPhotoPath);
                int orientation = ei.getAttributeInt(ExifInterface.TAG_ORIENTATION,
                        ExifInterface.ORIENTATION_UNDEFINED);
                Bitmap rotatedBitmap = null;
                switch (orientation) {
                    case ExifInterface.ORIENTATION_ROTATE_90:
                        rotatedBitmap = rotateImage(bitmap, 90);
                        break;

                    case ExifInterface.ORIENTATION_ROTATE_180:
                        rotatedBitmap = rotateImage(bitmap, 180);
                        break;

                    case ExifInterface.ORIENTATION_ROTATE_270:
                        rotatedBitmap = rotateImage(bitmap, 270);
                        break;

                    case ExifInterface.ORIENTATION_NORMAL:
                    default:
                        rotatedBitmap = bitmap;
                }
                Glide.with(this)
                        .load(mUser.getImage())
                        .apply(RequestOptions.circleCropTransform())
                        .into(ivProfile);
            } catch (FileNotFoundException e) {
                // TODO Auto-generated catch block

                e.printStackTrace();
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();

            }

            galleryAddPic();
        } else if (requestCode == REQUEST_IMAGE_GALLERY && resultCode == RESULT_OK) {
            Uri imageUri = data.getData();
            mUser.setImage(imageUri);
            Glide.with(this)
                    .load(mUser.getImage())
                    .apply(RequestOptions.circleCropTransform())
                    .into(ivProfile);

        }
    }

    public static Bitmap rotateImage(Bitmap source, float angle) {
        Matrix matrix = new Matrix();
        matrix.postRotate(angle);
        return Bitmap.createBitmap(source, 0, 0, source.getWidth(), source.getHeight(),
                matrix, true);
    }

    private void galleryAddPic() {
        Intent mediaScanIntent = new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE);
        File f = new File(mCurrentPhotoPath);
        //Uri contentUri = Uri.fromFile(f);
        Uri contentUri = FileProvider.getUriForFile(this,
                "com.example.camara",
                f);
        mediaScanIntent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
        mediaScanIntent.setData(contentUri);
        sendBroadcast(mediaScanIntent);
    }


    private void getImage() {
        try {
            DB snappydb = DBFactory.open(this); //create or open an existing databse using the default name
            mCurrentPhotoPath = snappydb.get("mCurrentPhotoPath");
            snappydb.close();
        } catch (SnappydbException e) {
        }
    }

    private class DownloadImageTask extends AsyncTask<String,Void,Bitmap>{
        LinearLayout bmimage;
        public DownloadImageTask(LinearLayout bmimage){
            this.bmimage=bmimage;
        }

        @Override
        protected void onPostExecute(Bitmap bitmap) {
            super.onPostExecute(bitmap);
            Drawable drbt=new BitmapDrawable(bitmap);
            bmimage.setBackground(drbt);
        }

        @Override
        protected Bitmap doInBackground(String... strings) {
            String urldisplay =strings[0];
            Bitmap miimage=null;
            try{
                InputStream in=new java.net.URL(urldisplay).openStream();
                miimage=BitmapFactory.decodeStream(in);

            }catch(Exception e){
                e.printStackTrace();
                Log.i("error","---"+e.getMessage());
            }
            return miimage;
        }
    }

}
