package com.example.hata;

/**
 * Created with IntelliJ IDEA.
 * User: Максим
 * Date: 05.03.13
 * Time: 21:15
 * To change this template use File | Settings | File Templates.
 */
public class Category {
    private int id;
    private String name;
    private int image;

    public Category( String name, int image) {
        this.name = name;
        this.image = image;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getImage() {
        return image;
    }

    public void setImage(int image) {
        this.image = image;
    }
}
