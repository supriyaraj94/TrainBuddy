package com.example.nihal.foodmenu;

import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;


public class MainActivity extends ActionBarActivity {


    private ListView lv;
    private Model[] modelItems;

    private ListView stationListView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.stations);
/*      lv = (ListView) findViewById(R.id.listView1);
        modelItems = new Model[5];
        modelItems[0] = new Model("pizza", 0);
        modelItems[1] = new Model("burger", 1);
        modelItems[2] = new Model("olives", 1);
        modelItems[3] = new Model("orange", 0);
        modelItems[4] = new Model("tomato", 1);
        CustomAdapter adapter = new CustomAdapter(this, modelItems);
        lv.setAdapter(adapter);*/


        /*REMOVE THIS AND INSERT DYNAMIC LIST GENERATOR*/

        String[] stationArray = {"Bangalore", "Delhi", "Mumbai", "Chennai", "Kolkata", "Jaipur", "Mohali", "Hyderabad", "Pondicherry", "Goa"};

        buildStationListView(stationArray);


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    private void buildStationListView(final String stationArray[]){
        ArrayAdapter arrayAdapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1, stationArray);
        stationListView = (ListView) findViewById(R.id.stationList);
        stationListView.setAdapter(arrayAdapter);

        stationListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
                Toast.makeText(MainActivity.this,
                        "City: " + stationArray[position] + " clicked",
                        Toast.LENGTH_SHORT).show();
                Intent callShops = new Intent(getApplicationContext(), ShopsActivity.class);
                //callShops.putExtra("City", stationArray[position]);
                startActivity(callShops); //Error
            }
        });
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
