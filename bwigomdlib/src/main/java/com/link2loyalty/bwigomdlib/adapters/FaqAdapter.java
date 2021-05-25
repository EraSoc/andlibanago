package com.link2loyalty.bwigomdlib.adapters;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.link2loyalty.bwigomdlib.R;
import com.link2loyalty.bwigomdlib.interfaces.FaqRecyclerViewOnItemClickListener;
import com.link2loyalty.bwigomdlib.models.FaqModel;

import java.util.List;

public class FaqAdapter extends RecyclerView.Adapter<FaqAdapter.MyViewHolder> {
    private List<FaqModel> data;
    private FaqRecyclerViewOnItemClickListener recyclerViewOnItemClickListener;

    public FaqAdapter(@NonNull List<FaqModel> data,
                                 @NonNull FaqRecyclerViewOnItemClickListener
                                         recyclerViewOnItemClickListener) {
        this.data = data;
        this.recyclerViewOnItemClickListener = recyclerViewOnItemClickListener;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View row = LayoutInflater.from(parent.getContext()).inflate(R.layout.faq_item, parent, false);
        return new MyViewHolder(row);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        FaqModel faq = data.get(position);
        holder.getNameTextView().setText(faq.getQuestion());

    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder implements View
            .OnClickListener {
        private TextView tvQuestion;


        public MyViewHolder(View itemView) {
            super(itemView);
            tvQuestion = (TextView) itemView.findViewById(R.id.tv_question);
            itemView.setOnClickListener(this);
        }

        public TextView getNameTextView() {
            return tvQuestion;
        }

        @Override
        public void onClick(View v) {
            recyclerViewOnItemClickListener.onClick(v, getAdapterPosition());
        }
    }

}