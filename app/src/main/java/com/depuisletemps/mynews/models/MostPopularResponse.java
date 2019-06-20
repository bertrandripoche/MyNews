package com.depuisletemps.mynews.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class MostPopularResponse {
    @SerializedName("results")
    @Expose
    private List<MostPopular> mostPopulars = null;

    public List<MostPopular> getMostPopulars() {
        return mostPopulars;
    }
}
