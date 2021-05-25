package com.link2loyalty.bwigomdlib.models2.coupon;

import java.io.Serializable;

public class Valor implements Serializable {

    String clvpro;
    String descor;
    String deslar;
    String ter;
    String con;
    String dis;
    String clvtipcod;
    String clvtipred;
    String limusu;
    int fav;
    String cup;

    public Valor() { }

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

    public int getFav() {
        return fav;
    }

    public void setFav(int fav) {
        this.fav = fav;
    }

    public String getCup() {
        return cup;
    }

    public void setCup(String cup) {
        this.cup = cup;
    }
}
