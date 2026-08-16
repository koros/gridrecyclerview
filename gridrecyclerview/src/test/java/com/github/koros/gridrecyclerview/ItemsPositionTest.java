package com.github.koros.gridrecyclerview;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

public class ItemsPositionTest {
    @Test
    public void shortConstructorDefaultsRangeToZero() {
        ItemsPosition<String> position = new ItemsPosition<>(GridLayoutMetadata.HEADER, "movies");

        assertEquals(GridLayoutMetadata.HEADER, position.getItemType());
        assertEquals("movies", position.getKey());
        assertEquals(0, position.getStart());
        assertEquals(0, position.getEnd());
    }

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

    @Test
    public void equalityIncludesTypeKeyAndRange() {
        ItemsPosition<String> position = new ItemsPosition<>(GridLayoutMetadata.GRID_ROW, "movies", 0, 2);

        assertEquals(position, new ItemsPosition<>(GridLayoutMetadata.GRID_ROW, "movies", 0, 2));
        assertEquals(position.hashCode(), new ItemsPosition<>(GridLayoutMetadata.GRID_ROW, "movies", 0, 2).hashCode());
        assertNotEquals(position, new ItemsPosition<>(GridLayoutMetadata.HEADER, "movies", 0, 2));
        assertNotEquals(position, new ItemsPosition<>(GridLayoutMetadata.GRID_ROW, "actors", 0, 2));
        assertNotEquals(position, new ItemsPosition<>(GridLayoutMetadata.GRID_ROW, "movies", 1, 2));
        assertNotEquals(position, new ItemsPosition<>(GridLayoutMetadata.GRID_ROW, "movies", 0, 3));
    }

    @Test
    public void toStringContainsUsefulFields() {
        String value = new ItemsPosition<>(GridLayoutMetadata.GRID_ROW, "movies", 0, 2).toString();

        assertTrue(value.contains("itemType=1"));
        assertTrue(value.contains("key=movies"));
        assertTrue(value.contains("start=0"));
        assertTrue(value.contains("end=2"));
    }
}
