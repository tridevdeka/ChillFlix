package com.tridev.chillflix.network;



import com.google.gson.JsonObject;
import com.tridev.chillflix.models.Actor;
import com.tridev.chillflix.models.Movie;
import com.tridev.chillflix.response.MovieResponse;

import java.util.HashMap;

import io.reactivex.rxjava3.core.Observable;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;
import retrofit2.http.QueryMap;

public interface ApiService {

    @GET("movie/now_playing")
    Observable<MovieResponse> getCurrentlyShowing(@QueryMap HashMap<String,String> queries, @Query("page")int page);

    @GET("movie/popular")
    Observable<MovieResponse> getPopular(@QueryMap HashMap<String,String> queries,@Query("page")int page);

    @GET("movie/upcoming")
    Observable<MovieResponse> getUpcoming(@QueryMap HashMap<String,String> queries,@Query("page")int page);

    @GET("movie/top_rated")
    Observable<MovieResponse> getTopRated(@QueryMap HashMap<String,String> queries,@Query("page")int page);

    @GET("movie/{movie_id}")
    Observable<Movie> getMovieDetails(@Path("movie_id") int id,@QueryMap HashMap<String,String> queries);

    @GET("movie/{movie_id}/credits")
    Observable<JsonObject> getCast(@Path ("movie_id") int id, @QueryMap HashMap<String,String> queries);

    @GET("person/{person_id}")
    Observable<Actor> getActorDetails(@Path ("person_id") int id, @QueryMap HashMap<String, String> queries);

    @GET("search/movie")
    Observable<JsonObject> getSearchMovies(@QueryMap HashMap<String,String>map,@Query("page")int page);

}
