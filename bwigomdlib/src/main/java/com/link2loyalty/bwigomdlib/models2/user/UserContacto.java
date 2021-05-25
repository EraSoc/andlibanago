package com.link2loyalty.bwigomdlib.models2.user;

import java.io.Serializable;

public class UserContacto implements Serializable {

    String ClvCon;
    String Tel1;
    String Tel2;
    String IdFace;
    String IdTwi;
    String IdIde;
    String NoIde;
    String Tip;

    public UserContacto() { }

    public String getClvCon() {
        return ClvCon;
    }

    public void setClvCon(String clvCon) {
        ClvCon = clvCon;
    }

    public String getTel1() {
        return Tel1;
    }

    public void setTel1(String tel1) {
        Tel1 = tel1;
    }

    public String getTel2() {
        return Tel2;
    }

    public void setTel2(String tel2) {
        Tel2 = tel2;
    }

    public String getIdFace() {
        return IdFace;
    }

    public void setIdFace(String idFace) {
        IdFace = idFace;
    }

    public String getIdTwi() {
        return IdTwi;
    }

    public void setIdTwi(String idTwi) {
        IdTwi = idTwi;
    }

    public String getIdIde() {
        return IdIde;
    }

    public void setIdIde(String idIde) {
        IdIde = idIde;
    }

    public String getNoIde() {
        return NoIde;
    }

    public void setNoIde(String noIde) {
        NoIde = noIde;
    }

    public String getTip() {
        return Tip;
    }

    public void setTip(String tip) {
        Tip = tip;
    }
}
