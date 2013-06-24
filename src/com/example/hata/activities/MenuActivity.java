package com.example.hata.activities;


import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.View;
import android.widget.*;
import com.actionbarsherlock.app.ActionBar;
import com.actionbarsherlock.app.SherlockActivity;
import com.actionbarsherlock.view.Menu;
import com.actionbarsherlock.view.MenuItem;
import com.example.hata.activities.DishesActivity;
import com.example.hata.R;


/**
 * Created with IntelliJ IDEA.
 * User: Максим
 * Date: 01.03.13
 * Time: 15:54
 * To change this template use File | Settings | File Templates.
 */
public class MenuActivity extends SherlockActivity {

    private ArrayAdapter<String> aa;

    private ListView lv;

    private TextView title;
    private static final int REQUEST_CODE = 10;
    private String[] menu_items;
//    private DBAdapter dish_DBAdapter;
    ActionBar actionBar;
    Typeface tf;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);    //To change body of overridden methods use File | Settings | File Templates.

        setContentView(R.layout.menu);
        tf = Typeface.createFromAsset(getAssets(), "fonts/HanZi.ttf");
        actionBar = getSupportActionBar();
        actionBar.setTitle(R.string.menu);
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setDisplayUseLogoEnabled(false);
        actionBar.setDisplayShowTitleEnabled(true);


//        title.setTypeface(tf);
        lv = (ListView) findViewById(R.id.menuLV);

        Resources res = getResources();

        menu_items = res.getStringArray(R.array.menu_items);

        aa = new ArrayAdapter<String>(this, R.layout.rowitem, R.id.label, menu_items);

        lv.setAdapter(aa);

        lv.setOnItemClickListener(new AdapterView.OnItemClickListener(){

            public void onItemClick(AdapterView <?> parent, View itemClicked, int position, long id){

                Intent intent = new Intent(MenuActivity.this, DishesActivity.class);
                intent.putExtra("category", menu_items[position]);
                startActivity(intent);
            }
        })  ;

    }

    @Override
    public boolean onMenuItemSelected(int featureId, MenuItem item) {
        int itemId = item.getItemId();
        switch (itemId) {
            case android.R.id.home:
                finish();
                break;
        }

        return true;
    }

}
