package com.tridev.uflixon.repositories;

import androidx.lifecycle.LiveData;

import com.google.gson.JsonObject;
import com.tridev.uflixon.db.FavoriteDao;
import com.tridev.uflixon.db.FavoriteMovieEntities;
import com.tridev.uflixon.models.Actor;
import com.tridev.uflixon.models.Movie;
import com.tridev.uflixon.network.ApiService;
import com.tridev.uflixon.response.MovieResponse;


import java.util.HashMap;
import java.util.List;

import javax.inject.Inject;

import io.reactivex.rxjava3.core.Observable;

public class MovieRepository {

    private final ApiService apiService;

    private final FavoriteDao favoriteDao;


    @Inject
    public MovieRepository(ApiService apiService,FavoriteDao favoriteDao) {
        this.apiService = apiService;
        this.favoriteDao=favoriteDao;
    }

    public Observable<MovieResponse> getCurrentlyShowing(HashMap<String, String> map, int page) {
        return apiService.getCurrentlyShowing(map, page);
    }

    public Observable<MovieResponse> getUpcoming(HashMap<String, String> map, int page) {
        return apiService.getUpcoming(map, page);
    }

    public Observable<MovieResponse> getPopular(HashMap<String, String> map, int page) {
        return apiService.getPopular(map, page);
    }

    public Observable<MovieResponse> getTopRated(HashMap<String, String> map, int page) {
        return apiService.getTopRated(map, page);
    }

    public Observable<Movie> getMovieDetails(int movieId, HashMap<String, String> map) {
        return apiService.getMovieDetails(movieId, map);
    }

    public Observable<JsonObject> getCast(int movieId, HashMap<String, String> map) {
        return apiService.getCast(movieId, map);
    }

    public Observable<Actor> getActorDetails(int id, HashMap<String, String> map) {
        return apiService.getActorDetails(id, map);
    }

    public Observable<JsonObject> getSearchMovies(HashMap<String, String> map, int page) {
        return apiService.getSearchMovies(map, page);
    }


    // Room Database

    public void insertMovie(FavoriteMovieEntities favoriteMovieEntities){
        favoriteDao.insert(favoriteMovieEntities);
    }

    public void deleteMovie(int movieId){
        favoriteDao.delete(movieId);
    }

    public void clearFavoriteList(){
        favoriteDao.clearFavoriteList();
    }

    public LiveData<List<FavoriteMovieEntities>> getFavoriteList(){
        return favoriteDao.getFavoriteList();
    }

    public FavoriteMovieEntities getFavoriteListMovie(int movieId){
        return favoriteDao.getFavoriteListMovie(movieId);
    }

}
