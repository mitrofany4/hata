package com.example.hata.contentprovider;

import android.content.ContentValues;
import android.content.Context;
import android.content.res.AssetManager;
import android.content.res.Resources;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
import com.example.hata.DBAdapter;
import com.example.hata.data.Dish;
import com.example.hata.R;
import org.xml.sax.InputSource;
import org.xml.sax.XMLReader;

import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import java.io.InputStream;
import java.util.ArrayList;

/**
 * Created with IntelliJ IDEA.
 * User: Максим
 * Date: 14.05.13
 * Time: 21:47
 * To change this template use File | Settings | File Templates.
 */
public class HataDatabase extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "yaponahata.sqlite";
    private static final int DATABASE_VERSION = 1;
    private Resources res;
    ArrayList<Dish> disheslist;
    private AssetManager assetManager;
    public HataDatabase(Context ctx){
        super(ctx, DATABASE_NAME, null, DATABASE_VERSION);
        res = ctx.getResources();
        assetManager = ctx.getAssets();
        parseXML();
    }
    /**
     * What to do when the database is created the first time
     */

    @Override
    public void onCreate(SQLiteDatabase db) {
        //To change body of implemented methods use File | Settings | File Templates.
        db.execSQL("CREATE TABLE " + ContentDescriptor.Category.NAME +" (" +
                ContentDescriptor.Category.Cols.ID + " INTEGER PRIMARY KEY autoincrement, " +
                ContentDescriptor.Category.Cols.NAME + " TEXT not null, " +
                ContentDescriptor.Category.Cols.IMAGE + " INTEGER);");

        db.execSQL("CREATE TABLE " + ContentDescriptor.Dish.NAME + " (" +
                ContentDescriptor.Dish.Cols.ID +" INTEGER PRIMARY KEY autoincrement, " +
                ContentDescriptor.Dish.Cols.NAME + " TEXT not null, " +
                ContentDescriptor.Dish.Cols.IMAGE +" INTEGER not null, " +
                ContentDescriptor.Dish.Cols.WEIGHT + " INTEGER not null, " +
                ContentDescriptor.Dish.Cols.DESCRIPTION + " TEXT, " +
                ContentDescriptor.Dish.Cols.PRICE + " REAL, " +
                ContentDescriptor.Dish.Cols.CAT_ID + " INTEGER, " +
                ContentDescriptor.Dish.Cols.CATEGORY + " TEXT not null, " +
                ContentDescriptor.Dish.Cols.FAV +" INTEGER not null);");

        db.execSQL("CREATE TABLE " + ContentDescriptor.Restaurant.NAME + " (" +
                ContentDescriptor.Restaurant.Cols.ID + " INTEGER PRIMARY KEY autoincrement, " +
                ContentDescriptor.Restaurant.Cols.ADDRESS + " TEXT not null, " +
                ContentDescriptor.Restaurant.Cols.CITY + " TEXT not null, " +
                ContentDescriptor.Restaurant.Cols.NAME + " TEXT not null, " +
                ContentDescriptor.Restaurant.Cols.DESCRIPTION + " TEXT not null, " +
                ContentDescriptor.Restaurant.Cols.EMAIL + " TEXT not null, " +
                ContentDescriptor.Restaurant.Cols.IMAGE + " TEXT not null, " +
                ContentDescriptor.Restaurant.Cols.LATITUDE + " REAL, " +
                ContentDescriptor.Restaurant.Cols.LONGITUDE + " REAL, " +
                ContentDescriptor.Restaurant.Cols.PHONE1 + " TEXT, " +
                ContentDescriptor.Restaurant.Cols.PHONE2 + " TEXT, " +
                ContentDescriptor.Restaurant.Cols.PHONE3 + " TEXT, " +
                ContentDescriptor.Restaurant.Cols.SCHEDULE + " TEXT not null);"

        );
/*
        db.execSQL("CREATE TABLE " + ContentDescriptor.Order.NAME + " (" +
                ContentDescriptor.Order.Cols.ID + " INTEGER PRIMARY KEY autoincrement, " +
                ContentDescriptor.Order.Cols.FIRSTNAME + " TEXT not null, " +
                ContentDescriptor.Order.Cols.LASTNAME +" TEXT not null, " +
                ContentDescriptor.Order.Cols.CITY +" TEXT not null, " +
                ContentDescriptor.Order.Cols.ORDER_DATE + " INTEGER not null, " +
                ContentDescriptor.Order.Cols.PHONE + " TEXT not null, " +
                ContentDescriptor.Order.Cols.PICKUPDATE + " INTEGER not null, " +
                ContentDescriptor.Order.Cols.PICKUPTIME + " INTEGER not null, " +
                ContentDescriptor.Order.Cols.USER_ID + " INTEGER not null);");

        db.execSQL("CREATE TABLE" + ContentDescriptor.Cart.NAME +" (" +
                ContentDescriptor.Cart.Cols.ID + " INTEGER PRIMARY KEY autoincrement, " +
                ContentDescriptor.Cart.Cols.DISH_ID + " INTEGER not null, " +
                ContentDescriptor.Cart.Cols.USER_ID + " INTEGER not null, " +
                ContentDescriptor.Cart.Cols.QUANTITY + " INTEGER not null);");

        db.execSQL("CREATE TABLE" + ContentDescriptor.Ordered_Cart.NAME +" (" +
                ContentDescriptor.Ordered_Cart.Cols.ID + " INTEGER PRIMARY KEY autoincrement, " +
                ContentDescriptor.Ordered_Cart.Cols.DISH_ID + " INTEGER not null, " +
                ContentDescriptor.Ordered_Cart.Cols.PRICE + " REAL, " +
                ContentDescriptor.Ordered_Cart.Cols.USER_ID + " INTEGER not null, " +
                ContentDescriptor.Ordered_Cart.Cols.QUANTITY + " INTEGER not null);");
*/
/*        db.execSQL("CREATE TABLE" + ContentDescriptor.User.NAME +" (" +
                ContentDescriptor.User.Cols.ID + " INTEGER PRIMARY KEY autoincrement, " +
                ContentDescriptor.User.Cols.FIRSTNAME + " TEXT not null, " +
                ContentDescriptor.User.Cols.LASTNAME +" TEXT not null, " +
                ContentDescriptor.User.Cols.PHONE + " TEXT not null, " +
                ContentDescriptor.User.Cols.DISCOUNT + " INTEGER not null, " +
                ContentDescriptor.User.Cols.CITY +" TEXT not null, " +
                ContentDescriptor.User.Cols.CARD + " INTEGER not null, " +
                ContentDescriptor.User.Cols.MONEY + " REAL, " +
                ContentDescriptor.User.Cols.IMAGE + " TEXT not null);");*/
        insertSQL_dishes(db);
    }
    /**
     * What to do when the database version changes: drop table and recreate
     */
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        //To change body of implemented methods use File | Settings | File Templates.
        if(oldVersion < newVersion){
            db.execSQL("DROP TABLE IF EXISTS " + ContentDescriptor.Category.NAME);
            db.execSQL("DROP TABLE IF EXISTS " + ContentDescriptor.Dish.NAME);
            db.execSQL("DROP TABLE IF EXISTS " + ContentDescriptor.Cart.NAME);
            db.execSQL("DROP TABLE IF EXISTS " + ContentDescriptor.Ordered_Cart.NAME);
            db.execSQL("DROP TABLE IF EXISTS " + ContentDescriptor.Order.NAME);
            db.execSQL("DROP TABLE IF EXISTS " + ContentDescriptor.User.NAME);
            db.execSQL("DROP TABLE IF EXISTS " + ContentDescriptor.Restaurant.NAME);

            onCreate(db);
        }
    }
    private void insertSQL_dishes(SQLiteDatabase db){
        ContentValues insertValues = new ContentValues();

        for (Dish item : disheslist) {
            insertValues.put(ContentDescriptor.Dish.Cols.NAME, item.getName());
            insertValues.put(ContentDescriptor.Dish.Cols.DESCRIPTION, item.getDescription());
            insertValues.put(ContentDescriptor.Dish.Cols.PRICE, item.getPrice());
            insertValues.put(ContentDescriptor.Dish.Cols.WEIGHT, item.getWeight());
            insertValues.put(ContentDescriptor.Dish.Cols.CAT_ID, item.getCat_id());
            insertValues.put(ContentDescriptor.Dish.Cols.IMAGE, item.getImage());
            insertValues.put(ContentDescriptor.Dish.Cols.FAV,0);
            insertValues.put(ContentDescriptor.Dish.Cols.CATEGORY, item.getCategory());
            Long rowID = db.insert(ContentDescriptor.Dish.NAME, null, insertValues);
            Log.d("INSERT INTO" + ContentDescriptor.Dish.NAME + " Row # ", String.valueOf(rowID));
        }

    }

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

}
