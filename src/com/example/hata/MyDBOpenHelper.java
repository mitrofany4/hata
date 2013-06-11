package com.example.hata;

import android.content.ContentValues;
import android.content.Context;
import android.content.res.AssetManager;
import android.content.res.Resources;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
import com.example.hata.contentprovider.ItemXMLHandler;
import org.xml.sax.InputSource;
import org.xml.sax.XMLReader;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import java.io.InputStream;
import java.util.ArrayList;


public class MyDBOpenHelper extends SQLiteOpenHelper {
    Resources res;
    private AssetManager assetManager;
    ArrayList<Dish> disheslist;

    private void insertSQL_categories(SQLiteDatabase db){

        String[] menu_items = res.getStringArray(R.array.menu_items);
//        TypedArray images = res.obtainTypedArray(R.array.cat_images);
        ContentValues insertValues = new ContentValues();

        for (String menu_item : menu_items) {
            insertValues.put(DBAdapter.CAT_NAME, menu_item);
            insertValues.put(DBAdapter.CAT_IMAGE, R.drawable.japane);
//            Drawable img = images.getDrawable(i);
//            insertValues.put("Image",img);
            long rowId = db.insert(DBAdapter.TABLE_CATEGORIES, null, insertValues);
            Log.d("INSERT INTO" + DBAdapter.TABLE_CATEGORIES + " Row # ", String.valueOf(rowId));
        }

    }

    private void insertSQL_dishes(SQLiteDatabase db){
        ContentValues insertValues = new ContentValues();

        for (Dish item : disheslist) {
            insertValues.put(DBAdapter.DISH_NAME, item.getName());
            insertValues.put(DBAdapter.DISH_DESCRIPTION, item.getDescription());
            insertValues.put(DBAdapter.DISH_PRICE, item.getPrice());
            insertValues.put(DBAdapter.DISH_WEIGHT, item.getWeight());
            insertValues.put(DBAdapter.DISH_CAT_ID, item.getCat_id());
            insertValues.put(DBAdapter.DISH_IMAGE, item.getImage());
            if (item.getFavorite()) insertValues.put(DBAdapter.DISH_FAV,1);
            else insertValues.put(DBAdapter.DISH_FAV,0);
            insertValues.put(DBAdapter.DISH_CATEGORY, item.getCategory());
            Long rowID = db.insert(DBAdapter.TABLE_DISHES, null, insertValues);
            Log.d("INSERT INTO" + DBAdapter.TABLE_DISHES + " Row # ", String.valueOf(rowID));
        }

    }

    public MyDBOpenHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
        res = context.getResources();

        assetManager = context.getAssets();
        parseXML();
    }

    private void parseXML(){

        try {
            InputStream inputStr = assetManager.open("dishes.xml");
            Log.w("Parse", "Start parsing");
            SAXParserFactory spf = SAXParserFactory.newInstance();
            SAXParser sp = spf.newSAXParser();
            XMLReader xr = sp.getXMLReader();
            ItemXMLHandler myXMLHandler = new ItemXMLHandler();
            xr.setContentHandler(myXMLHandler);
            InputSource inStream = new InputSource(inputStr);
            xr.parse(inStream);

            disheslist = myXMLHandler.getDisheslist();
        }
        catch (Exception e){
            Log.w("Parsing Error", e);
        }


    }


    @Override
    public void onCreate(SQLiteDatabase db) {
        //To change body of implemented methods use File | Settings | File Templates.

        db.execSQL(DBAdapter.CREATE_CAT);
        db.execSQL(DBAdapter.CREATE_DISH);
        insertSQL_categories(db);
        insertSQL_dishes(db);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        //To change body of implemented methods use File | Settings | File Templates.
        Log.w(MyDBOpenHelper.class.getName(), "Upgrading database from version "
                + oldVersion + " to " + newVersion
                + ", which will destroy all old data");
        db.execSQL("DROP TABLE IF EXISTS " + DBAdapter.TABLE_CATEGORIES);
        db.execSQL("DROP TABLE IF EXISTS " + DBAdapter.TABLE_DISHES);

        onCreate(db);
    }
}
