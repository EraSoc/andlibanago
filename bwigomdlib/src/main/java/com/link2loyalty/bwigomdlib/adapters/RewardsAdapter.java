package com.link2loyalty.bwigomdlib.adapters;


import android.content.Context;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.link2loyalty.bwigomdlib.R;
import com.link2loyalty.bwigomdlib.interfaces.RewardsRecyclerViewOnItemClickListener;
import com.link2loyalty.bwigomdlib.models2.user.LovReward;

import java.util.ArrayList;

public class RewardsAdapter extends RecyclerView.Adapter<RewardsAdapter.MyViewHolder> {

    private Context mContext;

    private ArrayList<LovReward> mRewards;
    private RewardsRecyclerViewOnItemClickListener recyclerViewOnItemClickListener;


    public class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        public TextView title;
        CardView cvReward;
        ImageView ivReward;

        public MyViewHolder(View view) {
            super(view);
            title = (TextView) view.findViewById(R.id.tv_reward);
            cvReward = view.findViewById(R.id.cv_reward);
            ivReward = view.findViewById(R.id.iv_reward);
            cvReward.setOnClickListener(this);
        }

        //Implementa!
        @Override
        public void onClick(View v) {
            recyclerViewOnItemClickListener.onClick(v, getAdapterPosition());
        }
    }

    // Provide a suitable constructor (depends on the kind of dataset)
    public  RewardsAdapter(Context mContext, ArrayList<LovReward> myDataset, RewardsRecyclerViewOnItemClickListener recyclerViewOnItemClickListener) {
        mRewards = myDataset;
        this.mContext = mContext;
        this.recyclerViewOnItemClickListener = recyclerViewOnItemClickListener;
    }

    // Create new views (invoked by the layout manager)
    @Override
    public RewardsAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_reward, parent, false);

        return new MyViewHolder(itemView);
    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        LovReward mReward = mRewards.get(position);
        holder.title.setText(mReward.getReg());
        Glide.with(this.mContext)
                .load(mReward.getImglog())
                // .fitCenter()
                // .dontTransform()
                .into(holder.ivReward);
    }

    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return mRewards.size();
    }

}
