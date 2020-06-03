package com.depuisletemps.mynews.controllers.activities;

import android.os.Bundle;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.depuisletemps.mynews.controllers.fragments.SearchResultFragment;
import com.depuisletemps.mynews.R;

import java.util.Objects;

public class SearchArticlesResultActivity extends AppCompatActivity {

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
        Objects.requireNonNull(ab).setDisplayHomeAsUpEnabled(true);
    }

    private void configureAndShowArticleFragment(){
        SearchResultFragment searchResultFragment = (SearchResultFragment) getSupportFragmentManager().findFragmentById(R.id.activity_search_result_layout);

        if (searchResultFragment == null) {
            searchResultFragment = new SearchResultFragment();
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.activity_search_result_layout, searchResultFragment)
                    .commit();
        }
    }
}