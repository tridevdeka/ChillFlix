package com.tridev.uflixon.db;


import androidx.room.Database;
import androidx.room.RoomDatabase;

@Database(entities = {FavoriteMovieEntities.class},version = 1,exportSchema = false)

public abstract class MovieDatabase extends RoomDatabase {

    public abstract FavoriteDao favoriteDao();
}
