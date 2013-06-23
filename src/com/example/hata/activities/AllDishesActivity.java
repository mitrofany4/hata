package com.example.hata.activities;

import android.database.Cursor;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;
import com.actionbarsherlock.app.ActionBar;
import com.actionbarsherlock.app.SherlockActivity;
import com.actionbarsherlock.view.Menu;
import com.actionbarsherlock.view.MenuItem;
import com.example.hata.DBAdapter;
import com.example.hata.MenuItemAdapter;
import com.example.hata.R;
import com.example.hata.data.Dish;

import java.util.ArrayList;

/**
 * Created with IntelliJ IDEA.
 * User: Максим
 * Date: 13.05.13
 * Time: 1:27
 * To change this template use File | Settings | File Templates.
 */
public class AllDishesActivity extends SherlockActivity {
    private static final int SEARCH = 2;
    private ArrayAdapter<Dish> adapter;
    private ArrayList<Dish> dish_items = new ArrayList<Dish>();
    private DBAdapter db;
    private Cursor cursor;


    @Override
    protected void onDestroy() {
        super.onDestroy();    //To change body of overridden methods use File | Settings | File Templates.
//        db.close();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);    //To change body of overridden methods use File | Settings | File Templates.
        setContentView(R.layout.dishes);

        ListView menuLV = (ListView) findViewById(R.id.itemsLV);
        adapter = new MenuItemAdapter(AllDishesActivity.this, R.layout.dish_item, dish_items);
        menuLV.setAdapter(adapter);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle("Список блюд");
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setDisplayUseLogoEnabled(false);

        db = new DBAdapter(this);
        db.open();
        populateDishesList();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        menu.add(0,SEARCH,0,"Search")
                .setIcon(R.drawable.searchtool_48)

                .setActionView(R.layout.searchfield)
                .setShowAsAction(MenuItem.SHOW_AS_ACTION_ALWAYS |
                        MenuItem.SHOW_AS_ACTION_COLLAPSE_ACTION_VIEW);




        final EditText editText = (EditText) menu.findItem(SEARCH).getActionView();
        editText.addTextChangedListener(textWatcher);

        MenuItem menuItem = menu.findItem(SEARCH);
        menuItem.setOnActionExpandListener(new MenuItem.OnActionExpandListener() {
            @Override
            public boolean onMenuItemActionExpand(MenuItem item) {
                editText.clearFocus();
                return true;  //To change body of implemented methods use File | Settings | File Templates.
            }

            @Override
            public boolean onMenuItemActionCollapse(MenuItem item) {
                return true;  //To change body of implemented methods use File | Settings | File Templates.
            }
        });
        return true;
    }

    @Override
    public boolean onMenuItemSelected(int featureId, MenuItem item) {
        int itemId = item.getItemId();
        switch (itemId) {
            case android.R.id.home:
                Toast.makeText(this, "home pressed", Toast.LENGTH_LONG).show();
                finish();
                break;

        }

        return true;
    }

    private TextWatcher textWatcher = new TextWatcher() {

        @Override
        public void onTextChanged(CharSequence s, int start, int before,
                                  int count) {

            adapter.getFilter().filter(s);

        }

        @Override
        public void beforeTextChanged(CharSequence s, int start, int count,
                                      int after) {
            // TODO Auto-generated method stub

        }

        @Override
        public void afterTextChanged(Editable s) {
            // TODO Auto-generated method stub

        }
    };

    private void populateDishesList(){
        cursor = db.getAllDishesCursor();
        startManagingCursor(cursor);
        updateArray();

    }

    private  void updateArray(){
        cursor.requery();
        dish_items.clear();
        if (cursor.moveToFirst()){
            do {
                long id = cursor.getInt(cursor.getColumnIndex(DBAdapter.DISH_ID));
                String name = cursor.getString(cursor.getColumnIndex(DBAdapter.DISH_NAME));
                String description = cursor.getString(cursor.getColumnIndex(DBAdapter.DISH_DESCRIPTION));
                Double price = cursor.getDouble(cursor.getColumnIndex(DBAdapter.DISH_PRICE));
                int weight = cursor.getInt(cursor.getColumnIndex(DBAdapter.DISH_WEIGHT));
                String imagestr = cursor.getString(cursor.getColumnIndex(DBAdapter.DISH_IMAGE));
                String path = "items/"+ imagestr;
                Bitmap image = DBAdapter.getBitmapFromAsset(this, path);
                String cat_name = cursor.getString(cursor.getColumnIndex(DBAdapter.DISH_CATEGORY));
                Dish newitem = new Dish(id,name, price, image, description, weight, cat_name, false);
                newitem.setImage(imagestr);
                dish_items.add(newitem);
            } while (cursor.moveToNext());
        }

        if (cursor != null && !cursor.isClosed()){
            cursor.close();
        }

        adapter.notifyDataSetChanged();

    }

    public ArrayList<Dish> getDish_items() {
        return dish_items;
    }

}
