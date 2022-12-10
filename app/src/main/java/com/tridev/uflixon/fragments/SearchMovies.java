package com.tridev.uflixon.fragments;

import android.content.pm.ActivityInfo;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;


import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.Toast;

import com.tridev.uflixon.adapters.SearchAdapter;
import com.tridev.uflixon.databinding.LayoutSearchMoviesBinding;
import com.tridev.uflixon.models.Movie;
import com.tridev.uflixon.utilities.Constants;
import com.tridev.uflixon.viewmodels.HomeViewModel;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Objects;

import dagger.hilt.android.AndroidEntryPoint;

@AndroidEntryPoint
public class SearchMovies extends Fragment {

    LayoutSearchMoviesBinding binding;
    HomeViewModel viewModel;
    SearchAdapter adapter;
    HashMap<String, String> map = new HashMap<>();
    ArrayList<Movie> moviesList = new ArrayList<>();
    String query = "";
    int currentPage = 1;
    int totalPage = 459;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }


    @Override
    public View onCreateView(@NotNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        binding = LayoutSearchMoviesBinding.inflate(inflater, container, false);
        viewModel = new ViewModelProvider(this).get(HomeViewModel.class);
        return binding.getRoot();
    }


    @Override
    public void onViewCreated(@NonNull @NotNull View view, @Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        map = new HashMap<>();
        map.put("api_key", Constants.API_KEY);
        map.put("query", query);

        doInitialization();
        observeData();
        viewModel.GetSearchMovies(map,currentPage);

        binding.imageSearch.setOnClickListener(v -> {
            query = binding.inputSearch.getText().toString().trim().toLowerCase();
            map.clear();

            map.put("api_key", Constants.API_KEY);
            map.put("query", query);
            viewModel.GetSearchMovies(map, currentPage);

        });



        binding.inputSearch.setOnEditorActionListener((v, actionId, event) -> {

            if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                query = binding.inputSearch.getText().toString().trim().toLowerCase();
                map.clear();

                map.put("api_key", Constants.API_KEY);
                map.put("query", query);

                viewModel.GetSearchMovies(map, currentPage);

            }
            return false;
        });

    }


    private void doInitialization() {

        binding.MoviesRecyclerView.setLayoutManager(new StaggeredGridLayoutManager(2, LinearLayoutManager.VERTICAL));
        adapter = new SearchAdapter(getContext(), moviesList);
        binding.MoviesRecyclerView.setAdapter(adapter);

        binding.MoviesRecyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                if (!binding.MoviesRecyclerView.canScrollVertically(1)) {//when not able to scroll on next page;
                    if (currentPage < totalPage) {
                        currentPage += 1;
                    }
                    viewModel.GetSearchMovies(map, currentPage);
                }
            }
        });
    }

    private void observeData() {
        viewModel.getSearchMoviesList().observe(getViewLifecycleOwner(), movies -> {
            if (movies.size()==0){
                Toast.makeText(getContext(), "No results found!", Toast.LENGTH_SHORT).show();
            }else {
                moviesList.addAll(movies);
                adapter.setMoviesList(moviesList);
                binding.notFound.setVisibility(View.GONE);
            }

        });
    }


    @Override
    public void onDestroyView() {
        binding = null;
        super.onDestroyView();
    }

    @Override
    public void onResume() {
        super.onResume();
        requireActivity().setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_UNSPECIFIED);
    }
}