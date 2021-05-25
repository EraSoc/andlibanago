package com.link2loyalty.bwigomdlib;



import android.content.Context;
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

import android.view.Window;
import android.view.WindowManager;

import com.link2loyalty.bwigomdlib.R;
import com.link2loyalty.bwigomdlib.fragments.FavoritesCouponsFragment;
import com.link2loyalty.bwigomdlib.fragments.UsedCouponsFragment;
import com.link2loyalty.bwigomdlib.models2.User;
import com.link2loyalty.bwigomdlib.models2.user.ValorMultitenancy;

import java.util.ArrayList;
import java.util.List;

public class CouponActivity extends AppCompatActivity {

    private String title = "Mis Cupones";
    private Toolbar myToolbar;
    private ViewPager viewPager;
    public Context mContext;
    private User mUser;


    private ValorMultitenancy mMultitenancy;

    // Widgets
    TabLayout tabs;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_coupon);
        mContext = getApplicationContext();
        mUser = new User(mContext);
        mMultitenancy = mUser.getMultitenancy();

        findViews();
        configToolbar();
        configTabs();

    }

    private void configTabs(){

        tabs.setupWithViewPager(viewPager);
        //Config multitenancy
        if( mMultitenancy.getColpri() != null ){
            tabs.setBackgroundColor(Color.parseColor( "#"+mMultitenancy.getColpri() ));
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

    private void findViews(){
        myToolbar = findViewById(R.id.tb_coupon);
        tabs = findViewById(R.id.tabs_coupon);
        viewPager = (ViewPager) findViewById(R.id.viewpager);
        setupViewPager(viewPager);
    }

    private void setupViewPager(ViewPager viewPager) {
        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager());
        adapter.addFragment(new FavoritesCouponsFragment(), "Favoritos");
        adapter.addFragment(new UsedCouponsFragment(), "Utilizados");
        viewPager.setAdapter(adapter);
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
