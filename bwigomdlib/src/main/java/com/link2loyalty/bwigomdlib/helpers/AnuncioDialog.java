package com.link2loyalty.bwigomdlib.helpers;

import android.app.Activity;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;

import com.bumptech.glide.Glide;
import com.link2loyalty.bwigomdlib.R;
import com.link2loyalty.bwigomdlib.models2.Anuncio;

public class AnuncioDialog {

    private AlertDialog dialog = null;



    public void showAnuncio(Activity activity, final Anuncio anuncio, final OnAnuncioClicked onAnuncioClicked) {
        AlertDialog.Builder builder = new AlertDialog.Builder(activity);
        LayoutInflater inflater = activity.getLayoutInflater();
        View dialogView = inflater.inflate(R.layout.anuncio_dialog, null);
        builder.setView(dialogView);
        builder.setCancelable(true);
        if( dialog == null ) {
            TextView tvTitle = dialogView.findViewById(R.id.tv_title);
            TextView tvDesCor = dialogView.findViewById(R.id.tv_des_cor);
            TextView tvDes = dialogView.findViewById(R.id.tv_des);
            ImageView ivCoupon = dialogView.findViewById(R.id.iv_coupon);
            Button btnGo = dialogView.findViewById(R.id.btn_go);

            btnGo.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    onAnuncioClicked.goCoupon();
                }
            });

            if(anuncio.getTit().isEmpty()) {
                tvTitle.setVisibility(View.GONE);
            }
            if( anuncio.getDesCor().isEmpty() ) {
                tvDesCor.setVisibility(View.GONE);
            }
            if( anuncio.getDesLar().isEmpty() ) {
                tvDes.setVisibility(View.GONE);
            }

            tvTitle.setText(anuncio.getTit());
            tvDesCor.setText(anuncio.getDesCor());
            tvDes.setText(anuncio.getDesLar());

            Glide.with( activity )
                    .load( anuncio.getFondo() )
                    .error(R.drawable.default_pleaceholder)
                    .into( ivCoupon );


            dialog = builder.create();
            //dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        }
        if( !dialog.isShowing() ) {
            dialog.show();
        }

    }

    public void hideAnuncio() {
        dialog.dismiss();
    }

    public interface OnAnuncioClicked {
        void goCoupon();
    }

}
