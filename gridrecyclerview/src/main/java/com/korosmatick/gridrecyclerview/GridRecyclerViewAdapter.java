package com.korosmatick.gridrecyclerview;

import static com.korosmatick.gridrecyclerview.GridUtils.createSublist;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * An adapter for displaying a grid layout in a RecyclerView with headers for each section.
 *
 * @param <K> The type of key used to identify sections in the grid.
 */
public class GridRecyclerViewAdapter<K> extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private static final int HEADER = 0;
    private static final int GRID_ROW = 1;
    private final List<String> headersPositionMap = new ArrayList<>();
    private final Map<Integer, ItemsPosition> itemsPositionMap = new LinkedHashMap<>();
    private Map<K, GridDescriptor<?>> gridItems = new HashMap<>();
    private boolean showHeadersForEmptySections = false;
    private final GridRecyclerViewHelper gridRecyclerViewHelper;

    /**
     * Constructor for GridRecyclerViewAdapter.
     *
     * @param gridRecyclerViewHelper An instance of GridRecyclerViewHelper.
     */
    @SuppressWarnings("unused")
    public GridRecyclerViewAdapter(GridRecyclerViewHelper gridRecyclerViewHelper) {
        this(gridRecyclerViewHelper, new HashMap<>(), false);
    }

    /**
     * Constructor for GridRecyclerViewAdapter with an option to show headers for empty sections.
     *
     * @param gridRecyclerViewHelper           An instance of GridRecyclerViewHelper.
     * @param showHeadersForEmptySections      True to show headers for empty sections, false otherwise.
     */
    @SuppressWarnings("unused")
    public GridRecyclerViewAdapter(GridRecyclerViewHelper gridRecyclerViewHelper, boolean showHeadersForEmptySections) {
        this(gridRecyclerViewHelper, new HashMap<>(), showHeadersForEmptySections);
    }

    /**
     * Constructor for GridRecyclerViewAdapter with initial grid items.
     *
     * @param gridRecyclerViewHelper An instance of GridRecyclerViewHelper.
     * @param gridItems              Initial grid items to be displayed.
     */
    @SuppressWarnings("unused")
    public GridRecyclerViewAdapter(GridRecyclerViewHelper gridRecyclerViewHelper, Map<K, GridDescriptor<?>> gridItems) {
        this(gridRecyclerViewHelper, gridItems, false);
    }

    /**
     * Constructor for GridRecyclerViewAdapter with initial grid items and an option to show headers for empty sections.
     *
     * @param gridRecyclerViewHelper           An instance of GridRecyclerViewHelper.
     * @param gridItems                        Initial grid items to be displayed.
     * @param showHeadersForEmptySections      True to show headers for empty sections, false otherwise.
     */
    public GridRecyclerViewAdapter(GridRecyclerViewHelper gridRecyclerViewHelper, Map<K, GridDescriptor<?>> gridItems, boolean showHeadersForEmptySections) {
        super();
        this.gridRecyclerViewHelper = gridRecyclerViewHelper;
        this.gridItems = gridItems;
        this.showHeadersForEmptySections = showHeadersForEmptySections;
        initializeGridMetaData();
    }

    /**
     * Initializes the metadata for the grid, including headers and item positions.
     */
    private void initializeGridMetaData() {
        // Start counting from 0
        itemsPositionMap.clear();
        for (Map.Entry<K, GridDescriptor<?>> entry : gridItems.entrySet()) {
            GridDescriptor<?> value = entry.getValue();
            List<?> items = value.getItems();
            K key = entry.getKey();

            int numberOfCols = value.getNumberOfColumns();

            // Add the header position
            if (showHeadersForEmptySections || items.size() > 0) {
                int currentPosition = itemsPositionMap.size();
                itemsPositionMap.put(currentPosition, new ItemsPosition<>(HEADER, key));
                headersPositionMap.add(String.valueOf(currentPosition));
            }
            for (int i = 0; i < items.size(); i += numberOfCols) {
                itemsPositionMap.put(itemsPositionMap.size(), new ItemsPosition<>(HEADER, key, i, i + numberOfCols));
            }
        }
    }

    /**
     * Creates and returns a new ViewHolder based on the given viewType.
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
        ViewGroup view = (ViewGroup) LayoutInflater.from(parent.getContext()).inflate(R.layout.grid_row_item, parent, false);
        return new GridRecyclerViewHolder(view, gridRecyclerViewHelper);
    }

    /**
     * Called by RecyclerView to display the data at the specified position.
     *
     * @param holder   The ViewHolder which should be updated to represent the contents of the item at the given position.
     * @param position The position of the item within the adapter's data set.
     */
    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        ItemsPosition pos = itemsPositionMap.get(position);
        assert pos != null;
        if (isHeaderPosition(position)) {
            gridRecyclerViewHelper.onBindHeaderViewHolder(holder, pos.getKey());
        } else {
            bindGridRow(holder, pos);
        }
    }

    /**
     * Binds data to a grid row in the RecyclerView.
     *
     * @param holder The ViewHolder which should be updated to represent the contents of the grid row.
     * @param pos    The position information for the grid row.
     */
    private void bindGridRow(@NonNull final RecyclerView.ViewHolder holder, final ItemsPosition pos) {
        GridDescriptor<?> gridItem = gridItems.get(pos.key);
        assert gridItem != null;
        List<?> subList = createSublist(gridItem.getItems(), pos.start, pos.end);
        GridRecyclerViewHolder vh = (GridRecyclerViewHolder) holder;
        vh.bind(gridItem.getNumberOfColumns(), subList, pos.key);
    }

    /**
     * Returns the total number of items in the adapter.
     *
     * @return The total number of items in the adapter.
     */
    @Override
    public int getItemCount() {
        return itemsPositionMap.size();
    }

    /**
     * Returns the view type of the item at the specified position.
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
     * Checks if the given position corresponds to a header position.
     *
     * @param position The position to check.
     * @return True if the position is a header position, false otherwise.
     */
    public boolean isHeaderPosition(int position) {
        return headersPositionMap.contains(String.valueOf(position));
    }

    /**
     * Sets new grid items for the adapter.
     *
     * @param gridItems New grid items to be displayed.
     */
    @SuppressWarnings("unused")
    public void setGridItems(Map<K, GridDescriptor<?>> gridItems) {
        this.gridItems = gridItems;
        initializeGridMetaData();
    }

    /**
     * Sets whether to show headers for empty sections.
     *
     * @param showHeadersForEmptySections True to show headers for empty sections, false otherwise.
     */
    @SuppressWarnings("unused")
    public void setShowHeadersForEmptySections(boolean showHeadersForEmptySections) {
        this.showHeadersForEmptySections = showHeadersForEmptySections;
    }
}
