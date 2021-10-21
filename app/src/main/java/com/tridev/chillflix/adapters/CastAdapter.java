package com.tridev.chillflix.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DecodeFormat;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.tridev.chillflix.R;
import com.tridev.chillflix.databinding.CastItemContainerBinding;
import com.tridev.chillflix.fragments.MovieDetailsDirections;
import com.tridev.chillflix.models.Cast;
import com.tridev.chillflix.utilities.Constants;

import java.util.ArrayList;


public class CastAdapter extends RecyclerView.Adapter<CastAdapter.CastViewModel> {
    private final Context context;
    private ArrayList<Cast> castList;

    public CastAdapter(Context context, ArrayList<Cast> castList) {
        this.context = context;
        this.castList = castList;
    }

    public void updateCastList(ArrayList<Cast> castList) {
        this.castList = castList;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public CastViewModel onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        CastItemContainerBinding binding = DataBindingUtil.inflate(inflater, R.layout.cast_item_container, parent, false);
        return new CastViewModel(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull CastViewModel holder, int position) {
        holder.castItemContainerBinding.txtCastName.setText(castList.get(position).getName());
        holder.castItemContainerBinding.txtCastRole.setText(castList.get(position).getCharacter());

        if (castList.get(position).getProfile_path()!=null) {
            Glide.with(context).load(Constants.ImageBaseURLw500 + castList.get(position).getProfile_path())
                    .diskCacheStrategy(DiskCacheStrategy.RESOURCE)
                    .override(150, 150)
                    .apply(RequestOptions.circleCropTransform()
                            .format(DecodeFormat.PREFER_RGB_565))
                    .into(holder.castItemContainerBinding.imgCastProfile);
        }else {
            Glide.with(context).load(Constants.ImageBaseURLw500 + castList.get(position).getProfile_path())
                    .error(R.drawable.ic_no_image)
                    .into(holder.castItemContainerBinding.imgCastProfile);
        }
        new Thread(() -> Glide.get(context).clearDiskCache()).start();


        holder.castItemContainerBinding.castItemLayout.setOnClickListener(view -> {
            MovieDetailsDirections.ActionMovieDetailsToActorDetails action =
                    MovieDetailsDirections.actionMovieDetailsToActorDetails(castList.get(position).getId());
            Navigation.findNavController(view).navigate(action);
        });

    }

    @Override
    public int getItemCount() {
        return castList == null ? 0 : castList.size();
    }

    static class CastViewModel extends RecyclerView.ViewHolder {

        private final CastItemContainerBinding castItemContainerBinding;
        public CastViewModel(CastItemContainerBinding castItemContainerBinding) {
            super(castItemContainerBinding.getRoot());
            this.castItemContainerBinding = castItemContainerBinding;
        }
    }
}

