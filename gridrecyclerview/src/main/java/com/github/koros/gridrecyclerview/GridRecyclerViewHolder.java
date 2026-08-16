package com.github.koros.gridrecyclerview;

import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

/**
 * ViewHolder class for a row in the grid layout of the RecyclerView.
 *
 * @param <K> The type of key used to identify sections in the grid.
 */
public class GridRecyclerViewHolder<K> extends RecyclerView.ViewHolder {
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
        if (!gridCategoryTag.equals(parentView.getTag())) {
            parentView.removeAllViews();
            parentView.setTag(gridCategoryTag);
        }
        // create the grids
        for (int i = 0; i < cols; i++) {
            String containerViewTag = "container_" + key.hashCode() + "_col_" + i;
            // get the grid container view
            GridCellContainer gridContainerView = parentView.findViewWithTag(containerViewTag);
            if (gridContainerView == null) {
                gridContainerView = createGridView();
                gridContainerView.setTag(containerViewTag);
                ViewGroup gridView = gridHelper.getGridView(key, gridContainerView);
                // get the View Holder
                gridContainerView.viewHolder = gridHelper.getGridViewHolder(key, gridView);
                gridContainerView.addView(gridView);
                parentView.addView(gridContainerView);
            }
            // bind the view
            if (i < items.size()) {
                gridContainerView.setVisibility(View.VISIBLE);
                Object item = items.get(i);
                GridCellViewHolder vh = gridContainerView.viewHolder;
                if (vh != null) {
                    vh.bind(item);
                }
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
    private GridCellContainer createGridView() {
        GridCellContainer container = new GridCellContainer(parentView);
        container.setOrientation(LinearLayout.HORIZONTAL);
        container.setLayoutParams(new LinearLayout.LayoutParams(
                0,
                ViewGroup.LayoutParams.WRAP_CONTENT,
                1f
        ));
        return container;
    }

    private static final class GridCellContainer extends LinearLayout {
        GridCellViewHolder<?> viewHolder;

        GridCellContainer(@NonNull ViewGroup parent) {
            super(parent.getContext());
        }
    }
}
