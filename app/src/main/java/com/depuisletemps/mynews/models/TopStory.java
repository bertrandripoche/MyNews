package com.depuisletemps.mynews.models;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

    public class TopStory {

        @SerializedName("section")
        @Expose
        private String section;
        @SerializedName("subsection")
        @Expose
        private String subsection;
        @SerializedName("title")
        @Expose
        private String title;
        @SerializedName("abstract")
        @Expose
        private String _abstract;
        @SerializedName("url")
        @Expose
        private String url;
        @SerializedName("published_date")
        @Expose
        private String publishedDate;
        @SerializedName("multimedia")
        @Expose
        private List<TopStoryMultimedium> multimedia = null;
        @SerializedName("short_url")
        @Expose
        private String shortUrl;

        public String getSection() {
            return section;
        }

        public String getSubsection() {
            return subsection;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getAbstract() {
            return _abstract;
        }

        public String getUrl() {
            return url;
        }

        public String getPublishedDate() {
            return publishedDate;
        }

        public List<TopStoryMultimedium> getMultimedia() {
            return multimedia;
        }

        public String getShortUrl() {
            return shortUrl;
        }

    }


