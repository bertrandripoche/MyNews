package com.depuisletemps.mynews.controllers.fragments;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.content.Intent;
import android.os.Bundle;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.depuisletemps.mynews.controllers.activities.ArticleActivity;
import com.depuisletemps.mynews.R;

import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.reactivex.disposables.Disposable;

abstract class BaseFragment extends Fragment {

    abstract void configureRecyclerView();
    abstract void configureOnClickRecyclerView();
    abstract void executeHttpRequestWithRetrofit();

    // FOR DESIGN
    @BindView(R.id.fragment_main_recycler_view) RecyclerView recyclerView;
    @BindView(R.id.fragment_main_swipe_container) SwipeRefreshLayout swipeRefreshLayout;

    //FOR DATA
    protected Disposable disposable;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_main, container, false);
        ButterKnife.bind(this, view);
        this.configureSwipeRefreshLayout();
        this.configureRecyclerView();
        this.configureOnClickRecyclerView();
        this.executeHttpRequestWithRetrofit();
        return view;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        this.disposeWhenDestroy();
    }

    /**
     * This method allows to swipe the view pager to refresh
     */
    protected void configureSwipeRefreshLayout(){
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                executeHttpRequestWithRetrofit();
            }
        });
    }

    private void disposeWhenDestroy(){
        if (this.disposable != null && !this.disposable.isDisposed()) this.disposable.dispose();
    }

    protected void startArticleActivity(String url) {
        Intent intent = new Intent(getActivity(), ArticleActivity.class);
        intent.putExtra("ARTICLE_URL", url);
        startActivity(intent);
    }

    /**
     * This method returns the a Map<String, String> needed to create the filters for streams
     * @param query : our serialized String representing our MoodStore
     * @param filterQuery : our serialized String representing our MoodStore
     * @param begin : our serialized String representing our MoodStore
     * @param end : our serialized String representing our MoodStore
     * @return a Map<String, String> of name of filter and their value, used for Streams
     */
    protected Map<String, String > createFilterForStreams(String query, String filterQuery, String begin, String end) {
        Map<String, String > data = new HashMap<>();
        if (!query.equals("")) {data.put("q",query);}
        if (!filterQuery.equals("")) {data.put("fq",filterQuery);}
        if (!begin.equals("")) {data.put("beginDate",begin);}
        if (!end.equals("")) {data.put("endDate",end);}
        return data;
    }

}

