package com.depuisletemps.mynews.Models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class SectionHeadline {
    @SerializedName("main")
    @Expose
    private String title;

    public String getTitle() {
        return title;
    }
}
