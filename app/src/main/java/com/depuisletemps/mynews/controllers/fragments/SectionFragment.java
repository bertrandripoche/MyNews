package com.depuisletemps.mynews.controllers.fragments;

import android.os.Bundle;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.util.Log;
import android.view.View;

import com.bumptech.glide.Glide;
import com.depuisletemps.mynews.models.Section;
import com.depuisletemps.mynews.models.SectionFirstResponse;
import com.depuisletemps.mynews.R;
import com.depuisletemps.mynews.utils.DateTools;
import com.depuisletemps.mynews.utils.ItemClickSupport;
import com.depuisletemps.mynews.utils.NytimesStreams;
import com.depuisletemps.mynews.views.SectionAdapter;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Map;

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

    /**
     * This method manages the recyclerView which allow to display each article on the view pager
     */
    void configureRecyclerView () {
        readBundle(getArguments());
        this.sections = new ArrayList<>();
        this.adapter = new SectionAdapter(this.sections, Glide.with(this), sectionName);
        this.recyclerView.setAdapter(this.adapter);
        this.recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
    }

    /**
     * This method manages the HTTP request to New-York Times to get the articles to display
     */
    void executeHttpRequestWithRetrofit () {
        String filterQuery = "section_name:(\""+sectionName+"\")";
        String beginDate = DateTools.oneYearAgo();
        Map<String, String> data = createFilterForStreams("", filterQuery, beginDate, "");

        this.disposable = NytimesStreams.streamFetchSearch(data).subscribeWith(new DisposableObserver<SectionFirstResponse>() {
            @Override
            public void onNext(SectionFirstResponse results) {
                List<Section> sections = results.getResponse().getDocs();
                Collections.sort(sections, new Comparator<Section>() {
                    public int compare(Section s1, Section s2) {
                        return s1.getPublishedDate().compareTo(s2.getPublishedDate());
                    }
                });

                updateUI(sections);
            }

            @Override
            public void onError(Throwable e) {
                Log.e("TAG", "On Error" + Log.getStackTraceString(e));
            }

            @Override
            public void onComplete() {
            }

        });
    }

    /**
     * This method updates the UI with the list of articles (on creation, on refresh)
     */
    private void updateUI (List <Section> sections) {
        swipeRefreshLayout.setRefreshing(false);
        this.sections.clear();
        this.sections.addAll(sections);
        adapter.notifyDataSetChanged();
    }

    private void readBundle(Bundle bundle) {
        if (bundle != null) {
            sectionName = bundle.getString("sectionName");
        }
    }
}