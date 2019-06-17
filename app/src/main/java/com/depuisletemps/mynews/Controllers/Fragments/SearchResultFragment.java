package com.depuisletemps.mynews.Controllers.Fragments;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.depuisletemps.mynews.Controllers.Activities.SearchArticlesActivity;
import com.depuisletemps.mynews.Models.Section;
import com.depuisletemps.mynews.Models.SectionFirstResponse;
import com.depuisletemps.mynews.R;
import com.depuisletemps.mynews.Utils.DateTools;
import com.depuisletemps.mynews.Utils.ItemClickSupport;
import com.depuisletemps.mynews.Utils.NytimesStreams;
import com.depuisletemps.mynews.Views.SectionAdapter;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import io.reactivex.observers.DisposableObserver;

public class SearchResultFragment extends BaseFragment {

    private static final String TAG = SearchResultFragment.class.getName();

    //FOR DATA
    private List<Section> sections;
    private SectionAdapter adapter;
    private Bundle extras;

    private String query = "";
    private String filterQuery = "";
    private String begin = "";
    private String end = "";

    public static SearchResultFragment newInstance() {
        return (new SearchResultFragment());
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getBundle();

        try {
            createSearchCriterias();
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }

    private void getBundle() {
        if (getActivity().getIntent().getExtras() != null) {
            extras = getActivity().getIntent().getExtras();
        }
    }

    private void createSearchCriterias() throws ParseException {
        if (extras != null) {
            query = buildQuery();
            filterQuery = buildFilterQuery();
            buildDatePart();
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
        this.sections = new ArrayList<>();
        this.adapter = new SectionAdapter(this.sections, Glide.with(this));
        this.recyclerView.setAdapter(this.adapter);
        this.recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
    }

    void executeHttpRequestWithRetrofit () {
        this.disposable = NytimesStreams.streamFetchSearch(query,filterQuery,begin,end).subscribeWith(new DisposableObserver<SectionFirstResponse>() {
            @Override
            public void onNext(SectionFirstResponse results) {
                Log.e(TAG, "On Next");
                List<Section> sections = results.getResponse().getDocs();

                if (results.getResponse().getMeta().getHits() == 0) {
                    Toast.makeText(getContext(), R.string.no_result_warning_message, Toast.LENGTH_LONG).show();
                    Intent myIntent = new Intent(getContext(),SearchArticlesActivity.class);
                    startActivity(myIntent);
                } else {
                    updateUI(sections);
                }
            }

            @Override
            public void onError(Throwable e) {
                Log.e(TAG, "On Error" + Log.getStackTraceString(e));
            }

            @Override
            public void onComplete() {
                Log.e(TAG, "On Complete !!");
            }

        });
    }

    private void updateUI (List <Section> sections) {
        swipeRefreshLayout.setRefreshing(false);
        this.sections.clear();
        this.sections.addAll(sections);
        adapter.notifyDataSetChanged();
    }

    private String buildQuery() {
        if (extras.get("terms") == null) {
            return "";
        } else {
            return extras.get("terms").toString().replace(" ","+");
        }
    }

    private String buildFilterQuery() {
        String sectionName = "";
        String sectionPartQuery = "";
        List<String> categoriesArray;
        categoriesArray = Arrays.asList("Arts","Books","Science","Sports","Technology","World");

        for(String category : categoriesArray) {
            if (extras.get(category).toString().equals("checked")) {
                sectionName = (sectionName.equals("")) ? category : sectionName+ "\" \"" +category;
            }
        }

        if (!sectionName.equals("")) {sectionPartQuery = "section_name:(" + sectionName + ")";}
        return sectionPartQuery;
    }

    private String buildDatePart() throws ParseException {
        String datePartQuery = "";
        if (!extras.get("beginDate").toString().equals("")) {
            begin = DateTools.getDateStringFromString(extras.get("beginDate").toString());
        }
        if (!extras.get("endDate").toString().equals("")) {
            end = DateTools.getDateStringFromString(extras.get("endDate").toString());
        }

        return datePartQuery;
    }

}