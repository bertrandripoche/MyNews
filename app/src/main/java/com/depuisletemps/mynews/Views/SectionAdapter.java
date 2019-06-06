package com.depuisletemps.mynews.Views;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bumptech.glide.RequestManager;
import com.depuisletemps.mynews.Controllers.Fragments.SectionFragment;
import com.depuisletemps.mynews.Models.Section;
import com.depuisletemps.mynews.R;

import java.util.List;

public class SectionAdapter extends RecyclerView.Adapter<NytimesViewHolder>{

    // FOR DATA
    private List<Section> sections;
    private RequestManager glide;

    // CONSTRUCTOR
    public SectionAdapter(List<Section> sections, RequestManager glide) {
        this.sections = sections;
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
        viewHolder.updateWithBusiness(this.sections.get(position), this.glide);
    }

    @Override
    public int getItemCount() {
        return this.sections.size();
    }
}
