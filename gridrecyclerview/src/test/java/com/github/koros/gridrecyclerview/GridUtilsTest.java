package com.github.koros.gridrecyclerview;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThrows;

import org.junit.Test;

import java.util.Arrays;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * Unit tests for {@link GridUtils}.
 */
public class GridUtilsTest {
    /**
     * Verifies that the checked overload returns an item of the requested type.
     */
    @Test
    public void getItemReturnsTypedItem() {
        Map<String, GridDescriptor<?>> sections = new LinkedHashMap<>();
        sections.put("numbers", new GridDescriptor<>(2, Arrays.asList(1, 2, 3)));

        Integer item = GridUtils.getItem(sections, "numbers", 1, Integer.class);

        assertEquals(Integer.valueOf(2), item);
    }

    /**
     * Verifies that the checked overload rejects mismatched item types.
     */
    @Test
    public void getItemFailsForWrongType() {
        Map<String, GridDescriptor<?>> sections = new LinkedHashMap<>();
        sections.put("numbers", new GridDescriptor<>(2, Collections.singletonList(1)));

        assertThrows(
                IllegalArgumentException.class,
                () -> GridUtils.getItem(sections, "numbers", 0, String.class)
        );
    }

    /**
     * Verifies that the generic overload returns the requested item.
     */
    @Test
    public void getItemReturnsUntypedItem() {
        Map<String, GridDescriptor<?>> sections = new LinkedHashMap<>();
        sections.put("letters", new GridDescriptor<>(2, Arrays.asList("A", "B")));

        String item = GridUtils.getItem(sections, "letters", 0);

        assertEquals("A", item);
    }

    /**
     * Verifies that missing section keys fail with a clear exception.
     */
    @Test
    public void getItemFailsForMissingKey() {
        Map<String, GridDescriptor<?>> sections = new LinkedHashMap<>();

        assertThrows(
                IllegalArgumentException.class,
                () -> GridUtils.getItem(sections, "missing", 0)
        );
    }

    /**
     * Verifies that negative and oversized item indexes fail.
     */
    @Test
    public void getItemFailsForOutOfBoundsIndex() {
        Map<String, GridDescriptor<?>> sections = new LinkedHashMap<>();
        sections.put("letters", new GridDescriptor<>(2, Collections.singletonList("A")));

        assertThrows(
                IndexOutOfBoundsException.class,
                () -> GridUtils.getItem(sections, "letters", 1, String.class)
        );
        assertThrows(
                IndexOutOfBoundsException.class,
                () -> GridUtils.getItem(sections, "letters", -1)
        );
    }

    /**
     * Verifies that sublist creation clamps requested bounds to the list size.
     */
    @Test
    public void createSublistClampsBounds() {
        List<?> sublist = GridUtils.createSublist(Arrays.asList("A", "B", "C"), -1, 5);

        assertEquals(Arrays.asList("A", "B", "C"), sublist);
    }

    /**
     * Verifies that sublist creation preserves a valid requested range.
     */
    @Test
    public void createSublistReturnsRequestedRange() {
        List<?> sublist = GridUtils.createSublist(Arrays.asList("A", "B", "C", "D"), 1, 3);

        assertEquals(Arrays.asList("B", "C"), sublist);
    }
}
