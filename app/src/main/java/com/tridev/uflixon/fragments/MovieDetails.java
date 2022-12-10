package com.tridev.uflixon.fragments;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DecodeFormat;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.google.gson.JsonArray;
import com.tridev.uflixon.R;
import com.tridev.uflixon.adapters.CastAdapter;
import com.tridev.uflixon.databinding.LayoutMovieDetailsBinding;
import com.tridev.uflixon.db.FavoriteMovieEntities;
import com.tridev.uflixon.models.Cast;
import com.tridev.uflixon.models.Movie;
import com.tridev.uflixon.utilities.Constants;
import com.tridev.uflixon.viewmodels.HomeViewModel;

import org.jetbrains.annotations.NotNull;

import java.text.MessageFormat;

import java.util.ArrayList;
import java.util.HashMap;

import dagger.hilt.android.AndroidEntryPoint;

@AndroidEntryPoint
public class MovieDetails extends Fragment {

    private LayoutMovieDetailsBinding binding;
    private HomeViewModel viewModel;
    private final HashMap<String, String> map = new HashMap<>();
    private Integer movieId;
    int hour, min = 0;
    String temp, videoId;
    ArrayList<Cast> casts = new ArrayList<>();
    CastAdapter castAdapter;
    private Boolean inFavList = false;
    Movie mMovie;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(@NotNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        binding = LayoutMovieDetailsBinding.inflate(inflater, container, false);
        viewModel = new ViewModelProvider(this).get(HomeViewModel.class);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull @NotNull View view, @Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        MovieDetailsArgs movieDetailsArgs = MovieDetailsArgs.fromBundle(getArguments());
        movieId = movieDetailsArgs.getMovieId();

        observeData();
        map.put("api_key", Constants.API_KEY);
        map.put("page", "1");
        map.put("append_to_response", "videos");

        setUpList();
        setUpAdapters();
        setUpListener();


        binding.imgDetailsWatchlist.setOnClickListener(view1 -> {
            if (mMovie != null) {
                if (inFavList) {
                    viewModel.deleteMovie(movieId);
                    inFavList = false;
                    Constants.IS_WATCHLIST_UPDATED = false;
                    binding.imgDetailsWatchlist.setImageResource(R.drawable.ic_watchlist);
                    Toast.makeText(getContext(), "Removed from Watchlist.", Toast.LENGTH_SHORT).show();
                } else {
                    FavoriteMovieEntities movie = new FavoriteMovieEntities(mMovie.getId(), mMovie.getPoster_path(), mMovie.getOverview(),
                            mMovie.getRelease_date(), mMovie.getTitle(), mMovie.getBackdrop_path(), mMovie.getVote_count(),
                            mMovie.getRuntime());
                    viewModel.insertMovie(movie);
                    inFavList = true;
                    Constants.IS_WATCHLIST_UPDATED = true;
                    binding.imgDetailsWatchlist.setImageResource(R.drawable.ic_check);
                    Toast.makeText(getContext(), "Added to Watchlist.", Toast.LENGTH_SHORT).show();
                }
            } else {
                Toast.makeText(getContext(), "Please Wait!", Toast.LENGTH_SHORT).show();
            }
        });


    }

    private void isMovieInFavList(int movieId) {
        if (viewModel.getFavoriteListMovie(movieId) != null) {
            binding.imgDetailsWatchlist.setImageResource(R.drawable.ic_check);
            inFavList = true;
            Constants.IS_WATCHLIST_UPDATED = true;
        } else {
            binding.imgDetailsWatchlist.setImageResource(R.drawable.ic_watchlist);
            inFavList = false;
            Constants.IS_WATCHLIST_UPDATED = false;
        }
    }


