package com.link2loyalty.bwigomdlib.models2.user;

import java.io.Serializable;

public class UserDireccion implements Serializable {

    String ClvDir;
    String IdEfe;
    String IdMun;
    String Col;
    String Pob;
    String Dom;
    String Nin;
    String Nex;
    String Cp;
    String Tip;

    public UserDireccion() { }

    public String getClvDir() {
        return ClvDir;
    }

    public void setClvDir(String clvDir) {
        ClvDir = clvDir;
    }

    public String getIdEfe() {
        return IdEfe;
    }

    public void setIdEfe(String idEfe) {
        IdEfe = idEfe;
    }

    public String getIdMun() {
        return IdMun;
    }

    public void setIdMun(String idMun) {
        IdMun = idMun;
    }

    public String getCol() {
        return Col;
    }

    public void setCol(String col) {
        Col = col;
    }

    public String getPob() {
        return Pob;
    }

    public void setPob(String pob) {
        Pob = pob;
    }

    public String getDom() {
        return Dom;
    }

    public void setDom(String dom) {
        Dom = dom;
    }

    public String getNin() {
        return Nin;
    }

    public void setNin(String nin) {
        Nin = nin;
    }

    public String getNex() {
        return Nex;
    }

    public void setNex(String nex) {
        Nex = nex;
    }

    public String getCp() {
        return Cp;
    }

    public void setCp(String cp) {
        Cp = cp;
    }

    public String getTip() {
        return Tip;
    }

    public void setTip(String tip) {
        Tip = tip;
    }
}
