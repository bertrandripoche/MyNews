package com.depuisletemps.mynews.Views;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.depuisletemps.mynews.Models.TopStory;
import com.depuisletemps.mynews.R;

import butterknife.BindView;
import butterknife.ButterKnife;

public class NytimesViewHolder extends RecyclerView.ViewHolder {

    @BindView(R.id.fragment_main_item_title) TextView textView;

    public NytimesViewHolder(View itemView) {
        super(itemView);
        ButterKnife.bind(this, itemView);
    }

    public void updateWithTopStories(TopStory topStory){
        this.textView.setText(topStory.getTitle());
    }
}