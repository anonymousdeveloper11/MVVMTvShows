package com.example.mvvmtvshows.viewmodel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.example.mvvmtvshows.repositories.SearchTvSHowRepository;
import com.example.mvvmtvshows.responses.TvShowResponse;

public class SearchViewModel extends ViewModel {

    private SearchTvSHowRepository searchTvSHowRepository;
    public SearchViewModel(){
        searchTvSHowRepository = new SearchTvSHowRepository();
    }

    public LiveData<TvShowResponse> searchTvShow(String query, int page){
        return searchTvSHowRepository.searchTvShow(query,page);
    }
}
