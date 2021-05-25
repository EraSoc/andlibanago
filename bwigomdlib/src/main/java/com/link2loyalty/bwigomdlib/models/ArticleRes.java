package com.link2loyalty.bwigomdlib.models;

import java.util.ArrayList;

public class ArticleRes {

    int err;
    int val;
    String men;
    String ses;
    int ite;
    ArrayList<Article> Lov;
    Article Valor;

    public ArticleRes() {
    }


    public int getErr() {
        return err;
    }

    public void setErr(int err) {
        this.err = err;
    }

    public int getVal() {
        return val;
    }

    public void setVal(int val) {
        this.val = val;
    }

    public String getMen() {
        return men;
    }

    public void setMen(String men) {
        this.men = men;
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

    public ArrayList<Article> getLov() {
        return Lov;
    }

    public void setLov(ArrayList<Article> lov) {
        Lov = lov;
    }

    public Article getValor() {
        return Valor;
    }

    public void setValor(Article valor) {
        Valor = valor;
    }
}
