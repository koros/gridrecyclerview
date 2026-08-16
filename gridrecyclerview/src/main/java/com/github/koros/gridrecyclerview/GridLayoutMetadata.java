package com.github.koros.gridrecyclerview;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Immutable lookup table that translates adapter/list positions into renderable grid rows.
 *
 * <p>The library uses this class from both the RecyclerView adapter and the Compose renderer so
 * section/header positioning stays consistent across both UI implementations.</p>
 *
 * @param <K> The type used to identify grid sections.
 */
final class GridLayoutMetadata<K> {
    /** View type used for section header rows. */
    static final int HEADER = 0;

    /** View type used for rows that contain one or more grid cells. */
    static final int GRID_ROW = 1;

    private final Set<Integer> headerPositions;
    private final Map<Integer, ItemsPosition<K>> itemPositions;

    private GridLayoutMetadata(Set<Integer> headerPositions, Map<Integer, ItemsPosition<K>> itemPositions) {
        this.headerPositions = headerPositions;
        this.itemPositions = itemPositions;
    }

    /**
     * Creates metadata from the caller-provided section descriptors.
     *
     * @param gridItems                    Ordered map of section keys to section descriptors.
     * @param showHeadersForEmptySections  Whether sections with no items still reserve a header row.
     * @param <K>                          The section key type.
     * @return Metadata containing adapter positions, row ranges, and header positions.
     */
    static <K> GridLayoutMetadata<K> from(Map<K, GridDescriptor<?>> gridItems, boolean showHeadersForEmptySections) {
        Set<Integer> headerPositions = new LinkedHashSet<>();
        Map<Integer, ItemsPosition<K>> itemPositions = new LinkedHashMap<>();

        for (Map.Entry<K, GridDescriptor<?>> entry : gridItems.entrySet()) {
            GridDescriptor<?> descriptor = entry.getValue();
            List<?> items = descriptor.getItems();
            K key = entry.getKey();
            int columnCount = descriptor.getNumberOfColumns();

            // Headers occupy their own adapter position so RecyclerView and Compose can render
            // them independently from the grid rows that follow.
            if (showHeadersForEmptySections || !items.isEmpty()) {
                int headerPosition = itemPositions.size();
                itemPositions.put(headerPosition, new ItemsPosition<>(HEADER, key));
                headerPositions.add(headerPosition);
            }

            // Each metadata row stores the original item-list start/end indices. The end index is
            // allowed to exceed the item count; GridUtils clamps it during binding.
            for (int start = 0; start < items.size(); start += columnCount) {
                itemPositions.put(
                        itemPositions.size(),
                        new ItemsPosition<>(GRID_ROW, key, start, start + columnCount)
                );
            }
        }

        return new GridLayoutMetadata<>(headerPositions, itemPositions);
    }

    /**
     * Returns the row metadata for an adapter/list position.
     *
     * @param adapterPosition Position in the flattened sectioned grid.
     * @return The item position metadata, or {@code null} when the position is outside the grid.
     */
    ItemsPosition<K> getItemPosition(int adapterPosition) {
        return itemPositions.get(adapterPosition);
    }

    /**
     * Returns the number of flattened rows, including header rows.
     *
     * @return Adapter/list item count.
     */
    int getItemCount() {
        return itemPositions.size();
    }

    /**
     * Checks whether a flattened position belongs to a section header.
     *
     * @param adapterPosition Position in the flattened sectioned grid.
     * @return {@code true} when the position renders a header.
     */
    boolean isHeaderPosition(int adapterPosition) {
        return headerPositions.contains(adapterPosition);
    }

    /**
     * Returns a copy of header positions.
     *
     * @return Header positions in insertion order.
     */
    List<Integer> getHeaderPositions() {
        return new ArrayList<>(headerPositions);
    }
}
