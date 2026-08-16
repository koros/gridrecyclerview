package com.github.koros.gridrecyclerview;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertSame;
import static org.junit.Assert.assertThrows;

import org.junit.Test;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class GridDescriptorTest {
    @Test
    public void constructorStoresColumnCountAndItems() {
        List<String> items = Arrays.asList("A", "B");

        GridDescriptor<String> descriptor = new GridDescriptor<>(2, items);

        assertEquals(2, descriptor.getNumberOfColumns());
        assertSame(items, descriptor.getItems());
    }

    @Test
    public void settersUpdateColumnCountAndItems() {
        GridDescriptor<String> descriptor = new GridDescriptor<>(1, Collections.singletonList("A"));
        List<String> newItems = Arrays.asList("B", "C");

        descriptor.setNumberOfColumns(3);
        descriptor.setItems(newItems);

        assertEquals(3, descriptor.getNumberOfColumns());
        assertSame(newItems, descriptor.getItems());
    }

    @Test
    public void rejectsInvalidColumnCounts() {
        assertThrows(
                IllegalArgumentException.class,
                () -> new GridDescriptor<>(0, Collections.emptyList())
        );
        assertThrows(
                IllegalArgumentException.class,
                () -> new GridDescriptor<>(-1, Collections.emptyList())
        );
    }

    @Test
    public void rejectsNullItems() {
        assertThrows(
                NullPointerException.class,
                () -> new GridDescriptor<String>(1, null)
        );
    }
}
