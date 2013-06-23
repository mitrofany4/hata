package com.example.hata.data;

import android.graphics.Bitmap;
import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created with IntelliJ IDEA.
 * User: Максим
 * Date: 03.03.13
 * Time: 19:34
 * To change this template use File | Settings | File Templates.
 */
public class Dish implements Parcelable{
    private long id;
    private String name;
    private double price;
//    private int image_id;
    private Bitmap image_bmp;
    private String image;
    private String description;
    private int weight;
    private int cat_id;
    private String category;
    private Boolean isFavorite=false;
    private int count;

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public Boolean getFavorite() {
        return isFavorite;
    }

    public void setFavorite(Boolean favorite) {
        isFavorite = favorite;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }



    public Bitmap getImage_bmp() {
        return image_bmp;
    }

    public void setImage_bmp(Bitmap image_bmp) {
        this.image_bmp = image_bmp;
}

    public Dish(long id, String name, double price, Bitmap image_bmp, String description, int weight, String category, Boolean isFavorite) {
        this.id = id;
        this.name = name;

        this.price = price;
        this.image_bmp = image_bmp;

        this.description = description;
        this.weight = weight;
        this.category = category;
        this.isFavorite = isFavorite;
    }

    /*public Dish(long id, String name, double price, int image_id, String description, int weight, int cat_id) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.image = image;

        this.description = description;
        this.weight = weight;
        this.cat_id = cat_id;
    }*/

    public int getCat_id() {
        return cat_id;
    }

    public void setCat_id(int cat_id) {
        this.cat_id = cat_id;
    }

    public int getWeight() {
        return weight;
    }

    public void setWeight(int weight) {
        this.weight = weight;
    }

    public Dish() {
        this.name="";
        this.price=0.0;

    }

    public long getId() {

        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getName() {
        return name;
    }

    public double getPrice() {
        return price;
    }

    public String getImage() {
        return image;
    }

    public void setName(String _name) {
        this.name = _name;
    }

    public void setPrice(double _price) {
        this.price = _price;
    }

    public void setImage(String _image) {
        this.image = _image;
    }

    public Dish(Parcel parcel){
        readFromParcel(parcel);
    }

    private void readFromParcel(Parcel parcel) {
        //To change body of created methods use File | Settings | File Templates.
        this.id=parcel.readLong();
        this.name=parcel.readString();
        this.price = parcel.readDouble();
        this.image=parcel.readString();
        this.description=parcel.readString();
        this.weight=parcel.readInt();

    }

    @Override
    public int describeContents() {
        return 0;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        //To change body of implemented methods use File | Settings | File Templates.
        parcel.writeLong(this.id);
        parcel.writeString(this.name);
        parcel.writeDouble(this.price);
        parcel.writeString(this.image);
        parcel.writeString(this.description);
        parcel.writeInt(this.weight);

    }
    public static final Parcelable.Creator<Dish> CREATOR =
            new Parcelable.Creator<Dish>() {
                @Override
                public Dish createFromParcel(Parcel parcel) {
                    return new Dish(parcel);
                }
                @Override
                public Dish[] newArray(int size) {
                    return new Dish[size];  //To change body of implemented methods use File | Settings | File Templates.
                }
            };
}


