package com.tridev.uflixon.fragments;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.tridev.uflixon.adapters.CategoryMoviesAdapter;
import com.tridev.uflixon.databinding.LayoutMoviesBinding;
import com.tridev.uflixon.models.Movie;
import com.tridev.uflixon.utilities.Constants;
import com.tridev.uflixon.viewmodels.HomeViewModel;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.HashMap;

import dagger.hilt.android.AndroidEntryPoint;

@AndroidEntryPoint
public class Movies extends Fragment {

    private LayoutMoviesBinding binding;
    HomeViewModel viewModel;
    HashMap<String, String> map = new HashMap<>();
    CategoryMoviesAdapter adapter;
    ArrayList<Movie> moviesList = new ArrayList<>();
    String moviesCategory = "";
    int currentPage = 1;
    int totalPage = 459;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }


    @Override
    public View onCreateView(@NotNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        binding = LayoutMoviesBinding.inflate(inflater, container, false);
        viewModel = new ViewModelProvider(this).get(HomeViewModel.class);
        return binding.getRoot();
    }


    @Override
    public void onViewCreated(@NonNull @NotNull View view, @Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        MoviesArgs args = MoviesArgs.fromBundle(getArguments());
        moviesCategory = args.getMovieCategory();

        binding.progressTop.setVisibility(View.VISIBLE);
        map.put("api_key", Constants.API_KEY);
        doInitialization();
        observeData();
        getMoviesList();
    }


    private void doInitialization() {
        adapter = new CategoryMoviesAdapter(getContext(), moviesList);
        binding.moviesRecyclerView.setAdapter(adapter);
        binding.moviesRecyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                if (!binding.moviesRecyclerView.canScrollVertically(1)) {//when not able to scroll on next page;
                    if (currentPage < totalPage) {
                        currentPage += 1;
                    }
                    getMoviesList();
                }
            }
        });

    }


    private void observeData() {
        switch ((moviesCategory)) {
            case Constants.Current:
                viewModel.getCurrentlyShowingMoviesList().observe(getViewLifecycleOwner(), movies -> {
                    if (movies.size() == 0) {
                        binding.progressTop.setVisibility(View.VISIBLE);
                        binding.txtMoviesCategoryTitle.setVisibility(View.GONE);
                    } else {
                        binding.progressTop.setVisibility(View.GONE);
                        binding.txtMoviesCategoryTitle.setVisibility(View.VISIBLE);
                        int old = moviesList.size();
                        moviesList.addAll(movies);
                        adapter.notifyItemRangeInserted(old, moviesList.size());
                    }
                });
                break;

            case Constants.Upcoming:
                viewModel.getUpcomingMoviesList().observe(getViewLifecycleOwner(), movies -> {
                    if (movies.size() == 0) {
                        binding.progressTop.setVisibility(View.VISIBLE);
                        binding.txtMoviesCategoryTitle.setVisibility(View.GONE);
                    } else {
                        binding.progressTop.setVisibility(View.GONE);
                        binding.txtMoviesCategoryTitle.setVisibility(View.VISIBLE);
                        int old = moviesList.size();
                        moviesList.addAll(movies);
                        adapter.notifyItemRangeInserted(old, moviesList.size());
                    }
                });
                break;
            case Constants.TopRated:
                viewModel.getTopRatedMoviesList().observe(getViewLifecycleOwner(), movies -> {
                    if (movies.size() == 0) {
                        binding.progressTop.setVisibility(View.VISIBLE);
                        binding.txtMoviesCategoryTitle.setVisibility(View.GONE);
                    } else {
                        binding.progressTop.setVisibility(View.GONE);
                        binding.txtMoviesCategoryTitle.setVisibility(View.VISIBLE);
                        int old = moviesList.size();
                        moviesList.addAll(movies);
                        adapter.notifyItemRangeInserted(old, moviesList.size());
                    }
                });
                break;
            case Constants.Popular:
                viewModel.getPopularMoviesList().observe(getViewLifecycleOwner(), movies -> {

                    if (movies.size() == 0) {
                        binding.progressTop.setVisibility(View.VISIBLE);
                        binding.txtMoviesCategoryTitle.setVisibility(View.GONE);
                    } else {
                        binding.progressTop.setVisibility(View.GONE);
                        binding.txtMoviesCategoryTitle.setVisibility(View.VISIBLE);
                        int old = moviesList.size();
                        moviesList.addAll(movies);
                        adapter.notifyItemRangeInserted(old, moviesList.size());
                    }
                });
                break;
        }
    }

    private void getMoviesList() {
        switch (moviesCategory) {
            case Constants.Current:
                viewModel.GetCurrentlyShowingMovies(map, currentPage);

                binding.txtMoviesCategoryTitle.setText(Constants.Current);
                break;
            case Constants.Upcoming:
                viewModel.GetUpcomingMovies(map, currentPage);
                binding.txtMoviesCategoryTitle.setText(Constants.Upcoming);
                break;
            case Constants.TopRated:
                viewModel.GetTopRatedMovies(map, currentPage);
                binding.txtMoviesCategoryTitle.setText(Constants.TopRated);
                break;
            case Constants.Popular:
                viewModel.GetPopularMovies(map, currentPage);
                binding.txtMoviesCategoryTitle.setText(Constants.Popular);
                break;
        }
    }


    @Override
    public void onDestroyView() {
        binding = null;
        super.onDestroyView();
    }
}