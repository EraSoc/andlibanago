package com.link2loyalty.bwigomdlib.models2.canje;

import java.io.Serializable;

public class LovBoletos implements Serializable {

    String serie;
    String fon;
    String imgcod;
    String vigencia;
    String pdf;
    String imglog;

    public LovBoletos() {  }

    public String getSerie() {
        return serie;
    }

    public void setSerie(String serie) {
        this.serie = serie;
    }

    public String getFon() {
        return fon;
    }

    public void setFon(String fon) {
        this.fon = fon;
    }

    public String getImgcod() {
        return imgcod;
    }

    public void setImgcod(String imgcod) {
        this.imgcod = imgcod;
    }

    public String getVigencia() {
        return vigencia;
    }

    public void setVigencia(String vigencia) {
        this.vigencia = vigencia;
    }

    public String getPdf() {
        return pdf;
    }

    public void setPdf(String pdf) {
        this.pdf = pdf;
    }

    public String getImglog() {
        return imglog;
    }

    public void setImglog(String imglog) {
        this.imglog = imglog;
    }
}
