package com.example.hata.activities;

import android.os.Bundle;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import com.actionbarsherlock.app.ActionBar;
import com.actionbarsherlock.app.SherlockActivity;
import com.actionbarsherlock.view.MenuItem;
import com.example.hata.CartItemAdapter;
import com.example.hata.R;
import com.example.hata.data.Dish;
import com.example.hata.hataApp;

import java.util.ArrayList;

/**
 * Created by Максим on 24.06.13.
 */
public class CartActivity extends SherlockActivity {
    private CartItemAdapter adapter;
    ArrayList<Dish> cart_items = new ArrayList<Dish>();
    private ActionBar actionBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        actionBar = getSupportActionBar();

        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setDisplayUseLogoEnabled(false);
        actionBar.setTitle("Корзина");
        setContentView(R.layout.cart);

        hataApp apphata = ((hataApp)getApplicationContext());
        cart_items = apphata.getCart();

        TextView message = (TextView) findViewById(R.id.messageTV);
        ListView cartlist = (ListView) findViewById(R.id.cartitems);
        TextView cost = (TextView) findViewById(R.id.costTV);
        TextView disc = (TextView) findViewById(R.id.discountTV);
        TextView final_coast = (TextView) findViewById(R.id.final_costTV);

        if (cart_items.size()<1) {
            message.setVisibility(View.VISIBLE);
            cartlist.setVisibility(View.GONE);
            cost.setVisibility(View.GONE);
            disc.setVisibility(View.GONE);
            final_coast.setVisibility(View.GONE);

        } else {

        message.setVisibility(View.GONE);
        cartlist.setVisibility(View.VISIBLE);
        cost.setVisibility(View.VISIBLE);
        disc.setVisibility(View.VISIBLE);
        final_coast.setVisibility(View.VISIBLE);
        adapter = new CartItemAdapter(CartActivity.this, R.layout.cart_item, cart_items);
        cartlist.setAdapter(adapter);
        cost.setText(String.format("%.2f грн.", apphata.getCart_value()));
        disc.setText(String.format("-%.2f грн.",apphata.getDiscount()));
        final_coast.setText(String.format("%.2f грн.",apphata.getFinal_value()));
        }
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
