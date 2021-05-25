package com.link2loyalty.bwigomdlib.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.link2loyalty.bwigomdlib.R;
import com.link2loyalty.bwigomdlib.models.Article;
import com.link2loyalty.bwigomdlib.models2.notification.Notification;

import java.util.ArrayList;

public class NotificationsListAdapter extends RecyclerView.Adapter<NotificationsListAdapter.NotificationsHolder> {

    ArrayList<Notification> notifications = new ArrayList<>();
    Context ctx;

    public NotificationsListAdapter(Context ctx, ArrayList<Notification> articlesList )   {
        this.notifications = articlesList;
        this.ctx = ctx;
    }

    public class NotificationsHolder extends RecyclerView.ViewHolder {
        public NotificationsHolder(@NonNull View itemView) {
            super(itemView);
        }

        TextView tvTitle = itemView.findViewById( R.id.tv_title );
        TextView tvContent = itemView.findViewById( R.id.tv_content );
        TextView tvDate = itemView.findViewById( R.id.tv_date );
    }

    @NonNull
    @Override
    public NotificationsHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {

        View itemView = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.notification_list_item, viewGroup , false);
        return new NotificationsHolder( itemView );
    }

    @Override
    public void onBindViewHolder(@NonNull NotificationsHolder notificationHolder, int i) {

        Notification notification = notifications.get( i );
        notificationHolder.tvTitle.setText( notification.getTitle() );
        notificationHolder.tvContent.setText( notification.getContent() );
        notificationHolder.tvDate.setText( notification.getDate() );


    }

    @Override
    public int getItemCount() {
        return this.notifications.size();
    }


}
