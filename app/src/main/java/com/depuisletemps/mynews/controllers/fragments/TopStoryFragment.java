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
import com.depuisletemps.mynews.views.TopStoryAdapter;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.observers.DisposableObserver;

public class TopStoryFragment extends BaseFragment {

    public static TopStoryFragment newInstance() {
        return (new TopStoryFragment());
    }

    //FOR DATA
    private List<TopStory> topStories;
    private TopStoryAdapter adapter;

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

    /**
     * This method manages the recyclerView which allow to display each article on the view pager
     */
    void configureRecyclerView(){
        this.topStories = new ArrayList<>();
        this.adapter = new TopStoryAdapter(this.topStories, Glide.with(this));
        this.recyclerView.setAdapter(this.adapter);
        this.recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
    }

    /**
     * This method manages the HTTP request to New-York Times to get the articles to display
     */
    void executeHttpRequestWithRetrofit(){
        this.disposable = NytimesStreams.streamFetchTopStories().subscribeWith(new DisposableObserver<TopStoryResponse>() {
            @Override
            public void onNext(TopStoryResponse results) {
                List<TopStory> stories = results.getTopStories();
                updateUI(stories);
            }

            @Override
            public void onError(Throwable e) {
                Log.e("TAG","On Error"+Log.getStackTraceString(e));
            }

            @Override
            public void onComplete() { }

        });
    }

    /**
     * This method updates the UI with the list of articles (on creation, on refresh)
     */
    private void updateUI(List<TopStory> topStories){
        swipeRefreshLayout.setRefreshing(false);
        this.topStories.clear();
        this.topStories.addAll(topStories);
        adapter.notifyDataSetChanged();
    }

}