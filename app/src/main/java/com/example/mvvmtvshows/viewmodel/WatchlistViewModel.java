package com.example.mvvmtvshows.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;

import com.example.mvvmtvshows.database.TvShowDatabase;
import com.example.mvvmtvshows.model.TvShow;

import java.util.List;

import io.reactivex.Completable;
import io.reactivex.Flowable;

public class WatchlistViewModel extends AndroidViewModel {
    private TvShowDatabase tvShowDatabase;
    public WatchlistViewModel(@NonNull Application application){
        super(application);
        tvShowDatabase = TvShowDatabase.getTvShowDatabase(application);

    }

    public Flowable<List<TvShow>> loadWatchlist(){
        return tvShowDatabase.tvShowDao().getWatchList();
    }

    public Completable removeTvShowFromWatchlist(TvShow tvShow){
        return tvShowDatabase.tvShowDao().removeFromWatchList(tvShow);
    }

}
