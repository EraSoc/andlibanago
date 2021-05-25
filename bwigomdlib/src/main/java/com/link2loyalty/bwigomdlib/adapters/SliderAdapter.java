package com.link2loyalty.bwigomdlib.adapters;

import android.content.Context;
import androidx.annotation.NonNull;
import androidx.viewpager.widget.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.link2loyalty.bwigomdlib.R;

public class SliderAdapter  extends PagerAdapter {

    Context context;
    LayoutInflater layoutInflater;

    public SliderAdapter( Context context ){
        this.context = context;
    }
    //Arrays
    public int[] slide_images = {

            R.drawable.slide_1,
            R.drawable.slide_2,
            R.drawable.slide_4,
            R.drawable.slide_5

    };


    @Override
    public int getCount() {
        return slide_images.length;
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object o) {
        return view == (RelativeLayout) o;
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {


        layoutInflater = (LayoutInflater) context.getSystemService(context.LAYOUT_INFLATER_SERVICE);
        View view = layoutInflater.inflate(R.layout.slider_layout, container, false);

        ImageView slideImageView = (ImageView) view.findViewById(R.id.slide_image);

        slideImageView.setImageResource(slide_images[position]);

        container.addView(view);

        return view;

    }


    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        container.removeView((RelativeLayout)object);
    }
}

