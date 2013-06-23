package com.example.hata.data;

import java.util.ArrayList;

/**
 * Created by Максим on 23.06.13.
 */
public class Order {


    private long id;
    private ArrayList<Dish> items;
    private String date;
    private String time;
    private double bill;
    private int discount;

    public Order(long id, ArrayList<Dish> items, String time, String date, int discount) {
        this.discount = discount;
        this.time = time;
        this.date = date;
        this.items = items;
        this.id = id;
        int sum =0;

        for (Dish it : items){
            sum=+it.getCount();
        }

        this.bill=sum*0.01*(100-discount);
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public ArrayList<Dish> getItems() {
        return items;
    }

    public void setItems(ArrayList<Dish> items) {
        this.items = items;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public double getBill() {
        return bill;
    }

    public void setBill(double bill) {
        this.bill = bill;
    }

    public int getDiscount() {
        return discount;
    }

    public void setDiscount(int discount) {
        this.discount = discount;
    }
}
