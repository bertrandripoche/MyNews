package com.depuisletemps.mynews.Controllers.Fragments;

import android.os.Bundle;
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

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import io.reactivex.observers.DisposableObserver;

public class SearchResultFragment extends BaseFragment {

    private static final String TAG = SearchResultFragment.class.getName();

    //FOR DATA
    private List<Section> sections;
    private SectionAdapter adapter;
    private Bundle extras;

    private String query;
    private String filterQuery;

    public static SearchResultFragment newInstance() {
        return (new SearchResultFragment());
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        getBundle();

        try {
            createSearchString();
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }

    private void getBundle() {
        if (getActivity().getIntent().getExtras() != null) {
            extras = getActivity().getIntent().getExtras();
        }
    }

    private void createSearchString() throws ParseException {
        if (extras != null) {
            query = buildQuery();
            filterQuery = buildFilterQuery();
            System.out.println(query + " ET " +filterQuery);
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
        this.disposable = NytimesStreams.streamFetchSearch(query,filterQuery).subscribeWith(new DisposableObserver<SectionFirstResponse>() {
            @Override
            public void onNext(SectionFirstResponse results) {
                Log.e(TAG, "On Next");
                List<Section> sections = results.getResponse().getDocs();
                updateUI(sections);
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
            return extras.get("terms").toString();
        }
    }

    private String buildFilterQuery() throws ParseException {
        String sectionPartQuery = buildSectionPart();
        String datePartQuery = buildDatePart();

        System.out.println("ICI : " +sectionPartQuery + datePartQuery);

        return sectionPartQuery + datePartQuery;
    }

    private String buildSectionPart() {
        String sectionName = "";
        String sectionPartQuery = "";
        List<String> categoriesArray;
        categoriesArray = Arrays.asList("Arts","Books","Science","Sports","Technology","World");

        for(String category : categoriesArray) {
            if (extras.get(category).toString().equals("checked")) {
                sectionName = (sectionName.equals("")) ? "\""+category+"\"" : sectionName+" \""+category+"\"";
            }
        }

        if (!sectionName.equals("")) {sectionPartQuery = "section_name:=(" + sectionName + ")";}
        return sectionPartQuery;
    }

    private String buildDatePart() throws ParseException {
        String datePartQuery = "";
        if (!extras.get("beginDate").toString().equals("")) {datePartQuery = "&beginDate=" + getDate(extras.get("beginDate").toString());}
        if (!extras.get("endDate").toString().equals("")) {datePartQuery = datePartQuery + "&endDate=" + getDate(extras.get("endDate").toString());}

        return datePartQuery;
    }

    private String getDate(String myDate) throws ParseException {
        SimpleDateFormat WRONG_DATE_FORMAT = new SimpleDateFormat("dd/MM/yyyy");
        SimpleDateFormat RIGHT_DATE_FORMAT = new SimpleDateFormat("yyyyMMdd");

        Date date = WRONG_DATE_FORMAT.parse(myDate);

        return RIGHT_DATE_FORMAT.format(date);
    }
}