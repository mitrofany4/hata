package com.example.hata;


import android.content.ContentResolver;
import android.database.Cursor;
import android.graphics.Bitmap;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;

import android.widget.*;
import com.actionbarsherlock.app.ActionBar;
import com.actionbarsherlock.app.SherlockActivity;
import com.actionbarsherlock.view.Menu;
import com.actionbarsherlock.view.MenuItem;
import com.actionbarsherlock.view.Window;
import com.example.hata.contentprovider.ContentDescriptor;

import java.util.ArrayList;


/**
 * Created with IntelliJ IDEA.
 * User: Максим
 * Date: 03.03.13
 * Time: 19:52
 * To change this template use File | Settings | File Templates.
 */
public class DishesActivity extends SherlockActivity {
    private static final int SEARCH = 2222;
    private MenuItemAdapter adapter;
    private ArrayList<Dish> dish_items = new ArrayList<Dish>();
    private Cursor cursor;
    ContentResolver contentResolver;
    private ActionBar actionBar;
    String cat_name;
    String[] projection;
    String[] selectionArgs;
    @Override
    protected void onDestroy() {
        super.onDestroy();    //To change body of overridden methods use File | Settings | File Templates.
        //db.close();
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);    //To change body of overridden methods use File | Settings | File Templates.
        requestWindowFeature(Window.FEATURE_INDETERMINATE_PROGRESS);

        setContentView(R.layout.dishes);
        contentResolver = getContentResolver();
        Bundle extras = getIntent().getExtras();
        cat_name = extras.getString("category");
        ListView menuLV = (ListView) findViewById(R.id.itemsLV);
        adapter = new MenuItemAdapter(DishesActivity.this, R.layout.dish_item, dish_items);
        menuLV.setAdapter(adapter);
        actionBar = getSupportActionBar();

        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setDisplayUseLogoEnabled(false);
        setSupportProgressBarIndeterminateVisibility(true);

        populateDishesList(cat_name);
    }

    private void populateDishesList(String cat){
        String[] projection = {
                ContentDescriptor.Dish.Cols.ID,
                ContentDescriptor.Dish.Cols.NAME,
                ContentDescriptor.Dish.Cols.PRICE,
                ContentDescriptor.Dish.Cols.FAV
        };
        if (cat.equals("all")){
            cursor = contentResolver.query(ContentDescriptor.Dish.CONTENT_URI,null,null,null,null);
            actionBar.setTitle("Список блюд");
        } else {
            if (cat.equals("favorites")) {
                cursor =  getFavorites();
                actionBar.setTitle(R.string.favorites);
            } else {
                cursor =  getDishesByCategory(cat);
                actionBar.setTitle(cat);
            }
        }

        startManagingCursor(cursor);
        updateArray();

    }

    public Cursor getFavorites(){
        String[] projection = {
                ContentDescriptor.Dish.Cols.ID,
                ContentDescriptor.Dish.Cols.NAME,
                ContentDescriptor.Dish.Cols.PRICE,
                ContentDescriptor.Dish.Cols.FAV
        };
        String where = ContentDescriptor.Dish.Cols.FAV + " = 0";
        Cursor c = getContentResolver().query(ContentDescriptor.Dish.CONTENT_URI,null, where, null, null);
        return c;
    }

    public Cursor getDishesByCategory(String _cat){
/*        String[] projection = {
                ContentDescriptor.Dish.Cols.ID,
                ContentDescriptor.Dish.Cols.NAME,
                ContentDescriptor.Dish.Cols.PRICE,
                ContentDescriptor.Dish.Cols.FAV
        };*/
        selectionArgs = new String[]{ _cat };
        String where = ContentDescriptor.Dish.Cols.CATEGORY + " = ?";
        Cursor c = getContentResolver().query(ContentDescriptor.Dish.CONTENT_URI,projection, where, selectionArgs, null);
        return c;
    }

    @Override
    protected void onStop() {
        super.onStop();
        if (cursor != null && !cursor.isClosed()){
            cursor.close();
        }
    }

    private  void updateArray(){
        cursor.requery();
        dish_items.clear();
        if (cursor.moveToFirst()){
            do {
                long id = cursor.getInt(cursor.getColumnIndex(ContentDescriptor.Dish.Cols.ID));
                String name = cursor.getString(cursor.getColumnIndex(ContentDescriptor.Dish.Cols.NAME));
                String description = cursor.getString(cursor.getColumnIndex(ContentDescriptor.Dish.Cols.DESCRIPTION));
                Double price = cursor.getDouble(cursor.getColumnIndex(ContentDescriptor.Dish.Cols.PRICE));
                int weight = cursor.getInt(cursor.getColumnIndex(ContentDescriptor.Dish.Cols.WEIGHT));
                String imagestr = cursor.getString(cursor.getColumnIndex(ContentDescriptor.Dish.Cols.IMAGE));
                String path = "items/"+ imagestr;

                Bitmap image = ContentDescriptor.getBitmapFromAsset(this, path);
                String cat_name = cursor.getString(cursor.getColumnIndex(ContentDescriptor.Dish.Cols.CATEGORY));

                Dish newitem = new Dish(id,name, price, image, description, weight, cat_name, false);
                newitem.setImage(imagestr);
                dish_items.add(newitem);
            } while (cursor.moveToNext());
        }



        adapter.notifyDataSetChanged();

    }

    public ArrayList<Dish> getDish_items() {
        return dish_items;
    }

    @Override
    protected void onResume() {
        super.onResume();    //To change body of overridden methods use File | Settings | File Templates.
        //populateDishesList(cat_name);
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

                adapter.getFilter().filter(s.toString());

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
}
