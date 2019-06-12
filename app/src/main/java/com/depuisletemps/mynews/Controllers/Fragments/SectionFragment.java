package com.depuisletemps.mynews.Controllers.Fragments;

import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;

import com.bumptech.glide.Glide;
import com.depuisletemps.mynews.Models.Section;
import com.depuisletemps.mynews.Models.SectionFirstResponse;
import com.depuisletemps.mynews.R;
import com.depuisletemps.mynews.Utils.ItemClickSupport;
import com.depuisletemps.mynews.Utils.NytimesStreams;
import com.depuisletemps.mynews.Views.SectionAdapter;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.observers.DisposableObserver;

public class SectionFragment extends BaseFragment {

    //FOR DATA
    private List<Section> sections;
    private SectionAdapter adapter;
    private String sectionName;

    public static SectionFragment newInstance(String sectionName) {
        SectionFragment myFragment = new SectionFragment();

        Bundle args = new Bundle();
        args.putString("sectionName", sectionName);
        myFragment.setArguments(args);

        return myFragment;
    }

    private void readBundle(Bundle bundle) {
        if (bundle != null) {
            sectionName = bundle.getString("sectionName");
        }
    }

    void configureOnClickRecyclerView () {
            ItemClickSupport.addTo(recyclerView, R.layout.fragment_main_item)
                    .setOnItemClickListener(new ItemClickSupport.OnItemClickListener() {
                        @Override
                        public void onItemClicked(RecyclerView recyclerView, int position, View v) {
                            String url = sections.get(position).getWebUrl();
                            startArticleActivity(url);
                        }
                    });
        }

    void configureRecyclerView () {
        readBundle(getArguments());
        this.sections = new ArrayList<>();
        this.adapter = new SectionAdapter(this.sections, Glide.with(this), sectionName);
        this.recyclerView.setAdapter(this.adapter);
        this.recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
    }

    void executeHttpRequestWithRetrofit () {
        this.disposable = NytimesStreams.streamFetchSection(sectionName).subscribeWith(new DisposableObserver<SectionFirstResponse>() {
            @Override
            public void onNext(SectionFirstResponse results) {
                Log.e("TAG", "On Next");
                List<Section> sections = results.getResponse().getDocs();
                updateUI(sections);
            }

            @Override
            public void onError(Throwable e) {
                Log.e("TAG", "On Error" + Log.getStackTraceString(e));
            }

            @Override
            public void onComplete() {
                Log.e("TAG", "On Complete !!");
            }

        });
    }

    private void updateUI (List <Section> sections) {
        swipeRefreshLayout.setRefreshing(false);
        this.sections.clear();
        this.sections.addAll(sections);
        adapter.notifyDataSetChanged();
    }

}