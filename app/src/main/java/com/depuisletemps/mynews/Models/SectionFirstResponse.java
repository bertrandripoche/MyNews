package com.depuisletemps.mynews.Models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class SectionFirstResponse {
    @SerializedName("response")
    @Expose
    private SectionResponse response;

    public SectionResponse getResponse() {
        return response;
    }
}
