package com.korosmatick.gridrecyclerview;

import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

/**
 * An interface providing methods to customize the behavior of the GridRecyclerViewAdapter.
 *
 * @param <K> The type of key used to identify sections in the grid.
 */
public interface GridRecyclerViewHelper<K> {

    /**
     * Gets a ViewHolder for the header of a grid section.
     *
     * @param parent The ViewGroup into which the new View will be added after it is bound to an adapter position.
     * @return A new ViewHolder for the header.
     */
    @NonNull
    RecyclerView.ViewHolder getHeaderViewHolder(@NonNull ViewGroup parent);

    /**
     * Binds data to the header ViewHolder.
     *
     * @param holder      The ViewHolder which should be updated to represent the contents of the header.
     * @param headerItem  The item associated with the header.
     */
    void onBindHeaderViewHolder(@NonNull RecyclerView.ViewHolder holder, K headerItem);

    /**
     * Gets a ViewGroup for the main content of a grid section.
     *
     * @param key    The key identifying the grid section.
     * @param parent The ViewGroup into which the new View will be added after it is bound to an adapter position.
     * @return A new ViewGroup for the main content of the grid section.
     */
    @NonNull
    ViewGroup getGridView(K key, @NonNull ViewGroup parent);

    /**
     * Gets a ViewHolder for a cell in the grid.
     *
     * @param key    The key identifying the grid section.
     * @param parent The ViewGroup into which the new View will be added after it is bound to an adapter position.
     * @return A new ViewHolder for a cell in the grid.
     */
    @NonNull
    GridCellViewHolder getGridViewHolder(K key, @NonNull ViewGroup parent);
}
