package com.depuisletemps.mynews.controllers.activities;

import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import com.depuisletemps.mynews.controllers.fragments.SearchResultFragment;
import com.depuisletemps.mynews.R;

public class SearchArticlesResultActivity extends AppCompatActivity {
    private static final String TAG = SearchResultFragment.class.getName();

    private SearchResultFragment searchResultFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_result);

        this.configureToolbar();
        this.configureAndShowArticleFragment();
    }

    private void configureToolbar(){
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        ActionBar ab = getSupportActionBar();
        ab.setDisplayHomeAsUpEnabled(true);
    }

    private void configureAndShowArticleFragment(){
        searchResultFragment = (SearchResultFragment) getSupportFragmentManager().findFragmentById(R.id.activity_search_result_layout);

        if (searchResultFragment == null) {
            searchResultFragment = new SearchResultFragment();
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.activity_search_result_layout, searchResultFragment)
                    .commit();
        }
    }
}