package com.korosmatick.gridviewexample;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ListView;
import android.widget.Toast;

import com.korosmatick.gridviewexample.adapters.GenericModelAdapter;
import com.korosmatick.gridviewexample.models.BananaModel;
import com.korosmatick.gridviewexample.models.GenericModel;
import com.korosmatick.gridviewexample.models.OrangeModel;
import com.korosmatick.gridviewexample.models.PineappleModel;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;


public class MainActivity extends Activity {

    GenericModelAdapter adapter;
    ListView listView;
    private static final int NUMBER_OF_COLS = 4;
    List<Map<String, List<Object>>> items = new ArrayList<Map<String, List<Object>>>();
    Map<String, String> sectionHeaderTitles = new HashMap<String, String>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        listView = (ListView)findViewById(R.id.listView);

        initDummyItems();
        adapter = new GenericModelAdapter(this,R.layout.list_item, items, sectionHeaderTitles, NUMBER_OF_COLS, mItemClickListener);
        listView.setAdapter(adapter);
    }

    View.OnClickListener mItemClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            int position = (Integer)v.getTag(R.id.row);
            int col = (Integer)v.getTag(R.id.col);

            Map<String, List<Object>> map = adapter.getItem(position);
            String selectedItemType = adapter.getItemTypeAtPosition(position);
            List<Object> list = map.get(selectedItemType);
            GenericModel model = (GenericModel)list.get(col);
            Toast.makeText(getApplicationContext(), "" + model.getHeader(), Toast.LENGTH_SHORT).show();
        }
    };

    private void initDummyItems(){
        List<String> itemTypesList = new ArrayList<String>();
        itemTypesList.add(Constants.ORANGE);
        itemTypesList.add(Constants.PINEAPPLE);
        itemTypesList.add(Constants.BANANA);
        sectionHeaderTitles.put(Constants.ORANGE, "Oranges");
        sectionHeaderTitles.put(Constants.PINEAPPLE, "Pineapples");
        sectionHeaderTitles.put(Constants.BANANA, "Bananas");

        for (String itemType : itemTypesList){
            Map<String, List<Object>> map = new HashMap<String, List<Object>>();
            List<Object> list = new ArrayList<Object>();

            for (int i = 0 ; i < 10 ; i++){
                String itemName = itemType + " " + i;
                String countryOfOrigin = "Country " + i;
                int image;
                Random rand = new Random();

                Object object = null;
                if (itemType == Constants.BANANA){
                    image = Constants.bananaImages[rand.nextInt(Constants.bananaImages.length)];
                    object = new BananaModel(itemName, countryOfOrigin, image);
                }
                else if(itemType == Constants.ORANGE){
                    image = Constants.orangeImages[rand.nextInt(Constants.orangeImages.length)];
                    object = new OrangeModel(itemName, countryOfOrigin, image);
                }
                else if (itemType == Constants.PINEAPPLE){
                    image = Constants.pineappleImages[rand.nextInt(Constants.pineappleImages.length)];
                    object = new PineappleModel(itemName, countryOfOrigin, image);
                }
                list.add(object);
            }

            map.put(itemType, list);
            items.add(map);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
