package com.github.koros.gridrecyclerview;

import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

/**
 * Callback interface used by {@link GridRecyclerViewAdapter} to create and bind views.
 *
 * @param <K> The type of key used to identify sections in the grid.
 */
public interface GridRecyclerViewHelper<K> {

    /**
     * Creates a ViewHolder for a section header row.
     *
     * @param parent The ViewGroup into which the new View will be added after it is bound to an adapter position.
     * @return A new ViewHolder for a section header.
     */
    @NonNull
    RecyclerView.ViewHolder getHeaderViewHolder(@NonNull ViewGroup parent);

    /**
     * Binds a section key or header model to its header ViewHolder.
     *
     * @param holder      The ViewHolder which should be updated to represent the contents of the header.
     * @param headerItem  The item associated with the header.
     */
    void onBindHeaderViewHolder(@NonNull RecyclerView.ViewHolder holder, K headerItem);

    /**
     * Creates the container used to hold one row of grid cells.
     *
     * @param key    The key identifying the grid section.
     * @param parent The ViewGroup into which the new View will be added after it is bound to an adapter position.
     * @return A ViewGroup that can host the row's cell views.
     */
    @NonNull
    ViewGroup getGridView(K key, @NonNull ViewGroup parent);

    /**
     * Creates a ViewHolder for one grid cell.
     *
     * @param key    The key identifying the grid section.
     * @param parent The ViewGroup into which the new View will be added after it is bound to an adapter position.
     * @return A new ViewHolder for a cell in the grid.
     */
    @NonNull
    GridCellViewHolder getGridViewHolder(K key, @NonNull ViewGroup parent);
}
