package com.github.koros.gridrecyclerview;

import java.util.List;
import java.util.Objects;

/**
 * A class representing the descriptor for a grid layout.
 *
 * @param <T> The type of items in the grid.
 */
public class GridDescriptor<T> {
    private int numberOfColumns;
    private List<T> items;

    /**
     * Constructor for GridDescriptor.
     *
     * @param numberOfColumns The number of columns in the grid.
     * @param items           The list of items to be displayed in the grid.
     */
    public GridDescriptor(int numberOfColumns, List<T> items) {
        setNumberOfColumns(numberOfColumns);
        setItems(items);
    }

    /**
     * Gets the number of columns in the grid.
     *
     * @return The number of columns.
     */
    public int getNumberOfColumns() {
        return numberOfColumns;
    }

    /**
     * Sets the number of columns in the grid.
     *
     * @param numberOfColumns The new number of columns.
     */
    public void setNumberOfColumns(int numberOfColumns) {
        if (numberOfColumns < 1) {
            throw new IllegalArgumentException("numberOfColumns must be greater than 0");
        }
        this.numberOfColumns = numberOfColumns;
    }

    /**
     * Gets the list of items in the grid.
     *
     * @return The list of items.
     */
    public List<T> getItems() {
        return items;
    }

    /**
     * Sets the list of items in the grid.
     *
     * @param items The new list of items.
     */
    public void setItems(List<T> items) {
        this.items = Objects.requireNonNull(items, "items");
    }
}
