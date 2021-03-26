package com.example.mvvmtvshows.listeners;

import com.example.mvvmtvshows.model.TvShow;

public interface WatchlistListener {

    void onTvShowClicked(TvShow tvShow);
    void removeTvShowFormWatchList(TvShow tvShow, int position);
}
