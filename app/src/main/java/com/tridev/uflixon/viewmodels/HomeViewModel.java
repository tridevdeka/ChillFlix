package com.tridev.uflixon.viewmodels;


import android.util.Log;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.reflect.TypeToken;
import com.tridev.uflixon.db.FavoriteMovieEntities;
import com.tridev.uflixon.models.Actor;
import com.tridev.uflixon.models.Cast;
import com.tridev.uflixon.models.Genre;
import com.tridev.uflixon.models.Movie;
import com.tridev.uflixon.repositories.MovieRepository;
import com.tridev.uflixon.response.MovieResponse;

import java.util.ArrayList;
import java.util.HashMap;

import javax.inject.Inject;

import dagger.hilt.android.lifecycle.HiltViewModel;
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.annotations.NonNull;
import io.reactivex.rxjava3.disposables.CompositeDisposable;
import io.reactivex.rxjava3.functions.Function;
import io.reactivex.rxjava3.observers.DisposableObserver;
import io.reactivex.rxjava3.schedulers.Schedulers;


@HiltViewModel
public class HomeViewModel extends ViewModel {
    private final MovieRepository movieRepository;
    private final String TAG = "HomeViewModel";

    @Inject
    public HomeViewModel(MovieRepository movieRepository) {
        this.movieRepository = movieRepository;
    }

    private final CompositeDisposable disposable = new CompositeDisposable();

    private final MutableLiveData<ArrayList<Movie>> currentlyShowingMoviesList = new MutableLiveData<>();
    private final MutableLiveData<ArrayList<Movie>> upcomingMoviesList = new MutableLiveData<>();
    private final MutableLiveData<ArrayList<Movie>> popularMoviesList = new MutableLiveData<>();
    private final MutableLiveData<ArrayList<Movie>> topRatedMoviesList = new MutableLiveData<>();
    private final MutableLiveData<Movie> movieDetails = new MutableLiveData<>();
    private final MutableLiveData<ArrayList<Cast>> castList = new MutableLiveData<>();
    private final MutableLiveData<Actor> actorDetails = new MutableLiveData<>();
    private final MutableLiveData<ArrayList<Movie>> searchMoviesList = new MutableLiveData<>();


    public MutableLiveData<ArrayList<Movie>> getCurrentlyShowingMoviesList() {
        return currentlyShowingMoviesList;
    }

    public MutableLiveData<ArrayList<Movie>> getUpcomingMoviesList() {
        return upcomingMoviesList;
    }

    public MutableLiveData<ArrayList<Movie>> getPopularMoviesList() {
        return popularMoviesList;
    }

    public MutableLiveData<ArrayList<Movie>> getTopRatedMoviesList() {
        return topRatedMoviesList;
    }

    public MutableLiveData<Movie> getMovieDetails() {
        return movieDetails;
    }

    public MutableLiveData<ArrayList<Cast>> getCastList() {
        return castList;
    }

    public MutableLiveData<Actor> getActorDetails() {
        return actorDetails;
    }

    public MutableLiveData<ArrayList<Movie>> getSearchMoviesList() {
        return searchMoviesList;
    }


    public void GetCurrentlyShowingMovies(HashMap<String, String> map, int page) {
        disposable.add(movieRepository.getCurrentlyShowing(map, page)
                .subscribeOn(Schedulers.io())
                .map(MovieResponse::getResults)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new DisposableObserver<ArrayList<Movie>>() {
                    @Override
                    public void onNext(@NonNull ArrayList<Movie> movies) {
                        currentlyShowingMoviesList.setValue(movies);
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {

                    }

                    @Override
                    public void onComplete() {

                    }
                })
        );
    }

    public void GetUpcomingMovies(HashMap<String, String> map, int page) {
        disposable.add(movieRepository.getUpcoming(map, page)
                .subscribeOn(Schedulers.io())
                .map(MovieResponse::getResults)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new DisposableObserver<ArrayList<Movie>>() {
                    @Override
                    public void onNext(@NonNull ArrayList<Movie> movies) {
                        upcomingMoviesList.setValue(movies);
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {

                    }

                    @Override
                    public void onComplete() {

                    }
                })
        );
    }


