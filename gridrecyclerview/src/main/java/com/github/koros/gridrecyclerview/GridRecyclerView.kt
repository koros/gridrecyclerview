package com.github.koros.gridrecyclerview

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyItemScope
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

/**
 * Compose-first renderer for sectioned grids with headers and per-section column counts.
 *
 * The data model intentionally reuses [GridDescriptor] so projects can migrate from
 * [GridRecyclerViewAdapter] without reshaping their section maps.
 *
 * @param gridItems ordered section data keyed by the object passed to [headerContent].
 * @param modifier modifier applied to the backing [LazyColumn].
 * @param showHeadersForEmptySections when true, empty sections still render their header.
 * @param contentPadding padding applied around the [LazyColumn] content.
 * @param verticalArrangement spacing and alignment between header/grid rows.
 * @param horizontalArrangement spacing and alignment between cells in a grid row.
 * @param headerContent composable used to render each section header.
 * @param gridItemContent composable used to render each item cell.
 */
@Composable
fun <K> GridRecyclerView(
    gridItems: Map<K, GridDescriptor<*>>,
    modifier: Modifier = Modifier,
    showHeadersForEmptySections: Boolean = false,
    contentPadding: PaddingValues = PaddingValues(0.dp),
    verticalArrangement: Arrangement.Vertical = Arrangement.spacedBy(12.dp),
    horizontalArrangement: Arrangement.Horizontal = Arrangement.spacedBy(12.dp),
    headerContent: @Composable LazyItemScope.(headerItem: K) -> Unit,
    gridItemContent: @Composable (sectionKey: K, item: Any?) -> Unit
) {
    // Metadata calculation is pure and depends only on section data and empty-header policy. It is
    // remembered so recomposition does not repeatedly rebuild the flattened row map.
    val metadata = remember(gridItems, showHeadersForEmptySections) {
        GridLayoutMetadata.from(gridItems, showHeadersForEmptySections)
    }

    LazyColumn(
        modifier = modifier,
        contentPadding = contentPadding,
        verticalArrangement = verticalArrangement
    ) {
        items(
            count = metadata.itemCount,
            key = { position -> position },
            contentType = { position ->
                if (metadata.isHeaderPosition(position)) {
                    GridLayoutMetadata.HEADER
                } else {
                    GridLayoutMetadata.GRID_ROW
                }
            }
        ) { position ->
            val itemPosition = metadata.getItemPosition(position) ?: return@items
            if (metadata.isHeaderPosition(position)) {
                // Header UI is intentionally caller-owned so title, subtitle, and action clicks can
                // be handled directly by the composable supplied by the app.
                headerContent(itemPosition.key)
            } else {
                GridRow(
                    gridItems = gridItems,
                    itemPosition = itemPosition,
                    horizontalArrangement = horizontalArrangement,
                    gridItemContent = gridItemContent
                )
            }
        }
    }
}

/**
 * Renders a single row of equally weighted cells for a section.
 *
 * @param gridItems source section map used to resolve the row descriptor.
 * @param itemPosition metadata containing the section key and source item range for this row.
 * @param horizontalArrangement spacing and alignment between cells.
 * @param gridItemContent composable used to render each populated cell.
 */
@Composable
private fun <K> GridRow(
    gridItems: Map<K, GridDescriptor<*>>,
    itemPosition: ItemsPosition<K>,
    horizontalArrangement: Arrangement.Horizontal,
    gridItemContent: @Composable (sectionKey: K, item: Any?) -> Unit
) {
    val descriptor = gridItems[itemPosition.key] ?: return
    val rowItems = GridUtils.createSublist(descriptor.items, itemPosition.start, itemPosition.end)

    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = horizontalArrangement
    ) {
        repeat(descriptor.numberOfColumns) { column ->
            if (column < rowItems.size) {
                val item = rowItems[column]
                // Each real item receives equal width. Clicks and other gestures should be applied
                // by the caller's item composable, where the item type and UI context are known.
                Box(modifier = Modifier.weight(1f)) {
                    gridItemContent(itemPosition.key, item)
                }
            } else {
                // Incomplete final rows keep invisible weight placeholders so earlier cells retain
                // the same width as fully populated rows.
                Spacer(modifier = Modifier.weight(1f))
            }
        }
    }
}
