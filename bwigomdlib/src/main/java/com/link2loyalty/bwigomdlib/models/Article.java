package com.link2loyalty.bwigomdlib.models;

public class Article {

    String id_articulo;
    String titulo;
    String contenido;
    String vigencia;
    String permanente;
    String vermasurl;
    String fecalta;
    String estatus;
    String img_art;

    public Article(String id_articulo, String titulo, String contenido, String vigencia, String permanente, String vermasurl, String fecalta, String estatus) {
        this.id_articulo = id_articulo;
        this.titulo = titulo;
        this.contenido = contenido;
        this.vigencia = vigencia;
        this.permanente = permanente;
        this.vermasurl = vermasurl;
        this.fecalta = fecalta;
        this.estatus = estatus;
    }

    public Article() {
    }

    public String getId_articulo() {
        return id_articulo;
    }

    public void setId_articulo(String id_articulo) {
        this.id_articulo = id_articulo;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getContenido() {
        return contenido;
    }

    public void setContenido(String contenido) {
        this.contenido = contenido;
    }

    public String getVigencia() {
        return vigencia;
    }

    public void setVigencia(String vigencia) {
        this.vigencia = vigencia;
    }

    public String getPermanente() {
        return permanente;
    }

    public void setPermanente(String permanente) {
        this.permanente = permanente;
    }

    public String getVermasurl() {
        return vermasurl;
    }

    public void setVermasurl(String vermasurl) {
        this.vermasurl = vermasurl;
    }

    public String getFecalta() {
        return fecalta;
    }

    public void setFecalta(String fecalta) {
        this.fecalta = fecalta;
    }

    public String getEstatus() {
        return estatus;
    }

    public void setEstatus(String estatus) {
        this.estatus = estatus;
    }

    public String getImg_art() {
        return img_art;
    }

    public void setImg_art(String img_art) {
        this.img_art = img_art;
    }
}
