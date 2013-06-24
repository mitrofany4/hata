package com.example.hata.activities;

import android.content.ContentResolver;
import android.content.ContentUris;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import com.actionbarsherlock.app.ActionBar;
import com.actionbarsherlock.app.SherlockActivity;
import com.actionbarsherlock.internal.view.menu.ActionMenuItemView;
import com.actionbarsherlock.view.MenuItem;
import com.example.hata.R;
import com.example.hata.contentprovider.ContentDescriptor;
import com.example.hata.data.Restaurant;

/**
 * Created by Максим on 21.05.13.
 */
public class RestaurantActivity extends SherlockActivity {
    private Cursor cursor;
    private ContentResolver contentResolver;
    private ActionBar actionBar;
    Restaurant restaurant;
    long id;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.restaurant_layout);
        contentResolver = getContentResolver();
        Bundle extras = getIntent().getExtras();
        id = extras.getLong("ID");

        /************ActionBar*******************/

        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayShowTitleEnabled(false);
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setDisplayUseLogoEnabled(true);

        restaurant = getRestaurantbyID(id);

        /*************Views********************/

        ImageButton mapbutton = (ImageButton) findViewById(R.id.map_btn);
        ImageButton homecallbutton = (ImageButton) findViewById(R.id.HomeCallButton);
        ImageButton mtscallbutton = (ImageButton) findViewById(R.id.MTSCallButton);
        ImageButton kievstarcallbutton = (ImageButton) findViewById(R.id.KyivstarCallButton);

        homecallbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String number = "tel:"+restaurant.getPhone1();
                Intent intent = new Intent(Intent.ACTION_CALL,Uri.parse(number));
                startActivity(intent);
            }
        });

        mtscallbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String number = "tel:"+restaurant.getPhone2();
                Intent intent = new Intent(Intent.ACTION_CALL,Uri.parse(number));
                startActivity(intent);
            }
        });

        kievstarcallbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String number = "tel:"+restaurant.getPhone3();
                Intent intent = new Intent(Intent.ACTION_CALL,Uri.parse(number));
                startActivity(intent);
            }
        });

        /*************/


    }

    public void OnMapButtonClick (View v){

        Intent intent = new Intent(getApplicationContext(), AddressActivity.class);
        startActivity(intent);
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


    Restaurant getRestaurantbyID(long ID){
        Restaurant rest = new Restaurant();

        Uri singleUri = ContentUris.withAppendedId(ContentDescriptor.Restaurant.CONTENT_URI, ID);

        Cursor c = getContentResolver().query(singleUri,null,null,null,null);

        rest.setId(c.getLong(c.getColumnIndex(ContentDescriptor.Restaurant.Cols.ID)));
        rest.setName(c.getString(c.getColumnIndex(ContentDescriptor.Restaurant.Cols.NAME)));
        rest.setCity(c.getString(c.getColumnIndex(ContentDescriptor.Restaurant.Cols.CITY)));
        rest.setAddress(c.getString(c.getColumnIndex(ContentDescriptor.Restaurant.Cols.ADDRESS)));
        rest.setDescription(c.getString(c.getColumnIndex(ContentDescriptor.Restaurant.Cols.DESCRIPTION)));
        rest.setEmail(c.getString(c.getColumnIndex(ContentDescriptor.Restaurant.Cols.EMAIL)));
        String path = "restaurants/"+c.getString(c.getColumnIndex(ContentDescriptor.Restaurant.Cols.IMAGE));
        Bitmap image = ContentDescriptor.getBitmapFromAsset(this, path);
        rest.setImage(image);
        rest.setSchedule(c.getString(c.getColumnIndex(ContentDescriptor.Restaurant.Cols.SCHEDULE)));
        rest.setPhone1(c.getString(c.getColumnIndex(ContentDescriptor.Restaurant.Cols.PHONE1)));
        rest.setPhone2(c.getString(c.getColumnIndex(ContentDescriptor.Restaurant.Cols.PHONE2)));
        rest.setPhone3(c.getString(c.getColumnIndex(ContentDescriptor.Restaurant.Cols.PHONE3)));
        rest.setLatitude(c.getDouble(c.getColumnIndex(ContentDescriptor.Restaurant.Cols.LATITUDE)));
        rest.setLongitude(c.getDouble(c.getColumnIndex(ContentDescriptor.Restaurant.Cols.LONGITUDE)));
        return rest;

    }
}
