package com.korosmatick.gridrecyclerview;

import java.util.Objects;

public class ItemsPosition<K> {
    int itemType;
    K key;
    int start;
    int end;

    public ItemsPosition(int itemType, K key, int start, int end) {
        this.itemType = itemType;
        this.key = key;
        this.start = start;
        this.end = end;
    }

    public ItemsPosition(int itemType, K key) {
        this(itemType, key, 0, 0);
    }

    public int getItemType() {
        return itemType;
    }

    public void setItemType(int itemType) {
        this.itemType = itemType;
    }

    public K getKey() {
        return key;
    }

    public void setKey(K key) {
        this.key = key;
    }

    public int getStart() {
        return start;
    }

    public void setStart(int start) {
        this.start = start;
    }

    public int getEnd() {
        return end;
    }

    public void setEnd(int end) {
        this.end = end;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof ItemsPosition)) return false;
        ItemsPosition<?> that = (ItemsPosition<?>) o;
        return itemType == that.itemType && start == that.start && end == that.end && Objects.equals(key, that.key);
    }

    @Override
    public int hashCode() {
        return Objects.hash(itemType, key, start, end);
    }

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
