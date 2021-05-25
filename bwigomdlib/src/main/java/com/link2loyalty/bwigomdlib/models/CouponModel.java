package com.link2loyalty.bwigo.models;

import java.io.Serializable;
import java.util.ArrayList;

public class CouponModel implements Serializable {

    private String id;
    private String discount_short;
    private String discount;
    private String store;
    private String category;
    private String conditions;

    private ArrayList<Sucursal> sucursales;


    public CouponModel() {
    }

    public CouponModel(String id, String discount, String discount_short, String store,
                       String category, String conditions, ArrayList<Sucursal> sucursales) {
        this.id = id;
        this.discount = discount;
        this.store = store;
        this.category = category;
        this.discount_short = discount_short;
        this.conditions = conditions;
        this.sucursales = sucursales;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getDiscount() {
        return discount;
    }

    public void setDiscount(String discount) {
        this.discount = discount;
    }

    public String getStore() {
        return store;
    }

    public void setStore(String store) {
        this.store = store;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getDiscount_short() {
        return discount_short;
    }

    public void setDiscount_short(String discount_short) {
        this.discount_short = discount_short;
    }

    public String getConditions() {
        return conditions;
    }

    public void setConditions(String conditions) {
        this.conditions = conditions;
    }

    public ArrayList<Sucursal> getSucursales() {
        return sucursales;
    }

    public void setSucursales(ArrayList<Sucursal> sucursales) {
        this.sucursales = sucursales;
    }
}


