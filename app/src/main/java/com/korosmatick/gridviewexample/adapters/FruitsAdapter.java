package com.korosmatick.gridviewexample.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.korosmatick.gridviewexample.R;
import com.korosmatick.gridviewexample.models.GenericModel;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Created by Geoffrey Koros on 9/4/2015.
 */
public class FruitsAdapter extends ArrayAdapter<Map<String, List<Object>>>{

    List<Map<String, List<Object>>> items = new ArrayList<Map<String, List<Object>>>();
    private int numberOfCols;
    private List<String> headerPositions = new ArrayList<String>();
    private Map<String, String> itemTypePositionsMap = new LinkedHashMap<String, String>();
    private Map<String, Integer> offsetForItemTypeMap = new LinkedHashMap<String, Integer>();
    LayoutInflater layoutInflater;
    View.OnClickListener mItemClickListener;

    public FruitsAdapter(Context context, int textViewResourceId, List<Map<String, List<Object>>> items, int numberOfCols, View.OnClickListener mItemClickListener){
        super(context, textViewResourceId, items);
        this.items = items;
        this.numberOfCols = numberOfCols;
        layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        this.mItemClickListener = mItemClickListener;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        convertView = layoutInflater.inflate(R.layout.list_item, null);

        if(isHeaderPosition(position)){
            View v = convertView.findViewById(R.id.listItemLayout);
            v.setVisibility(View.GONE);

            TextView headerText = (TextView)convertView.findViewById(R.id.headerText);
            String section = getItemTypeAtPosition(position);
            headerText.setText(getHeaderForSection(section));
            return convertView;
        }

        //hide the header
        View v = convertView.findViewById(R.id.headerLayout);
        v.setVisibility(View.GONE);

        Map<String, List<Object>> map = getItem(position);
        List<Object> list = map.get(getItemTypeAtPosition(position));

        for (int i = 0; i <= numberOfCols; i++){
            FrameLayout grid = (FrameLayout)convertView.findViewWithTag(String.valueOf(i+1));
            ImageView imageView;
            if (i < list.size()){
                GenericModel model = (GenericModel)list.get(i);
                if (grid != null){
                    imageView = (ImageView)grid.findViewWithTag("image");
                    imageView.setBackgroundResource(model.getImageResource());

                    TextView textView = (TextView)grid.findViewWithTag("subHeader");
                    textView.setText(model.getHeader());

                    grid.setTag(R.id.row, position);
                    grid.setTag(R.id.col, i);
                    grid.setOnClickListener(mItemClickListener);
                }
            }else{
                if (grid != null){
                    grid.setVisibility(View.INVISIBLE);
                }
            }

        }

        //set hooks for click listener


        return convertView;
    }

    @Override
    public int getCount() {
        int totalItems = 0;
        for (Map<String, List<Object>> map : items){
            Set<String> set = map.keySet();
            for(String key : set){
                //calculate the number of rows each set homogeneous grid would occupy
                List<Object> l = map.get(key);
                int rows = l.size() % numberOfCols == 0 ? l.size() / numberOfCols : (l.size() / numberOfCols) + 1;

                // insert the header position
                if (rows > 0){
                    headerPositions.add(String.valueOf(totalItems));
                    offsetForItemTypeMap.put(key, totalItems);

                    itemTypePositionsMap.put(key, totalItems + "," + (totalItems + rows) );
                    totalItems += 1; // header view takes up one position
                }
                totalItems+= rows;
            }
        }
        return totalItems;
    }

    @Override
    public Map<String, List<Object>> getItem(int position) {
        if (!isHeaderPosition(position)){
            String itemType = getItemTypeAtPosition(position);
            List<Object> list = null;
            for (Map<String, List<Object>> map : items) {
                if (map.containsKey(itemType)){
                    list = map.get(itemType);
                    break;
                }
            }
            if (list != null){
                int offset = position - getOffsetForItemType(itemType);
                //remove header position
                offset -= 1;
                int low = offset * numberOfCols;
                int high = low + numberOfCols  < list.size() ? (low + numberOfCols) : list.size();
                List<Object> subList = list.subList(low, high);
                Map<String, List<Object>> subListMap = new HashMap<String, List<Object>>();
                subListMap.put(itemType, subList);
                return subListMap;
            }
        }
        return null;
    }

    public String getItemTypeAtPosition(int position){
        String itemType = "Unknown";
        Set<String> set = itemTypePositionsMap.keySet();

        for(String key : set){
            String[] bounds = itemTypePositionsMap.get(key).split(",");
            int lowerBound = Integer.valueOf(bounds[0]);
            int upperBoundary = Integer.valueOf(bounds[1]);
            if (position >= lowerBound && position <= upperBoundary){
                itemType = key;
                break;
            }
        }
        return itemType;
    }

    public int getOffsetForItemType(String itemType){
        return offsetForItemTypeMap.get(itemType);
    }

    public boolean isHeaderPosition(int position){
        return headerPositions.contains(String.valueOf(position));
    }

    private String getHeaderForSection(String section){
        if (section.equalsIgnoreCase("Banana")){
            return "Bananas";
        }else if (section.equalsIgnoreCase("Pineapple")){
            return "Pineapples";
        }else if (section.equalsIgnoreCase("Orange")){
            return "Oranges";
        }
        return "";
    }

}
