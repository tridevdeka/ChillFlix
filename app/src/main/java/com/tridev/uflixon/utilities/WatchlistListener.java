package com.tridev.uflixon.utilities;

import com.tridev.uflixon.db.FavoriteMovieEntities;

public interface WatchlistListener {
    void removeMoviesFromWatchlist(FavoriteMovieEntities favoriteMovieEntities, int movieId,int position);
}
