package com.korosmatick.gridrecyclerview;

import static com.korosmatick.gridrecyclerview.GridUtils.createSublist;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class GridRecyclerViewAdapter<K> extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private static final int HEADER = 0;
    private static final int GRID_ROW = 1;
    private final List<String> headersPositionMap = new ArrayList<>();
    private final Map<Integer, ItemsPosition> itemsPositionMap = new LinkedHashMap<>();
    private Map<K, GridDescriptor<?>> gridItems = new HashMap<>();
    private boolean showHeadersForEmptySections = false;
    private final GridRecyclerViewHelper gridRecyclerViewHelper;

    @SuppressWarnings("unused")
    public GridRecyclerViewAdapter(GridRecyclerViewHelper gridRecyclerViewHelper) {
        this(gridRecyclerViewHelper, new HashMap<>(), false);
    }

    @SuppressWarnings("unused")
    public GridRecyclerViewAdapter(GridRecyclerViewHelper gridRecyclerViewHelper, boolean showHeadersForEmptySections) {
        this(gridRecyclerViewHelper, new HashMap<>(), showHeadersForEmptySections);
    }

    @SuppressWarnings("unused")
    public GridRecyclerViewAdapter(GridRecyclerViewHelper gridRecyclerViewHelper, Map<K, GridDescriptor<?>> gridItems) {
        this(gridRecyclerViewHelper, gridItems, false);
    }

    public GridRecyclerViewAdapter(GridRecyclerViewHelper gridRecyclerViewHelper, Map<K, GridDescriptor<?>> gridItems, boolean showHeadersForEmptySections) {
        super();
        this.gridRecyclerViewHelper = gridRecyclerViewHelper;
        this.gridItems = gridItems;
        this.showHeadersForEmptySections = showHeadersForEmptySections;
        initializeGridMetaData();
    }

    private void initializeGridMetaData() {
        // Start counting from 0
        itemsPositionMap.clear();
        for (Map.Entry<K, GridDescriptor<?>> entry : gridItems.entrySet() ) {
            GridDescriptor<?> value = entry.getValue();
            List<?> items = value.getItems();
            K key = entry.getKey();

            int numberOfCols = value.getNumberOfColumns();

            //  Add the header position
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

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if (viewType == HEADER) {
            return gridRecyclerViewHelper.getHeaderViewHolder(parent);
        }
        ViewGroup view = (ViewGroup) LayoutInflater.from(parent.getContext()).inflate(R.layout.grid_row_item, parent, false);
        return new GridRecyclerViewHolder(view, gridRecyclerViewHelper);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        ItemsPosition pos = itemsPositionMap.get(position);
        Log.d("onBindViewHolder", pos.toString());
        assert pos != null;
        if (isHeaderPosition(position)) {
            gridRecyclerViewHelper.onBindHeaderViewHolder(holder, pos.getKey());
        } else {
            bindGridRow(holder, pos);
        }
    }

    private void bindGridRow(@NonNull final RecyclerView.ViewHolder holder, final ItemsPosition pos) {
        GridDescriptor<?> gridItem = gridItems.get(pos.key);
        assert gridItem != null;
        List<?> subList = createSublist(gridItem.getItems(), pos.start, pos.end);
        GridRecyclerViewHolder vh = (GridRecyclerViewHolder)holder;
        vh.bind(gridItem.getNumberOfColumns(), subList, pos.key);
    }

    @Override
    public int getItemCount() {
        return itemsPositionMap.size();
    }

    @Override
    public int getItemViewType(int position) {
        if (isHeaderPosition(position)) {
            return HEADER;
        }
        return GRID_ROW;
    }

    public boolean isHeaderPosition(int position) {
        return headersPositionMap.contains(String.valueOf(position));
    }

    @SuppressWarnings("unused")
    public void setGridItems(Map<K, GridDescriptor<?>> gridItems) {
        this.gridItems = gridItems;
        initializeGridMetaData();
    }

    @SuppressWarnings("unused")
    public void setShowHeadersForEmptySections(boolean showHeadersForEmptySections) {
        this.showHeadersForEmptySections = showHeadersForEmptySections;
    }
}
