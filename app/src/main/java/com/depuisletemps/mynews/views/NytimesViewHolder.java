package com.depuisletemps.mynews.views;

import androidx.recyclerview.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.RequestManager;
import com.depuisletemps.mynews.models.Section;
import com.depuisletemps.mynews.models.MostPopular;
import com.depuisletemps.mynews.models.TopStory;
import com.depuisletemps.mynews.R;

import butterknife.BindView;
import butterknife.ButterKnife;

class NytimesViewHolder extends RecyclerView.ViewHolder {

    private String genericThumb = "https://static01.nyt.com/images/2018/08/24/admin/onboarding_10/onboarding_10-articleLarge-v6.jpg?quality=75&auto=webp&disable=upscale";
    private String imageUrl, date, category, title;

    @BindView(R.id.fragment_main_item_title) TextView textTitle;
    @BindView(R.id.fragment_main_item_category) TextView textCategory;
    @BindView(R.id.fragment_main_item_published_date) TextView textPublishedDate;
    @BindView(R.id.fragment_main_item_thumb) ImageView imageThumb;

    NytimesViewHolder(View itemView) {
        super(itemView);
        ButterKnife.bind(this, itemView);
    }

    // Get data to populate viewholder
    void updateWithTopStories(TopStory topStory, RequestManager glide){
        String onlyDay = topStory.getPublishedDate().split("T")[0];

        category = topStory.getSubsection().isEmpty() ? topStory.getSection():topStory.getSection()+" > "+topStory.getSubsection();
        title = topStory.getTitle();
        date = onlyDay.split("-")[2]+"/"+onlyDay.split("-")[1]+"/"+onlyDay.split("-")[0].substring(2);
        imageUrl = topStory.getMultimedia().isEmpty()? genericThumb : topStory.getMultimedia().get(0).getUrl();

        displayItem(category,date,title,imageUrl,glide);
    }

    // Get data to populate viewholder
    void updateWithMostPopulars(MostPopular mostPopular, RequestManager glide){
        if (mostPopular != null) {
            String nonFormattedDay = mostPopular.getPublishedDate();

            date = nonFormattedDay.split("-")[2]+"/"+nonFormattedDay.split("-")[1]+"/"+nonFormattedDay.split("-")[0].substring(2);
            category = !mostPopular.getSection().isEmpty() ? mostPopular.getSection() : "";
            title = mostPopular.getTitle();
            if (!mostPopular.getMedia().isEmpty()) imageUrl = mostPopular.getMedia().get(0).getMediaMetadata().isEmpty() ? genericThumb : mostPopular.getMedia().get(0).getMediaMetadata().get(0).getUrl();
            else imageUrl = genericThumb;

            displayItem(category,date,title,imageUrl,glide);
        }
    }

    // Get data to populate viewholder
    void updateWithSection(Section section, RequestManager glide){
        String nonFormattedDay = section.getPublishedDate().split("T")[0];

        date = nonFormattedDay.split("-")[2]+"/"+nonFormattedDay.split("-")[1]+"/"+nonFormattedDay.split("-")[0].substring(2);
        title = section.getArticleAbstract().equals("") ? section.getSectionHeadline().getTitle(): section.getArticleAbstract();
        title = title.equals("") ? "No title for this article": title;
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