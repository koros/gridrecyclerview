package com.korosmatick.gridrecyclerview;

import android.view.View;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public abstract class GridCellViewHolder<T> extends RecyclerView.ViewHolder {
    public GridCellViewHolder(@NonNull View itemView) {
        super(itemView);
    }

    public abstract void bind(T object);
}
