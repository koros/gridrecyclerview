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
                Box(modifier = Modifier.weight(1f)) {
                    gridItemContent(itemPosition.key, rowItems[column])
                }
            } else {
                Spacer(modifier = Modifier.weight(1f))
            }
        }
    }
}
