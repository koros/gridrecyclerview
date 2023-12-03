package com.korosmatick.gridrecyclerview;

import android.view.View;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

/**
 * An abstract class representing a ViewHolder for a cell in a grid layout.
 *
 * @param <T> The type of object to be bound to the ViewHolder.
 */
public abstract class GridCellViewHolder<T> extends RecyclerView.ViewHolder {

    /**
     * Constructor for GridCellViewHolder.
     *
     * @param itemView The view representing the grid cell.
     */
    public GridCellViewHolder(@NonNull View itemView) {
        super(itemView);
    }

    /**
     * Binds data to the ViewHolder.
     *
     * @param object The object to be bound to the ViewHolder.
     */
    public abstract void bind(T object);
}
