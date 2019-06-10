package com.depuisletemps.mynews.Controllers.Activities;

import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;

import com.depuisletemps.mynews.Controllers.Fragments.SearchResultFragment;
import com.depuisletemps.mynews.R;

public class SearchArticlesResultActivity extends AppCompatActivity {
    private SearchResultFragment searchResultFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_articles_result);

        this.configureToolbar();


        Bundle bundle = getIntent().getExtras();
        String test = bundle.getString("begin");
        Log.e("TAG", test);

        //this.configureAndShowArticleFragment();
    }

    private void configureAndShowArticleFragment(){

        searchResultFragment = (SearchResultFragment) getSupportFragmentManager().findFragmentById(R.id.activity_article_frame_layout);

        if (searchResultFragment == null) {
            searchResultFragment = new SearchResultFragment();
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.activity_article_frame_layout, searchResultFragment)
                    .commit();
        }
    }

    private void configureToolbar(){
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        ActionBar ab = getSupportActionBar();
        ab.setDisplayHomeAsUpEnabled(true);
    }
}
