package com.depuisletemps.mynews.Controllers.Fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bumptech.glide.Glide;
import com.depuisletemps.mynews.Models.TopStoryResponse;
import com.depuisletemps.mynews.Models.TopStory;
import com.depuisletemps.mynews.R;
import com.depuisletemps.mynews.Utils.NytimesStreams;
import com.depuisletemps.mynews.Views.NytimesAdapter;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.reactivex.disposables.Disposable;
import io.reactivex.observers.DisposableObserver;

public class MainFragment extends Fragment {

    // FOR DESIGN
    @BindView(R.id.fragment_main_recycler_view) RecyclerView recyclerView;
    @BindView(R.id.fragment_main_swipe_container) SwipeRefreshLayout swipeRefreshLayout;

    //FOR DATA
    private Disposable disposable;
    private TopStoryResponse response;
    private List<TopStory> topStories;
    private NytimesAdapter adapter;

    public MainFragment() {}

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_main, container, false);
        ButterKnife.bind(this, view);
        this.configureSwipeRefreshLayout();
        this.configureRecyclerView();
        this.executeHttpRequestWithRetrofit();
        return view;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        this.disposeWhenDestroy();
    }

    // -----------------
    // ACTIONS
    // -----------------

    /*@OnClick(R.id.fragment_main_button)
    public void submit(View view) {
        this.executeHttpRequestWithRetrofit();
    }*/

    // -----------------
    // CONFIGURATION
    // -----------------

    private void configureSwipeRefreshLayout(){
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                executeHttpRequestWithRetrofit();
            }
        });
    }

    private void configureRecyclerView(){
        this.topStories = new ArrayList<>();
        this.adapter = new NytimesAdapter(this.topStories, Glide.with(this));
        this.recyclerView.setAdapter(this.adapter);
        this.recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
    }

    // -------------------
    // HTTP (RxJAVA)
    // -------------------

    private void executeHttpRequestWithRetrofit(){
        this.disposable = NytimesStreams.streamFetchTopStories().subscribeWith(new DisposableObserver<TopStoryResponse>() {
            @Override
            public void onNext(TopStoryResponse results) {
                Log.e("TAG","On Next");
                List<TopStory> stories = results.getTopStories();
                updateUI(stories);
            }

            @Override
            public void onError(Throwable e) {
                Log.e("TAG","On Error"+Log.getStackTraceString(e));
            }

            @Override
            public void onComplete() {
                Log.e("TAG","On Complete !!");
            }
        });
    }

    private void disposeWhenDestroy(){
        if (this.disposable != null && !this.disposable.isDisposed()) this.disposable.dispose();
    }

    // -----------------
    // Update UI
    // -----------------

    private void updateUI(List<TopStory> topStories){
        swipeRefreshLayout.setRefreshing(false);
        this.topStories.clear();
        this.topStories.addAll(topStories);
        adapter.notifyDataSetChanged();
    }

}