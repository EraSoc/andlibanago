package com.link2loyalty.bwigomdlib.api.models;

import java.util.ArrayList;

public class DetCupon {

    private DetCuponItem Valor;
    private int err;
    private String men;
    private boolean val;
    private String ses;
    private int ite;

    public DetCupon(DetCuponItem valor, int err, String men, boolean val, String ses, int ite) {
        Valor = valor;
        this.err = err;
        this.men = men;
        this.val = val;
        this.ses = ses;
        this.ite = ite;
    }

    public DetCuponItem getValor() {
        return Valor;
    }

    public void setValor(DetCuponItem valor) {
        Valor = valor;
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
}
