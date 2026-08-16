package com.github.koros.gridrecyclerview;

/**
 * Callback invoked when a grid cell is clicked.
 *
 * @param <K> The type of key used to identify sections in the grid.
 */
public interface OnGridItemClickListener<K> {
    /**
     * Called when a non-empty grid cell is clicked.
     *
     * @param sectionKey The key identifying the grid section.
     * @param item       The item bound to the clicked grid cell.
     */
    void onGridItemClick(K sectionKey, Object item);
}
