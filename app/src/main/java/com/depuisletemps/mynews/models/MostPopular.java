package com.depuisletemps.mynews.models;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class MostPopular {

    @SerializedName("url")
    @Expose
    private String url;
    @SerializedName("section")
    @Expose
    private String section;
    @SerializedName("title")
    @Expose
    private String title;
    @SerializedName("abstract")
    @Expose
    private String _abstract;
    @SerializedName("published_date")
    @Expose
    private String publishedDate;
    @SerializedName("media")
    @Expose
    private List<MostPopularMedium> media = null;

    public String getUrl() {
        return url;
    }

    public String getSection() {
        return section;
    }

    public String getTitle() {
        return title;
    }

    public String getPublishedDate() {
        return publishedDate;
    }

    public List<MostPopularMedium> getMedia() {
        return media;
    }

}