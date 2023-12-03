package com.korosmatick.gridrecyclerview;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

/**
 * ViewHolder class for a row in the grid layout of the RecyclerView.
 *
 * @param <K> The type of key used to identify sections in the grid.
 */
public class GridRecyclerViewHolder<K> extends RecyclerView.ViewHolder {
    private final Context context;
    private final ViewGroup parentView;
    private final GridRecyclerViewHelper gridHelper;

    /**
     * Constructor for GridRecyclerViewHolder.
     *
     * @param itemView The view representing a row in the grid.
     * @param helper   An instance of GridRecyclerViewHelper.
     */
    GridRecyclerViewHolder(@NonNull ViewGroup itemView, GridRecyclerViewHelper helper) {
        super(itemView);
        this.context = itemView.getContext();
        this.parentView = itemView;
        this.gridHelper = helper;
    }

    /**
     * Binds data to the ViewHolder.
     *
     * @param cols  The number of columns in the grid.
     * @param items The list of items to be displayed in the grid.
     * @param key   The key identifying the grid section.
     */
    public void bind(int cols, List<?> items, K key) {
        // if the row belongs to a different category, remove all child views
        String gridCategoryTag = "grid_section_" + key.hashCode();
        if (parentView.getTag() != gridCategoryTag) {
            parentView.removeAllViews();
            parentView.setTag(gridCategoryTag);
        }
        // create the grids
        for (int i = 0; i < cols; i++) {
            String containerViewTag = "container_" + key.hashCode() + "_col_" + i;
            // get the grid container view
            ViewGroup gridContainerView = parentView.findViewWithTag(containerViewTag);
            if (gridContainerView == null) {
                gridContainerView = createGridView();
                gridContainerView.setTag(containerViewTag);
                ViewGroup gridView = gridHelper.getGridView(key, gridContainerView);
                // get the View Holder
                GridCellViewHolder<?> vh = gridHelper.getGridViewHolder(key, gridView);
                gridContainerView.setTag(R.id.grid_view_holder_id, vh);
                gridContainerView.addView(gridView);
                parentView.addView(gridContainerView);
            }
            // bind the view
            if (i < items.size()) {
                Object item = items.get(i);
                GridCellViewHolder vh = (GridCellViewHolder) gridContainerView.getTag(R.id.grid_view_holder_id);
                vh.bind(item);
            } else {
                // set the container view to invisible, there isn't data to bind
                gridContainerView.setVisibility(View.INVISIBLE);
            }
        }
    }

    /**
     * Creates a new ViewGroup for a grid.
     *
     * @return A new ViewGroup for the grid.
     */
    private ViewGroup createGridView() {
        return (ViewGroup) LayoutInflater.from(context).inflate(R.layout.grid, parentView, false);
    }
}
