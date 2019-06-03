package com.depuisletemps.mynews.Views;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bumptech.glide.RequestManager;
import com.depuisletemps.mynews.Models.MostPopular;
import com.depuisletemps.mynews.R;

import java.util.List;

public class MostPopularAdapter extends RecyclerView.Adapter<NytimesViewHolder> {
    // FOR DATA
    private List<MostPopular> mostPopulars;
    private RequestManager glide;

    // CONSTRUCTOR
    public MostPopularAdapter(List<MostPopular> mostPopulars, RequestManager glide) {
        this.mostPopulars = mostPopulars;
        this.glide = glide;
    }

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
