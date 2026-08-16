package com.github.koros.gridrecyclerview;

import java.util.List;
import java.util.Map;

/**
 * Utility methods for looking up and slicing section data.
 */
public class GridUtils {

    /**
     * Returns an item from a keyed grid section and verifies its runtime type.
     *
     * @param gridItems Map of section keys to descriptors.
     * @param key       The key identifying the grid section.
     * @param index     The index of the item within the section.
     * @param itemType  The type of the item to retrieve.
     * @param <K>       The type of key used to identify sections in the grid.
     * @param <T>       The type of the item to retrieve.
     * @return The item at the requested index, cast to {@code itemType}.
     * @throws IllegalArgumentException  If the key is missing or the item has a different type.
     * @throws IndexOutOfBoundsException If the index is outside the section bounds.
     */
    public static <K, T> T getItem(Map<K, GridDescriptor<?>> gridItems, K key, int index, Class<T> itemType) {
        GridDescriptor<?> descriptor = gridItems.get(key);

        if (descriptor != null) {
            List<?> items = descriptor.getItems();

            // Validate the index before touching the backing list so callers get a clear failure.
            if (index >= 0 && index < items.size()) {
                Object item = items.get(index);

                // Use Class#cast so the checked overload fails before returning an invalid type.
                if (itemType.isInstance(item)) {
                    return itemType.cast(item);
                } else {
                    throw new IllegalArgumentException("Item is not an instance of " + itemType.getName());
                }
            } else {
                throw new IndexOutOfBoundsException("Index is out of bounds");
            }
        } else {
            throw new IllegalArgumentException("Key not found in the gridItems map");
        }
    }

    /**
     * Returns an item from a keyed grid section using the caller's expected type.
     *
     * @param key       The key identifying the grid section.
     * @param index     The index of the item within the section.
     * @param <K>       The type of key used to identify sections in the grid.
     * @param <T>       The type of the item to retrieve.
     * @return The item at the requested index.
     * @throws IllegalArgumentException  If the key is missing.
     * @throws IndexOutOfBoundsException If the index is outside the section bounds.
     */
    public static <K, T> T getItem(Map<K, GridDescriptor<?>> gridItems, K key, int index) {
        GridDescriptor<?> descriptor = gridItems.get(key);

        if (descriptor != null) {
            List<?> items = descriptor.getItems();

            // Validate the index before performing the unchecked cast below.
            if (index >= 0 && index < items.size()) {
                @SuppressWarnings("unchecked")
                T item = (T) items.get(index); // The descriptor stores heterogeneous sections as wildcard values.

                return item;
            } else {
                throw new IndexOutOfBoundsException("Index is out of bounds");
            }
        } else {
            throw new IllegalArgumentException("Key not found in the gridItems map");
        }
    }

    /**
     * Creates a bounded view of a list between the requested indices.
     *
     * @param list The source list.
     * @param i    The requested inclusive start index.
     * @param j    The requested exclusive end index.
     * @return A sublist whose bounds are clamped to {@code list}.
     */
    public static List<?> createSublist(List<?> list, int i, int j) {
        // Clamp negative starts so recycled rows can request a defensive slice safely.
        if (i < 0) {
            i = 0;
        }

        // Clamp oversized ends to avoid asking List#subList for impossible ranges.
        if (j > list.size()) {
            j = list.size();
        }

        return list.subList(i, j);
    }
}
