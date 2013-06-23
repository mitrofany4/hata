package com.example.hata.data;

/**
 * Created by Максим on 27.05.13.
 */
public class Restaurant {
    private long id;
    private String address;
    private double longitude;
    private double latitude;
    private String city;
    private String name;
    private String description;
    private String email;
    private String phone1;
    private String phone2;
    private String phone3;
    private String schedule;
    private String image;

    @Override
    public String toString() {

        return this.address + ", " + this.city;
    }

    public Restaurant () {

    }

    public Restaurant(double longitude, double latitude, String name) {
        this.longitude = longitude;
        this.latitude = latitude;
        this.name = name;
    }

    public Restaurant(long id, String address, double longitude, double latitude, String city, String name, String description, String email, String phone1, String phone2, String phone3, String schedule, String image) {

        this.id = id;
        this.address = address;
        this.longitude = longitude;
        this.latitude = latitude;
        this.city = city;
        this.name = name;
        this.description = description;
        this.email = email;
        this.phone1 = phone1;
        this.phone2 = phone2;
        this.phone3 = phone3;
        this.schedule = schedule;
        this.image = image;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone1() {
        return phone1;
    }

    public void setPhone1(String phone1) {
        this.phone1 = phone1;
    }

    public String getPhone2() {
        return phone2;
    }

    public void setPhone2(String phone2) {
        this.phone2 = phone2;
    }

    public String getPhone3() {
        return phone3;
    }

    public void setPhone3(String phone3) {
        this.phone3 = phone3;
    }

    public String getSchedule() {
        return schedule;
    }

    public void setSchedule(String schedule) {
        this.schedule = schedule;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
 }
