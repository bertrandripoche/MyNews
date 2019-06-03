package com.depuisletemps.mynews.Models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class TopStoryResponse extends Response {
    @SerializedName("results")
    @Expose
    private List<TopStory> topStories = null;

    public List<TopStory> getTopStories() {
        return topStories;
    }
}
