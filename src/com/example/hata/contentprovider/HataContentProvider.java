package com.example.hata.contentprovider;

import android.content.ContentProvider;
import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Context;
import android.content.res.AssetManager;
import android.content.res.Resources;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteQueryBuilder;
import android.net.Uri;
import android.util.Log;
import com.example.hata.data.Dish;
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
 * Time: 21:50
 * To change this template use File | Settings | File Templates.
 */
public class HataContentProvider extends ContentProvider {
    private Resources res;
    private HataDatabase hataDb;
    private ContentResolver contentResolver;
    private AssetManager assetManager;
    private ArrayList<Dish> disheslist;
    private SQLiteDatabase db;
    @Override
    public boolean onCreate() {
        Context ctx = getContext();
        res = ctx.getResources();
        assetManager = ctx.getAssets();
        parseXML();
        hataDb = new HataDatabase(ctx);
        db = hataDb.getWritableDatabase();
        return true;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public Cursor query(Uri uri, String[] projection, String selection, String[] selectionArgs, String sortOrder) {
        SQLiteDatabase db = hataDb.getReadableDatabase();
        final int match = ContentDescriptor.URI_MATCHER.match(uri);
        SQLiteQueryBuilder builder = new SQLiteQueryBuilder();
        Cursor cursor;
        switch(match){
            // retrieve category list
            case ContentDescriptor.Category.PATH_TOKEN:{

                builder.setTables(ContentDescriptor.Category.NAME);
                cursor = builder.query(db, projection, selection, selectionArgs, null, null, sortOrder);
                cursor.setNotificationUri(getContext().getContentResolver(), uri);
                return cursor;
            }
            // retrieve category item
            case ContentDescriptor.Category.PATH_FOR_ID_TOKEN:{

                builder.setTables(ContentDescriptor.Category.NAME);
                builder.appendWhere(ContentDescriptor.Category.Cols.ID + "=" + uri.getLastPathSegment());
                cursor = builder.query(db, projection, selection, selectionArgs, null, null, sortOrder);
                cursor.setNotificationUri(getContext().getContentResolver(), uri);
                return cursor;
            }
            // retrieve dishes list
            case ContentDescriptor.Dish.PATH_TOKEN:{

                builder.setTables(ContentDescriptor.Dish.NAME);
                cursor = builder.query(db, projection, selection, selectionArgs, null, null, sortOrder);
                cursor.setNotificationUri(getContext().getContentResolver(), uri);
                return cursor;
            }

            default: return null;
        }
    }

    @Override
    public String getType(Uri uri) {
//        return null;  //To change body of implemented methods use File | Settings | File Templates.
        Log.d("COntProv", "********getType()");
        final int match = ContentDescriptor.URI_MATCHER.match(uri);
        switch(match){
            case ContentDescriptor.Category.PATH_TOKEN:
                return ContentDescriptor.Category.CONTENT_TYPE_DIR;
            case ContentDescriptor.Category.PATH_FOR_ID_TOKEN:
                return ContentDescriptor.Category.CONTENT_ITEM_TYPE;
            case ContentDescriptor.Dish.PATH_TOKEN:
                return ContentDescriptor.Dish.CONTENT_TYPE_DIR;
            case ContentDescriptor.Dish.PATH_FOR_ID_TOKEN:
                return ContentDescriptor.Dish.CONTENT_ITEM_TYPE;
            case ContentDescriptor.Dish.PATH_FOR_CAT_NAME_TOKEN:
                return ContentDescriptor.Dish.CONTENT_ITEM_TYPE;
            case ContentDescriptor.Dish.PATH_FOR_FAVORITE_TOKEN:
                return ContentDescriptor.Dish.FAVORITE_TYPE_DIR;
            default:
                throw new UnsupportedOperationException ("URI " + uri + " is not supported.");
        }
    }

    @Override
    public Uri insert(Uri uri, ContentValues values) {
        int token = ContentDescriptor.URI_MATCHER.match(uri);
        switch (token){
            case ContentDescriptor.Category.PATH_TOKEN:{
                long id = db.insert(ContentDescriptor.Category.NAME, null, values);
                getContext().getContentResolver().notifyChange(uri, null);
                return ContentDescriptor.Category.CONTENT_URI.buildUpon().appendPath(String.valueOf(id)).build();

            }

            case ContentDescriptor.Dish.PATH_TOKEN:{
                long id = db.insert(ContentDescriptor.Dish.NAME, null, values);
                getContext().getContentResolver().notifyChange(uri, null);
                return ContentDescriptor.Dish.CONTENT_URI.buildUpon().appendPath(String.valueOf(id)).build();
            }

            case ContentDescriptor.Order.PATH_TOKEN:{
                long id = db.insert(ContentDescriptor.Order.NAME, null, values);
                getContext().getContentResolver().notifyChange(uri, null);
                return ContentDescriptor.Order.CONTENT_URI.buildUpon().appendPath(String.valueOf(id)).build();

            }

            case ContentDescriptor.Cart.PATH_TOKEN:{
                long id = db.insert(ContentDescriptor.Cart.NAME, null, values);
                getContext().getContentResolver().notifyChange(uri, null);
                return ContentDescriptor.Cart.CONTENT_URI.buildUpon().appendPath(String.valueOf(id)).build();
            }

            case ContentDescriptor.Ordered_Cart.PATH_TOKEN:{
                long id = db.insert(ContentDescriptor.Ordered_Cart.NAME, null, values);
                getContext().getContentResolver().notifyChange(uri, null);
                return ContentDescriptor.Ordered_Cart.CONTENT_URI.buildUpon().appendPath(String.valueOf(id)).build();

            }

            case ContentDescriptor.User.PATH_TOKEN:{
                long id = db.insert(ContentDescriptor.User.NAME, null, values);
                getContext().getContentResolver().notifyChange(uri, null);
                return ContentDescriptor.User.CONTENT_URI.buildUpon().appendPath(String.valueOf(id)).build();
            }
            default: throw new SQLException("Failed to insert row into " + uri);

        }


    }

    @Override
    public int delete(Uri uri, String selection, String[] selectionArgs) {
        return 0;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public int update(Uri uri, ContentValues values, String selection, String[] selectionArgs) {
        return 0;  //To change body of implemented methods use File | Settings | File Templates.
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
