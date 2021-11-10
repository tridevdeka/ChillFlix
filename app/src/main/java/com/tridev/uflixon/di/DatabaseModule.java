package com.tridev.uflixon.di;

import android.app.Application;

import androidx.room.Room;

import com.tridev.uflixon.db.FavoriteDao;
import com.tridev.uflixon.db.MovieDatabase;
import com.tridev.uflixon.utilities.Constants;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import dagger.hilt.InstallIn;
import dagger.hilt.components.SingletonComponent;


@Module
@InstallIn(SingletonComponent.class)
public class DatabaseModule {

    @Provides
    @Singleton
    MovieDatabase provideMovieDatabase(Application application) {
        return Room.databaseBuilder(application, MovieDatabase.class, Constants.DATABASE_NAME)
                .fallbackToDestructiveMigration()
                .allowMainThreadQueries()
                .build();
    }

    @Provides
    @Singleton
    FavoriteDao provideFavoriteDao(MovieDatabase movieDatabase) {
        return movieDatabase.favoriteDao();
    }
}
