package com.depuisletemps.mynews.views;

import android.content.Context;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bumptech.glide.RequestManager;
import com.depuisletemps.mynews.models.MostPopular;
import com.depuisletemps.mynews.R;

import java.util.List;

public class MostPopularAdapter extends RecyclerView.Adapter<NytimesViewHolder> {
    private List<MostPopular> mostPopulars;
    private RequestManager glide;

    public MostPopularAdapter(List<MostPopular> mostPopulars, RequestManager glide) {
        this.mostPopulars = mostPopulars;
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
        viewHolder.updateWithMostPopulars(this.mostPopulars.get(position), this.glide);
    }

    @Override
    public int getItemCount() {
        return this.mostPopulars.size();
    }

}
