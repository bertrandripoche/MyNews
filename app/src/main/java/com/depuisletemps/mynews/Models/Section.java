package com.depuisletemps.mynews.Models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Section {
    @SerializedName("web_url")
    @Expose
    private String webUrl;
    @SerializedName("abstract")
    @Expose
    private String _abstract;
    @SerializedName("section_name")
    @Expose
    private String sectionName;
    @SerializedName("multimedia")
    @Expose
    private List<SectionMultimedium> multimedia = null;
    @SerializedName("headline")
    @Expose
    private SectionHeadline sectionHeadline;
    @SerializedName("pub_date")
    @Expose
    private String publishedDate;

    public String getWebUrl() {
        return webUrl;
    }

    public void setWebUrl(String webUrl) {
        this.webUrl = webUrl;
    }

    public String getSectionName() {
        return sectionName;
    }

    public String getAbstract() {
        return _abstract;
    }

    public void setAbstract(String _abstract) {
        this._abstract = _abstract;
    }

    public List<SectionMultimedium> getMultimedia() {
        return multimedia;
    }

    public SectionHeadline getSectionHeadline() {
        return sectionHeadline;
    }

    public String getPublishedDate() {
        return publishedDate;
    }

}
