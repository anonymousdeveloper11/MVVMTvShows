package com.example.mvvmtvshows.responses;

import com.example.mvvmtvshows.model.TvShow;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class TvShowResponse {

    @SerializedName("page")
    private int page;
    @SerializedName("pages")
    private int totalPages;

    @SerializedName("tv_shows")
    private List<TvShow> tvShows;

    public int getPage() {
        return page;
    }

    public int getTotalPages() {
        return totalPages;
    }

    public List<TvShow> getTvShows() {
        return tvShows;
    }
}
