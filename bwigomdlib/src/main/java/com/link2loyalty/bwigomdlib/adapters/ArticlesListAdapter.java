package com.link2loyalty.bwigomdlib.adapters;

import android.content.Context;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.link2loyalty.bwigomdlib.R;
import com.link2loyalty.bwigomdlib.models.Article;

import java.util.ArrayList;

public class ArticlesListAdapter extends RecyclerView.Adapter<ArticlesListAdapter.ArticlesHolder> {

    ArrayList<Article> articleList = new ArrayList<>();
    Context ctx;

    public ArticlesListAdapter(Context ctx, ArrayList<Article> articlesList )   {
        this.articleList = articlesList;
        this.ctx = ctx;
    }

    public class ArticlesHolder extends RecyclerView.ViewHolder {
        public ArticlesHolder(@NonNull View itemView) {
            super(itemView);
        }

        TextView tvTitle = itemView.findViewById( R.id.tv_title );
        TextView tvDescription = itemView.findViewById( R.id.tv_description );
        ImageView ivArticle = itemView.findViewById( R.id.iv_article );

    }

    @NonNull
    @Override
    public ArticlesHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {

        View itemView = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.article_list_item, viewGroup , false);
        return new ArticlesHolder( itemView );
    }

    @Override
    public void onBindViewHolder(@NonNull ArticlesHolder articlesHolder, int i) {

        Article article = this.articleList.get(i);
        articlesHolder.tvTitle.setText( article.getTitulo() );
        articlesHolder.tvDescription.setText( article.getContenido() );

        Glide.with( ctx )
                .load(article.getImg_art())
                .error(R.drawable.default_pleaceholder)
                .into( articlesHolder.ivArticle );

    }

    @Override
    public int getItemCount() {
        return this.articleList.size();
    }


}
