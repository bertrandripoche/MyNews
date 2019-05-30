package com.depuisletemps.mynews.Controllers.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.depuisletemps.mynews.Controllers.Fragments.ArticleFragment;
import com.depuisletemps.mynews.R;

public class ArticleActivity extends AppCompatActivity {

    private ArticleFragment articleFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_article);

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

}
