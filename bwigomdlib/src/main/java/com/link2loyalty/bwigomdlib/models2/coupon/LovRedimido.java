package com.link2loyalty.bwigomdlib.models2.coupon;

import java.io.Serializable;

public class LovRedimido implements Serializable {
    String clvred;
    String clvpro;
    String descor;
    String ima;
    String ran;
    String ali;
    String cat;

    public LovRedimido() {
    }

    public String getAli() {
        return ali;
    }

    public void setAli(String ali) {
        this.ali = ali;
    }

    public String getCat() {
        return cat;
    }

    public void setCat(String cat) {
        this.cat = cat;
    }

    public String getClvred() {
        return clvred;
    }

    public void setClvred(String clvred) {
        this.clvred = clvred;
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

    public String getIma() {
        return ima;
    }

    public void setIma(String ima) {
        this.ima = ima;
    }

    public String getRan() {
        return ran;
    }

    public void setRan(String ran) {
        this.ran = ran;
    }
}

