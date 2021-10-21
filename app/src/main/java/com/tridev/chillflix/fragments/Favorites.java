package com.tridev.chillflix.fragments;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.tridev.chillflix.adapters.FavoriteAdapter;
import com.tridev.chillflix.databinding.LayoutFavoriteBinding;
import com.tridev.chillflix.db.FavoriteMovieEntities;
import com.tridev.chillflix.utilities.Constants;
import com.tridev.chillflix.utilities.WatchlistListener;
import com.tridev.chillflix.viewmodels.FavoriteViewModel;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

import dagger.hilt.android.AndroidEntryPoint;

@AndroidEntryPoint
public class Favorites extends Fragment implements WatchlistListener {

    FavoriteViewModel viewModel;
    LayoutFavoriteBinding binding;
    FavoriteAdapter adapter;
    List<FavoriteMovieEntities> list = new ArrayList<>();

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(@NotNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        binding = LayoutFavoriteBinding.inflate(inflater, container, false);
        viewModel = new ViewModelProvider(this).get(FavoriteViewModel.class);
        return binding.getRoot();
    }


    @Override
    public void onViewCreated(@NonNull @NotNull View view, @Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);


        setAdapter();
        observeData();
        doInitialization();

    }

    private void doInitialization() {
        binding.imgDeleteAll.setOnClickListener(v -> {
            if (list.size() != 0) {
                viewModel.clearFavoriteList();
                Toast.makeText(getContext(), "Watchlist is cleared!", Toast.LENGTH_SHORT).show();
                list.clear();
                adapter.setMoviesList(list);
            } else {
                Toast.makeText(getContext(), "Nothing to clear!", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void setAdapter() {
        binding.watchlistRecyclerview.setLayoutManager(new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL));
        adapter = new FavoriteAdapter(getContext(), list, this);
        binding.watchlistRecyclerview.setAdapter(adapter);
    }


    private void observeData() {
        viewModel.getFavoriteList().observe(getViewLifecycleOwner(), favoriteMovieEntities -> {
            if (favoriteMovieEntities.size() == 0 || list == null) {
                binding.txtPlaceHolder.setVisibility(View.VISIBLE);
                binding.imgNoItems.setVisibility(View.VISIBLE);
            } else {
                binding.imgNoItems.setVisibility(View.GONE);
                binding.txtPlaceHolder.setVisibility(View.GONE);
                adapter.setMoviesList(favoriteMovieEntities);
                binding.watchlistRecyclerview.setVisibility(View.VISIBLE);
                list = favoriteMovieEntities;
            }
        });
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

    @Override
    public void onResume() {
        if (Constants.IS_WATCHLIST_UPDATED) {
            observeData();
            Constants.IS_WATCHLIST_UPDATED = false;
        }
        super.onResume();
    }

    @Override
    public void removeMoviesFromWatchlist(FavoriteMovieEntities favoriteMovieEntities, int movieId, int position) {
        viewModel.deleteMovie(movieId);
        adapter.notifyItemRemoved(position);
        adapter.notifyItemChanged(position, adapter.getItemCount());
        if (list.size() == 1) {
            list.clear();
            adapter.setMoviesList(list);
            adapter.notifyItemRemoved(position);
            adapter.notifyItemChanged(position, adapter.getItemCount());
        }

    }
}