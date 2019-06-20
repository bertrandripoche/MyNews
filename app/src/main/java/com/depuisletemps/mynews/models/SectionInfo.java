package com.depuisletemps.mynews.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class SectionInfo {
    @SerializedName("hits")
    @Expose
    private int hits;

    @SerializedName("offset")
    @Expose
    private int offset;

    public int getHits() {
        return hits;
    }

    public int getOffset() {
        return offset;
    }
}
