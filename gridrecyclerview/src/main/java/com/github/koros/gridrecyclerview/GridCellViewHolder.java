package com.github.koros.gridrecyclerview;

import android.view.View;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

/**
 * Base ViewHolder used for one cell inside a grid row.
 *
 * @param <T> The type of item bound to the cell.
 */
public abstract class GridCellViewHolder<T> extends RecyclerView.ViewHolder {

    /**
     * Creates a cell holder around the supplied item view.
     *
     * @param itemView The view representing the grid cell.
     */
    public GridCellViewHolder(@NonNull View itemView) {
        super(itemView);
    }

    /**
     * Binds the supplied item into this cell holder.
     *
     * @param object The item to display in the cell.
     */
    public abstract void bind(T object);
}
