package com.github.koros.gridrecyclerview;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

/**
 * Unit tests for {@link ItemsPosition}.
 */
public class ItemsPositionTest {
    /**
     * Verifies that the header constructor initializes an empty item range.
     */
    @Test
    public void shortConstructorDefaultsRangeToZero() {
        ItemsPosition<String> position = new ItemsPosition<>(GridLayoutMetadata.HEADER, "movies");

        assertEquals(GridLayoutMetadata.HEADER, position.getItemType());
        assertEquals("movies", position.getKey());
        assertEquals(0, position.getStart());
        assertEquals(0, position.getEnd());
    }

    /**
     * Verifies that every mutable field can be updated.
     */
    @Test
    public void settersUpdateAllFields() {
        ItemsPosition<String> position = new ItemsPosition<>(GridLayoutMetadata.HEADER, "movies");

        position.setItemType(GridLayoutMetadata.GRID_ROW);
        position.setKey("actors");
        position.setStart(3);
        position.setEnd(6);

        assertEquals(GridLayoutMetadata.GRID_ROW, position.getItemType());
        assertEquals("actors", position.getKey());
        assertEquals(3, position.getStart());
        assertEquals(6, position.getEnd());
    }

    /**
     * Verifies that equality includes item type, key, and row range.
     */
    @Test
    public void equalityIncludesTypeKeyAndRange() {
        ItemsPosition<String> position = new ItemsPosition<>(GridLayoutMetadata.GRID_ROW, "movies", 0, 2);

        // Each differing field should be enough to make positions distinct.
        assertEquals(position, new ItemsPosition<>(GridLayoutMetadata.GRID_ROW, "movies", 0, 2));
        assertEquals(position.hashCode(), new ItemsPosition<>(GridLayoutMetadata.GRID_ROW, "movies", 0, 2).hashCode());
        assertNotEquals(position, new ItemsPosition<>(GridLayoutMetadata.HEADER, "movies", 0, 2));
        assertNotEquals(position, new ItemsPosition<>(GridLayoutMetadata.GRID_ROW, "actors", 0, 2));
        assertNotEquals(position, new ItemsPosition<>(GridLayoutMetadata.GRID_ROW, "movies", 1, 2));
        assertNotEquals(position, new ItemsPosition<>(GridLayoutMetadata.GRID_ROW, "movies", 0, 3));
    }

    /**
     * Verifies that {@link ItemsPosition#toString()} includes useful diagnostics.
     */
    @Test
    public void toStringContainsUsefulFields() {
        String value = new ItemsPosition<>(GridLayoutMetadata.GRID_ROW, "movies", 0, 2).toString();

        assertTrue(value.contains("itemType=1"));
        assertTrue(value.contains("key=movies"));
        assertTrue(value.contains("start=0"));
        assertTrue(value.contains("end=2"));
    }
}
