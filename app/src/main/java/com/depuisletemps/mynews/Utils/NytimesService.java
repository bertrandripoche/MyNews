package com.depuisletemps.mynews.Utils;

import com.depuisletemps.mynews.Models.Response;
import com.depuisletemps.mynews.Models.TopStoryResponse;

import io.reactivex.Observable;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.GET;

public interface NytimesService {

    String endUrl = "svc/topstories/v2/home.json?api-key=SYmDviMp91gFcBBaBnVMFeOArfvt7Y92";

    @GET(endUrl)
    Observable<TopStoryResponse> getResults();

    public static final Retrofit retrofit = new Retrofit.Builder()
            .baseUrl("https://api.nytimes.com/")
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .build();
}
