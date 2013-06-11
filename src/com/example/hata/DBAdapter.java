package com.example.hata;

import android.content.Context;
import android.content.res.AssetManager;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;

import java.io.IOException;
import java.io.InputStream;

/**
 * Created with IntelliJ IDEA.
 * User: Максим
 * Date: 05.03.13
 * Time: 18:09
 * To change this template use File | Settings | File Templates.
 */
public class DBAdapter  {

    private static final String DATABASE_NAME = "hata2.db";
    private static final int DATABASE_VERSION = 1;
    private SQLiteDatabase db;
    private MyDBOpenHelper dbHelper;
    private final Context context;


    //  ***************CATEGORIES*********************************

    public static final String CAT_ID = "_id";
    public static final String TABLE_CATEGORIES = "Categories";
    public static final String CAT_NAME = "Name";
    public static final String CAT_IMAGE = "Image";

    public static final String CREATE_CAT = "CREATE TABLE " + TABLE_CATEGORIES + " (" + CAT_ID +
            " INTEGER PRIMARY KEY autoincrement, " + CAT_NAME + " TEXT not null, " + CAT_IMAGE + " INTEGER);";


//  **********DISHES**************************************

    public static final String DISH_ID = "_id";
    public static final String DISH_NAME = "Name";
    public static final String DISH_IMAGE = "Image";
    public static final String TABLE_DISHES = "Dishes";
    public static final String DISH_DESCRIPTION = "Description";
    public static final String DISH_PRICE = "Price";
    public static final String DISH_WEIGHT = "Weight";
    public static final String DISH_CAT_ID = "Categories_id";
    public static final String DISH_CATEGORY = "Category";
    public static final String DISH_FAV = "Favorite";

    public static final String CREATE_DISH = "CREATE TABLE " + TABLE_DISHES + " (" + DISH_ID +
            " INTEGER PRIMARY KEY autoincrement, " + DISH_NAME + " TEXT not null, " + DISH_IMAGE +
            " INTEGER not null, " +DISH_WEIGHT + " INTEGER not null, " + DISH_DESCRIPTION + " TEXT, " + DISH_PRICE +
            " REAL, " + DISH_CAT_ID + " INTEGER, " + DISH_CATEGORY + " TEXT not null, "+DISH_FAV+" INTEGER not null);";

//  -------------------------------------------------------------------

    public DBAdapter(Context context) {
        this.context = context;
        dbHelper = new MyDBOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    public void close(){
//        db.close();
    }

    public void open() throws SQLiteException{
        try {
            db = dbHelper.getWritableDatabase();
        }
        catch (SQLiteException e){
            db = dbHelper.getReadableDatabase();
            Log.w("DataBase open readable", e);

        }
    }

    public Cursor getAllCategoriesCursor(){
        return db.query(TABLE_CATEGORIES, new String[]{CAT_ID, CAT_NAME, CAT_IMAGE}, null, null, null, null, null);
    }

    public Cursor getAllDishesCursor(){
        return db.query(TABLE_DISHES, new String[]{DISH_ID,DISH_NAME, DISH_PRICE, DISH_IMAGE, DISH_DESCRIPTION, DISH_WEIGHT, DISH_CAT_ID, DISH_CATEGORY,DISH_FAV},
                null, null, null, null, null);
    }

    public Cursor getDishesByCatID(long catID){
        String catID_str = String.valueOf(catID);
        Cursor c = db.query(TABLE_DISHES, new String[]{DISH_ID,DISH_NAME, DISH_PRICE, DISH_IMAGE, DISH_DESCRIPTION, DISH_WEIGHT, DISH_CAT_ID, DISH_CATEGORY, DISH_FAV},
                DISH_CAT_ID + " = " + catID, null, null, null, null);
        return c;

    }

    public Cursor getFavoriteDishes(){
        return db.query(TABLE_DISHES, new String[]{DISH_ID,DISH_NAME, DISH_PRICE, DISH_IMAGE, DISH_DESCRIPTION, DISH_WEIGHT, DISH_CAT_ID, DISH_CATEGORY, DISH_FAV},
                DISH_FAV + " = 1", null, null, null, null);
    }

    public static Bitmap getBitmapFromAsset(Context context, String strName) {
        AssetManager assetManager = context.getAssets();

        InputStream istr;
        Bitmap bitmap = null;
        try {
            istr = assetManager.open(strName);
            bitmap = BitmapFactory.decodeStream(istr);
        } catch (IOException e) {
            return null;
        }

        return bitmap;
    }

    public Dish getDishByID(int ID) throws SQLiteException{
        String ID_str = Integer.toString(ID);
        Cursor cursor = db.query(TABLE_DISHES, new String[]{DISH_ID,DISH_NAME, DISH_PRICE, DISH_IMAGE, DISH_DESCRIPTION, DISH_WEIGHT, DISH_CAT_ID, DISH_FAV},
                DISH_ID+"=?", new String[] {ID_str}, null, null, null);
        if ((cursor.getCount() == 0) || !cursor.moveToFirst()) {
            throw new SQLException("No dishes found for row: " + ID);
        }

        long id = cursor.getLong(cursor.getColumnIndex(DISH_ID));
        String name = cursor.getString(cursor.getColumnIndex(DISH_NAME));
        double price = cursor.getDouble(cursor.getColumnIndex(DISH_PRICE));
        String image = cursor.getString(cursor.getColumnIndex(DBAdapter.DISH_IMAGE));
        String path = "items/"+image;

        Bitmap bmp = getBitmapFromAsset(context, path);

        String description = cursor.getString(cursor.getColumnIndex(DISH_DESCRIPTION));
        int weight = cursor.getInt(cursor.getColumnIndex(DISH_WEIGHT));
//        int cat_id = cursor.getInt(cursor.getColumnIndex(DISH_CAT_ID));
        String cat_name = cursor.getString(cursor.getColumnIndex(DISH_CATEGORY));
        Boolean isFavorite=(cursor.getInt(cursor.getColumnIndex(DISH_FAV))==1);

        Dish result = new Dish(id,name, price, bmp, description, weight,cat_name, isFavorite);
        result.setImage(image);
        return result;
    }

    public Cursor getDishesByCatName(String cat_name){

        return db.query(TABLE_DISHES, new String[]{DISH_ID,DISH_NAME, DISH_PRICE, DISH_IMAGE, DISH_DESCRIPTION, DISH_WEIGHT, DISH_CAT_ID, DISH_CATEGORY,DISH_FAV},
                DISH_CATEGORY+"=?", new String[] {cat_name}, null, null, null);

    }

    public Category getCategoryByID(int ID){
        String ID_str = Integer.toString(ID);
        Cursor cursor = db.query(TABLE_CATEGORIES, new String[]{CAT_ID, CAT_NAME, CAT_IMAGE},
                CAT_ID+"=?", new String[]{ID_str}, null, null, null);

        String name = cursor.getString(cursor.getColumnIndex(CAT_NAME));
        int image = cursor.getInt(cursor.getColumnIndex(CAT_IMAGE));

        Category result = new Category(name,image);

        return result;
    }



}
