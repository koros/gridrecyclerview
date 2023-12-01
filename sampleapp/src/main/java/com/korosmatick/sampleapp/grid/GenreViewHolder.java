package com.korosmatick.sampleapp.grid;

import static com.korosmatick.sampleapp.util.ImageUtils.getDrawableFromName;

import android.content.Context;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.korosmatick.gridrecyclerview.GridCellViewHolder;
import com.korosmatick.sampleapp.R;
import com.korosmatick.sampleapp.model.Genre;

public class GenreViewHolder extends GridCellViewHolder<Genre> {

    private final Context context;
    private final TextView name;
    private final ImageView image;

    public GenreViewHolder(@NonNull View itemView) {
        super(itemView);
        this.context = itemView.getContext();
        this.name = itemView.findViewById(R.id.name);
        this.image = itemView.findViewById(R.id.image);
    }

    @Override
    public void bind(Genre genre) {
        this.name.setText(genre.getName());
        this.image.setImageDrawable(getDrawableFromName(context, genre.getImage()));
    }
}
