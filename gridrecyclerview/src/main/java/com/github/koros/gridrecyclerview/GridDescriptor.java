package com.github.koros.gridrecyclerview;

import java.util.List;
import java.util.Objects;

/**
 * Describes the layout and data for one logical grid section.
 *
 * <p>The descriptor is intentionally small: callers provide the number of
 * columns for the section and the ordered items that should be rendered in
 * those columns.</p>
 *
 * @param <T> The type of items in the section.
 */
public class GridDescriptor<T> {
    private int numberOfColumns;
    private List<T> items;

    /**
     * Creates a descriptor for a grid section.
     *
     * @param numberOfColumns The positive number of columns to display.
     * @param items           The non-null list of items to display.
     * @throws IllegalArgumentException If {@code numberOfColumns} is less than one.
     * @throws NullPointerException     If {@code items} is null.
     */
    public GridDescriptor(int numberOfColumns, List<T> items) {
        setNumberOfColumns(numberOfColumns);
        setItems(items);
    }

    /**
     * Returns the configured number of columns for the section.
     *
     * @return The positive column count.
     */
    public int getNumberOfColumns() {
        return numberOfColumns;
    }

    /**
     * Updates the number of columns for this section.
     *
     * @param numberOfColumns The positive number of columns to display.
     * @throws IllegalArgumentException If {@code numberOfColumns} is less than one.
     */
    public void setNumberOfColumns(int numberOfColumns) {
        // A zero-column section cannot be rendered or divided into rows safely.
        if (numberOfColumns < 1) {
            throw new IllegalArgumentException("numberOfColumns must be greater than 0");
        }
        this.numberOfColumns = numberOfColumns;
    }

    /**
     * Returns the items displayed by this section.
     *
     * @return The non-null section items in display order.
     */
    public List<T> getItems() {
        return items;
    }

    /**
     * Replaces the items displayed by this section.
     *
     * @param items The non-null section items in display order.
     * @throws NullPointerException If {@code items} is null.
     */
    public void setItems(List<T> items) {
        // Null item lists make metadata calculations ambiguous, so reject them early.
        this.items = Objects.requireNonNull(items, "items");
    }
}
