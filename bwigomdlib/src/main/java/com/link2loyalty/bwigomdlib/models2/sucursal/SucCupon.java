package com.link2loyalty.bwigomdlib.models2.sucursal;

import java.io.Serializable;

public class SucCupon implements Serializable {

    private String clvsuc;
    private String ali;
    private String suc;
    private String tel;
    private String lat;
    private String lon;
    private String dir;

    public SucCupon() {
    }

    public String getClvsuc() {
        return clvsuc;
    }

    public void setClvsuc(String clvsuc) {
        this.clvsuc = clvsuc;
    }

    public String getAli() {
        return ali;
    }

    public void setAli(String ali) {
        this.ali = ali;
    }

    public String getSuc() {
        return suc;
    }

    public void setSuc(String suc) {
        this.suc = suc;
    }

    public String getTel() {
        return tel;
    }

    public void setTel(String tel) {
        this.tel = tel;
    }

    public String getLat() {
        return lat;
    }

    public void setLat(String lat) {
        this.lat = lat;
    }

    public String getLon() {
        return lon;
    }

    public void setLon(String lon) {
        this.lon = lon;
    }

    public String getDir() {
        return dir;
    }

    public void setDir(String dir) {
        this.dir = dir;
    }
}
