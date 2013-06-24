package com.example.hata.activities;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.*;

import com.actionbarsherlock.app.ActionBar;
import com.actionbarsherlock.app.SherlockActivity;
import com.actionbarsherlock.view.Menu;
import com.actionbarsherlock.view.MenuItem;
import com.example.hata.R;
import com.example.hata.Wheel.NumericWheelAdapter;
import com.example.hata.Wheel.OnWheelChangedListener;
import com.example.hata.Wheel.OnWheelScrollListener;
import com.example.hata.Wheel.WheelView;
import com.example.hata.data.Dish;
import com.example.hata.hataApp;

import static android.view.View.OnClickListener;
import static com.example.hata.DBAdapter.getBitmapFromAsset;
import static com.example.hata.MenuItemAdapter.fmt;

/**
 * Created with IntelliJ IDEA.
 * User: Максим
 * Date: 18.03.13
 * Time: 10:35
 * To change this template use File | Settings | File Templates.
 */
public class ProductActivity extends SherlockActivity {
    final ImageView ProductIV = (ImageView) findViewById(R.id.ProductImageView);
    final TextView DescriptionTV = (TextView) findViewById(R.id.DescriptionTextView);
    final TextView PriceTV = (TextView) findViewById(R.id.PriceTextView);
    final TextView WeightTV = (TextView) findViewById(R.id.WeightTextView);
    //        final TextView name = (TextView) findViewById(R.id.nameTV);
    final Button button = (Button) findViewById(R.id.button);
    final TextView value = (TextView) findViewById(R.id.ValueTextView);
    final WheelView count = (WheelView) findViewById(R.id.numberPicker);


    private Dish product = new Dish();

    Double curent_price;
    // Number changed flag
    private boolean pickerChanged = false;

    // Number scrolled flag
    private boolean pickerScrolled = false;
    private ActionBar actionBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);    //To change body of overridden methods use File | Settings | File Templates.

 /**
   *    Get item
  */
//        product = (Dish) getIntent().getParcelableExtra("product");

        curent_price=product.getPrice();

  /**
  *     Set layout
  * */

        setContentView(R.layout.item);

/*****************************
 *         Name
 *****************************/
//     name.setText(product.getName());
//     ProductIV.setContentDescription(product.getName());
        ActionBar actionBar = getSupportActionBar();

        actionBar.setTitle(product.getName());
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setDisplayUseLogoEnabled(false);
/*****************************
 *         RatingBar
 *****************************/

        RatingBar ProductRB = (RatingBar) findViewById(R.id.ProductRatingBar);
        ProductRB.setNumStars(5);
        ProductRB.setRating(4);

        ProductRB.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener(){

            @Override
            public void onRatingChanged(RatingBar ratingBar, float rating,
                                        boolean fromUser) {
                // TODO Auto-generated method stub
                Toast toast = Toast.makeText(ProductActivity.this,"рейтинг: " + String.valueOf(rating),Toast.LENGTH_LONG);
                toast.setGravity(Gravity.CENTER, 0, 0);
                toast.show();
            }});

/*******************************
*         NumberPicker
********************************/

        count.setViewAdapter(new NumericWheelAdapter(this,1,20,"%02d"));
        count.setCyclic(true);

        OnWheelChangedListener wheelListener = new OnWheelChangedListener() {
            public void onChanged(WheelView wheel, int oldValue, int newValue) {
                if (!pickerScrolled) {
                    pickerChanged = true;
                    curent_price=product.getPrice()*(count.getCurrentItem()+1);
                    value.setText(String.valueOf(count.getCurrentItem()+1)+" x "+fmt(product.getPrice()));
                    PriceTV.setText(fmt(curent_price));
                    pickerChanged = false;
                }
            }
        };
        count.addChangingListener(wheelListener);

        OnWheelScrollListener scrollListener = new OnWheelScrollListener() {
            public void onScrollingStarted(WheelView wheel) {
                pickerScrolled = true;
            }
            public void onScrollingFinished(WheelView wheel) {
                pickerScrolled = false;
                pickerChanged = true;
                curent_price= product.getPrice() * (count.getCurrentItem() + 1);
                value.setText(String.valueOf(count.getCurrentItem()+1)+" x "+fmt(product.getPrice()));
                PriceTV.setText(fmt(curent_price));
                pickerChanged = false;
            }
        };

        count.addScrollingListener(scrollListener);


//      *************************************
/***************************************
 *         Product Image
****************************************/


        String path = "items/"+ product.getImage();
        Bitmap img = getBitmapFromAsset(this, path);
        product.setImage_bmp(img);
        ProductIV.setImageBitmap(img);

/********************************************
 *        Description
 ******************************************/

        DescriptionTV.setText(product.getDescription());

/********************************************
 *        Price
 * *******************************************/
         value.setText(String.valueOf(count.getCurrentItem()+1)+" x "+fmt(product.getPrice()));
         PriceTV.setText(fmt(product.getPrice()));

/********************************************
 *        Weight
 * *******************************************/

         WeightTV.setText(String.valueOf(product.getWeight())+" г.");

/*****************************
*         Button
*****************************/

     button.setOnClickListener(ButtonClickListener);

    }

    private OnClickListener ButtonClickListener = new OnClickListener() {
        @Override
        public void onClick(View view) {
            //To change body of implemented methods use File | Settings | File Templates.
//            TODO: send object to Cart

            product.setCount(count.getCurrentItem()+1);
            hataApp apphata = ((hataApp)getApplicationContext());
            apphata.addItem(product);
            Toast toast = Toast.makeText(ProductActivity.this,product.getName() + " добавлено в корзину",Toast.LENGTH_LONG);
            toast.setGravity(Gravity.CENTER, 0, 0);
            toast.show();
            finish();
        }
    };

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        menu.add(0,2,1,"Favorites")
                .setIcon(R.drawable.favorites_redheart)
                .setShowAsAction(MenuItem.SHOW_AS_ACTION_IF_ROOM);
        return true;
    }

    @Override
    public boolean onMenuItemSelected(int featureId, MenuItem item) {
        int itemId = item.getItemId();
        switch (itemId) {
            case android.R.id.home:

                finish();
                break;
            case 2:
                Toast.makeText(this, "like pressed", Toast.LENGTH_SHORT).show();

        }

        return true;
    }
}
