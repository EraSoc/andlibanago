package com.link2loyalty.bwigomdlib.fragments;

import android.content.Intent;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.util.Log;
import android.view.GestureDetector;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.android.volley.VolleyError;
import com.google.gson.Gson;
import com.link2loyalty.bwigomdlib.R;
import com.link2loyalty.bwigomdlib.ArticleDetailActivity;
import com.link2loyalty.bwigomdlib.adapters.ArticlesListAdapter;
import com.link2loyalty.bwigomdlib.api.ServerCallback;
import com.link2loyalty.bwigomdlib.models.Article;
import com.link2loyalty.bwigomdlib.models.ArticleRes;
import com.link2loyalty.bwigomdlib.models2.ArticleService;
import com.link2loyalty.bwigomdlib.models2.User;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 */
public class AllArticlesFragment extends Fragment implements RecyclerView.OnItemTouchListener {

    ArticleService articleService;
    User mUser;

    SwipeRefreshLayout srlArticles;
    RecyclerView rvArticles;
    ArrayList<Article> articlesList = new ArrayList<>();
    ArticlesListAdapter articleAdapter;

    ProgressBar progressBar;

    GestureDetector mGestureDetector;

    int page = 1;

    public AllArticlesFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_all_articles, container, false);

        progressBar = view.findViewById(R.id.pb_articles);
        srlArticles = view.findViewById( R.id.srl_articles );
        rvArticles = view.findViewById( R.id.rv_all_articles );
        mUser = new User( getActivity().getApplicationContext() );
        articleService = new ArticleService( getActivity().getApplicationContext() );
        articleAdapter = new ArticlesListAdapter(getActivity().getApplicationContext(), articlesList);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getActivity().getApplicationContext(), LinearLayoutManager.VERTICAL, false);
        rvArticles.setLayoutManager( layoutManager );
        rvArticles.setAdapter( articleAdapter );
        mGestureDetector = new GestureDetector(getActivity().getApplicationContext(), new GestureDetector.SimpleOnGestureListener() {
            @Override public boolean onSingleTapUp(MotionEvent e) {
                return true;
            }
        });

        rvArticles.addOnScrollListener(onScrollListener);
        rvArticles.addOnItemTouchListener(this);
        getArticles();

        srlArticles.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                articlesList.clear();
                page = 1;
                getArticles();
            }
        });

        return view;
    }

    private RecyclerView.OnScrollListener onScrollListener = new RecyclerView.OnScrollListener() {
        @Override
        public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
            super.onScrollStateChanged(recyclerView, newState);
        }

        @Override
        public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
            if( isLastItemDisplaying( recyclerView ) ){
                getArticles();
            }
            super.onScrolled(recyclerView, dx, dy);
        }
    };

    private boolean isLastItemDisplaying(RecyclerView recyclerView) {

        if( recyclerView.getAdapter().getItemCount() != 0 ){
            int lastVisibleItemPosition = ((LinearLayoutManager) recyclerView.getLayoutManager()).findLastCompletelyVisibleItemPosition();
            return lastVisibleItemPosition != RecyclerView.NO_POSITION && lastVisibleItemPosition == recyclerView.getAdapter().getItemCount() - 1;
        }

        return false;
    }


    private void getArticles(){
        Log.d("recyclerView", "page: "+page);
        progressBar.setVisibility(View.VISIBLE);
        articleService.getArticles(mUser.getSes(), new ServerCallback() {
            @Override
            public void onSuccess(String result) {
                Gson mGson = new Gson();
                ArticleRes articleRes = mGson.fromJson( result, ArticleRes.class );
                if( articleRes.getErr() == 0 ){

                    articlesList.addAll( articleRes.getLov() );
                    articleAdapter.notifyDataSetChanged();
                }
                progressBar.setVisibility(View.INVISIBLE);
                srlArticles.setRefreshing( false );
                page = page + 1;
            }

            @Override
            public void onError(VolleyError err) {
                Toast.makeText(getActivity().getApplicationContext(), "Error de conexion!", Toast.LENGTH_SHORT).show();
                progressBar.setVisibility(View.INVISIBLE);
                srlArticles.setRefreshing( false );
            }
        });
    }


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
    public void onTouchEvent(@NonNull RecyclerView rv, @NonNull MotionEvent e) {

    }

    @Override
    public void onRequestDisallowInterceptTouchEvent(boolean disallowIntercept) {

    }
}
