package com.korosmatick.sampleapp.grid;

import static com.korosmatick.sampleapp.util.ImageUtils.getDrawableFromName;

import android.content.Context;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.korosmatick.gridrecyclerview.GridCellViewHolder;
import com.korosmatick.sampleapp.R;
import com.korosmatick.sampleapp.model.Movie;

public class MovieViewHolder extends GridCellViewHolder<Movie> {

    private final Context context;
    private final TextView name;
    private final ImageView image;

    public MovieViewHolder(@NonNull View itemView) {
        super(itemView);
        this.context = itemView.getContext();
        this.name = itemView.findViewById(R.id.name);
        this.image = itemView.findViewById(R.id.image);
    }

    @Override
    public void bind(Movie movie) {
        this.name.setText(movie.getName());
        this.image.setImageDrawable(getDrawableFromName(context, movie.getCover()));
    }
}
