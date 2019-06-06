package com.depuisletemps.mynews.Utils;

import com.depuisletemps.mynews.Controllers.Fragments.SectionFragment;
import com.depuisletemps.mynews.Models.SectionFirstResponse;
import com.depuisletemps.mynews.Models.MostPopularResponse;
import com.depuisletemps.mynews.Models.TopStoryResponse;

import io.reactivex.Observable;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.GET;

public interface NytimesService {

    String KEY= "SYmDviMp91gFcBBaBnVMFeOArfvt7Y92";

    String endUrlTopStory = "svc/topstories/v2/home.json?api-key="+ KEY;
    String endUrlMostPopular = "svc/mostpopular/v2/viewed/7.json?api-key="+ KEY;
    String endUrlBusiness = "svc/search/v2/articlesearch.json?fq=business&api-key="+ KEY;
    String endUrlBooks = "svc/search/v2/articlesearch.json?fq=books&api-key="+ KEY;

    @GET(endUrlTopStory)
    Observable<TopStoryResponse> getTopStoryResults();

    @GET(endUrlMostPopular)
    Observable<MostPopularResponse> getMostPopularResults();

    @GET(endUrlBusiness)
    Observable<SectionFirstResponse> getBusinessResults();

    @GET(endUrlBooks)
    Observable<SectionFirstResponse> getBooksResults();

    public static final Retrofit retrofit = new Retrofit.Builder()
            .baseUrl("https://api.nytimes.com/")
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .build();
}