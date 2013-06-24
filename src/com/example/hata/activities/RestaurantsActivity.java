package com.example.hata.activities;

import android.content.Intent;
import android.database.Cursor;
import android.view.View;
import android.widget.*;
import com.actionbarsherlock.app.ActionBar;
import com.actionbarsherlock.app.SherlockActivity;
import com.actionbarsherlock.view.MenuItem;
import com.example.hata.R;
import com.example.hata.contentprovider.ContentDescriptor;
import com.example.hata.data.Restaurant;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Максим on 23.06.13.
 */
public class RestaurantsActivity extends SherlockActivity {

    String[] projection = null;
    String[] selectionArgs = null;
    List<Restaurant> restaurants = new ArrayList<Restaurant>();
    Cursor cursor;
    private Spinner cities;
    private ListView restaurantsLV;
    ArrayAdapter<String> adapter;
    @Override
    protected void onStart() {
        super.onStart();

        /************ActionBar*******************/

        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayShowTitleEnabled(false);
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setDisplayUseLogoEnabled(true);

        setContentView(R.layout.restaurants);

        /***
          *          Spiner
          *
         */
        ArrayAdapter <?> Spadapter = ArrayAdapter.createFromResource(this, R.array.cities, R.layout.sherlock_spinner_item);
        Spadapter.setDropDownViewResource(R.layout.sherlock_spinner_dropdown_item);
        cities = (Spinner) findViewById(R.id.CatSpinner);
        cities.setAdapter(Spadapter);



        /*************************************/

        restaurantsLV = (ListView) findViewById(R.id.CatLV);
        String[] names = new String[0];
        for (int i=0; i<restaurants.size(); i++){
            names[i]=restaurants.get(i).getName();
        }

        adapter = new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,names);
        restaurantsLV.setAdapter(adapter);
        restaurantsLV.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                long Id = restaurants.get(position).getId();
                Intent intent = new Intent(RestaurantsActivity.this, RestaurantActivity.class);
                intent.putExtra("ID", Id);
                startActivity(intent);
            }
        });

        cities.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                String[] choice = getResources().getStringArray(R.array.cities);

                if(position==0){
                    cursor = getAllRestaurants();
                }
                else cursor = getRestaurantsByCity(choice[position]);
                restaurants = CursorToRestaurants(cursor);
                adapter.notifyDataSetChanged();
            }
        });
    }

    public Cursor getRestaurantsByCity(String city){
        selectionArgs = new String[]{ city };
        String where = ContentDescriptor.Restaurant.Cols.CITY + " = ?";
        Cursor c = getContentResolver().query(ContentDescriptor.Restaurant.CONTENT_URI,projection, where, selectionArgs, null);
        return c;
    }

    public Cursor getAllRestaurants(){
        Cursor c = getContentResolver().query(ContentDescriptor.Restaurant.CONTENT_URI,projection, null, null, null);
        return c;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        cursor.close();
    }

    public List<Restaurant> CursorToRestaurants(Cursor c){

        List<Restaurant> rests = new ArrayList<Restaurant>();
        while (c.moveToNext()){
            Restaurant r = new Restaurant();
            r.setId(c.getLong(c.getColumnIndex(ContentDescriptor.Restaurant.Cols.ID)));
            r.setName(c.getString(c.getColumnIndex(ContentDescriptor.Restaurant.Cols.NAME)));
            rests.add(r);
        }
        c.close();
        return rests;
    }

    @Override
    public boolean onMenuItemSelected(int featureId, MenuItem item) {
        int itemId = item.getItemId();
        switch (itemId) {
            case android.R.id.home:
//                Toast.makeText(this, "home pressed", Toast.LENGTH_LONG).show();
                finish();
                break;
        }

        return true;
    }
}
