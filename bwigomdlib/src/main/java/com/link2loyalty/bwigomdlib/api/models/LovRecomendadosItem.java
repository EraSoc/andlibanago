package com.link2loyalty.bwigomdlib.api.models;

import java.io.Serializable;

public class LovRecomendadosItem implements Serializable {

    private String  ClvPro;
    private String Ali;
    private String DesLar;
    private String DesCor;
    private String Cat;
    private String Cup;
    private String Logo;
    private String Ran;

    public LovRecomendadosItem() {
    }

    public LovRecomendadosItem(String clvPro, String ali, String desLar, String desCor, String cat,
                               String cup, String logo, String ran) {
        ClvPro = clvPro;
        Ali = ali;
        DesLar = desLar;
        DesCor = desCor;
        Cat = cat;
        Cup = cup;
        Logo = logo;
        Ran = ran;
    }

    public String getClvPro() {
        return ClvPro;
    }

    public void setClvPro(String clvPro) {
        ClvPro = clvPro;
    }

    public String getAli() {
        return Ali;
    }

    public void setAli(String ali) {
        Ali = ali;
    }

    public String getDesLar() {
        return DesLar;
    }

    public void setDesLar(String desLar) {
        DesLar = desLar;
    }

    public String getDesCor() {
        return DesCor;
    }

    public void setDesCor(String desCor) {
        DesCor = desCor;
    }

    public String getCat() {
        return Cat;
    }

    public void setCat(String cat) {
        Cat = cat;
    }

    public String getCup() {
        return Cup;
    }

    public void setCup(String cup) {
        Cup = cup;
    }

    public String getLogo() {
        return Logo;
    }

    public void setLogo(String logo) {
        Logo = logo;
    }

    public String getRan() {
        return Ran;
    }

    public void setRan(String ran) {
        Ran = ran;
    }
}
