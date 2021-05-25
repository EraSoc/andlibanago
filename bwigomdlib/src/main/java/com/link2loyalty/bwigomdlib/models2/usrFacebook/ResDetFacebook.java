package com.link2loyalty.bwigomdlib.models2.usrFacebook;

public class ResDetFacebook {

        ValorFacebook Valor;
        int err;
        String men;
        Boolean val;
        String ses;
        int ite;

    public ResDetFacebook() {
    }

    public ValorFacebook getValor() {
        return Valor;
    }

    public void setValor(ValorFacebook valor) {
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

    public Boolean getVal() {
        return val;
    }

    public void setVal(Boolean val) {
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
