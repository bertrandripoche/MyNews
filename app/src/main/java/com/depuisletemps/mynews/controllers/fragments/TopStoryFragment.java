package com.depuisletemps.mynews.controllers.fragments;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;

import com.bumptech.glide.Glide;
import com.depuisletemps.mynews.models.TopStory;
import com.depuisletemps.mynews.models.TopStoryResponse;
import com.depuisletemps.mynews.R;
import com.depuisletemps.mynews.utils.ItemClickSupport;
import com.depuisletemps.mynews.utils.NytimesStreams;
import com.depuisletemps.mynews.views.NytimesAdapter;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.observers.DisposableObserver;

public class TopStoryFragment extends BaseFragment {

    public static TopStoryFragment newInstance() {
        return (new TopStoryFragment());
    }

    //FOR DATA
    private List<TopStory> topStories;
    private NytimesAdapter adapter;

    void configureOnClickRecyclerView(){
        ItemClickSupport.addTo(recyclerView, R.layout.fragment_main_item)
                .setOnItemClickListener(new ItemClickSupport.OnItemClickListener() {
                    @Override
                    public void onItemClicked(RecyclerView recyclerView, int position, View v) {
                        Log.e("TAG", "Position : "+position);
                        String url = topStories.get(position).getUrl();
                        startArticleActivity(url);
                    }
                });
    }

    void configureRecyclerView(){
        this.topStories = new ArrayList<>();
        this.adapter = new NytimesAdapter(this.topStories, Glide.with(this));
        this.recyclerView.setAdapter(this.adapter);
        this.recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
    }

    void executeHttpRequestWithRetrofit(){
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

    private void updateUI(List<TopStory> topStories){
        swipeRefreshLayout.setRefreshing(false);
        this.topStories.clear();
        this.topStories.addAll(topStories);
        adapter.notifyDataSetChanged();
    }

}