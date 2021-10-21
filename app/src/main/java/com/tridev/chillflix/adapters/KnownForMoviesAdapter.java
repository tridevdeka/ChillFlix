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
import com.tridev.chillflix.R;
import com.tridev.chillflix.databinding.HomeItemContainerBinding;
import com.tridev.chillflix.fragments.ActorDetailsDirections;
import com.tridev.chillflix.models.Movie;
import com.tridev.chillflix.utilities.Constants;

import java.util.ArrayList;

public class KnownForMoviesAdapter extends RecyclerView.Adapter<KnownForMoviesAdapter.KnownForMoviesViewHolder> {
    private ArrayList<Movie> moviesList;
    private Context context;

    public KnownForMoviesAdapter(Context context, ArrayList<Movie> moviesList) {
        this.context = context;
        this.moviesList = moviesList;
    }


    @NonNull
    @Override
    public KnownForMoviesViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        HomeItemContainerBinding binding = HomeItemContainerBinding.inflate(inflater,parent,false);
        return new KnownForMoviesViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull KnownForMoviesViewHolder holder, int position) {
        holder.binding.txtMovieTitle.setText(moviesList.get(position).getTitle());
        holder.binding.txtMovieVotes.setText(String.valueOf(moviesList.get(position).getVote_count()));

        if (moviesList.get(position).getPoster_path()!=null) {
            Glide.with(context)
                    .load(Constants.ImageBaseURLw500 + moviesList.get(position).getPoster_path())
                    .diskCacheStrategy(DiskCacheStrategy.RESOURCE)
                    .centerInside().override(200, 250)
                    .apply(new RequestOptions()
                            .format(DecodeFormat.PREFER_RGB_565))
                    .into(holder.binding.imgHomeItem);
        }else {
            Glide.with(context)
                    .load(Constants.ImageBaseURLw500 + moviesList.get(position).getPoster_path())
                    .error(R.drawable.ic_no_image)
                    .into(holder.binding.imgHomeItem);
        }

        new Thread(() -> Glide.get(context).clearDiskCache()).start();

        holder.binding.movieItemContainer.setOnClickListener(v -> {
            ActorDetailsDirections.ActionActorDetailsToMovieDetails2 action = ActorDetailsDirections.actionActorDetailsToMovieDetails2(moviesList.get(position).getId());
            Navigation.findNavController(v).navigate(action);
        });

    }

    @Override
    public int getItemCount() {
        return moviesList == null ? 0 : moviesList.size();
    }

    static class KnownForMoviesViewHolder extends RecyclerView.ViewHolder{

        private final HomeItemContainerBinding binding;
        public KnownForMoviesViewHolder(HomeItemContainerBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }
}

