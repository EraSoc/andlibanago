package com.link2loyalty.bwigomdlib;


import android.content.Intent;
import androidx.viewpager.widget.ViewPager;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Html;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.link2loyalty.bwigomdlib.R;
import com.link2loyalty.bwigomdlib.adapters.SliderAdapter;

public class OnboardingActivity extends AppCompatActivity {


    private ViewPager mSlideViewPager;
    private LinearLayout mDotLayout;

    private TextView[] mDots;

    private SliderAdapter sliderAdapter;

    private Button mNextBtn, mBackBtn;

    private int mCurrentPage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_onboarding);
        mSlideViewPager = (ViewPager) findViewById(R.id.sliderViewPager);
        mDotLayout = (LinearLayout) findViewById(R.id.dotsLayout);
        mNextBtn = (Button) findViewById(R.id.nextBtn);
        mBackBtn = (Button) findViewById(R.id.prevBtn);
        sliderAdapter = new SliderAdapter(this);
        mSlideViewPager.setAdapter(sliderAdapter);
        addDotsIndicator(0);
        mSlideViewPager.addOnPageChangeListener(viewListener);

        //SET LISTENER
        mBackBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if( mCurrentPage == 0 ){
                    Intent intent = new Intent(OnboardingActivity.this, HomeActivity.class);
                    startActivity(intent);
                    finish();
                }else{
                    mSlideViewPager.setCurrentItem(mCurrentPage-1);
                }


            }
        });

        mNextBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if( mCurrentPage == 3 ){
                    Intent intent = new Intent(OnboardingActivity.this, HomeActivity.class);
                    startActivity(intent);
                    finish();
                }else{
                    mSlideViewPager.setCurrentItem(mCurrentPage+1);
                }
            }
        });


    }


    public void addDotsIndicator(int position){

        mDots = new TextView[4];
        mDotLayout.removeAllViews();
        for (int i=0; i<mDots.length; i++){
            mDots[i] = new TextView(this);
            mDots[i].setText(Html.fromHtml("&#8226;"));
            mDots[i].setTextSize(35);
            mDots[i].setTextColor(getResources().getColor(R.color.colorTransparentWhite));
            mDotLayout.addView(mDots[i]);
        }

        if(mDots.length > 0){
            mDots[position].setTextColor(getResources().getColor(R.color.colorWhite));
        }

    }

    ViewPager.OnPageChangeListener viewListener = new ViewPager.OnPageChangeListener() {
        @Override
        public void onPageScrolled(int i, float v, int i1) {

        }

        @Override
        public void onPageSelected(int i) {
            addDotsIndicator(i);
            mCurrentPage = i;
            if( i == 0){
                mNextBtn.setEnabled(true);
                mBackBtn.setEnabled(true);
                mBackBtn.setVisibility(View.VISIBLE);
                mNextBtn.setText("Siguiente");
                mBackBtn.setText("Saltar");
            }else if( i == mDots.length -1 ){
                mNextBtn.setEnabled(true);
                mBackBtn.setEnabled(true);
                mBackBtn.setVisibility(View.VISIBLE);
                mNextBtn.setText("Terminar");
                mBackBtn.setText("Atras");
            }else{
                mNextBtn.setEnabled(true);
                mBackBtn.setEnabled(true);
                mBackBtn.setVisibility(View.VISIBLE);
                mNextBtn.setText("Siguiente");
                mBackBtn.setText("Atras");
            }

        }

        @Override
        public void onPageScrollStateChanged(int i) {

        }
    };


}
