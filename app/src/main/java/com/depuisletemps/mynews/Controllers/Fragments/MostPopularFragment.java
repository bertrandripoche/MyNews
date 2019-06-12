package com.depuisletemps.mynews.Controllers.Fragments;

import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bumptech.glide.Glide;
import com.depuisletemps.mynews.Models.MostPopular;
import com.depuisletemps.mynews.Models.MostPopularResponse;
														
import com.depuisletemps.mynews.R;
import com.depuisletemps.mynews.Utils.ItemClickSupport;
import com.depuisletemps.mynews.Utils.NytimesStreams;
import com.depuisletemps.mynews.Views.MostPopularAdapter;

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
                        Log.e("TAG", "Position : "+position);
                        String url = mostPopulars.get(position).getUrl();
                        startArticleActivity(url);
                    }
                });
    }

    void configureRecyclerView(){
        this.mostPopulars = new ArrayList<>();
        this.adapter = new MostPopularAdapter(this.mostPopulars, Glide.with(this));
        this.recyclerView.setAdapter(this.adapter);
        this.recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
    }

    void executeHttpRequestWithRetrofit(){
        this.disposable = NytimesStreams.streamFetchMostPopulars().subscribeWith(new DisposableObserver<MostPopularResponse>() {
            @Override
            public void onNext(MostPopularResponse results) {
                Log.e("TAG","On Next");
                List<MostPopular> mostPopulars = results.getMostPopulars();
                updateUI(mostPopulars);
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

    private void updateUI(List<MostPopular> mostPopulars){
        swipeRefreshLayout.setRefreshing(false);
        this.mostPopulars.clear();
        this.mostPopulars.addAll(mostPopulars);
        adapter.notifyDataSetChanged();
    }

}