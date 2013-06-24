package com.example.hata.contentprovider;

import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Context;
import android.content.UriMatcher;
import android.content.res.AssetManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.provider.BaseColumns;
import com.example.hata.R;

import java.io.IOException;
import java.io.InputStream;

/**
 * Created with IntelliJ IDEA.
 * User: Максим
 * Date: 14.05.13
 * Time: 21:55
 * To change this template use File | Settings | File Templates.
 */
public class        ContentDescriptor {
    // utility variables
    public static final String AUTHORITY = "com.example.hata.contentprovider";
    private static final Uri BASE_URI = Uri.parse("content://" + AUTHORITY);
    public static final UriMatcher URI_MATCHER = buildUriMatcher();

    private ContentDescriptor(){};

    // register identifying URIs for Restaurant entity
    // the TOKEN value is associated with each URI registered
    private static  UriMatcher buildUriMatcher() {
        final UriMatcher matcher = new UriMatcher(UriMatcher.NO_MATCH);
        final String authority = AUTHORITY;

/************************************ Category ***********************************/
        matcher.addURI(authority, Category.PATH, Category.PATH_TOKEN);
        matcher.addURI(authority, Category.PATH_FOR_ID, Category.PATH_FOR_ID_TOKEN);

/************************************ Dish ***********************************/
        matcher.addURI(authority, Dish.PATH, Dish.PATH_TOKEN);
        matcher.addURI(authority, Dish.PATH_FOR_ID, Dish.PATH_FOR_ID_TOKEN);
        matcher.addURI(authority, Dish.PATH_FOR_CAT_NAME, Dish.PATH_FOR_CAT_NAME_TOKEN);
        matcher.addURI(authority, Dish.PATH_FOR_FAVORITE, Dish.PATH_FOR_FAVORITE_TOKEN);

/************************************ Cart ***********************************/
        matcher.addURI(authority, Cart.PATH, Cart.PATH_TOKEN);
        matcher.addURI(authority, Cart.PATH_FOR_ID, Cart.PATH_FOR_ID_TOKEN);

/************************************ Ordered_Cart ***********************************/
        matcher.addURI(authority, Ordered_Cart.PATH, Ordered_Cart.PATH_TOKEN);
        matcher.addURI(authority, Ordered_Cart.PATH_FOR_ID, Ordered_Cart.PATH_FOR_ID_TOKEN);

/************************************ User ***********************************/
        matcher.addURI(authority, User.PATH, User.PATH_TOKEN);
        matcher.addURI(authority, User.PATH_FOR_ID, User.PATH_FOR_ID_TOKEN);

/************************************ Order ***********************************/
        matcher.addURI(authority, Order.PATH, Order.PATH_TOKEN);
        matcher.addURI(authority, Order.PATH_FOR_ID, Order.PATH_FOR_ID_TOKEN);

/************************************ Restaurant ***********************************/

        matcher.addURI(authority, Restaurant.PATH, Restaurant.PATH_TOKEN);
        matcher.addURI(authority, Restaurant.PATH_FOR_ID, Restaurant.PATH_FOR_ID_TOKEN);

        return matcher;
    }

    public static class Category{
        // an identifying name for entity
        public static final String NAME = "Category";

        // define a URI paths to access entity
        // BASE_URI/restaurants - for list of restaurants
        // BASE_URI/restaurants/* - retreive specific restaurant by id
        // the toke value are used to register path in matcher (see above)
        public static final String PATH = "category";
        public static final int PATH_TOKEN = 1000;
        public static final String PATH_FOR_ID = "category/#";
        public static final int PATH_FOR_ID_TOKEN = 2000;

        // define content mime type for entity
        public static final String CONTENT_TYPE_DIR = ContentResolver.CURSOR_DIR_BASE_TYPE + AUTHORITY + "." + PATH;
        public static final String CONTENT_ITEM_TYPE = ContentResolver.CURSOR_ITEM_BASE_TYPE + AUTHORITY + "." + PATH;

        // URI for all content stored as Category entity
        // BASE_URI + PATH ==> "content://com.favrestaurant.contentprovider/restaurants";
        public static final Uri CONTENT_URI = BASE_URI.buildUpon().appendPath(PATH).build();

