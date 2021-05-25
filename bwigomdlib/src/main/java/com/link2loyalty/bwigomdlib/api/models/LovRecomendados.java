package com.link2loyalty.bwigomdlib.api.models;

import java.util.ArrayList;

public class LovRecomendados {

    private ArrayList<LovRecomendadosItem> Lov;
    private int err;
    private String men;
    private boolean val;
    private String ses;
    private int ite;

    public LovRecomendados(ArrayList<LovRecomendadosItem> lov, int err, String men, boolean val, String ses, int ite) {
        Lov = lov;
        this.err = err;
        this.men = men;
        this.val = val;
        this.ses = ses;
        this.ite = ite;
    }

    public ArrayList<LovRecomendadosItem> getLov() {
        return Lov;
    }

    public void setLov(ArrayList<LovRecomendadosItem> lov) {
        Lov = lov;
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
