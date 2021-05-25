package com.link2loyalty.bwigomdlib.fragments;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.GestureDetector;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;

import com.link2loyalty.bwigomdlib.R;
import com.link2loyalty.bwigomdlib.ArticleDetailActivity;
import com.link2loyalty.bwigomdlib.adapters.ArticlesListAdapter;
import com.link2loyalty.bwigomdlib.models.Article;
import com.link2loyalty.bwigomdlib.models2.ArticleService;
import com.link2loyalty.bwigomdlib.models2.User;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 */
public class FavoritesArticlesFragment extends Fragment {

    RecyclerView rvArticles;
    ArrayList<Article> articlesList = new ArrayList<>();
    ArticlesListAdapter articleAdapter;

    User mUser;
    ArticleService articleService;

    public FavoritesArticlesFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_favorites_articles, container, false);

        mUser = new User(getActivity().getApplicationContext());
        articleService = new ArticleService(getActivity().getApplicationContext());


        rvArticles = view.findViewById( R.id.rv_articles_favorite );
        articleAdapter = new ArticlesListAdapter(getActivity().getApplicationContext(), articlesList);

        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getActivity().getApplicationContext(), LinearLayoutManager.VERTICAL, false);
        rvArticles.setLayoutManager( layoutManager );
        rvArticles.setAdapter( articleAdapter );


        articlesList.addAll( articleService.getFavorites() );



        articleAdapter.notifyDataSetChanged();


        final GestureDetector mGestureDetector = new GestureDetector(getActivity().getApplicationContext(), new GestureDetector.SimpleOnGestureListener() {
            @Override public boolean onSingleTapUp(MotionEvent e) {
                return true;
            }
        });

        rvArticles.addOnItemTouchListener(new RecyclerView.OnItemTouchListener() {
            @Override
            public boolean onInterceptTouchEvent(@NonNull RecyclerView recyclerView, @NonNull MotionEvent motionEvent) {
                try {
                    View child = recyclerView.findChildViewUnder(motionEvent.getX(), motionEvent.getY());
                    if (child != null && mGestureDetector.onTouchEvent(motionEvent)) {
                        int position = recyclerView.getChildAdapterPosition(child);
                        Article articleSelected = articlesList.get( position );
                        Intent myIntent = new Intent(getActivity(), ArticleDetailActivity.class);
                        myIntent.putExtra( "article_id", articleSelected.getId_articulo());
                        getActivity().startActivity(myIntent);

                        return true;
                    }
                }catch (Exception e){
                    e.printStackTrace();
                }
                return false;
            }

            @Override
            public void onTouchEvent(@NonNull RecyclerView recyclerView, @NonNull MotionEvent motionEvent) {

            }

            @Override
            public void onRequestDisallowInterceptTouchEvent(boolean b) {

            }
        });



        return view;
    }
}
