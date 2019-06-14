package com.depuisletemps.mynews.Controllers.Fragments;

import android.support.v4.app.Fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.depuisletemps.mynews.Controllers.Activities.ArticleActivity;
import com.depuisletemps.mynews.R;

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
        public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
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

        // -----------------
        // CONFIGURATION
        // -----------------

        protected void configureSwipeRefreshLayout(){
            swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
                @Override
                public void onRefresh() {
                    executeHttpRequestWithRetrofit();
                }
            });
        }

        // -------------------
        // HTTP (RxJAVA)
        // -------------------

    private void disposeWhenDestroy(){
        if (this.disposable != null && !this.disposable.isDisposed()) this.disposable.dispose();
    }

    protected void startArticleActivity(String url) {
        Intent intent = new Intent(getActivity(), ArticleActivity.class);
        intent.putExtra("ARTICLE_URL", url);
        startActivity(intent);
    }

}

