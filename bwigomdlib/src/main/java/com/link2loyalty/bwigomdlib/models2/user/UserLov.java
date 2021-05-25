package com.link2loyalty.bwigomdlib.models2.user;

import java.io.Serializable;

public class UserLov implements Serializable {

    UserGeneral General;
    UserContacto Contacto;
    UserDireccion Direccion;
    String foltar;
    String notar;
    String nom;
    String apa;
    String ama;
    String ultcan;
    String imgtar;

    public UserLov() { }

    public UserGeneral getGeneral() {
        return General;
    }

    public void setGeneral(UserGeneral general) {
        General = general;
    }

    public UserContacto getContacto() {
        return Contacto;
    }

    public void setContacto(UserContacto contacto) {
        Contacto = contacto;
    }

    public UserDireccion getDireccion() {
        return Direccion;
    }

    public void setDireccion(UserDireccion direccion) {
        Direccion = direccion;
    }

    public String getFoltar() {
        return foltar;
    }

    public void setFoltar(String foltar) {
        this.foltar = foltar;
    }

    public String getNotar() {
        return notar;
    }

    public void setNotar(String notar) {
        this.notar = notar;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getApa() {
        return apa;
    }

    public void setApa(String apa) {
        this.apa = apa;
    }

    public String getAma() {
        return ama;
    }

    public void setAma(String ama) {
        this.ama = ama;
    }

    public String getUltcan() {
        return ultcan;
    }

    public void setUltcan(String ultcan) {
        this.ultcan = ultcan;
    }

    public String getImgtar(){return imgtar;}
    public void setImgtar(String imgtar){this.imgtar=imgtar;}
}
