package com.depuisletemps.mynews.Views;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.RequestManager;
import com.depuisletemps.mynews.Models.Section;
import com.depuisletemps.mynews.Models.MostPopular;
import com.depuisletemps.mynews.Models.TopStory;
import com.depuisletemps.mynews.R;

import butterknife.BindView;
import butterknife.ButterKnife;

public class NytimesViewHolder extends RecyclerView.ViewHolder {

    String genericThumb = "https://static01.nyt.com/images/2018/08/24/admin/onboarding_10/onboarding_10-articleLarge-v6.jpg?quality=75&auto=webp&disable=upscale";
    String imageUrl, date, category, title;

    @BindView(R.id.fragment_main_item_title) TextView textTitle;
    @BindView(R.id.fragment_main_item_category) TextView textCategory;
    @BindView(R.id.fragment_main_item_published_date) TextView textPublishedDate;
    @BindView(R.id.fragment_main_item_thumb) ImageView imageThumb;

    public NytimesViewHolder(View itemView) {
        super(itemView);
        ButterKnife.bind(this, itemView);
    }

    public void updateWithTopStories(TopStory topStory, RequestManager glide){
        String onlyDay = topStory.getPublishedDate().split("T")[0];

        category = topStory.getSubsection().isEmpty() ? topStory.getSection():topStory.getSection()+" > "+topStory.getSubsection();
        title = topStory.getTitle();
        date = onlyDay.split("-")[2]+"/"+onlyDay.split("-")[1]+"/"+onlyDay.split("-")[0].substring(2);
        imageUrl = topStory.getMultimedia().isEmpty()? genericThumb : topStory.getMultimedia().get(0).getUrl();

        displayItem(category,date,title,imageUrl,glide);
    }

    public void updateWithMostPopulars(MostPopular mostPopular, RequestManager glide){
        String nonFormattedDay = mostPopular.getPublishedDate();

        date = nonFormattedDay.split("-")[2]+"/"+nonFormattedDay.split("-")[1]+"/"+nonFormattedDay.split("-")[0].substring(2);
        category = !mostPopular.getSection().isEmpty() ? mostPopular.getSection() : "";
        title = mostPopular.getTitle();
        imageUrl = mostPopular.getMedia().get(0).getMediaMetadata().isEmpty() ? genericThumb : mostPopular.getMedia().get(0).getMediaMetadata().get(0).getUrl();

        displayItem(category,date,title,imageUrl,glide);
    }

    public void updateWithSection(Section section, RequestManager glide){
        String nonFormattedDay = section.getPublishedDate().split("T")[0];

        date = nonFormattedDay.split("-")[2]+"/"+nonFormattedDay.split("-")[1]+"/"+nonFormattedDay.split("-")[0].substring(2);
        title = section.getSectionHeadline().getTitle();
        imageUrl = section.getMultimedia().isEmpty() ? genericThumb : "https://static01.nyt.com/"+ section.getMultimedia().get(0).getUrl();
        category = section.getSectionName();

        displayItem(category,date,title,imageUrl,glide);
    }

    private void displayItem(String category, String date, String title, String imageUrl, RequestManager glide) {
        this.textTitle.setText(title);
        this.textCategory.setText(category);
        this.textPublishedDate.setText(date);
        glide.load(imageUrl).into(imageThumb);
    }

}