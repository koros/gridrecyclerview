package com.github.koros.sampleapp.grid;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.github.koros.gridrecyclerview.GridCellViewHolder;
import com.github.koros.gridrecyclerview.GridRecyclerViewHelper;
import com.github.koros.sampleapp.R;
import com.github.koros.sampleapp.model.GridHeader;

public class SampleGridRecyclerViewHelper implements GridRecyclerViewHelper<GridHeader> {
    @NonNull
    @Override
    public RecyclerView.ViewHolder getHeaderViewHolder(@NonNull ViewGroup parent) {
        ViewGroup view = (ViewGroup) LayoutInflater.from(parent.getContext()).inflate(R.layout.header_view, parent, false);
        return new HeaderViewHolder(view);
    }

    @Override
    public void onBindHeaderViewHolder(RecyclerView.ViewHolder holder, GridHeader headerItem) {
        ((HeaderViewHolder) holder).bind(headerItem);
    }

    @NonNull
    @Override
    public ViewGroup getGridView(GridHeader key, @NonNull ViewGroup parent) {
        return GridViewHolderHelper.getGridView(key, parent);
    }

    @NonNull
    @Override
    public GridCellViewHolder getGridViewHolder(GridHeader key, @NonNull ViewGroup parent) {
        return GridViewHolderHelper.getGridViewHolder(key, parent);
    }
}
