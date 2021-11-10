package com.tridev.uflixon.viewmodels;


import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.tridev.uflixon.db.FavoriteMovieEntities;
import com.tridev.uflixon.repositories.MovieRepository;

import java.util.List;

import javax.inject.Inject;

import dagger.hilt.android.lifecycle.HiltViewModel;

@HiltViewModel
public class FavoriteViewModel extends ViewModel {
    private final MovieRepository movieRepository;
    private final LiveData<List<FavoriteMovieEntities>> favoriteList;


    @Inject
    public FavoriteViewModel(MovieRepository movieRepository) {
        this.movieRepository = movieRepository;
        this.favoriteList = movieRepository.getFavoriteList();
    }

    public LiveData<List<FavoriteMovieEntities>> getFavoriteList(){
        return favoriteList;
    }

    public void clearFavoriteList(){
        movieRepository.clearFavoriteList();
    }

    public void deleteMovie(int movieId) {
        movieRepository.deleteMovie(movieId);
    }
}
