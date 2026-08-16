package com.github.koros.gridrecyclerview;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertSame;
import static org.junit.Assert.assertThrows;

import org.junit.Test;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 * Unit tests for {@link GridDescriptor}.
 */
public class GridDescriptorTest {
    /**
     * Verifies that constructor arguments are stored without copying.
     */
    @Test
    public void constructorStoresColumnCountAndItems() {
        List<String> items = Arrays.asList("A", "B");

        GridDescriptor<String> descriptor = new GridDescriptor<>(2, items);

        assertEquals(2, descriptor.getNumberOfColumns());
        assertSame(items, descriptor.getItems());
    }

    /**
     * Verifies that mutable descriptor properties can be replaced.
     */
    @Test
    public void settersUpdateColumnCountAndItems() {
        GridDescriptor<String> descriptor = new GridDescriptor<>(1, Collections.singletonList("A"));
        List<String> newItems = Arrays.asList("B", "C");

        descriptor.setNumberOfColumns(3);
        descriptor.setItems(newItems);

        assertEquals(3, descriptor.getNumberOfColumns());
        assertSame(newItems, descriptor.getItems());
    }

    /**
     * Verifies that descriptors reject column counts that cannot form a grid.
     */
    @Test
    public void rejectsInvalidColumnCounts() {
        // Both zero and negative counts should fail before layout metadata is calculated.
        assertThrows(
                IllegalArgumentException.class,
                () -> new GridDescriptor<>(0, Collections.emptyList())
        );
        assertThrows(
                IllegalArgumentException.class,
                () -> new GridDescriptor<>(-1, Collections.emptyList())
        );
    }

    /**
     * Verifies that descriptors reject null item lists.
     */
    @Test
    public void rejectsNullItems() {
        assertThrows(
                NullPointerException.class,
                () -> new GridDescriptor<String>(1, null)
        );
    }
}
