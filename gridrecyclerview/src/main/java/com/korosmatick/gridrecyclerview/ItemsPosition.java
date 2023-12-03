package com.korosmatick.gridrecyclerview;

import java.util.Objects;

/**
 * Represents the position and type of items in a grid layout.
 *
 * @param <K> The type of key used to identify sections in the grid.
 */
public class ItemsPosition<K> {
    int itemType;
    K key;
    int start;
    int end;

    /**
     * Constructor for ItemsPosition with specified itemType, key, start, and end.
     *
     * @param itemType The type of the item.
     * @param key      The key identifying the grid section.
     * @param start    The starting position of the item.
     * @param end      The ending position of the item.
     */
    public ItemsPosition(int itemType, K key, int start, int end) {
        this.itemType = itemType;
        this.key = key;
        this.start = start;
        this.end = end;
    }

    /**
     * Constructor for ItemsPosition with specified itemType and key.
     *
     * @param itemType The type of the item.
     * @param key      The key identifying the grid section.
     */
    public ItemsPosition(int itemType, K key) {
        this(itemType, key, 0, 0);
    }

    /**
     * Gets the type of the item.
     *
     * @return The item type.
     */
    public int getItemType() {
        return itemType;
    }

    /**
     * Sets the type of the item.
     *
     * @param itemType The new item type.
     */
    public void setItemType(int itemType) {
        this.itemType = itemType;
    }

    /**
     * Gets the key identifying the grid section.
     *
     * @return The key.
     */
    public K getKey() {
        return key;
    }

    /**
     * Sets the key identifying the grid section.
     *
     * @param key The new key.
     */
    public void setKey(K key) {
        this.key = key;
    }

    /**
     * Gets the starting position of the item.
     *
     * @return The starting position.
     */
    public int getStart() {
        return start;
    }

    /**
     * Sets the starting position of the item.
     *
     * @param start The new starting position.
     */
    public void setStart(int start) {
        this.start = start;
    }

    /**
     * Gets the ending position of the item.
     *
     * @return The ending position.
     */
    public int getEnd() {
        return end;
    }

    /**
     * Sets the ending position of the item.
     *
     * @param end The new ending position.
     */
    public void setEnd(int end) {
        this.end = end;
    }

    /**
     * Checks if this ItemsPosition is equal to another object.
     *
     * @param o The object to compare with.
     * @return True if the objects are equal, false otherwise.
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof ItemsPosition)) return false;
        ItemsPosition<?> that = (ItemsPosition<?>) o;
        return itemType == that.itemType && start == that.start && end == that.end && Objects.equals(key, that.key);
    }

    /**
     * Generates a hash code for this ItemsPosition.
     *
     * @return The hash code.
     */
    @Override
    public int hashCode() {
        return Objects.hash(itemType, key, start, end);
    }

    /**
     * Returns a string representation of this ItemsPosition.
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
