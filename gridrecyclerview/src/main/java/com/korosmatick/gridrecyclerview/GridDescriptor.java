package com.korosmatick.gridrecyclerview;

import java.util.List;

public class GridDescriptor<T> {
    private int numberOfColumns;
    private List<T> items;

    public GridDescriptor(int numberOfColumns, List<T> items) {
        this.numberOfColumns = numberOfColumns;
        this.items = items;
    }

    public int getNumberOfColumns() {
        return numberOfColumns;
    }

    public void setNumberOfColumns(int numberOfColumns) {
        this.numberOfColumns = numberOfColumns;
    }

    public List<T> getItems() {
        return items;
    }

    public void setItems(List<T> items) {
        this.items = items;
    }
}
