package com.github.koros.gridrecyclerview;

import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

/**
 * RecyclerView ViewHolder that renders one horizontal row of grid cells.
 *
 * <p>The holder creates its row and cell containers programmatically so the library can support
 * the legacy RecyclerView API without shipping XML layout resources.</p>
 *
 * @param <K> The type of key used to identify sections in the grid.
 */
public class GridRecyclerViewHolder<K> extends RecyclerView.ViewHolder {
    private final ViewGroup parentView;
    private final GridRecyclerViewHelper gridHelper;

    /**
     * Creates a row holder around a horizontal parent container.
     *
     * @param itemView The view representing a row in the grid.
     * @param helper   Helper used to create and bind caller-provided cell views.
     */
    GridRecyclerViewHolder(@NonNull ViewGroup itemView, GridRecyclerViewHelper helper) {
        super(itemView);
        this.parentView = itemView;
        this.gridHelper = helper;
    }

    /**
     * Binds the row's visible cells and hides any trailing placeholders.
     *
     * @param cols  The number of columns in the grid.
     * @param items The list of items to be displayed in the grid.
     * @param key   The key identifying the grid section.
     */
    public void bind(int cols, List<?> items, K key) {
        String gridCategoryTag = "grid_section_" + key.hashCode();

        // RecyclerView rows are recycled across sections. When the section changes, discard
        // existing cell containers because the caller may provide a different cell layout type.
        if (!gridCategoryTag.equals(parentView.getTag())) {
            parentView.removeAllViews();
            parentView.setTag(gridCategoryTag);
        }

        for (int i = 0; i < cols; i++) {
            String containerViewTag = "container_" + key.hashCode() + "_col_" + i;
            GridCellContainer gridContainerView = parentView.findViewWithTag(containerViewTag);

            // Cell containers are reused within the same section to avoid repeatedly asking the
            // helper to inflate/create item views while rows scroll in and out.
            if (gridContainerView == null) {
                gridContainerView = createGridView();
                gridContainerView.setTag(containerViewTag);
                ViewGroup gridView = gridHelper.getGridView(key, gridContainerView);
                gridContainerView.viewHolder = gridHelper.getGridViewHolder(key, gridView);
                gridContainerView.addView(gridView);
                parentView.addView(gridContainerView);
            }

            if (i < items.size()) {
                // A recycled container may have been hidden when it represented an empty trailing
                // column in a previous row, so make it visible before binding real data.
                gridContainerView.setVisibility(View.VISIBLE);
                Object item = items.get(i);
                GridCellViewHolder vh = gridContainerView.viewHolder;
                if (vh != null) {
                    vh.bind(item);
                }
            } else {
                // Preserve column width with an invisible placeholder when the final row is short.
                gridContainerView.setVisibility(View.INVISIBLE);
            }
        }
    }

    /**
     * Creates a weighted cell container for one column in the row.
     *
     * @return A new container ready to host a caller-provided cell view.
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

        /**
         * Creates a cell container with the same context as the parent row.
         *
         * @param parent Parent row used only for obtaining the Android context.
         */
        GridCellContainer(@NonNull ViewGroup parent) {
            super(parent.getContext());
        }
    }
}
