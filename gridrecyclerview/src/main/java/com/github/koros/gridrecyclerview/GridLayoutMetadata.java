package com.github.koros.gridrecyclerview;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

final class GridLayoutMetadata<K> {
    static final int HEADER = 0;
    static final int GRID_ROW = 1;

    private final Set<Integer> headerPositions;
    private final Map<Integer, ItemsPosition<K>> itemPositions;

    private GridLayoutMetadata(Set<Integer> headerPositions, Map<Integer, ItemsPosition<K>> itemPositions) {
        this.headerPositions = headerPositions;
        this.itemPositions = itemPositions;
    }

    static <K> GridLayoutMetadata<K> from(Map<K, GridDescriptor<?>> gridItems, boolean showHeadersForEmptySections) {
        Set<Integer> headerPositions = new LinkedHashSet<>();
        Map<Integer, ItemsPosition<K>> itemPositions = new LinkedHashMap<>();

        for (Map.Entry<K, GridDescriptor<?>> entry : gridItems.entrySet()) {
            GridDescriptor<?> descriptor = entry.getValue();
            List<?> items = descriptor.getItems();
            K key = entry.getKey();
            int columnCount = descriptor.getNumberOfColumns();

            if (showHeadersForEmptySections || !items.isEmpty()) {
                int headerPosition = itemPositions.size();
                itemPositions.put(headerPosition, new ItemsPosition<>(HEADER, key));
                headerPositions.add(headerPosition);
            }

            for (int start = 0; start < items.size(); start += columnCount) {
                itemPositions.put(
                        itemPositions.size(),
                        new ItemsPosition<>(GRID_ROW, key, start, start + columnCount)
                );
            }
        }

        return new GridLayoutMetadata<>(headerPositions, itemPositions);
    }

    ItemsPosition<K> getItemPosition(int adapterPosition) {
        return itemPositions.get(adapterPosition);
    }

    int getItemCount() {
        return itemPositions.size();
    }

    boolean isHeaderPosition(int adapterPosition) {
        return headerPositions.contains(adapterPosition);
    }

    List<Integer> getHeaderPositions() {
        return new ArrayList<>(headerPositions);
    }
}
