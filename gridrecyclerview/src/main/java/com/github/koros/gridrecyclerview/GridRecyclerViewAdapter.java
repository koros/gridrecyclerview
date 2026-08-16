package com.github.koros.gridrecyclerview;

import static com.github.koros.gridrecyclerview.GridUtils.createSublist;

import android.view.ViewGroup;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * RecyclerView adapter for sectioned grids with optional headers for empty sections.
 *
 * <p>This class keeps the original View-based API available for apps that have not migrated to
 * Compose. New Compose callers should prefer {@code GridRecyclerView} from the Kotlin source.</p>
 *
 * @param <K> The type of key used to identify sections in the grid.
 */
public class GridRecyclerViewAdapter<K> extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private static final int HEADER = GridLayoutMetadata.HEADER;
    private static final int GRID_ROW = GridLayoutMetadata.GRID_ROW;
    private Map<K, GridDescriptor<?>> gridItems = new HashMap<>();
    private boolean showHeadersForEmptySections = false;
    private final GridRecyclerViewHelper gridRecyclerViewHelper;
    private GridLayoutMetadata<K> layoutMetadata;

    /**
     * Creates an empty adapter that hides headers for empty sections.
     *
     * @param gridRecyclerViewHelper Helper responsible for creating section header and cell views.
     */
    @SuppressWarnings("unused")
    public GridRecyclerViewAdapter(GridRecyclerViewHelper gridRecyclerViewHelper) {
        this(gridRecyclerViewHelper, new HashMap<>(), false);
    }

    /**
     * Creates an empty adapter with configurable empty-section header behavior.
     *
     * @param gridRecyclerViewHelper      Helper responsible for creating section header and cell views.
     * @param showHeadersForEmptySections True to show headers for empty sections, false otherwise.
     */
    @SuppressWarnings("unused")
    public GridRecyclerViewAdapter(GridRecyclerViewHelper gridRecyclerViewHelper, boolean showHeadersForEmptySections) {
        this(gridRecyclerViewHelper, new HashMap<>(), showHeadersForEmptySections);
    }

    /**
     * Creates an adapter with initial grid items that hides headers for empty sections.
     *
     * @param gridRecyclerViewHelper Helper responsible for creating section header and cell views.
     * @param gridItems              Initial grid items to be displayed.
     */
    @SuppressWarnings("unused")
    public GridRecyclerViewAdapter(GridRecyclerViewHelper gridRecyclerViewHelper, Map<K, GridDescriptor<?>> gridItems) {
        this(gridRecyclerViewHelper, gridItems, false);
    }

    /**
     * Creates an adapter with initial grid items and configurable empty-section header behavior.
     *
     * @param gridRecyclerViewHelper      Helper responsible for creating section header and cell views.
     * @param gridItems                   Initial grid items to be displayed.
     * @param showHeadersForEmptySections True to show headers for empty sections, false otherwise.
     */
    public GridRecyclerViewAdapter(GridRecyclerViewHelper gridRecyclerViewHelper, Map<K, GridDescriptor<?>> gridItems, boolean showHeadersForEmptySections) {
        super();
        this.gridRecyclerViewHelper = gridRecyclerViewHelper;
        this.gridItems = gridItems;
        this.showHeadersForEmptySections = showHeadersForEmptySections;
        initializeGridMetaData();
    }

    /**
     * Rebuilds the flattened metadata used by RecyclerView callbacks.
     */
    private void initializeGridMetaData() {
        layoutMetadata = GridLayoutMetadata.from(gridItems, showHeadersForEmptySections);
    }

    /**
     * Creates a ViewHolder for either a section header or a horizontal grid row.
     *
     * @param parent   The ViewGroup into which the new View will be added after it is bound to an adapter position.
     * @param viewType The view type of the new View.
     * @return A new ViewHolder.
     */
    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if (viewType == HEADER) {
            return gridRecyclerViewHelper.getHeaderViewHolder(parent);
        }
        return new GridRecyclerViewHolder(createGridRowView(parent), gridRecyclerViewHelper);
    }

    /**
     * Creates the programmatic parent container for one grid row.
     *
     * @param parent RecyclerView parent used for context and layout parameters.
     * @return Horizontal row container whose children are weighted grid-cell containers.
     */
    private ViewGroup createGridRowView(@NonNull ViewGroup parent) {
        LinearLayout row = new LinearLayout(parent.getContext());
        row.setOrientation(LinearLayout.HORIZONTAL);
        row.setLayoutParams(new RecyclerView.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT
        ));
        return row;
    }

    /**
     * Binds a header row or grid row for a flattened adapter position.
     *
     * @param holder   The ViewHolder which should be updated to represent the contents of the item at the given position.
     * @param position The position of the item within the adapter's data set.
     */
    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        ItemsPosition pos = layoutMetadata.getItemPosition(position);
        assert pos != null;
        if (isHeaderPosition(position)) {
            // Header binding is fully caller-owned because apps define their own header view shape.
            gridRecyclerViewHelper.onBindHeaderViewHolder(holder, pos.getKey());
        } else {
            bindGridRow(holder, pos);
        }
    }

    /**
     * Extracts a row-sized slice from the section data and delegates cell binding to the row holder.
     *
     * @param holder The ViewHolder which should be updated to represent the contents of the grid row.
     * @param pos    The position information for the grid row.
     */
    private void bindGridRow(@NonNull final RecyclerView.ViewHolder holder, final ItemsPosition pos) {
        GridDescriptor<?> gridItem = gridItems.get(pos.key);
        assert gridItem != null;
        // Metadata end positions may extend past the list size on partial rows; createSublist
        // clamps that range before binding.
        List<?> subList = createSublist(gridItem.getItems(), pos.start, pos.end);
        GridRecyclerViewHolder vh = (GridRecyclerViewHolder) holder;
        vh.bind(gridItem.getNumberOfColumns(), subList, pos.key);
    }

    /**
     * Returns the total number of flattened rows, including headers.
     *
     * @return The total number of items in the adapter.
     */
    @Override
    public int getItemCount() {
        return layoutMetadata.getItemCount();
    }

    /**
     * Returns the RecyclerView type for a flattened row position.
     *
     * @param position The position of the item within the adapter's data set.
     * @return An integer representing the view type.
     */
    @Override
    public int getItemViewType(int position) {
        if (isHeaderPosition(position)) {
            return HEADER;
        }
        return GRID_ROW;
    }

    /**
     * Checks whether a flattened adapter position corresponds to a section header.
     *
     * @param position The position to check.
     * @return True if the position is a header position, false otherwise.
     */
    public boolean isHeaderPosition(int position) {
        return layoutMetadata.isHeaderPosition(position);
    }

    /**
     * Replaces the section data and refreshes adapter metadata.
     *
     * @param gridItems New grid items to be displayed.
     */
    @SuppressWarnings("unused")
    public void setGridItems(Map<K, GridDescriptor<?>> gridItems) {
        this.gridItems = gridItems;
        initializeGridMetaData();
        notifyDataSetChanged();
    }

    /**
     * Updates empty-section header behavior and refreshes adapter metadata.
     *
     * @param showHeadersForEmptySections True to show headers for empty sections, false otherwise.
     */
    @SuppressWarnings("unused")
    public void setShowHeadersForEmptySections(boolean showHeadersForEmptySections) {
        this.showHeadersForEmptySections = showHeadersForEmptySections;
        initializeGridMetaData();
        notifyDataSetChanged();
    }
}
