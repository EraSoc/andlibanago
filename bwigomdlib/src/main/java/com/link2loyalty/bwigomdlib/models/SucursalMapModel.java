package com.link2loyalty.bwigo.models;

public class SucursalMapModel {

    private int id;
    private String address;
    private String lat;
    private String lng;
    private String category;
    private String discount;
    private String distance;


    public SucursalMapModel(int id, String address, String lat, String lng, String category, String discount, String distance) {
        this.id = id;
        this.address = address;
        this.lat = lat;
        this.lng = lng;
        this.category = category;
        this.discount = discount;
        this.distance = distance;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getLat() {
        return lat;
    }

    public void setLat(String lat) {
        this.lat = lat;
    }

    public String getLng() {
        return lng;
    }

    public void setLng(String lng) {
        this.lng = lng;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getDiscount() {
        return discount;
    }

    public void setDiscount(String discount) {
        this.discount = discount;
    }

    public String getDistance() {
        return distance;
    }

    public void setDistance(String distance) {
        this.distance = distance;
    }
}