    public void GetPopularMovies(HashMap<String, String> map, int page) {
        disposable.add(movieRepository.getPopular(map, page)
                .subscribeOn(Schedulers.io())
                .map(MovieResponse::getResults)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new DisposableObserver<ArrayList<Movie>>() {
                    @Override
                    public void onNext(@NonNull ArrayList<Movie> movies) {
                        popularMoviesList.setValue(movies);
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {

                    }

                    @Override
                    public void onComplete() {

                    }
                })
        );
    }

    public void GetTopRatedMovies(HashMap<String, String> map, int page) {
        disposable.add(movieRepository.getTopRated(map, page)
                .subscribeOn(Schedulers.io())
                .map(MovieResponse::getResults)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new DisposableObserver<ArrayList<Movie>>() {
                    @Override
                    public void onNext(@NonNull ArrayList<Movie> movies) {
                        topRatedMoviesList.setValue(movies);
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {

                    }

                    @Override
                    public void onComplete() {

                    }
                })
        );
    }

    public void GetMovieDetails(int movieId, HashMap<String, String> map) {
        disposable.add(movieRepository.getMovieDetails(movieId, map)
                .subscribeOn(Schedulers.io())
                .map(movie -> {
                    ArrayList<String> genreNames = new ArrayList<>();
                    for (Genre genre : movie.getGenres()) {
                        genreNames.add(genre.getName());
                    }
                    movie.setGenre_names(genreNames);
                    return movie;
                })
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(movieDetails::setValue,
                        error -> Log.e(TAG, "getMovieDetails: " + error.getMessage()))
        );
    }


    public void GetCast(int movieId, HashMap<String, String> map) {
        disposable.add(movieRepository.getCast(movieId, map)
                .subscribeOn(Schedulers.io())
                .map((Function<JsonObject, ArrayList<Cast>>) jsonObject -> {
                    JsonArray jsonArray = jsonObject.getAsJsonArray("cast");
                    return new Gson().fromJson(jsonArray.toString(), new TypeToken<ArrayList<Cast>>() {
                    }.getType());
                })
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(castList::setValue,
                        error -> Log.e(TAG, "getCastList: " + error.getMessage()))
        );
    }

    public void GetActorDetails(int personId, HashMap<String, String> map) {
        disposable.add(movieRepository.getActorDetails(personId, map)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(actorDetails::setValue, throwable -> Log.e(TAG, "GetActorDetails: " + throwable.getLocalizedMessage())
                )
        );
    }

    public void GetSearchMovies(HashMap<String, String> map, int page) {
        disposable.add(movieRepository.getSearchMovies(map, page)
                .subscribeOn(Schedulers.io())
                .map(jsonObject -> {
                    JsonArray jsonArray = jsonObject.getAsJsonArray("results");
                    return new Gson().<ArrayList<Movie>>fromJson(jsonArray.toString(), new TypeToken<ArrayList<Movie>>() {
                            }.getType()
                    );
                })
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new DisposableObserver<ArrayList<Movie>>() {
                    @Override
                    public void onNext(@NonNull ArrayList<Movie> movies) {
                        searchMoviesList.setValue(movies);
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {

                    }

                    @Override
                    public void onComplete() {

                    }
                })
        );

    }

    //Room Database;

    public void insertMovie(FavoriteMovieEntities favoriteMovieEntities) {
        Log.e(TAG, "insertMovie: " + favoriteMovieEntities);
        movieRepository.insertMovie(favoriteMovieEntities);
    }


    public void deleteMovie(int movieId) {
        movieRepository.deleteMovie(movieId);
    }

    public FavoriteMovieEntities getFavoriteListMovie(int movieId) {
        return movieRepository.getFavoriteListMovie(movieId);
    }

    @Override
    protected void onCleared() {
        super.onCleared();
        disposable.clear();
    }
}
