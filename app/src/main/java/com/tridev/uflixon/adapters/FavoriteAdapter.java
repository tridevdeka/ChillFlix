package com.tridev.uflixon.adapters;

import android.content.Context;
import android.os.Build;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DecodeFormat;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.tridev.uflixon.R;
import com.tridev.uflixon.databinding.FavoriteItemContainerBinding;
import com.tridev.uflixon.db.FavoriteMovieEntities;
import com.tridev.uflixon.fragments.FavoritesDirections;
import com.tridev.uflixon.utilities.Constants;
import com.tridev.uflixon.utilities.WatchlistListener;


import java.util.List;

public class FavoriteAdapter extends RecyclerView.Adapter<FavoriteAdapter.FavoriteViewHolder> {
    private final Context context;
    WatchlistListener watchlistListener;
    private List<FavoriteMovieEntities> moviesList;
    private FavoriteMovieEntities favoriteMovieEntities;

    public FavoriteAdapter(Context context, List<FavoriteMovieEntities> moviesList,WatchlistListener watchlistListener) {
        this.context = context;
        this.moviesList = moviesList;
        this.watchlistListener=watchlistListener;
    }

    public void setMoviesList(List<FavoriteMovieEntities> moviesList) {
        this.moviesList = moviesList;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public FavoriteViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        FavoriteItemContainerBinding binding = FavoriteItemContainerBinding.inflate(inflater, parent, false);
        return new FavoriteViewHolder(binding);
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    public void onBindViewHolder(@NonNull FavoriteViewHolder holder, int position) {
        holder.binding.movieItemContainer.setClipToOutline(true);
        holder.binding.txtMovieTitle.setText(moviesList.get(position).getTitle());

        holder.binding.imageDelete.setOnClickListener(v -> watchlistListener.removeMoviesFromWatchlist(favoriteMovieEntities,moviesList.get(position).getId(),position));


        if (moviesList.get(position).getPoster_path()!=null) {
            Glide.with(context).load(Constants.ImageBaseURLw500 + moviesList.get(position).getPoster_path())
                    .diskCacheStrategy(DiskCacheStrategy.RESOURCE)
                    .centerCrop()
                    .apply(new RequestOptions().format(DecodeFormat.PREFER_RGB_565))
                    .into(holder.binding.imgHomeItem);
        }else {
            Glide.with(context).load(Constants.ImageBaseURLw500 + moviesList.get(position).getPoster_path())
                    .error(R.drawable.ic_no_image)
                    .into(holder.binding.imgHomeItem);

        }
        new Thread(() -> Glide.get(context).clearDiskCache()).start();

        holder.binding.movieItemContainer.setOnClickListener(view -> {
            FavoritesDirections.ActionFavoriteToMovieDetails action =
                    FavoritesDirections.actionFavoriteToMovieDetails(moviesList.get(position).getId());
            Navigation.findNavController(view).navigate(action);
        });


    }

    @Override
    public int getItemCount() {
        return moviesList == null ? 0 : moviesList.size();
    }

    static class FavoriteViewHolder extends RecyclerView.ViewHolder {

        private final FavoriteItemContainerBinding binding;

        public FavoriteViewHolder(FavoriteItemContainerBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }
}
