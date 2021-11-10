package com.tridev.uflixon.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DecodeFormat;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.tridev.uflixon.R;
import com.tridev.uflixon.databinding.CurrentlyShowItemContainerBinding;
import com.tridev.uflixon.fragments.HomeDirections;
import com.tridev.uflixon.models.Movie;
import com.tridev.uflixon.utilities.Constants;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

public class ViewPagerAdapter extends RecyclerView.Adapter<ViewPagerAdapter.ViewPagerViewHolder> {

    private ArrayList<Movie> movieList;
    private final Context context;


    public ViewPagerAdapter(ArrayList<Movie> movieList, Context context) {
        this.movieList = movieList;
        this.context = context;
    }

    public void updateMovieListResults(ArrayList<Movie> movieList) {
        this.movieList = movieList;
        notifyDataSetChanged();
    }

    @NonNull
    @NotNull
    @Override
    public ViewPagerViewHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(context);
        CurrentlyShowItemContainerBinding binding = DataBindingUtil.inflate(layoutInflater, R.layout.currently_show_item_container, parent, false);
        return new ViewPagerViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull ViewPagerViewHolder holder, int position) {
        holder.currentlyShowItemContainerBinding.currentlyShowingMovieName.setText(movieList.get(position).getTitle());

        Glide.with(context).load(Constants.ImageBaseURLw500 + movieList.get(position).getBackdrop_path())
                .diskCacheStrategy(DiskCacheStrategy.RESOURCE)
                .centerInside()
                .error(R.drawable.ic_no_image)
                .apply(new RequestOptions()
                        .format(DecodeFormat.PREFER_RGB_565))
                .into(holder.currentlyShowItemContainerBinding.currentlyShowingMovieImage);

        new Thread(() -> Glide.get(context).clearDiskCache()).start();

        holder.currentlyShowItemContainerBinding.currentlyShowingLayout.setOnClickListener(v -> {
            HomeDirections.ActionHome2ToMovieDetails action = HomeDirections.actionHome2ToMovieDetails(movieList.get(position).getId());
            Navigation.findNavController(v).navigate(action);
        });

        holder.currentlyShowItemContainerBinding.currentlyShowingLayout.setLayoutParams(new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
    }

    @Override
    public int getItemCount() {
        return movieList.size();
    }

    public static class ViewPagerViewHolder extends RecyclerView.ViewHolder {
        private final CurrentlyShowItemContainerBinding currentlyShowItemContainerBinding;

        public ViewPagerViewHolder(CurrentlyShowItemContainerBinding currentlyShowItemContainerBinding) {
            super(currentlyShowItemContainerBinding.getRoot());
            this.currentlyShowItemContainerBinding = currentlyShowItemContainerBinding;
        }
    }
}
