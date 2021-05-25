package com.link2loyalty.bwigomdlib.adapters;

import android.content.Context;
import androidx.cardview.widget.CardView;
import androidx.appcompat.widget.PopupMenu;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.link2loyalty.bwigomdlib.R;
import com.link2loyalty.bwigomdlib.HomeActivity;
import com.link2loyalty.bwigomdlib.interfaces.CouponsRecyclerViewOnItemClickListener;
import com.link2loyalty.bwigomdlib.models2.Coupon;
import com.link2loyalty.bwigomdlib.models2.User;
import com.link2loyalty.bwigomdlib.models2.coupon.LovRedimido;

import java.util.List;

public class UsedCouponAdapter extends RecyclerView.Adapter<UsedCouponAdapter.PersonViewHolder>{

    private CouponsRecyclerViewOnItemClickListener recyclerViewOnItemClickListener;

    List<LovRedimido> couponModels;
    Context mContext;
    User mUser;
    Coupon mCoupon;

    public UsedCouponAdapter(List<LovRedimido> couponModels,Context mContext,
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
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_used_coupon, viewGroup, false);
        PersonViewHolder pvh = new PersonViewHolder(v);
        return pvh;
    }

    @Override
    public void onBindViewHolder(final PersonViewHolder personViewHolder, final int i) {
        personViewHolder.tvCategory.setText(couponModels.get(i).getAli());
        personViewHolder.tvDiscount.setText(couponModels.get(i).getDescor());
        personViewHolder.tvShop.setText(couponModels.get(i).getCat());


        Glide.with(mContext).load(couponModels.get(i).getIma()).into(personViewHolder.iv);



        /*
        personViewHolder.btnCoupon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showPopupMenu(personViewHolder.btnCoupon, i);
            }
        });
        */

    }

    /*private void showPopupMenu(View view,int position) {
        // inflate menu
        PopupMenu popup = new PopupMenu(view.getContext(),view );
        MenuInflater inflater = popup.getMenuInflater();
        inflater.inflate(R.menu.menu_popup, popup.getMenu());
        popup.setOnMenuItemClickListener(new UsedCouponAdapter.MyMenuItemClickListener(position));
        popup.show();
    }*/

    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
    }


    public class PersonViewHolder extends RecyclerView.ViewHolder implements View
            .OnClickListener {
        CardView cv;
        TextView tvCategory;
        TextView tvDiscount;
        TextView tvShop;
        ImageView iv;
        //ImageButton btnCoupon;

        PersonViewHolder(View itemView) {
            super(itemView);
            cv = (CardView)itemView.findViewById(R.id.cv_coupon);
            tvCategory = (TextView)itemView.findViewById(R.id.tv_category);
            tvDiscount = (TextView)itemView.findViewById(R.id.tv_discount);
            tvShop = (TextView)itemView.findViewById(R.id.tv_shop);
            iv = itemView.findViewById(R.id.imageView4);
            //btnCoupon = (ImageButton) itemView.findViewById(R.id.btn_coupon);

            cv.setOnClickListener(this);
            //btnCoupon.setOnClickListener(this);

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
                Toast.makeText(HomeActivity.mContext, "Add to favourite", Toast.LENGTH_SHORT).show();

                return true;
            } else if (itemId == R.id.No_interasted) {
                Toast.makeText(HomeActivity.mContext, "Done for now", Toast.LENGTH_SHORT).show();
                return true;
            }
            return false;
        }
    }


}
