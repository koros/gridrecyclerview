package com.github.koros.gridrecyclerview;

import java.util.List;

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
        this.numberOfColumns = numberOfColumns;
        this.items = items;
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
        this.items = items;
    }
}
