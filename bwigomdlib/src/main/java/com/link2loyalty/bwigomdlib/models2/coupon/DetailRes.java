package com.link2loyalty.bwigomdlib.models2.coupon;

import java.io.Serializable;
import java.util.List;

public class DetailRes implements Serializable {

    int err;
    String men;
    boolean val;
    String ses;
    int ite;
    Valor Valor;

    public DetailRes() { }

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


    public Valor getValor() {
        return Valor;
    }

    public void setValor(Valor valor) {
        this.Valor = valor;
    }
}
