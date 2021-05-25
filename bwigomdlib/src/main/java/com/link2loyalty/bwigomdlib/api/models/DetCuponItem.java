package com.link2loyalty.bwigomdlib.api.models;

import java.util.ArrayList;

public class DetCuponItem {

    private String clvpro;
    private String descor;
    private String deslar;
    private String ter;
    private String con;
    private String dis;
    private String clvtipcod;
    private String clvtipred;
    private String limusu;
    private String fav;
    private String cup;

    public DetCuponItem(String clvpro, String descor, String deslar, String ter, String con, String dis, String clvtipcod, String clvtipred, String limusu, String fav, String cup) {
        this.clvpro = clvpro;
        this.descor = descor;
        this.deslar = deslar;
        this.ter = ter;
        this.con = con;
        this.dis = dis;
        this.clvtipcod = clvtipcod;
        this.clvtipred = clvtipred;
        this.limusu = limusu;
        this.fav = fav;
        this.cup = cup;
    }

    public String getClvpro() {
        return clvpro;
    }

    public void setClvpro(String clvpro) {
        this.clvpro = clvpro;
    }

    public String getDescor() {
        return descor;
    }

    public void setDescor(String descor) {
        this.descor = descor;
    }

    public String getDeslar() {
        return deslar;
    }

    public void setDeslar(String deslar) {
        this.deslar = deslar;
    }

    public String getTer() {
        return ter;
    }

    public void setTer(String ter) {
        this.ter = ter;
    }

    public String getCon() {
        return con;
    }

    public void setCon(String con) {
        this.con = con;
    }

    public String getDis() {
        return dis;
    }

    public void setDis(String dis) {
        this.dis = dis;
    }

    public String getClvtipcod() {
        return clvtipcod;
    }

    public void setClvtipcod(String clvtipcod) {
        this.clvtipcod = clvtipcod;
    }

    public String getClvtipred() {
        return clvtipred;
    }

    public void setClvtipred(String clvtipred) {
        this.clvtipred = clvtipred;
    }

    public String getLimusu() {
        return limusu;
    }

    public void setLimusu(String limusu) {
        this.limusu = limusu;
    }

    public String getFav() {
        return fav;
    }

    public void setFav(String fav) {
        this.fav = fav;
    }

    public String getCup() {
        return cup;
    }

    public void setCup(String cup) {
        this.cup = cup;
    }
}
