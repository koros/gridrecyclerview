package com.korosmatick.gridrecyclerview;

import java.util.List;
import java.util.Map;

public class GridUtils {
    public static <K, T> T getItem(Map<K, GridDescriptor<?>> gridItems, K key, int index, Class<T> itemType) {
        GridDescriptor<?> tuple = gridItems.get(key);

        if (tuple != null) {
            List<?> items = tuple.getItems();

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

    public static <K, T> T getItem(Map<K, GridDescriptor<?>> gridItems, K key, int index) {
        GridDescriptor<?> tuple = gridItems.get(key);

        if (tuple != null) {
            List<?> items = tuple.getItems();

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
        List<?> sublist = list.subList(i, j);
        return sublist;
    }
}