        // a static class to store columns in entity
        public static class Cols {
            public static final String ID = BaseColumns._ID; // convention
            public static final String NAME = "Name";
            public static final String IMAGE = "Image";
        }
    }

    public static class Dish{
        // an identifying name for entity
        public static final String NAME = "Dishes";

        // define a URI paths to access entity
        // BASE_URI/restaurants - for list of restaurants
        // BASE_URI/restaurants/* - retreive specific restaurant by id
        // the toke value are used to register path in matcher (see above)
        public static final String PATH = "dishes";
        public static final int PATH_TOKEN = 3000;
        public static final String PATH_FOR_ID = "dishes/#";
        public static final int PATH_FOR_ID_TOKEN = 4000;
        public static final String PATH_FOR_CAT_NAME = "dishes/by_category";
        public static final int PATH_FOR_CAT_NAME_TOKEN = 4100;
        public static final String PATH_FOR_FAVORITE = "dishes/favorites";
        public static final int PATH_FOR_FAVORITE_TOKEN = 4200;

        // define content mime type for entity
        public static final String CONTENT_TYPE_DIR = ContentResolver.CURSOR_DIR_BASE_TYPE + AUTHORITY + "." + PATH;
        public static final String CONTENT_ITEM_TYPE = ContentResolver.CURSOR_ITEM_BASE_TYPE + AUTHORITY + "." + PATH;
        public static final String FAVORITE_TYPE_DIR = ContentResolver.CURSOR_DIR_BASE_TYPE + AUTHORITY + "." + PATH_FOR_FAVORITE;


        // URI for all content stored as Category entity
        // BASE_URI + PATH ==> "content://com.favrestaurant.contentprovider/restaurants";
        public static final Uri CONTENT_URI = BASE_URI.buildUpon().appendPath(PATH).build();
        public static final Uri FAVORITE_URI = BASE_URI.buildUpon().appendPath(PATH_FOR_FAVORITE).build();
        // a static class to store columns in entity
        public static class Cols {
            public static final String ID = BaseColumns._ID; // convention
            public static final String NAME = "Name";
            public static final String IMAGE = "Image";
            public static final String DESCRIPTION = "Description";
            public static final String PRICE = "Price";
            public static final String WEIGHT = "Weight";
            public static final String CAT_ID = "Categories_ID";
            public static final String CATEGORY = "Category";
            public static final String FAV = "Favorite";
        }
    }

    public static class User{
        // an identifying name for entity
        public static final String NAME = "User";

        // define a URI paths to access entity
        // BASE_URI/restaurants - for list of restaurants
        // BASE_URI/restaurants/* - retreive specific restaurant by id
        // the toke value are used to register path in matcher (see above)
        public static final String PATH = "users";
        public static final int PATH_TOKEN = 5000;
        public static final String PATH_FOR_ID = "users/#";
        public static final int PATH_FOR_ID_TOKEN = 6000;


        // define content mime type for entity
        public static final String CONTENT_TYPE_DIR = ContentResolver.CURSOR_DIR_BASE_TYPE + "/users";
        public static final String CONTENT_ITEM_TYPE = ContentResolver.CURSOR_ITEM_BASE_TYPE + "/user";

        // URI for all content stored as Category entity
        // BASE_URI + PATH ==> "content://com.favrestaurant.contentprovider/restaurants";
        public static final Uri CONTENT_URI = BASE_URI.buildUpon().appendPath(PATH).build();

        // a static class to store columns in entity
        public static class Cols {
            public static final String ID = BaseColumns._ID; // convention
            public static final String FIRSTNAME = "FirstName";
            public static final String LASTNAME = "LastName";
            public static final String IMAGE = "Image";
            public static final String PHONE = "Phone";
            public static final String CITY = "City";
            public static final String CARD = "Card";
            public static final String DISCOUNT = "Discount";
            public static final String MONEY = "Money";
        }
    }

    public static class Cart{
        // an identifying name for entity
        public static final String NAME = "Cart";

        // define a URI paths to access entity
        // BASE_URI/restaurants - for list of restaurants
        // BASE_URI/restaurants/* - retreive specific restaurant by id
        // the toke value are used to register path in matcher (see above)
        public static final String PATH = "cart";
        public static final int PATH_TOKEN = 7000;
        public static final String PATH_FOR_ID = "cart/#";
        public static final int PATH_FOR_ID_TOKEN = 8000;

