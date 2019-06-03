package com.depuisletemps.mynews.Models;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class MostPopularMedium {

    @SerializedName("media-metadata")
    @Expose
    private List<MostPopularMediumData> mediaMetadata = null;

    public List<MostPopularMediumData> getMediaMetadata() {
        return mediaMetadata;
    }

}
