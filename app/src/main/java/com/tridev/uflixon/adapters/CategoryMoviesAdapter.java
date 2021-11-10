package com.tridev.uflixon.adapters;

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
import com.tridev.uflixon.R;
import com.tridev.uflixon.databinding.MoviesContainerBinding;
import com.tridev.uflixon.fragments.MoviesDirections;
import com.tridev.uflixon.models.Movie;
import com.tridev.uflixon.utilities.Constants;

import java.util.ArrayList;


public class CategoryMoviesAdapter extends RecyclerView.Adapter<CategoryMoviesAdapter.CategoryMoviesViewHolder> {

    private final Context context;
    private final ArrayList<Movie> moviesList;
    private String temp;

    public CategoryMoviesAdapter(Context context, ArrayList<Movie> movieList) {
        this.context = context;
        this.moviesList = movieList;
    }

    @NonNull
    @Override
    public CategoryMoviesViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        MoviesContainerBinding binding = MoviesContainerBinding.inflate(inflater, parent, false);
        return new CategoryMoviesViewHolder(binding);
    }


    @Override
    public void onBindViewHolder(@NonNull CategoryMoviesViewHolder holder, int position) {
        if (moviesList.get(position).getRelease_date() != null) {
            String[] year = moviesList.get(position).getRelease_date().split("-");
            holder.binding.txtMovieYear.setText(year[0]);
        }
        holder.binding.textName.setText(moviesList.get(position).getTitle());
        holder.binding.rateMovieRating.setRating(moviesList.get(position).getVote_average().floatValue() / 2);

        Glide.with(context).load(Constants.ImageBaseURLw500 + moviesList.get(position).getPoster_path())
                .centerInside()
                .diskCacheStrategy(DiskCacheStrategy.RESOURCE)
                .thumbnail(0.5f)
                .error(R.drawable.ic_no_image)
                .apply(new RequestOptions().override(120, 120)
                        .format(DecodeFormat.PREFER_RGB_565))
                .into(holder.binding.imgPosterPath);

        new Thread(() -> Glide.get(context).clearDiskCache()).start();


        holder.binding.rateMovieRating.setRating(moviesList.get(position).getVote_average().floatValue() / 2);
        temp = "";

        for (int i = 0; i < moviesList.get(position).getGenre_ids().size(); i++) {
            if (i == moviesList.get(position).getGenre_ids().size() - 1)
                temp += Constants.getGenreMap().get(moviesList.get(position).getGenre_ids().get(i));
            else
                temp += Constants.getGenreMap().get(moviesList.get(position).getGenre_ids().get(i)) + " • ";
        }
        holder.binding.textGenre.setText(temp);

        holder.binding.moviesLayout.setOnClickListener(v -> {
            MoviesDirections.ActionMoviesToMovieDetails action =
                    MoviesDirections.actionMoviesToMovieDetails(moviesList.get(position).getId());
            Navigation.findNavController(v).navigate(action);
        });
    }

    @Override
    public int getItemCount() {
        return moviesList.size();
    }

    static class CategoryMoviesViewHolder extends RecyclerView.ViewHolder {
        private final MoviesContainerBinding binding;

        public CategoryMoviesViewHolder(MoviesContainerBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }
}

