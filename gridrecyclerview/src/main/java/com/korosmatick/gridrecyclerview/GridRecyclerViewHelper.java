package com.korosmatick.gridrecyclerview;

import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public interface GridRecyclerViewHelper<K> {
    @NonNull
    RecyclerView.ViewHolder getHeaderViewHolder(@NonNull ViewGroup parent);

    void onBindHeaderViewHolder(@NonNull RecyclerView.ViewHolder holder, K headerItem);

    @NonNull
    ViewGroup getGridView(K key, @NonNull ViewGroup parent);

    @NonNull
    GridCellViewHolder getGridViewHolder(K key, @NonNull ViewGroup parent);

    void onBindGridView(@NonNull GridCellViewHolder holder, Object item);
}
