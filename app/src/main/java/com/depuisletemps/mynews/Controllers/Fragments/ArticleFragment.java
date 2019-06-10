package com.depuisletemps.mynews.Controllers.Fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.depuisletemps.mynews.R;

public class ArticleFragment extends Fragment {

    private WebView webView;

    public ArticleFragment() {}

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        String url = getActivity().getIntent().getExtras().getString("ARTICLE_URL");

        View view = inflater.inflate(R.layout.fragment_article, container, false);
        webView = (WebView) view.findViewById(R.id.activity_article_webview);
        webView.setWebViewClient(new WebViewClient());
        webView.getSettings().setJavaScriptEnabled(true);
        webView.loadUrl(url);
        return view;
    }

}
