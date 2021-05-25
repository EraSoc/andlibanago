package com.link2loyalty.bwigomdlib.models;

import java.io.Serializable;

public class CategoryModel implements Serializable {

    private String id;
    private String color;
    private int icon;
    private String name;

    public CategoryModel(){ }

    public CategoryModel(String id, String color, int icon, String name) {
        this.id = id;
        this.color = color;
        this.icon = icon;
        this.name = name;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public int getIcon() {
        return icon;
    }

    public void setIcon(int icon) {
        this.icon = icon;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
