package com.tridev.uflixon.fragments;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DecodeFormat;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.reflect.TypeToken;
import com.tridev.uflixon.R;
import com.tridev.uflixon.adapters.KnownForMoviesAdapter;
import com.tridev.uflixon.databinding.LayoutActorDetailsBinding;
import com.tridev.uflixon.models.Movie;
import com.tridev.uflixon.utilities.Constants;
import com.tridev.uflixon.viewmodels.HomeViewModel;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.HashMap;

import dagger.hilt.android.AndroidEntryPoint;

@AndroidEntryPoint
public class ActorDetails extends Fragment {
    private LayoutActorDetailsBinding binding;
    HomeViewModel viewModel;
    ArrayList<Movie> movies = new ArrayList<>();
    KnownForMoviesAdapter adapter;
    Integer personId;
    HashMap<String, String> map = new HashMap<>();

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(@NotNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = LayoutActorDetailsBinding.inflate(inflater, container, false);
        viewModel = new ViewModelProvider(this).get(HomeViewModel.class);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull @NotNull View view, @Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ActorDetailsArgs args = ActorDetailsArgs.fromBundle(getArguments());
        personId = args.getPersonId();

        observeData();
        map.put("api_key", Constants.API_KEY);
        map.put("append_to_response", "movie_credits");
        setUpDetailsList();
    }

    private void observeData() {
        viewModel.getActorDetails().observe(getViewLifecycleOwner(), actor -> {


            Glide.with(requireContext()).load(Constants.ImageBaseURLw500 + actor.getProfile_path())
                    .diskCacheStrategy(DiskCacheStrategy.RESOURCE)
                    .centerInside()
                    .error(R.drawable.ic_no_image)
                    .apply(new RequestOptions().override(200, 250)
                            .format(DecodeFormat.PREFER_RGB_565))
                    .into(binding.castImage);

            new Thread(() -> Glide.get(ActorDetails.this.requireContext()).clearDiskCache()).start();



            binding.txtActorName.setText(actor.getName());
            binding.txtKnownForProfession.setText(actor.getKnown_for_department());
            binding.dateOfBirth.setText(actor.getBirthday());
            binding.birthPlace.setText(actor.getPlace_of_birth());
            binding.Biography.setText(actor.getBiography());
            binding.txtKnownFor.setVisibility(View.VISIBLE);
            binding.txtDateOfBirth.setVisibility(View.VISIBLE);
            binding.txtBirthPlace.setVisibility(View.VISIBLE);
            binding.txtBiography.setVisibility(View.VISIBLE);
            binding.txtKnownForMovies.setVisibility(View.VISIBLE);
            binding.knownForRecyclerView.setVisibility(View.VISIBLE);

            JsonArray array = actor.getMovie_credits().getAsJsonArray("cast");
            movies = new Gson().fromJson(array.toString(), new TypeToken<ArrayList<Movie>>() {
            }.getType());
            setUpAdapter(movies);

        });
    }

    private void setUpDetailsList() {
        viewModel.GetActorDetails(personId, map);
    }

    private void setUpAdapter(ArrayList<Movie> movies) {
        adapter = new KnownForMoviesAdapter(getContext(), movies);
        binding.knownForRecyclerView.setAdapter(adapter);
    }

    @Override
    public void onDestroyView() {
        binding = null;
        super.onDestroyView();
    }
}