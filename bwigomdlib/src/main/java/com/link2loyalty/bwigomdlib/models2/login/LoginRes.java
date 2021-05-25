package com.link2loyalty.bwigomdlib.models2.login;

import java.io.Serializable;

public class LoginRes implements Serializable {

    public int err;
    public String men;
    public boolean val;
    public String ses;
    public int ite;
    public Valor Valor;

    public LoginRes() { }

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

    public com.link2loyalty.bwigomdlib.models2.login.Valor getValor() {
        return Valor;
    }

    public void setValor(com.link2loyalty.bwigomdlib.models2.login.Valor valor) {
        Valor = valor;
    }
}
