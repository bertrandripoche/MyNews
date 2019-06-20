package com.depuisletemps.mynews.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class SectionResponse {
    @SerializedName("docs")
    @Expose
    private List<Section> docs = null;

    @SerializedName("meta")
    @Expose
    private SectionInfo meta = null;

    public List<Section> getDocs() {
        return docs;
    }

    public SectionInfo getMeta() {
        return meta;
    }
}