        // URI for all content stored as Category entity
        // BASE_URI + PATH ==> "content://com.favrestaurant.contentprovider/restaurants";
        public static final Uri CONTENT_URI = BASE_URI.buildUpon().appendPath(PATH).build();

        // a static class to store columns in entity
        public static class Cols {
            public static final String ID = BaseColumns._ID; // convention
            public static final String DISH_ID = "Dish_ID";
            public static final String USER_ID = "User_ID";
            public static final String QUANTITY = "Quantity";
        }
    }

    public static class Ordered_Cart{
        // an identifying name for entity
        public static final String NAME = "Ordered_cart";

        // define a URI paths to access entity
        // BASE_URI/restaurants - for list of restaurants
        // BASE_URI/restaurants/* - retreive specific restaurant by id
        // the toke value are used to register path in matcher (see above)
        public static final String PATH = "ordered_cart";
        public static final int PATH_TOKEN = 9000;
        public static final String PATH_FOR_ID = "ordered_cart/#";
        public static final int PATH_FOR_ID_TOKEN = 10000;

        // URI for all content stored as Category entity
        // BASE_URI + PATH ==> "content://com.favrestaurant.contentprovider/restaurants";
        public static final Uri CONTENT_URI = BASE_URI.buildUpon().appendPath(PATH).build();

        // a static class to store columns in entity
        public static class Cols {
            public static final String ID = BaseColumns._ID; // convention
            public static final String DISH_ID = "Dish_ID";
            public static final String USER_ID = "Order_ID";
            public static final String QUANTITY = "Quantity";
            public static final String PRICE = "Price";
        }
    }

    public static class Order{
        // an identifying name for entity
        public static final String NAME = "Order";

        // define a URI paths to access entity
        // BASE_URI/restaurants - for list of restaurants
        // BASE_URI/restaurants/* - retreive specific restaurant by id
        // the toke value are used to register path in matcher (see above)
        public static final String PATH = "order";
        public static final int PATH_TOKEN = 11000;
        public static final String PATH_FOR_ID = "order/#";
        public static final int PATH_FOR_ID_TOKEN = 12000;

        // URI for all content stored as Category entity
        // BASE_URI + PATH ==> "content://com.favrestaurant.contentprovider/restaurants";
        public static final Uri CONTENT_URI = BASE_URI.buildUpon().appendPath(PATH).build();

        // a static class to store columns in entity
        public static class Cols {
            public static final String ID = BaseColumns._ID; // convention
            public static final String USER_ID = "User_ID";
            public static final String ORDER_DATE = "OrderDate";
            public static final String FIRSTNAME = "FirstName";
            public static final String LASTNAME = "LastName";
            public static final String PHONE = "Phone";
            public static final String PICKUPDATE = "PickUpDate";
            public static final String PICKUPTIME = "PickUpTime";
            public static final String CITY = "City";
        }
    }

    public static class Restaurant{
        // an identifying name for entity
        public static final String NAME = "Restaurant";

        // define a URI paths to access entity
        // BASE_URI/restaurants - for list of restaurants
        // BASE_URI/restaurants/* - retreive specific restaurant by id
        // the toke value are used to register path in matcher (see above)
        public static final String PATH = "restaurants";
        public static final int PATH_TOKEN = 13000;
        public static final String PATH_FOR_ID = "restaurants/#";
        public static final int PATH_FOR_ID_TOKEN = 14000;

        // URI for all content stored as Category entity
        // BASE_URI + PATH ==> "content://com.favrestaurant.contentprovider/restaurants";
        public static final Uri CONTENT_URI = BASE_URI.buildUpon().appendPath(PATH).build();

        // a static class to store columns in entity
        public static class Cols {
            public static final String ID = BaseColumns._ID; // convention
            public static final String ADDRESS = "Address";
            public static final String PHONE1 = "Phone1";
            public static final String PHONE2 = "Phone2";
            public static final String PHONE3 = "Phone3";
            public static final String SCHEDULE = "Schedule";
            public static final String EMAIL = "Email";
            public static final String CITY = "City";
            public static final String LATITUDE = "Latitude";
            public static final String LONGITUDE = "Longitude";
            public static final String IMAGE = "Image";
            public static final String NAME = "Name";
            public static final String DESCRIPTION = "Description";

        }
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
}
