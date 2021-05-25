package com.link2loyalty.bwigomdlib.models2.coupon;

import java.io.Serializable;
import java.util.ArrayList;

public class ResRedimidos implements Serializable {
    int err;
    String men;
    boolean val;
    String ses;
    int ite;
    ArrayList<LovRedimido> Lov;

    public ResRedimidos() {
    }

    public int getErr() {
        return err;
    }

    public void setErr(int err) {
        this.err = err;
    }

    public String getMen() {
        return men;
    }

    public void setMen(String men) {
        this.men = men;
    }

    public boolean isVal() {
        return val;
    }

    public void setVal(boolean val) {
        this.val = val;
    }

    public String getSes() {
        return ses;
    }

    public void setSes(String ses) {
        this.ses = ses;
    }

    public int getIte() {
        return ite;
    }

    public void setIte(int ite) {
        this.ite = ite;
    }

    public ArrayList<LovRedimido> getLov() {
        return Lov;
    }

    public void setLov(ArrayList<LovRedimido> lov) {
        Lov = lov;
    }
}
