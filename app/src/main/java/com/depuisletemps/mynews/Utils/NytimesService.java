package com.depuisletemps.mynews.Utils;

import com.depuisletemps.mynews.Controllers.Fragments.SectionFragment;
import com.depuisletemps.mynews.Models.SectionFirstResponse;
import com.depuisletemps.mynews.Models.MostPopularResponse;
import com.depuisletemps.mynews.Models.TopStoryResponse;

import java.util.Map;

import io.reactivex.Observable;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.GET;
import retrofit2.http.Query;
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
    Observable<SectionFirstResponse> getSectionResults(@Query("fq") String filterQuery);

    @GET(endUrlSection)
    //Observable<SectionFirstResponse> getSearchResults(@Query("q") String query, @Query("fq") String filterQuery);
    Observable<SectionFirstResponse> getSearchResults(@QueryMap Map<String, String> options);


    public static final Retrofit retrofit = new Retrofit.Builder()
            .baseUrl("https://api.nytimes.com/")
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .build();
}