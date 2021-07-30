/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.telcel.carga.entry;

/**
 *
 * @author ELopez
 */
//Clase validada en ambiente productivo 21/05/2021
public class CiVO {
    private String compania;
    private String sitioAlrma;
    private String nombre;
    private String descripcion;
    private String region;
    private String conectado;
    private String tecnologia;
    private String tipo;
    private String grupo;
    private String ip;

    public CiVO(String compania, String sitioAlrma, String nombre, String descripcion, String region, String conectado, String tecnologia, String tipo, String grupo, String ip) {
        this.compania = compania;
        this.sitioAlrma = sitioAlrma;
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.region = region;
        this.conectado = conectado;
        this.tecnologia = tecnologia;
        this.tipo = tipo;
        this.grupo = grupo;
        this.ip = ip;
    }

    public CiVO() {
    }

    public String getCompania() {
        return compania;
    }

    public String getSitioAlrma() {
        return sitioAlrma;
    }

    public String getNombre() {
        return nombre;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public String getRegion() {
        return region;
    }

    public String getConectado() {
        return conectado;
    }

    public String getTecnologia() {
        return tecnologia;
    }

    public String getTipo() {
        return tipo;
    }

    public String getGrupo() {
        return grupo;
    }

    public String getIp() {
        return ip;
    }

    public void setCompania(String compania) {
        this.compania = compania;
    }

    public void setSitioAlrma(String sitioAlrma) {
        this.sitioAlrma = sitioAlrma;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public void setRegion(String region) {
        this.region = region;
    }

    public void setConectado(String conectado) {
        this.conectado = conectado;
    }

    public void setTecnologia(String tecnologia) {
        this.tecnologia = tecnologia;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public void setGrupo(String grupo) {
        this.grupo = grupo;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    @Override
    public String toString() {
        return "Sitio{" + "compania=" + compania + ", sitioAlrma=" + sitioAlrma + ", nombre=" + nombre + ", descripcion=" + descripcion + ", region=" + region + ", conectado=" + conectado + ", tecnologia=" + tecnologia + ", tipo=" + tipo + ", grupo=" + grupo + ", ip=" + ip + '}';
    }
}
