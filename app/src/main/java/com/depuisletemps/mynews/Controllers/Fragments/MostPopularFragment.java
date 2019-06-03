package com.depuisletemps.mynews.Controllers.Fragments;

        import android.content.Intent;
        import android.os.Bundle;
        import android.support.v4.app.Fragment;
        import android.support.v4.widget.SwipeRefreshLayout;
        import android.support.v7.widget.LinearLayoutManager;
        import android.support.v7.widget.RecyclerView;
        import android.util.Log;
        import android.view.LayoutInflater;
        import android.view.View;
        import android.view.ViewGroup;

        import com.bumptech.glide.Glide;
        import com.depuisletemps.mynews.Controllers.Activities.ArticleActivity;
        import com.depuisletemps.mynews.Models.MostPopular;
        import com.depuisletemps.mynews.Models.MostPopularResponse;
        import com.depuisletemps.mynews.R;
        import com.depuisletemps.mynews.Utils.ItemClickSupport;
        import com.depuisletemps.mynews.Utils.NytimesStreams;
        import com.depuisletemps.mynews.Views.MostPopularAdapter;

        import java.util.ArrayList;
        import java.util.List;

        import butterknife.BindView;
        import butterknife.ButterKnife;
        import io.reactivex.disposables.Disposable;
        import io.reactivex.observers.DisposableObserver;

public class MostPopularFragment extends Fragment {

    public static MostPopularFragment newInstance() {
        return (new MostPopularFragment());
    }

    /*
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_most_popular, container, false);
    } */

    // FOR DESIGN
    @BindView(R.id.fragment_main_recycler_view) RecyclerView recyclerView;
    @BindView(R.id.fragment_main_swipe_container) SwipeRefreshLayout swipeRefreshLayout;

    //FOR DATA
    private Disposable disposable;
    private List<MostPopular> mostPopulars;
    private MostPopularAdapter adapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_main, container, false);
        ButterKnife.bind(this, view);
        this.configureSwipeRefreshLayout();
        this.configureRecyclerView();
        this.configureOnClickRecyclerView();
        this.executeHttpRequestWithRetrofit();
        return view;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        this.disposeWhenDestroy();
    }

    // -----------------
    // ACTIONS
    // -----------------

    private void configureOnClickRecyclerView(){
        ItemClickSupport.addTo(recyclerView, R.layout.fragment_main_item)
                .setOnItemClickListener(new ItemClickSupport.OnItemClickListener() {
                    @Override
                    public void onItemClicked(RecyclerView recyclerView, int position, View v) {
                        Log.e("TAG", "Position : "+position);
                        String url = mostPopulars.get(position).getUrl();
                        startArticleActivity(url);
                    }
                });
    }

    // -----------------
    // CONFIGURATION
    // -----------------

    private void configureSwipeRefreshLayout(){
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                executeHttpRequestWithRetrofit();
            }
        });
    }

    private void configureRecyclerView(){
        this.mostPopulars = new ArrayList<>();
        this.adapter = new MostPopularAdapter(this.mostPopulars, Glide.with(this));
        this.recyclerView.setAdapter(this.adapter);
        this.recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
    }

    // -------------------
    // HTTP (RxJAVA)
    // -------------------

    private void executeHttpRequestWithRetrofit(){
        this.disposable = NytimesStreams.streamFetchMostPopulars().subscribeWith(new DisposableObserver<MostPopularResponse>() {
            @Override
            public void onNext(MostPopularResponse results) {
                Log.e("TAG","On Next Most Populars");
                List<MostPopular> mostPopulars = results.getMostPopulars();
                updateUI(mostPopulars);
            }

            @Override
            public void onError(Throwable e) {
                Log.e("TAG","On Error Most Popular "+Log.getStackTraceString(e));
            }

            @Override
            public void onComplete() {
                Log.e("TAG","On Complete Most Popular !!");
            }

        });
    }

    private void disposeWhenDestroy(){
        if (this.disposable != null && !this.disposable.isDisposed()) this.disposable.dispose();
    }

    // -----------------
    // Update UI
    // -----------------

    private void updateUI(List<MostPopular> mostPopulars){
        swipeRefreshLayout.setRefreshing(false);
        this.mostPopulars.clear();
        this.mostPopulars.addAll(mostPopulars);
        adapter.notifyDataSetChanged();
    }

    private void startArticleActivity(String url) {
        Intent intent = new Intent(getActivity(), ArticleActivity.class);
        intent.putExtra("ARTICLE_URL", url);
        startActivity(intent);
    }

}