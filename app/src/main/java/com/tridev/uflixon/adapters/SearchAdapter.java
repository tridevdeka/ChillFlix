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
import com.tridev.uflixon.databinding.MovieItemContainerBinding;
import com.tridev.uflixon.fragments.SearchMoviesDirections;
import com.tridev.uflixon.models.Movie;
import com.tridev.uflixon.utilities.Constants;

import java.util.ArrayList;

public class SearchAdapter extends RecyclerView.Adapter<SearchAdapter.SearchViewHolder> {
    private final Context context;
    private ArrayList<Movie> moviesList;
    private String temp;

    public SearchAdapter(Context context, ArrayList<Movie> moviesList) {
        this.context = context;
        this.moviesList = moviesList;
    }

    public void setMoviesList(ArrayList<Movie> moviesList) {
        this.moviesList = moviesList;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public SearchViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        MovieItemContainerBinding binding = MovieItemContainerBinding.inflate(inflater, parent, false);
        return new SearchViewHolder(binding);
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    public void onBindViewHolder(@NonNull SearchViewHolder holder, int position) {
        holder.binding.txtMovieName.setText(moviesList.get(position).getTitle());

        temp = "";

        for (int i = 0; i < moviesList.get(position).getGenre_ids().size(); i++) {
            if (i == moviesList.get(position).getGenre_ids().size() - 1)
                temp += Constants.getGenreMap().get(moviesList.get(position).getGenre_ids().get(i));
            else
                temp += Constants.getGenreMap().get(moviesList.get(position).getGenre_ids().get(i)) + " • ";
        }

        holder.binding.txtMovieGenre.setText(temp);
        holder.binding.rateMovieRating.setRating(moviesList.get(position).getVote_average().floatValue() / 2);

        if (moviesList.get(position).getRelease_date() != null) {
            String[] movieYear = moviesList.get(position).getRelease_date()
                    .split("-");
            holder.binding.txtMovieYear.setText(movieYear[0]);
        }


        if (moviesList.get(position).getPoster_path() != null) {
            Glide.with(context).load(Constants.ImageBaseURLw500 + moviesList.get(position).getPoster_path())
                    .diskCacheStrategy(DiskCacheStrategy.RESOURCE)
                    .centerInside()
                    .apply(new RequestOptions().override(200, 250)
                            .format(DecodeFormat.PREFER_RGB_565))
                    .into(holder.binding.imgMovieImage);
        } else {
            Glide.with(context).load(Constants.ImageBaseURLw500 + moviesList.get(position).getPoster_path())
                    .error(R.drawable.ic_no_image)
                    .into(holder.binding.imgMovieImage);
        }
        new Thread(() -> Glide.get(context).clearDiskCache()).start();

        holder.binding.movieItemLayout.setOnClickListener(view -> {
            SearchMoviesDirections.ActionSearchMoviesToMovieDetails action =
                    SearchMoviesDirections.actionSearchMoviesToMovieDetails(moviesList.get(position).getId());
            Navigation.findNavController(view).navigate(action);
        });

        holder.binding.movieItemLayout.setClipToOutline(true);
    }

    @Override
    public int getItemCount() {
        return moviesList == null ? 0 : moviesList.size();
    }

    static class SearchViewHolder extends RecyclerView.ViewHolder {

        private final MovieItemContainerBinding binding;

        public SearchViewHolder(MovieItemContainerBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }
}
