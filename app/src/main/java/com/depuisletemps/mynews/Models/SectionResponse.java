package com.depuisletemps.mynews.Models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class SectionResponse {
    @SerializedName("docs")
    @Expose
    private List<Section> docs = null;

    public List<Section> getDocs() {
        return docs;
    }
}
