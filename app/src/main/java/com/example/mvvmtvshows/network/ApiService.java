package com.example.mvvmtvshows.network;

import com.example.mvvmtvshows.responses.TvShowDetailsResponse;
import com.example.mvvmtvshows.responses.TvShowResponse;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface ApiService {
    @GET("most-popular")
    Call<TvShowResponse> getMostPopularTvShows(@Query("page") int page);

    @GET("show-details")
    Call<TvShowDetailsResponse> getTvShowDetails(@Query("q") String tvShowId);

    @GET("search")
    Call<TvShowResponse> searchTvShow(@Query("q") String query, @Query("page") int page);

}