    private void observeData() {
        viewModel.getMovieDetails().observe(getViewLifecycleOwner(), movie -> {
            if (movie != null) {
                mMovie = movie;

                if (movie.getBackdrop_path() != null) {
                    Glide.with(this).load(Constants.ImageBaseURLw500 + movie.getBackdrop_path())
                            .diskCacheStrategy(DiskCacheStrategy.NONE)
                            .centerInside()
                            .apply(new RequestOptions()
                                    .format(DecodeFormat.PREFER_RGB_565))
                            .into(binding.imgBackdrop);
                } else {
                    Glide.with(this).load(Constants.ImageBaseURLw780 + movie.getBackdrop_path())
                            .error(R.drawable.ic_no_image)
                            .into(binding.imgBackdrop);
                }

                new Thread(() -> Glide.get(requireContext()).clearDiskCache()).start();

                if (movie.getPoster_path() != null) {
                    Glide.with(this).load(Constants.ImageBaseURLw500 + movie.getPoster_path())
                            .diskCacheStrategy(DiskCacheStrategy.NONE)
                            .centerInside()
                            .apply(new RequestOptions().override(150,200)
                                    .format(DecodeFormat.PREFER_RGB_565))
                            .into(binding.imgMoviesPoster);
                } else {
                    Glide.with(this).load(Constants.ImageBaseURLw500 + movie.getPoster_path())
                            .error(R.drawable.ic_no_image)
                            .into(binding.imgMoviesPoster);

                }

                new Thread(() -> Glide.get(requireContext()).clearDiskCache()).start();

                binding.txtMovieName.setText(movie.getTitle());
                binding.txtDescription.setText(movie.getOverview());

                temp = "";
                for (int i = 0; i < movie.getGenres().size(); i++) {
                    if (i == movie.getGenres().size() - 1)
                        temp += movie.getGenres().get(i).getName();
                    else
                        temp += movie.getGenres().get(i).getName() + " • ";
                }
                binding.txtGenre.setText(temp);
                binding.txtReleaseDate.setText(String.format("Released On: %s", movie.getRelease_date()));

                binding.textReadMore.setOnClickListener(v -> {

                    if (binding.textReadMore.getText().toString().equals("Read more")) {
                        binding.txtDescription.setMaxLines(Integer.MAX_VALUE);
                        binding.txtDescription.setEllipsize(null);
                        binding.textReadMore.setText(R.string.read_less);
                    } else {
                        binding.txtDescription.setMaxLines(3);
                        binding.txtDescription.setEllipsize(TextUtils.TruncateAt.END);
                        binding.textReadMore.setText(R.string.read_more);
                    }
                });

                if (movie.getRuntime() != null) {
                    hour = movie.getRuntime() / 60;
                    min = movie.getRuntime() % 60;
                    binding.txtRuntime.setText(MessageFormat.format("{0}Hr {1}Min", hour, min));
                } else {
                    binding.txtRuntime.setText("N/A");
                }

                if (movie.getVote_average() != null) {
                    binding.txtRating.setRating(Float.parseFloat(String.valueOf(movie.getVote_average().floatValue() / 2)));
                } else {
                    binding.txtRating.setRating(Float.parseFloat("N/A"));
                }

                isMovieInFavList(movieId);


                JsonArray jsonArray = movie.getVideos().getAsJsonArray("results");
                videoId = jsonArray.get(0).getAsJsonObject().get("key").getAsString();
            } else {
                Toast.makeText(MovieDetails.this.getContext(), "Some error occurred!!", Toast.LENGTH_SHORT).show();
            }
        });

        viewModel.getCastList().observe(getViewLifecycleOwner(), casts -> castAdapter.updateCastList(casts));

    }

    private void setUpList() {
        viewModel.GetMovieDetails(movieId, map);
        viewModel.GetCast(movieId, map);
    }

    private void setUpAdapters() {
        castAdapter = new CastAdapter(getContext(), casts);
        binding.castRecyclerView.setAdapter(castAdapter);
    }

    private void setUpListener() {
        binding.imgPlayTrailer.setOnClickListener(view -> {
            if (videoId != null) {
                VideoDialog dialog = new VideoDialog(videoId);
                dialog.show(getParentFragmentManager(), "Video Dialog");
            } else
                Toast.makeText(getContext(), "Sorry trailer not found!", Toast.LENGTH_SHORT).show();
        });
    }


    @Override
    public void onDestroyView() {
        binding = null;
        super.onDestroyView();
    }

}