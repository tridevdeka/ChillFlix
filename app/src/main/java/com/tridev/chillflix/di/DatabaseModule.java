package com.tridev.chillflix.di;

import android.app.Application;

import androidx.room.Room;

import com.tridev.chillflix.db.FavoriteDao;
import com.tridev.chillflix.db.MovieDatabase;
import com.tridev.chillflix.utilities.Constants;

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
