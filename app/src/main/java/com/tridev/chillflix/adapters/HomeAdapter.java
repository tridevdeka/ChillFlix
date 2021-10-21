package com.tridev.chillflix.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import com.bumptech.glide.load.DecodeFormat;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.tridev.chillflix.databinding.HomeItemContainerBinding;
import com.tridev.chillflix.fragments.HomeDirections;
import com.tridev.chillflix.models.Movie;
import com.tridev.chillflix.utilities.Constants;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

public class HomeAdapter extends RecyclerView.Adapter<HomeAdapter.HomeViewHolder> {

    private ArrayList<Movie> movies;
    private final Context context;

    public HomeAdapter(ArrayList<Movie> movies, Context context) {
        this.movies = movies;
        this.context = context;
    }

    public void updateMoviesList(ArrayList<Movie> movies) {
        this.movies = movies;
        notifyDataSetChanged();
    }

    @NonNull
    @NotNull
    @Override
    public HomeViewHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(context);
        HomeItemContainerBinding binding = HomeItemContainerBinding.inflate(layoutInflater, parent, false);
        return new HomeViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull HomeViewHolder holder, int position) {
        holder.homeItemContainerBinding.txtMovieTitle.setText(movies.get(position).getTitle());
        holder.homeItemContainerBinding.txtMovieVotes.setText(String.valueOf(movies.get(position).getVote_count()));

        Glide.with(context)
                .asBitmap()
                .load(Constants.ImageBaseURLw500 + movies.get(position).getPoster_path())
                .diskCacheStrategy(DiskCacheStrategy.RESOURCE)
                .centerInside()
                .apply(new RequestOptions().override(200,250)
                        .format(DecodeFormat.PREFER_RGB_565))
                .into(holder.homeItemContainerBinding.imgHomeItem);

        new Thread(() -> Glide.get(context).clearDiskCache()).start();

        holder.homeItemContainerBinding.movieItemContainer.setOnClickListener(v -> {
            HomeDirections.ActionHome2ToMovieDetails action = HomeDirections.actionHome2ToMovieDetails(movies.get(position).getId());
            Navigation.findNavController(v).navigate(action);
        });

    }

    @Override
    public int getItemCount() {
        return movies ==null? 0: movies.size();
    }

    public static class HomeViewHolder extends RecyclerView.ViewHolder {
        HomeItemContainerBinding homeItemContainerBinding;

        public HomeViewHolder(HomeItemContainerBinding homeItemContainerBinding) {
            super(homeItemContainerBinding.getRoot());
            this.homeItemContainerBinding = homeItemContainerBinding;
        }
    }
}
