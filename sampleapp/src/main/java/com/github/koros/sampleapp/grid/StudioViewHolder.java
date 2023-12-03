package com.github.koros.sampleapp.grid;

import android.content.Context;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.github.koros.gridrecyclerview.GridCellViewHolder;
import com.github.koros.sampleapp.R;
import com.github.koros.sampleapp.model.Studio;

public class StudioViewHolder extends GridCellViewHolder<Studio> {

    private final Context context;
    private final TextView name;

    public StudioViewHolder(@NonNull View itemView) {
        super(itemView);
        this.context = itemView.getContext();
        this.name = itemView.findViewById(R.id.name);
    }

    @Override
    public void bind(Studio studio) {
        this.name.setText(studio.getName());
    }
}
