package com.depuisletemps.mynews.utils;

import com.depuisletemps.mynews.models.SectionFirstResponse;
import com.depuisletemps.mynews.models.MostPopularResponse;
import com.depuisletemps.mynews.models.TopStoryResponse;

import java.util.Map;
import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class NytimesStreams {
    public static Observable<TopStoryResponse> streamFetchTopStories(){
        NytimesService nytimesService = NytimesService.retrofit.create(NytimesService.class);
        return nytimesService.getTopStoryResults()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .timeout(10, TimeUnit.SECONDS);
    }

    public static Observable<MostPopularResponse> streamFetchMostPopulars(){
        NytimesService nytimesService = NytimesService.retrofit.create(NytimesService.class);
        return nytimesService.getMostPopularResults()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .timeout(10, TimeUnit.SECONDS);
    }

    public static Observable<SectionFirstResponse> streamFetchSearch(Map<String, String > data){

        NytimesService nytimesService = NytimesService.retrofit.create(NytimesService.class);
        return nytimesService.getSearchResults(data)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .timeout(10, TimeUnit.SECONDS);
    }
}
