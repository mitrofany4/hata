package com.example.hata.activities;

import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import com.actionbarsherlock.app.ActionBar;
import com.actionbarsherlock.app.SherlockActivity;
import com.example.hata.R;


public class MainActivity extends SherlockActivity {
    /**
     * Called when the activity is first created.
     */

    Button menu_button,fav_button, mapIB, newsIB, smileIB;
    public Typeface tf;
    ActionBar actionBar;
    Button profile_button;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);    //To change body of overridden methods use File | Settings | File Templates.
        setContentView(R.layout.main);
        actionBar = getSupportActionBar();
        actionBar.setDisplayUseLogoEnabled(true);
        actionBar.setDisplayHomeAsUpEnabled(false);
//        actionBar.setDisplayShowHomeEnabled(false);
        actionBar.setDisplayShowTitleEnabled(false);
        actionBar.setLogo(R.drawable.hata_logo);
        menu_button = (Button) findViewById(R.id.menu_btn);
        fav_button = (Button) findViewById(R.id.fav_btn);
        newsIB = (Button) findViewById(R.id.news_btn);
        mapIB = (Button) findViewById(R.id.map_btn);
        profile_button = (Button) findViewById(R.id.profile_btn);
//        smileIB = (Button) findViewById(R.id.feeds_btn);

//        actionBar = getSupportActionBar();
//        actionBar.hide();


        tf = Typeface.createFromAsset(getAssets(), "fonts/HanZi.ttf");
    }

    public void menu_button_Click(View v) {

        Intent  intent = new Intent(getApplicationContext(), MenuActivity.class);
        startActivity(intent);

    }

    public void profile_Click(View v) {

        Intent  intent = new Intent(getApplicationContext(), ProfileActivity.class);
        startActivity(intent);

    }

    public void fav_button_Click(View v) {

        Intent  intent = new Intent(getApplicationContext(), DishesActivity.class);
        intent.putExtra("category", "favorites");
        startActivity(intent);

    }

    public void all_button_Click(View v){
        Intent  intent = new Intent(getApplicationContext(), DishesActivity.class);
        intent.putExtra("category", "all");
        startActivity(intent);

    }

    public void restaurant_Click(View v){
        Intent  intent = new Intent(getApplicationContext(), RestaurantsActivity.class);
        startActivity(intent);

    }
}
