package com.link2loyalty.bwigomdlib;

import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Build;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.ContextCompat;

import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.VolleyError;
import com.google.gson.Gson;
import com.link2loyalty.bwigomdlib.R;
import com.link2loyalty.bwigomdlib.api.ServerCallback;
import com.link2loyalty.bwigomdlib.models.Article;
import com.link2loyalty.bwigomdlib.models.ArticleRes;
import com.link2loyalty.bwigomdlib.models2.ArticleService;
import com.link2loyalty.bwigomdlib.models2.User;
import com.link2loyalty.bwigomdlib.models2.user.ValorMultitenancy;

import java.util.ArrayList;
import java.util.List;

import ahmed.easyslider.EasySlider;
import ahmed.easyslider.SliderItem;

public class ArticleDetailActivity extends AppCompatActivity {

    TextView tvTitle, tvContent;
    User mUser;
    Button btnMore;
    ArticleService articleService;

    private ProgressBar progressBar;
    private Toolbar myToolbar;
    private ValorMultitenancy mMultitenancy;
    private String title = "";
    Article article;

    EasySlider esDetails;

    Boolean isFavorite = false;
    private Menu menu;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_article_detail);

        mUser = new User(getApplicationContext());
        mMultitenancy = mUser.getMultitenancy();
        articleService = new ArticleService(getApplicationContext());

        tvContent = findViewById( R.id.tv_content );
        tvTitle = findViewById( R.id.tv_title );
        btnMore = findViewById(R.id.btn_show_more);
        myToolbar = findViewById(R.id.tb_article_detail);
        progressBar = findViewById(R.id.pb_article_detail);
        esDetails = findViewById(R.id.es_details);

        List<SliderItem> sliderItems = new ArrayList<>();
        sliderItems.add(new SliderItem("title1","https://miro.medium.com/max/1200/1*_-K1Uz6Fr7sirjhqXGlluw.png"));
        sliderItems.add(new SliderItem("title2","https://developer.android.com/images/android-developers.png"));
        sliderItems.add(new SliderItem("title3","https://2.bp.blogspot.com/-SyYsE6lCBK4/WpbnmkKnvjI/AAAAAAAAFG4/iALBir1-WU0NzVTf-83eo3MB0kvaHZliQCLcBGAs/s1600/ad_logo_twitter_card.png"));

        esDetails.setPages(sliderItems);


        configToolbar();

        Intent intent = getIntent();
        String articleId = intent.getStringExtra("article_id");

        //articleService.clearFavorites();


        articleService.getArticle(mUser.getSes(), articleId, new ServerCallback() {
            @Override
            public void onSuccess(String result) {

                Gson mGson = new Gson();
                ArticleRes articleRes = mGson.fromJson( result, ArticleRes.class );
                article = articleRes.getValor();
                title = article.getTitulo();
                tvTitle.setText( article.getTitulo() );
                tvContent.setText( article.getContenido() );
                if( article.getVermasurl().length() > 0 ){
                    btnMore.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            String url = article.getVermasurl();
                            Intent i = new Intent(Intent.ACTION_VIEW);
                            i.setData(Uri.parse(url));
                            startActivity(i);
                        }
                    });
                }else{
                    btnMore.setVisibility( View.INVISIBLE );
                }
                isFavorite();
                progressBar.setVisibility( View.INVISIBLE );


            }

            @Override
            public void onError(VolleyError err) {
                Toast.makeText(getApplicationContext(), "Error de conexion!", Toast.LENGTH_SHORT).show();
                progressBar.setVisibility( View.INVISIBLE );
            }
        });






    }

    private void configToolbar() {
        setSupportActionBar(myToolbar);
        getSupportActionBar().setTitle(title);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        //Config multitenancy
        if( mMultitenancy.getColpri() != null ){
            myToolbar.setBackgroundColor(Color.parseColor( "#"+mMultitenancy.getColpri() ));
            //myToolbar.setTitle( mMultitenancy.getDes() );
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                Window window = getWindow();
                window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
                window.setStatusBarColor(Color.parseColor( "#"+mMultitenancy.getColosc() ));
            }
            btnMore.setBackgroundColor(Color.parseColor( "#"+mMultitenancy.getColpri() ));
        }
    }


    private void isFavorite(){
        if( articleService.isFavorite( article ) ){
            menu.getItem(0).setIcon(ContextCompat.getDrawable(ArticleDetailActivity.this, R.drawable.ic_heart));
        }else{
            menu.getItem(0).setIcon(ContextCompat.getDrawable(ArticleDetailActivity.this, R.drawable.ic_heart_outline));
        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        this.menu = menu;
        getMenuInflater().inflate( R.menu.menu_article, menu );
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        if (item.getItemId() == R.id.menu_article_favorite) {
            String favoritesMsg = articleService.toggleFavorite( article );

            isFavorite();



            Toast.makeText(this, favoritesMsg, Toast.LENGTH_LONG).show();

            return super.onOptionsItemSelected(item);
        }
        return super.onOptionsItemSelected(item);


    }
}
