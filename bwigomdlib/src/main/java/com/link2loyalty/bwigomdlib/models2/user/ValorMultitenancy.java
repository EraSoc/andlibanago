package com.link2loyalty.bwigomdlib.models2.user;

import java.io.Serializable;

public class ValorMultitenancy implements Serializable {

    String nom;
    String des;
    String colpri;
    String colcla;
    String colosc;
    String imglog;

    public ValorMultitenancy() {
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getDes() {
        return des;
    }

    public void setDes(String des) {
        this.des = des;
    }

    public String getColpri() {
        return colpri;
    }

    public void setColpri(String colpri) {
        this.colpri = colpri;
    }

    public String getColcla() {
        return colcla;
    }

    public void setColcla(String colcla) {
        this.colcla = colcla;
    }

    public String getColosc() {
        return colosc;
    }

    public void setColosc(String colosc) {
        this.colosc = colosc;
    }

    public String getImglog() {
        return imglog;
    }

    public void setImglog(String imglog) {
        this.imglog = imglog;
    }
}
