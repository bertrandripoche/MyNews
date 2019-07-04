package com.depuisletemps.mynews.controllers.fragments;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;

import com.bumptech.glide.Glide;
import com.depuisletemps.mynews.models.MostPopular;
import com.depuisletemps.mynews.models.MostPopularResponse;
														
import com.depuisletemps.mynews.R;
import com.depuisletemps.mynews.utils.ItemClickSupport;
import com.depuisletemps.mynews.utils.NytimesStreams;
import com.depuisletemps.mynews.views.MostPopularAdapter;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.observers.DisposableObserver;

public class MostPopularFragment extends BaseFragment {

    public static MostPopularFragment newInstance() {
        return (new MostPopularFragment());
    }

    //FOR DATA
    private List<MostPopular> mostPopulars;
    private MostPopularAdapter adapter;

    void configureOnClickRecyclerView(){
        ItemClickSupport.addTo(recyclerView, R.layout.fragment_main_item)
                .setOnItemClickListener(new ItemClickSupport.OnItemClickListener() {
                    @Override
                    public void onItemClicked(RecyclerView recyclerView, int position, View v) {
                        String url = mostPopulars.get(position).getUrl();
                        startArticleActivity(url);
                    }
                });
    }

    /**
     * This method manages the recyclerView which allow to display each article on the view pager
     */
    void configureRecyclerView(){
        this.mostPopulars = new ArrayList<>();
        this.adapter = new MostPopularAdapter(this.mostPopulars, Glide.with(this));
        this.recyclerView.setAdapter(this.adapter);
        this.recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
    }

    /**
     * This method manages the HTTP request to New-York Times to get the articles to display
     */
    void executeHttpRequestWithRetrofit(){
        this.disposable = NytimesStreams.streamFetchMostPopulars().subscribeWith(new DisposableObserver<MostPopularResponse>() {
            @Override
            public void onNext(MostPopularResponse results) {
                List<MostPopular> mostPopulars = results.getMostPopulars();
                updateUI(mostPopulars);
            }

            @Override
            public void onError(Throwable e) {
                Log.e("TAG", "On Error" + Log.getStackTraceString(e));
            }

            @Override
            public void onComplete() {  }

        });
    }

    /**
     * This method updates the UI with the list of articles (on creation, on refresh)
     */
    private void updateUI(List<MostPopular> mostPopulars){
        swipeRefreshLayout.setRefreshing(false);
        this.mostPopulars.clear();
        this.mostPopulars.addAll(mostPopulars);
        adapter.notifyDataSetChanged();
    }

}