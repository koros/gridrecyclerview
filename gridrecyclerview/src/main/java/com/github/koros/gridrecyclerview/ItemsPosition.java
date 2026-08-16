package com.github.koros.gridrecyclerview;

import java.util.Objects;

/**
 * Maps one adapter row to the grid section data it should render.
 *
 * <p>A row can represent either a section header or a grid content row. For
 * grid rows, {@code start} and {@code end} describe the slice of section items
 * that belongs in that row.</p>
 *
 * @param <K> The type of key used to identify sections in the grid.
 */
public class ItemsPosition<K> {
    int itemType;
    K key;
    int start;
    int end;

    /**
     * Creates a position entry for a header or grid content row.
     *
     * @param key      The key identifying the grid section.
     * @param start    The inclusive item start index for grid rows.
     * @param end      The exclusive item end index for grid rows.
     */
    public ItemsPosition(int itemType, K key, int start, int end) {
        this.itemType = itemType;
        this.key = key;
        this.start = start;
        this.end = end;
    }

    /**
     * Creates a header position entry with no item range.
     *
     * @param itemType The type of the item.
     * @param key      The key identifying the grid section.
     */
    public ItemsPosition(int itemType, K key) {
        this(itemType, key, 0, 0);
    }

    /**
     * Returns the adapter item type represented by this position.
     *
     * @return The item type.
     */
    public int getItemType() {
        return itemType;
    }

    /**
     * Updates the adapter item type represented by this position.
     *
     * @param itemType The new item type.
     */
    public void setItemType(int itemType) {
        this.itemType = itemType;
    }

    /**
     * Returns the key identifying the grid section.
     *
     * @return The key.
     */
    public K getKey() {
        return key;
    }

    /**
     * Updates the key identifying the grid section.
     *
     * @param key The new key.
     */
    public void setKey(K key) {
        this.key = key;
    }

    /**
     * Returns the inclusive start index for grid row slices.
     *
     * @return The starting position.
     */
    public int getStart() {
        return start;
    }

    /**
     * Updates the inclusive start index for grid row slices.
     *
     * @param start The new starting position.
     */
    public void setStart(int start) {
        this.start = start;
    }

    /**
     * Returns the exclusive end index for grid row slices.
     *
     * @return The ending position.
     */
    public int getEnd() {
        return end;
    }

    /**
     * Updates the exclusive end index for grid row slices.
     *
     * @param end The new ending position.
     */
    public void setEnd(int end) {
        this.end = end;
    }

    /**
     * Compares positions using the item type, section key, and row slice.
     *
     * @param o The object to compare with.
     * @return True if the objects are equal, false otherwise.
     */
    @Override
    public boolean equals(Object o) {
        // Fast path for the common case where RecyclerView metadata reuses the same instance.
        if (this == o) return true;
        if (!(o instanceof ItemsPosition)) return false;
        ItemsPosition<?> that = (ItemsPosition<?>) o;
        return itemType == that.itemType && start == that.start && end == that.end && Objects.equals(key, that.key);
    }

    /**
     * Generates a hash code from the fields used by {@link #equals(Object)}.
     *
     * @return The hash code.
     */
    @Override
    public int hashCode() {
        return Objects.hash(itemType, key, start, end);
    }

    /**
     * Returns a debug-friendly representation of this adapter position.
     *
     * @return A string representation.
     */
    @Override
    public String toString() {
        return "ItemsPosition{" +
                "itemType=" + itemType +
                ", key=" + key +
                ", start=" + start +
                ", end=" + end +
                '}';
    }
}
