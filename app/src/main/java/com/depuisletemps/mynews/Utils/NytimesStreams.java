package com.depuisletemps.mynews.Utils;

import com.depuisletemps.mynews.Controllers.Fragments.SectionFragment;
import com.depuisletemps.mynews.Models.SectionFirstResponse;
import com.depuisletemps.mynews.Models.MostPopularResponse;
import com.depuisletemps.mynews.Models.TopStoryResponse;

import java.util.HashMap;
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

    public static Observable<SectionFirstResponse> streamFetchSection(String sectionName){
        NytimesService nytimesService = NytimesService.retrofit.create(NytimesService.class);
        String querySectionName = "section_name:(\""+sectionName+"\")";
        return nytimesService.getSectionResults(querySectionName)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .timeout(10, TimeUnit.SECONDS);
    }

    public static Observable<SectionFirstResponse> streamFetchSearch(String query,String filterQuery, String begin, String end){
        Map<String, String > data = new HashMap<>();
        if (!query.equals("")) {data.put("q",query);}
        if (!filterQuery.equals("")) {data.put("fq",filterQuery);}
        if (!begin.equals("")) {data.put("beginDate",begin);}
        if (!end.equals("")) {data.put("endDate",end);}
        System.out.println("Q : "+ query + " -FQ : "+ filterQuery + "- Begin : "+ begin + "- End : "+ end);

        NytimesService nytimesService = NytimesService.retrofit.create(NytimesService.class);
        return nytimesService.getSearchResults(data)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .timeout(10, TimeUnit.SECONDS);
    }
}
