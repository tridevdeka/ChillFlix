package com.tridev.chillflix.utilities;

import com.tridev.chillflix.db.FavoriteMovieEntities;

public interface WatchlistListener {
    void removeMoviesFromWatchlist(FavoriteMovieEntities favoriteMovieEntities, int movieId,int position);
}
