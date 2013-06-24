package com.example.hata;

import android.app.Application;
import com.example.hata.data.Dish;

import java.util.ArrayList;

/**
 * Created by Максим on 24.06.13.
 */
public class hataApp extends Application {

    private ArrayList <Dish> cart = new ArrayList<Dish>();

    private Double cart_value;

    private int count;

    public Double getCart_value() {
        cart_value = 0.0;
        for (int i=0; i<cart.size(); i++){
            cart_value+=cart.get(i).getPrice()*cart.get(i).getCount();
        }
        return cart_value;
    }

    public int getCount() {
        count = 0;
        for (int i=0; i<cart.size(); i++){
            count+=cart.get(i).getCount();
        }
        return count;
    }

    public ArrayList<Dish> getCart() {
        return cart;
    }

    public void setCart(ArrayList<Dish> cart) {
        this.cart = cart;
    }

    public void addItem(Dish item){
        this.cart.add(item);
    }


}
