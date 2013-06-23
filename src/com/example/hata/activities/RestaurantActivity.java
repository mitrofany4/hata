package com.example.hata.activities;

import android.content.ContentResolver;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import com.actionbarsherlock.app.ActionBar;
import com.actionbarsherlock.app.SherlockActivity;
import com.actionbarsherlock.view.MenuItem;
import com.example.hata.R;

/**
 * Created by Максим on 21.05.13.
 */
public class RestaurantActivity extends SherlockActivity {
    private Cursor cursor;
    private ContentResolver contentResolver;
    private ActionBar actionBar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.restaurant_layout);
        contentResolver = getContentResolver();
        Bundle extras = getIntent().getExtras();

        /************ActionBar*******************/

        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayShowTitleEnabled(false);
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setDisplayUseLogoEnabled(true);

        /*************Views********************/

        ImageButton mapbutton = (ImageButton) findViewById(R.id.map_btn);
        ImageButton homecallbutton = (ImageButton) findViewById(R.id.HomeCallButton);
        ImageButton mtscallbutton = (ImageButton) findViewById(R.id.MTSCallButton);
        ImageButton kievstarcallbutton = (ImageButton) findViewById(R.id.KyivstarCallButton);

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
}
