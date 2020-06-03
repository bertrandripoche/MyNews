package com.depuisletemps.mynews.controllers.fragments;

import android.content.Intent;
import android.os.Bundle;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.depuisletemps.mynews.controllers.activities.SearchArticlesActivity;
import com.depuisletemps.mynews.models.Section;
import com.depuisletemps.mynews.models.SectionFirstResponse;
import com.depuisletemps.mynews.R;
import com.depuisletemps.mynews.utils.DateTools;
import com.depuisletemps.mynews.utils.ItemClickSupport;
import com.depuisletemps.mynews.utils.NytimesStreams;
import com.depuisletemps.mynews.views.SectionAdapter;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Objects;

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

        try {createSearchCriterias();
        } catch (ParseException e) {e.printStackTrace();}
    }

    /**
     * This method allows to retrieve the bundle to get the search parameters
     */
    private void getBundle() {
        if (Objects.requireNonNull(getActivity()).getIntent().getExtras() != null) {
            extras = getActivity().getIntent().getExtras();
        }
    }

    /**
     * This method
     */
    private void createSearchCriterias() throws ParseException {
        if (extras != null) {
            query = buildQuery();
            filterQuery = buildFilterQuery(extras);
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

    /**
     * This method manages the recyclerView which allow to display each article on the view pager
     */
    void configureRecyclerView () {
        this.sections = new ArrayList<>();
        this.adapter = new SectionAdapter(this.sections, Glide.with(this));
        this.recyclerView.setAdapter(this.adapter);
        this.recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
    }

    /**
     * This method manages the HTTP request to New-York Times to get the articles to display
     */
    void executeHttpRequestWithRetrofit () {
        Map<String, String> data = createFilterForStreams(query, filterQuery, begin, end);

        this.disposable = NytimesStreams.streamFetchSearch(data).subscribeWith(new DisposableObserver<SectionFirstResponse>() {
            @Override
            public void onNext(SectionFirstResponse results) {
                List<Section> sections = results.getResponse().getDocs();

                if (results.getResponse().getMeta().getHits() == 0) {
                    Toast.makeText(getContext(), R.string.no_result_warning_message, Toast.LENGTH_LONG).show();
                    Intent myIntent = new Intent(getContext(),SearchArticlesActivity.class);
                    startActivity(myIntent);
                } else {
                    Collections.sort(sections, new Comparator<Section>() {
                        public int compare(Section s1, Section s2) {
                            return s1.getPublishedDate().compareTo(s2.getPublishedDate());
                        }
                    });
                    updateUI(sections);
                }
            }

            @Override
            public void onError(Throwable e) {
                Log.e(TAG, "On Error" + Log.getStackTraceString(e));
            }

            @Override
            public void onComplete() { }

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

    /**
     * This method returns the string used for the section filter for the streams
     * @return a string which will be one filter for the streams
     */
    private String buildQuery() {
        if (extras.get("terms") == null) {
            return "";
        } else {
            return Objects.requireNonNull(extras.get("terms")).toString().replace(" ","+");
        }
    }

    /**
     * This method returns the string used for the section filter for the streams
     * @param bundle : our bundle coming from the SearchArticlesActivity
     * @return a string which will be the filter
     */
    protected String buildFilterQuery(Bundle bundle) {
        String sectionName = "";
        String sectionPartQuery = "";
        List<String> categoriesArray;
        categoriesArray = Arrays.asList("Arts","Books","Science","Sports","Technology","World");

        for(String category : categoriesArray) {
            if (bundle.getString(category) != null) {
                if (Objects.requireNonNull(bundle.getString(category)).equals("checked")) {
                    sectionName = (sectionName.equals("")) ? category : sectionName+ "\" \"" +category;
                }
            } else {sectionPartQuery="";}
        }

        if (!sectionName.equals("")) {sectionPartQuery = "section_name:(" + sectionName + ")";}

        return sectionPartQuery;
    }

    /**
     * This method set the begin or end variable, used for the date filter for the streams
     */
    private void buildDatePart() throws ParseException {
        if (!Objects.requireNonNull(extras.get("beginDate")).toString().equals("")) {
            begin = DateTools.getDateStringFromString(Objects.requireNonNull(extras.get("beginDate")).toString());
        }
        if (!Objects.requireNonNull(extras.get("endDate")).toString().equals("")) {
            end = DateTools.getDateStringFromString(Objects.requireNonNull(extras.get("endDate")).toString());
        }
    }

}