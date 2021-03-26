package com.example.mvvmtvshows.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.example.mvvmtvshows.R;
import com.example.mvvmtvshows.adapters.TvShowsAdapter;
import com.example.mvvmtvshows.databinding.ActivityMainBinding;
import com.example.mvvmtvshows.listeners.TvShowListener;
import com.example.mvvmtvshows.model.TvShow;
import com.example.mvvmtvshows.viewmodel.MostPopularTvShowsViewModel;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements TvShowListener {

    private MostPopularTvShowsViewModel viewModel;
    private ActivityMainBinding activityMainBinding;
    private List<TvShow> tvShows = new ArrayList<>();
    private TvShowsAdapter tvShowsAdapter;

    private int currentPage = 1;
    private int totalAvailablePages = 1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activityMainBinding = DataBindingUtil.setContentView(this,R.layout.activity_main);
        doInitialization();

    }
    private void doInitialization(){
        activityMainBinding.tvShowRv.setHasFixedSize(true);
        viewModel = new ViewModelProvider(this).get(MostPopularTvShowsViewModel.class);
        tvShowsAdapter = new TvShowsAdapter(tvShows,this);
        activityMainBinding.tvShowRv.setAdapter(tvShowsAdapter);
        activityMainBinding.tvShowRv.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                if(!activityMainBinding.tvShowRv.canScrollVertically(1)){
                    if(currentPage <= totalAvailablePages){
                        currentPage += 1;
                        getMostPopularTvShows();
                    }
                }
            }
        });
        activityMainBinding.watchIv.setOnClickListener(v -> startActivity(new Intent(getApplicationContext(),WatchListActivity.class)));
        activityMainBinding.searchIv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(),SearchActivity.class));
            }
        });
        getMostPopularTvShows();
    }

    private void getMostPopularTvShows(){
        toggleLoading();
        viewModel.getMostPopularTvShows(currentPage).observe(this, mostPopularTvShowsResponse ->{


                        toggleLoading();

        if (mostPopularTvShowsResponse != null){
            totalAvailablePages = mostPopularTvShowsResponse.getTotalPages();
            if(mostPopularTvShowsResponse.getTvShows() !=null){
                int oldCount = tvShows.size();
                tvShows.addAll(mostPopularTvShowsResponse.getTvShows());
                tvShowsAdapter.notifyItemRangeInserted(oldCount, tvShows.size());
            }

        }
        });

    }
    private void toggleLoading(){
        if(currentPage == 1){
            if(activityMainBinding.getIsLoading() != null && activityMainBinding.getIsLoading()){
                activityMainBinding.setIsLoading(false);
            } else {
                activityMainBinding.setIsLoading(true);
            }
        } else {
            if(activityMainBinding.getIsLoadingMore() != null && activityMainBinding.getIsLoadingMore()){
                activityMainBinding.setIsLoadingMore(false);
            } else {
                activityMainBinding.setIsLoadingMore(true);
            }
        }
    }

    @Override
    public void onTvShowClicked(TvShow tvShow) {
        Intent intent = new Intent(getApplicationContext(),TvShowDetailsActivity.class);
        intent.putExtra("tvShow", tvShow);
        startActivity(intent);
    }
}