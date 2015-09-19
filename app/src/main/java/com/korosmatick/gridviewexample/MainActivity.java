package com.korosmatick.gridviewexample;

import android.content.Context;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ListView;
import android.widget.Toast;

import com.korosmatick.gridviewexample.adapters.FruitsAdapter;
import com.korosmatick.gridviewexample.models.BananaModel;
import com.korosmatick.gridviewexample.models.GenericModel;
import com.korosmatick.gridviewexample.models.OrangeModel;
import com.korosmatick.gridviewexample.models.PineappleModel;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Random;


public class MainActivity extends ActionBarActivity {

    FruitsAdapter adapter;
    ListView listView;
    List<Map<String, List<Object>>> items = new ArrayList<Map<String, List<Object>>>();
    int[] bananaImages = new int[]{R.drawable.banana4, R.drawable.banana5, R.drawable.banana6,
            R.drawable.banana7, R.drawable.banana8, R.drawable.banana9};

    int[] orangeImages = new int[]{R.drawable.orange1, R.drawable.orange2, R.drawable.orange3, R.drawable.orange4, R.drawable.orange5, R.drawable.orange6,
            R.drawable.orange8, R.drawable.orange9, R.drawable.orange10, R.drawable.orange11, R.drawable.orange12, R.drawable.orange14, R.drawable.orange15};

    int[] pineappleImages = new int[]{R.drawable.pineapple1, R.drawable.pineapple2, R.drawable.pineapple3, R.drawable.pineapple4, R.drawable.pineapple5, R.drawable.pineapple6,
            R.drawable.pineapple7, R.drawable.pineapple8};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        listView = (ListView)findViewById(R.id.listView);

        initDummyItems();
        adapter = new FruitsAdapter(this,R.layout.list_item, items, 3, mItemClickListener);
        listView.setAdapter(adapter);
    }

    View.OnClickListener mItemClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            int position = (Integer)v.getTag(R.id.row);
            int col = (Integer)v.getTag(R.id.col);
            
            Map<String, List<Object>> map = adapter.getItem(position);
            List<Object> list = map.get(adapter.getItemTypeAtPosition(position));
            GenericModel model = (GenericModel)list.get(col);
            Toast.makeText(getApplicationContext(), "" + model.getHeader(), Toast.LENGTH_SHORT).show();
        }
    };

    private void initDummyItems(){
        List<String> itemTypesList = new ArrayList<String>();
        itemTypesList.add(Constants.ORANGE);
        itemTypesList.add(Constants.PINEAPPLE);
        itemTypesList.add(Constants.BANANA);

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
                    //String name, String countryOfOrigin, int image
                    image = bananaImages[rand.nextInt(bananaImages.length)];
                    object = new BananaModel(itemName, countryOfOrigin, image);
                }
                else if(itemType == Constants.ORANGE){
                    image = orangeImages[rand.nextInt(orangeImages.length)];
                    object = new OrangeModel(itemName, countryOfOrigin, image);
                }
                else if (itemType == Constants.PINEAPPLE){
                    image = pineappleImages[rand.nextInt(pineappleImages.length)];
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
