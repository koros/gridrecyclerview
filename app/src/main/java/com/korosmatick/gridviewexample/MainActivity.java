package com.korosmatick.gridviewexample;

import android.content.Context;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ListView;

import com.korosmatick.gridviewexample.adapters.FruitsAdapter;
import com.korosmatick.gridviewexample.models.BananaModel;
import com.korosmatick.gridviewexample.models.OrangeModel;
import com.korosmatick.gridviewexample.models.PineappleModel;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;


public class MainActivity extends ActionBarActivity {

    FruitsAdapter adapter;
    ListView listView;
    List<Map<String, List<Object>>> items = new ArrayList<Map<String, List<Object>>>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        listView = (ListView)findViewById(R.id.listView);

        initDummyItems();
        adapter = new FruitsAdapter(this,R.layout.list_item, items, 3);
        listView.setAdapter(adapter);
    }

    private void initDummyItems(){
        List<String> itemTypesList = new ArrayList<String>();
        itemTypesList.add(Constants.BANANA);
        itemTypesList.add(Constants.ORANGE);
        itemTypesList.add(Constants.PINEAPPLE);

        for (String itemType : itemTypesList){
            Map<String, List<Object>> map = new HashMap<String, List<Object>>();
            List<Object> list = new ArrayList<Object>();
            for (int i = 0 ; i < 10 ; i++){
                String itemName = "Item " + i;
                String countryOfOrigin = "Country " + i;
                int image = R.drawable.image;
                Object object = null;
                if (itemType == Constants.BANANA){
                    //String name, String countryOfOrigin, int image
                    object = new BananaModel(itemName, countryOfOrigin, image);
                }
                else if(itemType == Constants.ORANGE){
                    object = new OrangeModel(itemName, countryOfOrigin, image);
                }
                else if (itemType == Constants.PINEAPPLE){
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
