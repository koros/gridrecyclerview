package com.github.koros.gridrecyclerview;

import java.util.List;
import java.util.Map;

/**
 * Utility class for common operations related to grid layouts.
 */
public class GridUtils {

    /**
     * Gets an item of a specific type from the grid based on the key and index.
     *
     * @param gridItems Map of grid items.
     * @param key       The key identifying the grid section.
     * @param index     The index of the item within the section.
     * @param itemType  The type of the item to retrieve.
     * @param <K>       The type of key used to identify sections in the grid.
     * @param <T>       The type of the item to retrieve.
     * @return The item of the specified type.
     * @throws IllegalArgumentException    If the key is not found in the gridItems map.
     * @throws IndexOutOfBoundsException   If the index is out of bounds.
     * @throws IllegalArgumentException    If the item is not an instance of the specified type.
     */
    public static <K, T> T getItem(Map<K, GridDescriptor<?>> gridItems, K key, int index, Class<T> itemType) {
        GridDescriptor<?> descriptor = gridItems.get(key);

        if (descriptor != null) {
            List<?> items = descriptor.getItems();

            if (index >= 0 && index < items.size()) {
                Object item = items.get(index);

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
     * Gets an item from the grid based on the key and index.
     *
     * @param gridItems Map of grid items.
     * @param key       The key identifying the grid section.
     * @param index     The index of the item within the section.
     * @param <K>       The type of key used to identify sections in the grid.
     * @param <T>       The type of the item to retrieve.
     * @return The item at the specified index.
     * @throws IllegalArgumentException    If the key is not found in the gridItems map.
     * @throws IndexOutOfBoundsException   If the index is out of bounds.
     */
    public static <K, T> T getItem(Map<K, GridDescriptor<?>> gridItems, K key, int index) {
        GridDescriptor<?> descriptor = gridItems.get(key);

        if (descriptor != null) {
            List<?> items = descriptor.getItems();

            if (index >= 0 && index < items.size()) {
                @SuppressWarnings("unchecked")
                T item = (T) items.get(index); // Unchecked cast, be careful

                return item;
            } else {
                throw new IndexOutOfBoundsException("Index is out of bounds");
            }
        } else {
            throw new IllegalArgumentException("Key not found in the gridItems map");
        }
    }

    /**
     * Creates a sublist from the given list based on start and end indices.
     *
     * @param list The original list.
     * @param i    The start index.
     * @param j    The end index.
     * @return The sublist.
     */
    public static List<?> createSublist(List<?> list, int i, int j) {
        // Ensure that i is within bounds
        if (i < 0) {
            i = 0;
        }
        // Ensure that j is within bounds
        if (j > list.size()) {
            j = list.size();
        }
        // Create the sublist
        return list.subList(i, j);
    }
}
