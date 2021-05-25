package com.link2loyalty.bwigomdlib.models2;

import java.util.List;

public class ResAnuncios {


    int err;
    String men;
    boolean val;
    String ses;
    int ite;
    List<Anuncio> Lov;


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

    public List<Anuncio> getLov() {
        return Lov;
    }

    public void setLov(List<Anuncio> lov) {
        Lov = lov;
    }
}
