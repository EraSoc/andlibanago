package com.link2loyalty.bwigo.api.models;

import java.io.Serializable;
import java.util.ArrayList;

public class DetUsuario implements Serializable {

    private ArrayList<DetUsuarioItem> Lov;
    private int err;
    private String men;
    private boolean val;
    private String ses;
    private int ite;

    public DetUsuario(ArrayList<DetUsuarioItem> lov, int err, String men, boolean val, String ses, int ite) {
        Lov = lov;
        this.err = err;
        this.men = men;
        this.val = val;
        this.ses = ses;
        this.ite = ite;
    }

    public ArrayList<DetUsuarioItem> getLov() {
        return Lov;
    }

    public void setLov(ArrayList<DetUsuarioItem> lov) {
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

    public static class DetUsuarioItem implements Serializable{
        MyGeneral General;
        MyContacto Contacto;
        MyDireccion Direccion;

        String foltar;
        String notar;
        String nom;
        String apa;
        String ama;

        public DetUsuarioItem() {
        }

        public DetUsuarioItem(MyGeneral general, MyContacto contacto, MyDireccion direccion, String foltar, String notar, String nom, String apa, String ama) {
            General = general;
            Contacto = contacto;
            Direccion = direccion;
            this.foltar = foltar;
            this.notar = notar;
            this.nom = nom;
            this.apa = apa;
            this.ama = ama;
        }

        public MyGeneral getGeneral() {
            return General;
        }

        public void setGeneral(MyGeneral general) {
            General = general;
        }

        public MyContacto getContacto() {
            return Contacto;
        }

        public void setContacto(MyContacto contacto) {
            Contacto = contacto;
        }

        public MyDireccion getDireccion() {
            return Direccion;
        }

        public void setDireccion(MyDireccion direccion) {
            Direccion = direccion;
        }

        public String getFoltar() {
            return foltar;
        }

        public void setFoltar(String foltar) {
            this.foltar = foltar;
        }

        public String getNotar() {
            return notar;
        }

        public void setNotar(String notar) {
            this.notar = notar;
        }

        public String getNom() {
            return nom;
        }

        public void setNom(String nom) {
            this.nom = nom;
        }

        public String getApa() {
            return apa;
        }

        public void setApa(String apa) {
            this.apa = apa;
        }

        public String getAma() {
            return ama;
        }

        public void setAma(String ama) {
            this.ama = ama;
        }

        public static class MyGeneral implements Serializable {
            String gen;
            String edociv;
            String ono;
            String ema;
            String nemp;
            String ocu;
            String fecafi;
            String ter;
            String emp;
            String fecven;

            public MyGeneral() {
            }

            public MyGeneral(String gen, String edociv, String ono, String ema, String nemp, String ocu, String fecafi, String ter, String emp, String fecven) {
                this.gen = gen;
                this.edociv = edociv;
                this.ono = ono;
                this.ema = ema;
                this.nemp = nemp;
                this.ocu = ocu;
                this.fecafi = fecafi;
                this.ter = ter;
                this.emp = emp;
                this.fecven = fecven;
            }

            public String getGen() {
                return gen;
            }

            public void setGen(String gen) {
                this.gen = gen;
            }

            public String getEdociv() {
                return edociv;
            }

            public void setEdociv(String edociv) {
                this.edociv = edociv;
            }

            public String getOno() {
                return ono;
            }

            public void setOno(String ono) {
                this.ono = ono;
            }

            public String getEma() {
                return ema;
            }

            public void setEma(String ema) {
                this.ema = ema;
            }

            public String getNemp() {
                return nemp;
            }

            public void setNemp(String nemp) {
                this.nemp = nemp;
            }

            public String getOcu() {
                return ocu;
            }

            public void setOcu(String ocu) {
                this.ocu = ocu;
            }

            public String getFecafi() {
                return fecafi;
            }

            public void setFecafi(String fecafi) {
                this.fecafi = fecafi;
            }

            public String getTer() {
                return ter;
            }

            public void setTer(String ter) {
                this.ter = ter;
            }

            public String getEmp() {
                return emp;
            }

            public void setEmp(String emp) {
                this.emp = emp;
            }

            public String getFecven() {
                return fecven;
            }

            public void setFecven(String fecven) {
                this.fecven = fecven;
            }
        }

        public static class MyContacto implements Serializable {
            String ClvCon;
            String Tel1;
            String Tel2;
            String IdFace;
            String IdTwi;
            String IdIde;
            String NoIde;
            String Tip;

            public MyContacto() {
            }

            public MyContacto(String clvCon, String tel1, String tel2, String idFace, String idTwi, String idIde, String noIde, String tip) {
                ClvCon = clvCon;
                Tel1 = tel1;
                Tel2 = tel2;
                IdFace = idFace;
                IdTwi = idTwi;
                IdIde = idIde;
                NoIde = noIde;
                Tip = tip;
            }

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

        public static class MyDireccion implements Serializable {
            String ClvDir;
            String IdEfe;
            String IdMun;
            String Col;
            String Pob;
            String Dom;
            String Nin;
            String Nex;
            String Cp;
            String Tip;

            public MyDireccion() {
            }

            public MyDireccion(String clvDir, String idEfe, String idMun, String col, String pob, String dom, String nin, String nex, String cp, String tip) {
                ClvDir = clvDir;
                IdEfe = idEfe;
                IdMun = idMun;
                Col = col;
                Pob = pob;
                Dom = dom;
                Nin = nin;
                Nex = nex;
                Cp = cp;
                Tip = tip;
            }

            public String getClvDir() {
                return ClvDir;
            }

            public void setClvDir(String clvDir) {
                ClvDir = clvDir;
            }

            public String getIdEfe() {
                return IdEfe;
            }

            public void setIdEfe(String idEfe) {
                IdEfe = idEfe;
            }

            public String getIdMun() {
                return IdMun;
            }

            public void setIdMun(String idMun) {
                IdMun = idMun;
            }

            public String getCol() {
                return Col;
            }

            public void setCol(String col) {
                Col = col;
            }

            public String getPob() {
                return Pob;
            }

            public void setPob(String pob) {
                Pob = pob;
            }

            public String getDom() {
                return Dom;
            }

            public void setDom(String dom) {
                Dom = dom;
            }

            public String getNin() {
                return Nin;
            }

            public void setNin(String nin) {
                Nin = nin;
            }

            public String getNex() {
                return Nex;
            }

            public void setNex(String nex) {
                Nex = nex;
            }

            public String getCp() {
                return Cp;
            }

            public void setCp(String cp) {
                Cp = cp;
            }

            public String getTip() {
                return Tip;
            }

            public void setTip(String tip) {
                Tip = tip;
            }
        }
    }
}
