package com.depuisletemps.mynews.utils;

import com.depuisletemps.mynews.models.SectionFirstResponse;
import com.depuisletemps.mynews.models.MostPopularResponse;
import com.depuisletemps.mynews.models.TopStoryResponse;

import java.util.Map;

import io.reactivex.Observable;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.GET;
import retrofit2.http.QueryMap;

public interface NytimesService {

    @SuppressWarnings("SpellCheckingInspection")
    String KEY= "SYmDviMp91gFcBBaBnVMFeOArfvt7Y92";

    String endUrlTopStory = "svc/topstories/v2/home.json?api-key="+ KEY;
    String endUrlMostPopular = "svc/mostpopular/v2/viewed/7.json?api-key="+ KEY;
    String endUrlSection = "svc/search/v2/articlesearch.json?api-key="+ KEY + "&";

    @GET(endUrlTopStory)
    Observable<TopStoryResponse> getTopStoryResults();

    @GET(endUrlMostPopular)
    Observable<MostPopularResponse> getMostPopularResults();

    @GET(endUrlSection)
    Observable<SectionFirstResponse> getSearchResults(@QueryMap Map<String, String> options);

    // Retrofit query to get the article list
    Retrofit retrofit = new Retrofit.Builder()
            .baseUrl("https://api.nytimes.com/")
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .build();
}