package com.depuisletemps.mynews.controllers.fragments;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.depuisletemps.mynews.R;

import java.util.Objects;

public class ArticleFragment extends Fragment {

    public ArticleFragment() {}

    @SuppressLint("SetJavaScriptEnabled")
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        String url = Objects.requireNonNull(Objects.requireNonNull(getActivity()).getIntent().getExtras()).getString("ARTICLE_URL");

        View view = inflater.inflate(R.layout.fragment_article, container, false);
        WebView webView = view.findViewById(R.id.activity_article_webview);
        webView.setWebViewClient(new WebViewClient());
        webView.getSettings().setJavaScriptEnabled(true);
        webView.loadUrl(url);
        return view;
    }

}
