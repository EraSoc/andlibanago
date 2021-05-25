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
import com.link2loyalty.bwigomdlib.models2.canje.LovBoletos;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class CanjesAdapter extends RecyclerView.Adapter<CanjesAdapter.MyViewHolder> {

    private ArrayList<LovBoletos> mRewards;
    private RewardsRecyclerViewOnItemClickListener recyclerViewOnItemClickListener;
    private Context context;


    public class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        public TextView title;
        CardView cvReward;
        ImageView ivCanje;

        public MyViewHolder(View view) {
            super(view);
            title = (TextView) view.findViewById(R.id.tv_canje);
            cvReward = view.findViewById(R.id.cv_canje);
            ivCanje = view.findViewById( R.id.iv_canje );

            cvReward.setOnClickListener(this);
        }

        //Implementa!
        @Override
        public void onClick(View v) {
            recyclerViewOnItemClickListener.onClick(v, getAdapterPosition());
        }
    }

    // Provide a suitable constructor (depends on the kind of dataset)
    public CanjesAdapter(ArrayList<LovBoletos> myDataset, Context context, RewardsRecyclerViewOnItemClickListener recyclerViewOnItemClickListener) {
        mRewards = myDataset;
        this.recyclerViewOnItemClickListener = recyclerViewOnItemClickListener;
        this.context = context;
    }

    // Create new views (invoked by the layout manager)
    @Override
    public CanjesAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_canje, parent, false);

        return new MyViewHolder(itemView);
    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {


        String mReward = mRewards.get(position).getVigencia();

        Glide
                .with(context)
                //CAMBIAR A LA IMAGEN DESDE MODELO
                .load( mRewards.get(position).getImglog())
                .dontTransform()
                .into(holder.ivCanje);

        //Convertir data
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Date convertedCurrentDate = null;
        try {
            convertedCurrentDate = sdf.parse(mReward);
            String date=sdf.format(convertedCurrentDate );
            //System.out.println(date);
            holder.title.setText("Valido hasta el: "+date);

        } catch (ParseException e) {
            e.printStackTrace();
        }






    }

    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return mRewards.size();
    }

}
