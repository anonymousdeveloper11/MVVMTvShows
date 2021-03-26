package com.example.mvvmtvshows.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProvider;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.example.mvvmtvshows.R;
import com.example.mvvmtvshows.adapters.WatchlistAdapter;
import com.example.mvvmtvshows.databinding.ActivityWatchListBinding;
import com.example.mvvmtvshows.listeners.WatchlistListener;
import com.example.mvvmtvshows.model.TvShow;
import com.example.mvvmtvshows.utilities.TempDataHolder;
import com.example.mvvmtvshows.viewmodel.WatchlistViewModel;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.schedulers.Schedulers;

public class WatchListActivity extends AppCompatActivity implements WatchlistListener {
private ActivityWatchListBinding activityWatchListBinding;
private WatchlistViewModel viewModel;
private WatchlistAdapter watchlistAdapter;
private List<TvShow> watchList;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activityWatchListBinding = DataBindingUtil.setContentView(this, R.layout.activity_watch_list);
        donInitialization();
    }

    private void donInitialization() {
        viewModel = new ViewModelProvider(this).get(WatchlistViewModel.class);
        activityWatchListBinding.backIv.setOnClickListener(v -> onBackPressed());
        watchList = new ArrayList<>();
        loadWatchlist();

    }
    private void loadWatchlist(){
        activityWatchListBinding.setIsLoading(true);
        CompositeDisposable compositeDisposable = new CompositeDisposable();
        compositeDisposable.add(viewModel.loadWatchlist().subscribeOn(Schedulers.computation())
        .observeOn(AndroidSchedulers.mainThread())
        .subscribe(tvShows -> {
            activityWatchListBinding.setIsLoading(false);

            if(watchList.size() > 0){
                watchList.clear();
            }
            watchList.addAll(tvShows);
            watchlistAdapter = new WatchlistAdapter(watchList, this);
            activityWatchListBinding.watchListRv.setAdapter(watchlistAdapter);
            activityWatchListBinding.watchListRv.setVisibility(View.VISIBLE);
            compositeDisposable.dispose();

        }));

    }

    @Override
    protected void onResume() {
        super.onResume();
        if(TempDataHolder.IS_WATCHLIST_UPDATED){
            loadWatchlist();
            TempDataHolder.IS_WATCHLIST_UPDATED = false;

        }
    }

    @Override
    public void onTvShowClicked(TvShow tvShow) {

        Intent intent = new Intent(getApplicationContext(),TvShowDetailsActivity.class);
        intent.putExtra("tvShow", tvShow);
        startActivity(intent);


    }

    @Override
    public void removeTvShowFormWatchList(TvShow tvShow, int position) {

        CompositeDisposable compositeDisposableForDelete = new CompositeDisposable();
        compositeDisposableForDelete.add(viewModel.removeTvShowFromWatchlist(tvShow)
        .subscribeOn(Schedulers.computation())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(() -> {
                    watchList.remove(position);
                watchlistAdapter.notifyItemRemoved(position);
                watchlistAdapter.notifyItemRangeChanged(position, watchlistAdapter.getItemCount());
                compositeDisposableForDelete.dispose();

                }));

    }
}