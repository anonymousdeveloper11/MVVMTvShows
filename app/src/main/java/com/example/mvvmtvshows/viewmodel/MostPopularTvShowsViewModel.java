package com.example.mvvmtvshows.viewmodel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.example.mvvmtvshows.repositories.MostPopularTvShowsRepositories;
import com.example.mvvmtvshows.responses.TvShowResponse;

public class MostPopularTvShowsViewModel  extends ViewModel {
    private MostPopularTvShowsRepositories mostPopularTvShowsRepositories;
    public MostPopularTvShowsViewModel(){
        mostPopularTvShowsRepositories = new MostPopularTvShowsRepositories();
    }

    public LiveData<TvShowResponse> getMostPopularTvShows(int page){
        return mostPopularTvShowsRepositories.getMostPopularTvShows(page);
    }
}
