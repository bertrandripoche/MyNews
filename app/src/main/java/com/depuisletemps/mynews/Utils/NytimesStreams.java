package com.depuisletemps.mynews.Utils;

import com.depuisletemps.mynews.Controllers.Fragments.SectionFragment;
import com.depuisletemps.mynews.Models.SectionFirstResponse;
import com.depuisletemps.mynews.Models.MostPopularResponse;
import com.depuisletemps.mynews.Models.TopStoryResponse;

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

    public static Observable<SectionFirstResponse> streamFetchSection(String sectionName){
        NytimesService nytimesService = NytimesService.retrofit.create(NytimesService.class);
        return nytimesService.getSectionResults(sectionName.toLowerCase())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .timeout(10, TimeUnit.SECONDS);
    }
}
