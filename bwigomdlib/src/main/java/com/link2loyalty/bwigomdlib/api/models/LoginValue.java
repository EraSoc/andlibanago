package com.link2loyalty.bwigomdlib.api.models;

import java.io.Serializable;

public class LoginValue implements Serializable {
    private String ses;
    private String nom;
    private String apa;
    private String ama;
    private String ema;
    private String fol;
    private String usu;
    private String nta;
    private int cambol;
    private int ter;
    private int can;
    private int tes;
    private int niv;
    private String imgtar;

    public LoginValue() {
        this.ses = "";
        this.nom = "";
        this.apa = "";
        this.ama = "";
        this.ema = "";
        this.fol = "";
        this.usu = "";
        this.nta = "";
        this.cambol = 0;
        this.ter = 0;
        this.can = 0;
        this.tes = 0;
        this.niv = 0;
        this.imgtar="";
    }

    // Getter Methods
    public String getSes() {
        return ses;
    }
    public String getNom() {
        return nom;
    }
    public String getApa() {
        return apa;
    }
    public String getAma() {
        return ama;
    }
    public String getEma() {
        return ema;
    }
    public String getFol() {
        return fol;
    }
    public String getUsu() {
        return usu;
    }
    public String getNta() {
        return nta;
    }
    public int getCambol() {
        return cambol;
    }
    public int getTer() {
        return ter;
    }
    public int getCan() {
        return can;
    }
    public int getTes() {
        return tes;
    }
    public int getNiv() {
        return niv;
    }
    public String getImgtar(){return imgtar;}
    // Setter Methods
    public void setSes(String ses) {
        this.ses = ses;
    }
    public void setNom(String nom) {
        this.nom = nom;
    }
    public void setApa(String apa) {
        this.apa = apa;
    }
    public void setAma(String ama) {
        this.ama = ama;
    }
    public void setEma(String ema) {
        this.ema = ema;
    }
    public void setFol(String fol) {
        this.fol = fol;
    }
    public void setUsu(String usu) {
        this.usu = usu;
    }
    public void setNta(String nta) {
        this.nta = nta;
    }
    public void setCambol(int cambol) {
        this.cambol = cambol;
    }
    public void setTer(int ter) {
        this.ter = ter;
    }
    public void setCan(int can) {
        this.can = can;
    }
    public void setTes(int tes) {
        this.tes = tes;
    }
    public void setNiv(int niv) {
        this.niv = niv;
    }
    public void setImgtar(String imgtar){this.imgtar=imgtar;}
}
