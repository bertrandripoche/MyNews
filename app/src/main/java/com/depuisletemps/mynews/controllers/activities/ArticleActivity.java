package com.depuisletemps.mynews.controllers.activities;

import android.os.Bundle;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.depuisletemps.mynews.controllers.fragments.ArticleFragment;
import com.depuisletemps.mynews.R;

import java.util.Objects;

public class ArticleActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_article);

        this.configureToolbar();
        this.configureAndShowArticleFragment();
    }

    private void configureAndShowArticleFragment(){
        ArticleFragment articleFragment = (ArticleFragment) getSupportFragmentManager().findFragmentById(R.id.activity_article_frame_layout);

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
        Objects.requireNonNull(ab).setDisplayHomeAsUpEnabled(true);
    }

}
