package com.example.mvvmtvshows.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.example.mvvmtvshows.database.TvShowDatabase;
import com.example.mvvmtvshows.model.TvShow;
import com.example.mvvmtvshows.repositories.TvShowDetailsRepository;
import com.example.mvvmtvshows.responses.TvShowDetailsResponse;

import io.reactivex.Completable;
import io.reactivex.Flowable;

public class TvShowDetailsViewModel extends AndroidViewModel {
    private TvShowDetailsRepository tvShowDetailsRepository;
    private TvShowDatabase tvShowDatabase;

    public TvShowDetailsViewModel(@NonNull Application application){
        super(application);
        tvShowDetailsRepository = new TvShowDetailsRepository();
        tvShowDatabase = TvShowDatabase.getTvShowDatabase(application);
    }

    public LiveData<TvShowDetailsResponse> getTvShowDetails(String tvShowId){
        return tvShowDetailsRepository.getTvShowDetails(tvShowId);
    }
    public Completable addToWatchList(TvShow tvShow){
        return  tvShowDatabase.tvShowDao().addToWatchList(tvShow);
    }

    public Flowable<TvShow> getTvShowFromWatchlist(String tvShowId){
        return tvShowDatabase.tvShowDao().getTvShowFromWatchlist(tvShowId);
    }

    public Completable removeTvShowFromWatchlist(TvShow tvShow){
        return tvShowDatabase.tvShowDao().removeFromWatchList(tvShow);
    }
}
