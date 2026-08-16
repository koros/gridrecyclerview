package com.github.koros.gridrecyclerview;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import java.util.Arrays;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * Unit tests for {@link GridLayoutMetadata}.
 */
public class GridLayoutMetadataTest {
    /**
     * Verifies that non-empty sections create a header row followed by grid rows.
     */
    @Test
    public void createsHeadersAndRowsForNonEmptySections() {
        Map<String, GridDescriptor<?>> sections = new LinkedHashMap<>();
        sections.put("movies", new GridDescriptor<>(2, Arrays.asList("Alien", "Heat", "Jaws")));
        sections.put("actors", new GridDescriptor<>(3, Arrays.asList("Ada", "Grace")));

        GridLayoutMetadata<String> metadata = GridLayoutMetadata.from(sections, false);

        // Three movie items at two columns produce two rows, while two actors at three columns produce one row.
        assertEquals(5, metadata.getItemCount());
        assertTrue(metadata.isHeaderPosition(0));
        assertFalse(metadata.isHeaderPosition(1));
        assertTrue(metadata.isHeaderPosition(3));

        assertEquals(new ItemsPosition<>(GridLayoutMetadata.HEADER, "movies"), metadata.getItemPosition(0));
        assertEquals(new ItemsPosition<>(GridLayoutMetadata.GRID_ROW, "movies", 0, 2), metadata.getItemPosition(1));
        assertEquals(new ItemsPosition<>(GridLayoutMetadata.GRID_ROW, "movies", 2, 4), metadata.getItemPosition(2));
        assertEquals(new ItemsPosition<>(GridLayoutMetadata.HEADER, "actors"), metadata.getItemPosition(3));
        assertEquals(new ItemsPosition<>(GridLayoutMetadata.GRID_ROW, "actors", 0, 3), metadata.getItemPosition(4));
    }

    /**
     * Verifies that empty sections are skipped unless explicitly requested.
     */
    @Test
    public void hidesEmptySectionHeadersByDefault() {
        Map<String, GridDescriptor<?>> sections = new LinkedHashMap<>();
        sections.put("empty", new GridDescriptor<>(2, Collections.emptyList()));
        sections.put("movies", new GridDescriptor<>(2, Collections.singletonList("Alien")));

        GridLayoutMetadata<String> metadata = GridLayoutMetadata.from(sections, false);

        assertEquals(2, metadata.getItemCount());
        assertEquals(Collections.singletonList(0), metadata.getHeaderPositions());
        assertEquals(new ItemsPosition<>(GridLayoutMetadata.HEADER, "movies"), metadata.getItemPosition(0));
    }

    /**
     * Verifies that empty sections can still render header-only rows.
     */
    @Test
    public void canShowHeadersForEmptySections() {
        Map<String, GridDescriptor<?>> sections = new LinkedHashMap<>();
        sections.put("empty", new GridDescriptor<>(2, Collections.emptyList()));

        GridLayoutMetadata<String> metadata = GridLayoutMetadata.from(sections, true);

        assertEquals(1, metadata.getItemCount());
        assertTrue(metadata.isHeaderPosition(0));
        assertEquals(new ItemsPosition<>(GridLayoutMetadata.HEADER, "empty"), metadata.getItemPosition(0));
    }

    /**
     * Verifies that exact column multiples do not create extra trailing rows.
     */
    @Test
    public void createsRowsForExactColumnMultiples() {
        Map<String, GridDescriptor<?>> sections = new LinkedHashMap<>();
        sections.put("movies", new GridDescriptor<>(2, Arrays.asList("Alien", "Heat", "Jaws", "Moon")));

        GridLayoutMetadata<String> metadata = GridLayoutMetadata.from(sections, false);

        assertEquals(3, metadata.getItemCount());
        assertEquals(new ItemsPosition<>(GridLayoutMetadata.GRID_ROW, "movies", 0, 2), metadata.getItemPosition(1));
        assertEquals(new ItemsPosition<>(GridLayoutMetadata.GRID_ROW, "movies", 2, 4), metadata.getItemPosition(2));
    }

    /**
     * Verifies that an empty input map creates empty metadata.
     */
    @Test
    public void handlesCompletelyEmptyInput() {
        GridLayoutMetadata<String> metadata = GridLayoutMetadata.from(Collections.emptyMap(), false);

        assertEquals(0, metadata.getItemCount());
        assertFalse(metadata.isHeaderPosition(0));
        assertNull(metadata.getItemPosition(0));
        assertEquals(Collections.emptyList(), metadata.getHeaderPositions());
    }

    /**
     * Verifies that callers cannot mutate internal header position state.
     */
    @Test
    public void headerPositionsReturnsACopy() {
        Map<String, GridDescriptor<?>> sections = new LinkedHashMap<>();
        sections.put("movies", new GridDescriptor<>(2, Collections.singletonList("Alien")));
        GridLayoutMetadata<String> metadata = GridLayoutMetadata.from(sections, false);

        // Mutating the returned copy must not change the metadata used by the adapter.
        metadata.getHeaderPositions().clear();

        assertTrue(metadata.isHeaderPosition(0));
        assertEquals(Collections.singletonList(0), metadata.getHeaderPositions());
    }
}
