package com.example.hata.data;

import java.util.ArrayList;

/**
 * Created by Максим on 23.06.13.
 */
public class Order {

    private long id;
    private ArrayList<Dish> items = new ArrayList<Dish>();
    private String date;
    private String time;
    private double bill;
    private int discount;
    private String client;

    public String getClient() {
        return client;
    }

    public void setClient(String client) {
        this.client = client;
    }

    public Order(long id,String client, ArrayList<Dish> _items, String time, String date, int discount) {
        this.discount = discount;
        this.time = time;
        this.date = date;
        this.items = _items;
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
