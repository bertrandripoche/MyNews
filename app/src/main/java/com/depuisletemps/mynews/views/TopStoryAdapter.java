package com.depuisletemps.mynews.views;

import android.content.Context;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bumptech.glide.RequestManager;
import com.depuisletemps.mynews.models.TopStory;
import com.depuisletemps.mynews.R;

import java.util.List;

public class TopStoryAdapter extends RecyclerView.Adapter<NytimesViewHolder> {

    // FOR DATA
    private List<TopStory> topStories;
    private RequestManager glide;

    // CONSTRUCTOR
    public TopStoryAdapter(List<TopStory> topStories, RequestManager glide) {
        this.topStories = topStories;
        this.glide = glide;
    }

    // Create a view of an article summary in the list
    @Override
    public NytimesViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.fragment_main_item, parent, false);

        return new NytimesViewHolder(view);
    }

    @Override
    public void onBindViewHolder(NytimesViewHolder viewHolder, int position) {
        viewHolder.updateWithTopStories(this.topStories.get(position), this.glide);
    }

    @Override
    public int getItemCount() {
        return this.topStories.size();
    }
}


