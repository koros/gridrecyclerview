package com.github.koros.gridrecyclerview;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThrows;

import org.junit.Test;

import java.util.Arrays;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class GridUtilsTest {
    @Test
    public void getItemReturnsTypedItem() {
        Map<String, GridDescriptor<?>> sections = new LinkedHashMap<>();
        sections.put("numbers", new GridDescriptor<>(2, Arrays.asList(1, 2, 3)));

        Integer item = GridUtils.getItem(sections, "numbers", 1, Integer.class);

        assertEquals(Integer.valueOf(2), item);
    }

    @Test
    public void getItemFailsForWrongType() {
        Map<String, GridDescriptor<?>> sections = new LinkedHashMap<>();
        sections.put("numbers", new GridDescriptor<>(2, Collections.singletonList(1)));

        assertThrows(
                IllegalArgumentException.class,
                () -> GridUtils.getItem(sections, "numbers", 0, String.class)
        );
    }

    @Test
    public void getItemReturnsUntypedItem() {
        Map<String, GridDescriptor<?>> sections = new LinkedHashMap<>();
        sections.put("letters", new GridDescriptor<>(2, Arrays.asList("A", "B")));

        String item = GridUtils.getItem(sections, "letters", 0);

        assertEquals("A", item);
    }

    @Test
    public void getItemFailsForMissingKey() {
        Map<String, GridDescriptor<?>> sections = new LinkedHashMap<>();

        assertThrows(
                IllegalArgumentException.class,
                () -> GridUtils.getItem(sections, "missing", 0)
        );
    }

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

    @Test
    public void createSublistClampsBounds() {
        List<?> sublist = GridUtils.createSublist(Arrays.asList("A", "B", "C"), -1, 5);

        assertEquals(Arrays.asList("A", "B", "C"), sublist);
    }

    @Test
    public void createSublistReturnsRequestedRange() {
        List<?> sublist = GridUtils.createSublist(Arrays.asList("A", "B", "C", "D"), 1, 3);

        assertEquals(Arrays.asList("B", "C"), sublist);
    }
}
