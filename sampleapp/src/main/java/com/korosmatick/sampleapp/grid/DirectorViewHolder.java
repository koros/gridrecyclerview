package com.korosmatick.sampleapp.grid;

import android.content.Context;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.korosmatick.gridrecyclerview.GridCellViewHolder;
import com.korosmatick.sampleapp.R;
import com.korosmatick.sampleapp.model.Director;

public class DirectorViewHolder extends GridCellViewHolder<Director> {
    private final Context context;
    private final TextView name;

    public DirectorViewHolder(@NonNull View itemView) {
        super(itemView);
        this.context = itemView.getContext();
        this.name = itemView.findViewById(R.id.name);
    }

    @Override
    public void bind(Director director) {
        this.name.setText(director.getName());
    }
}
