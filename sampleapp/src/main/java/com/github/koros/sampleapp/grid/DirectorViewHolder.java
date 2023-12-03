package com.github.koros.sampleapp.grid;

import android.content.Context;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.github.koros.gridrecyclerview.GridCellViewHolder;
import com.github.koros.sampleapp.R;
import com.github.koros.sampleapp.model.Director;

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
