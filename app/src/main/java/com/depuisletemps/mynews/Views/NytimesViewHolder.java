package com.depuisletemps.mynews.Views;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.RequestManager;
import com.bumptech.glide.request.RequestOptions;
import com.depuisletemps.mynews.Models.TopStory;
import com.depuisletemps.mynews.Models.TopStoryMultimedium;
import com.depuisletemps.mynews.R;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class NytimesViewHolder extends RecyclerView.ViewHolder {

    @BindView(R.id.fragment_main_item_title) TextView textTitle;
    @BindView(R.id.fragment_main_item_category) TextView textCategory;
    @BindView(R.id.fragment_main_item_published_date) TextView textPublishedDate;
    @BindView(R.id.fragment_main_item_thumb) ImageView imageThumb;

    public NytimesViewHolder(View itemView) {
        super(itemView);
        ButterKnife.bind(this, itemView);
    }

    public void updateWithTopStories(TopStory topStory, RequestManager glide){
        String category = topStory.getSubsection().isEmpty() ? topStory.getSection():topStory.getSection()+" > "+topStory.getSubsection();
        String onlyDay = topStory.getPublishedDate().split("T")[0];
        String date = onlyDay.split("-")[2]+"/"+onlyDay.split("-")[1]+"/"+onlyDay.split("-")[0].substring(2);

        String imageUrl;
        if (!topStory.getMultimedia().isEmpty()) {imageUrl = topStory.getMultimedia().get(0).getUrl();} else {imageUrl = "";}

        this.textTitle.setText(topStory.getTitle());
        this.textCategory.setText(category);
        this.textPublishedDate.setText(date);
        glide.load(imageUrl).into(imageThumb);
    }
}