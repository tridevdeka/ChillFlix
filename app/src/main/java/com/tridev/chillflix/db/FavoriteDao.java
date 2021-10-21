package com.tridev.chillflix.db;


import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import java.util.List;


@Dao
public interface FavoriteDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(FavoriteMovieEntities favoriteMovieEntities);

    @Query("SELECT * FROM favorite")
    LiveData<List<FavoriteMovieEntities>> getFavoriteList();

    @Query("DELETE From favorite WHERE id = :movieId")
    void delete(int movieId);

    @Query("SELECT * FROM favorite WHERE id = :movieId ")
    FavoriteMovieEntities getFavoriteListMovie(int movieId);

    @Query("DELETE FROM favorite")
    void clearFavoriteList();
}
