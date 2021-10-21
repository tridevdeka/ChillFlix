package com.tridev.chillflix.fragments;

import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;

import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;

import com.tridev.chillflix.adapters.HomeAdapter;
import com.tridev.chillflix.adapters.ViewPagerAdapter;
import com.tridev.chillflix.databinding.LayoutHomeBinding;
import com.tridev.chillflix.models.Movie;
import com.tridev.chillflix.utilities.Constants;
import com.tridev.chillflix.viewmodels.HomeViewModel;

import java.util.ArrayList;
import java.util.HashMap;

import dagger.hilt.android.AndroidEntryPoint;

@AndroidEntryPoint
public class Home extends Fragment {

    private LayoutHomeBinding binding;
    private HomeViewModel homeViewModel;
    private ViewPagerAdapter viewPagerAdapter;
    private HomeAdapter upcomingAdapter, popularAdapter, topRatedAdapter;
    private final HashMap<String, String> map = new HashMap<>();
    private final ArrayList<Movie> currentMovies = new ArrayList<>();
    private final ArrayList<Movie> popularMovies = new ArrayList<>();
    private final ArrayList<Movie> topRatedMovies = new ArrayList<>();
    private final ArrayList<Movie> upcomingMovies = new ArrayList<>();
    int page = 1;



    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = LayoutHomeBinding.inflate(inflater, container, false);
        homeViewModel = new ViewModelProvider(Home.this).get(HomeViewModel.class);
        return binding.getRoot();
    }


    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override

    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        map.put("api_key", Constants.API_KEY);
        observeData();
        setUpOnClickListeners();
        setUpRecyclerViewsAndViewPager();
        getMoviesList();

    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    public void onStart() {
        super.onStart();
        if (Constants.isNetworkAvailable(requireActivity())) {
            swipeDown();
        }else {
            binding.refreshLayout.setVisibility(View.VISIBLE);
            swipeDown();
        }
    }


    private void observeData() {

        homeViewModel.getCurrentlyShowingMoviesList().observe(getViewLifecycleOwner(), movies -> {
            if (movies.size() == 0) {
                binding.progressBar.setVisibility(View.VISIBLE);
                binding.txtCurrentlyShowing.setVisibility(View.GONE);
                binding.imgViewAllCurrent.setVisibility(View.GONE);
                binding.currentlyShowingViewPager.setVisibility(View.GONE);

            } else {
                binding.progressBar.setVisibility(View.GONE);
                binding.refreshLayout.setVisibility(View.GONE);
                viewPagerAdapter.updateMovieListResults(movies);
                binding.txtCurrentlyShowing.setVisibility(View.VISIBLE);
                binding.imgViewAllCurrent.setVisibility(View.VISIBLE);
                binding.currentlyShowingViewPager.setVisibility(View.VISIBLE);
            }
        });

        homeViewModel.getUpcomingMoviesList().observe(getViewLifecycleOwner(), movies -> {
            if (movies.size() != 0) {
                upcomingAdapter.updateMoviesList(movies);
                binding.txtUpcomingShowing.setVisibility(View.VISIBLE);
                binding.imgViewAllUpcoming.setVisibility(View.VISIBLE);
                binding.upcomingRecyclerView.setVisibility(View.VISIBLE);
            }

        });

        homeViewModel.getPopularMoviesList().observe(getViewLifecycleOwner(), movies -> {
            if (movies.size() != 0) {
                popularAdapter.updateMoviesList(movies);
                binding.txtPopular.setVisibility(View.VISIBLE);
                binding.imgViewAllPopular.setVisibility(View.VISIBLE);
                binding.popularRecyclerView.setVisibility(View.VISIBLE);
            }
        });

        homeViewModel.getTopRatedMoviesList().observe(getViewLifecycleOwner(), movies -> {
            if (movies.size() != 0) {
                topRatedAdapter.updateMoviesList(movies);
                binding.txtTopRated.setVisibility(View.VISIBLE);
                binding.imgViewAllTopRated.setVisibility(View.VISIBLE);
                binding.topRatedRecyclerView.setVisibility(View.VISIBLE);
            }
        });
    }

    private void getMoviesList() {
        homeViewModel.GetCurrentlyShowingMovies(map, page);
        homeViewModel.GetUpcomingMovies(map, page);
        homeViewModel.GetPopularMovies(map, page);
        homeViewModel.GetTopRatedMovies(map, page);
    }


    private void setUpRecyclerViewsAndViewPager() {
        viewPagerAdapter = new ViewPagerAdapter(currentMovies, getContext());
        binding.currentlyShowingViewPager.setAdapter(viewPagerAdapter);

        upcomingAdapter = new HomeAdapter(upcomingMovies, getContext());
        binding.upcomingRecyclerView.setAdapter(upcomingAdapter);

        popularAdapter = new HomeAdapter(popularMovies, getContext());
        binding.popularRecyclerView.setAdapter(popularAdapter);

        topRatedAdapter = new HomeAdapter(topRatedMovies, getContext());
        binding.topRatedRecyclerView.setAdapter(topRatedAdapter);

    }

    private void setUpOnClickListeners() {
        binding.imgViewAllCurrent.setOnClickListener(v -> {
            HomeDirections.ActionHome2ToMovies action = HomeDirections.actionHome2ToMovies();
            action.setMovieCategory(Constants.Current);
            Navigation.findNavController(v).navigate(action);
        });

        binding.imgViewAllUpcoming.setOnClickListener(v -> {
            HomeDirections.ActionHome2ToMovies action = HomeDirections.actionHome2ToMovies();
            action.setMovieCategory(Constants.Upcoming);
            Navigation.findNavController(v).navigate(action);
        });

        binding.imgViewAllPopular.setOnClickListener(v -> {
            HomeDirections.ActionHome2ToMovies action = HomeDirections.actionHome2ToMovies();
            action.setMovieCategory(Constants.Popular);
            Navigation.findNavController(v).navigate(action);
        });

        binding.imgViewAllTopRated.setOnClickListener(v -> {
            HomeDirections.ActionHome2ToMovies action = HomeDirections.actionHome2ToMovies();
            action.setMovieCategory(Constants.TopRated);
            Navigation.findNavController(v).navigate(action);
        });
    }

    private void swipeDown() {
        binding.swipe.setOnRefreshListener(() -> {

            observeData();
            if (currentMovies.size() == 0) {
                binding.progressBar.setVisibility(View.VISIBLE);
                binding.txtCurrentlyShowing.setVisibility(View.GONE);
                binding.imgViewAllCurrent.setVisibility(View.GONE);
                binding.currentlyShowingViewPager.setVisibility(View.GONE);
                binding.txtUpcomingShowing.setVisibility(View.GONE);
                binding.imgViewAllUpcoming.setVisibility(View.GONE);
                binding.upcomingRecyclerView.setVisibility(View.GONE);
                binding.txtPopular.setVisibility(View.GONE);
                binding.imgViewAllPopular.setVisibility(View.GONE);
                binding.popularRecyclerView.setVisibility(View.GONE);
                binding.txtTopRated.setVisibility(View.GONE);
                binding.imgViewAllTopRated.setVisibility(View.GONE);
                binding.topRatedRecyclerView.setVisibility(View.GONE);
            }

            setUpOnClickListeners();
            setUpRecyclerViewsAndViewPager();
            getMoviesList();
            binding.swipe.setRefreshing(false);
        });
    }

    @Override
    public void onDestroyView() {
        binding = null;
        super.onDestroyView();
    }

}