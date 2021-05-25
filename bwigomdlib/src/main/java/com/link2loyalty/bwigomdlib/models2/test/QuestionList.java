package com.link2loyalty.bwigomdlib.models2.test;

import java.util.ArrayList;

public class QuestionList {

    int idp;
    String pre;
    String tpr;
    String img;
    String mas;
    ArrayList<AnswerList> Respuestas;

    public QuestionList() { }


    public ArrayList<AnswerList> getRespuestas() {
        return Respuestas;
    }

    public void setRespuestas(ArrayList<AnswerList> respuestas) {
        Respuestas = respuestas;
    }

    public int getIdp() {
        return idp;
    }

    public void setIdp(int idp) {
        this.idp = idp;
    }

    public String getPre() {
        return pre;
    }

    public void setPre(String pre) {
        this.pre = pre;
    }

    public String getTpr() {
        return tpr;
    }

    public void setTpr(String tpr) {
        this.tpr = tpr;
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }

    public String getMas() {
        return mas;
    }

    public void setMas(String mas) {
        this.mas = mas;
    }


}
