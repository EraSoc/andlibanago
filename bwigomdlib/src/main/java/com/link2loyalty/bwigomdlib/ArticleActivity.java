package com.link2loyalty.bwigomdlib;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import com.google.android.material.tabs.TabLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import androidx.appcompat.widget.Toolbar;

import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.Window;
import android.view.WindowManager;

import com.link2loyalty.bwigomdlib.R;
import com.link2loyalty.bwigomdlib.fragments.AllArticlesFragment;
import com.link2loyalty.bwigomdlib.fragments.FavoritesArticlesFragment;
import com.link2loyalty.bwigomdlib.models2.User;
import com.link2loyalty.bwigomdlib.models2.user.ValorMultitenancy;

import java.util.ArrayList;
import java.util.List;

public class ArticleActivity extends AppCompatActivity {

    private String title = "Articulos";
    private Toolbar myToolbar;
    private ViewPager viewPager;
    public Context mContext;
    private User mUser;
    private ValorMultitenancy mMultitenancy;
    // Widgets
    TabLayout tab;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_article);

        mContext = getApplicationContext();
        mUser = new User(mContext);
        mMultitenancy = mUser.getMultitenancy();

        findViews();
        configToolbar();
        configTabs();

    }

    private void findViews(){
        myToolbar = findViewById(R.id.tb_articles);
        tab = findViewById(R.id.tl_articles);
        viewPager = (ViewPager) findViewById(R.id.vp_articles);
        setupViewPager(viewPager);
    }

    private void setupViewPager(ViewPager viewPager) {
        ArticleActivity.ViewPagerAdapter adapter = new ArticleActivity.ViewPagerAdapter(getSupportFragmentManager());
        adapter.addFragment(new AllArticlesFragment(), "Todos");
        adapter.addFragment(new FavoritesArticlesFragment(), "Favoritos");
        viewPager.setAdapter(adapter);
    }

    private void configTabs(){

        tab.setupWithViewPager(viewPager);
        //Config multitenancy
        if( mMultitenancy.getColpri() != null ){
            tab.setBackgroundColor(Color.parseColor( "#"+mMultitenancy.getColpri() ));
        }
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
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate( R.menu.menu_notifications, menu );
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        if( item.getItemId() == R.id.menu_notifications ){
            Intent intent = new Intent(this, NotificationsActivity.class);
            startActivity(intent);
        }

        return super.onOptionsItemSelected(item);
    }

    class ViewPagerAdapter extends FragmentPagerAdapter {
        private final List<Fragment> mFragmentList = new ArrayList<>();
        private final List<String> mFragmentTitleList = new ArrayList<>();

        public ViewPagerAdapter(FragmentManager manager) {
            super(manager);
        }

        @Override
        public Fragment getItem(int position) {
            return mFragmentList.get(position);
        }

        @Override
        public int getCount() {
            return mFragmentList.size();
        }

        public void addFragment(Fragment fragment, String title) {
            mFragmentList.add(fragment);
            mFragmentTitleList.add(title);
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return mFragmentTitleList.get(position);
        }
    }












}
