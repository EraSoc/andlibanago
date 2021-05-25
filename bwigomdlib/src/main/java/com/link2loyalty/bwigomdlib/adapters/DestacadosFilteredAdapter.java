package com.link2loyalty.bwigomdlib.adapters;

import android.content.Context;
import android.content.Intent;
import androidx.cardview.widget.CardView;
import androidx.appcompat.widget.PopupMenu;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.VolleyError;
import com.bumptech.glide.Glide;
import com.google.gson.Gson;
import com.link2loyalty.bwigomdlib.R;
import com.link2loyalty.bwigomdlib.RedimirActivity;
import com.link2loyalty.bwigomdlib.api.ServerCallback;
import com.link2loyalty.bwigomdlib.api.models.AddFav;
import com.link2loyalty.bwigomdlib.interfaces.CouponsRecyclerViewOnItemClickListener;
import com.link2loyalty.bwigomdlib.models2.Coupon;
import com.link2loyalty.bwigomdlib.models2.User;
import com.link2loyalty.bwigomdlib.models2.coupon.LovFiltered;

import java.util.List;

public class DestacadosFilteredAdapter extends RecyclerView.Adapter<DestacadosFilteredAdapter.PersonViewHolder>{

    private CouponsRecyclerViewOnItemClickListener recyclerViewOnItemClickListener;

    List<LovFiltered> couponModels;

    Context mContext;
    User mUser;
    Coupon mCoupon;

    public DestacadosFilteredAdapter(List<LovFiltered> couponModels, Context mContext,
                                     CouponsRecyclerViewOnItemClickListener recyclerViewOnItemClickListener){
        this.couponModels = couponModels;
        this.recyclerViewOnItemClickListener = recyclerViewOnItemClickListener;
        this.mContext = mContext;
        mUser = new User(mContext);
        mCoupon = new Coupon(mContext);
    }

    @Override
    public int getItemCount() {
        return couponModels.size();
    }

    @Override
    public PersonViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_highlight_coupon, viewGroup, false);
        PersonViewHolder pvh = new PersonViewHolder(v);
        return pvh;
    }

    @Override
    public void onBindViewHolder(final PersonViewHolder personViewHolder, final int i) {
        personViewHolder.tvCategoria.setText(couponModels.get(i).getCat());
        personViewHolder.tvDescuento.setText(couponModels.get(i).getDesCor());
        personViewHolder.tvTienda.setText(couponModels.get(i).getAli());

        Glide.with(mContext).load(couponModels.get(i).getCup()).placeholder(R.drawable.default_pleaceholder).into(personViewHolder.ivCoupon);
        personViewHolder.btnMore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showPopupMenu(personViewHolder.btnMore, i);
            }
        });

        personViewHolder.btnMore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showPopupMenu(personViewHolder.btnMore, i);
            }
        });
    }

    private void showPopupMenu(View view,int position) {
        // inflate menu
        PopupMenu popup = new PopupMenu(view.getContext(),view );
        MenuInflater inflater = popup.getMenuInflater();
        inflater.inflate(R.menu.menu_popup, popup.getMenu());
        popup.setOnMenuItemClickListener(new DestacadosFilteredAdapter.MyMenuItemClickListener(position));
        popup.show();
    }

    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
    }


    public class PersonViewHolder extends RecyclerView.ViewHolder implements View
            .OnClickListener {
        CardView cv;
        TextView tvCategoria;
        TextView tvDescuento;
        TextView tvTienda;
        ImageButton btnMore;
        ImageView ivCoupon;

        PersonViewHolder(View itemView) {
            super(itemView);
            cv = (CardView)itemView.findViewById(R.id.cv_coupon_des);
            tvCategoria = (TextView)itemView.findViewById(R.id.tv_categoria);
            tvDescuento = (TextView)itemView.findViewById(R.id.tv_descuento);
            tvTienda = (TextView)itemView.findViewById(R.id.tv_tienda);
            btnMore = (ImageButton) itemView.findViewById(R.id.btn_destacados);
            ivCoupon = itemView.findViewById(R.id.iv_destacado);

            cv.setOnClickListener(this);
            btnMore.setOnClickListener(this);

        }

        @Override
        public void onClick(View v) {
            recyclerViewOnItemClickListener.onClick(v, getAdapterPosition());
        }

    }

    private class MyMenuItemClickListener implements PopupMenu.OnMenuItemClickListener {

        private int position;
        public MyMenuItemClickListener(int positon) {
            this.position=positon;
        }

        @Override
        public boolean onMenuItemClick(MenuItem menuItem) {
            int itemId = menuItem.getItemId();
            if (itemId == R.id.Not_interasted_catugury) {
                agregarFavorito(couponModels.get(position).getClvPro());
                return true;
            } else if (itemId == R.id.No_interasted) {
                Intent intent = new Intent(mContext, RedimirActivity.class);
                intent.putExtra("couponId", Integer.valueOf(couponModels.get(position).getClvPro()));
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                mContext.startActivity(intent);
                return true;
            }
            return false;
        }


        private void agregarFavorito(final String clvPro) {

            mCoupon.addFavorite(mUser.getSes(), String.valueOf(clvPro), new ServerCallback() {
                @Override
                public void onSuccess(String result) {
                    Gson mGson = new Gson();
                    AddFav mAddFav = mGson.fromJson(result, AddFav.class);
                    if (mAddFav.getErr() == 0) {
                        Toast.makeText(mContext, mAddFav.getMen(), Toast.LENGTH_LONG).show();
                    } else {
                        Toast.makeText(mContext, mAddFav.getMen(), Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onError(VolleyError err) {
                    Toast.makeText(mContext, "Error de conexion!", Toast.LENGTH_SHORT).show();
                }
            });

        }

    }


}
