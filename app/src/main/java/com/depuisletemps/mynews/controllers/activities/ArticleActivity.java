package com.depuisletemps.mynews.controllers.activities;

import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import com.depuisletemps.mynews.controllers.fragments.ArticleFragment;
import com.depuisletemps.mynews.R;

public class ArticleActivity extends AppCompatActivity {

    private ArticleFragment articleFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_article);

        this.configureToolbar();
        this.configureAndShowArticleFragment();
    }

    private void configureAndShowArticleFragment(){

        articleFragment = (ArticleFragment) getSupportFragmentManager().findFragmentById(R.id.activity_article_frame_layout);

        if (articleFragment == null) {
            articleFragment = new ArticleFragment();
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.activity_article_frame_layout, articleFragment)
                    .commit();
        }
    }

    private void configureToolbar(){
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        ActionBar ab = getSupportActionBar();
        ab.setDisplayHomeAsUpEnabled(true);
    }

}
