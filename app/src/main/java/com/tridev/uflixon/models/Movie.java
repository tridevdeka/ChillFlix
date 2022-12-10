package com.tridev.uflixon.models;

import com.google.gson.JsonObject;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class Movie {

    @SerializedName("poster_path")
    private String poster_path;

    @SerializedName("overview")
    private String overview;

    @SerializedName("release_date")
    private String release_date;

    @SerializedName("title")
    private String title;

    @SerializedName("backdrop_path")
    private String backdrop_path;

    @SerializedName("id")
    private Integer id;

    @SerializedName("vote_count")
    private Integer vote_count;

    @SerializedName("runtime")
    private Integer runtime;

    @SerializedName("popularity")
    private Number popularity;

    @SerializedName("vote_average")
    private Number vote_average;

    @SerializedName("genre_ids")
    private ArrayList<Integer> genre_ids;

    @SerializedName("genre_names")
    private ArrayList<String> genre_names;

    @SerializedName("genres")
    private ArrayList<Genre> genres;

    @SerializedName("videos")
    private JsonObject videos ;


    public Movie(String poster_path, String overview, String release_date, String title, String backdrop_path, Integer id, Integer vote_count, Integer runtime, Number popularity, Number vote_average, ArrayList<Integer> genre_ids, ArrayList<String> genre_names, ArrayList<Genre> genres, JsonObject videos) {
        this.poster_path = poster_path;
        this.overview = overview;
        this.release_date = release_date;
        this.title = title;
        this.backdrop_path = backdrop_path;
        this.id = id;
        this.vote_count = vote_count;
        this.runtime = runtime;
        this.popularity = popularity;
        this.vote_average = vote_average;
        this.genre_ids = genre_ids;
        this.genre_names = genre_names;
        this.genres = genres;
        this.videos = videos;
    }


    public String getPoster_path() {
        return poster_path;
    }

    public void setPoster_path(String poster_path) {
        this.poster_path = poster_path;
    }

    public String getOverview() {
        return overview;
    }

    public void setOverview(String overview) {
        this.overview = overview;
    }

    public String getRelease_date() {
        return release_date;
    }

    public void setRelease_date(String release_date) {
        this.release_date = release_date;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getBackdrop_path() {
        return backdrop_path;
    }

    public void setBackdrop_path(String backdrop_path) {
        this.backdrop_path = backdrop_path;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getVote_count() {
        return vote_count;
    }

    public void setVote_count(Integer vote_count) {
        this.vote_count = vote_count;
    }

    public Integer getRuntime() {
        return runtime;
    }

    public void setRuntime(Integer runtime) {
        this.runtime = runtime;
    }

    public Number getPopularity() {
        return popularity;
    }

    public void setPopularity(Number popularity) {
        this.popularity = popularity;
    }

    public Number getVote_average() {
        return vote_average;
    }

    public void setVote_average(Number vote_average) {
        this.vote_average = vote_average;
    }

    public ArrayList<Integer> getGenre_ids() {
        return genre_ids;
    }

    public void setGenre_ids(ArrayList<Integer> genre_ids) {
        this.genre_ids = genre_ids;
    }

    public ArrayList<String> getGenre_names() {
        return genre_names;
    }

    public void setGenre_names(ArrayList<String> genre_names) {
        this.genre_names = genre_names;
    }

    public ArrayList<Genre> getGenres() {
        return genres;
    }

    public void setGenres(ArrayList<Genre> genres) {
        this.genres = genres;
    }

    public JsonObject getVideos() {
        return videos;
    }

    public void setVideos(JsonObject videos) {
        this.videos = videos;
    }
}
