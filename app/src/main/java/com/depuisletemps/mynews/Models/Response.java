package com.depuisletemps.mynews.Models;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;


public class Response {
    @SerializedName("last_updated")
    @Expose
    private String lastUpdated;

    public String getLastUpdated() {
        return lastUpdated;
    }

}