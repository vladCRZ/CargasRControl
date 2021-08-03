/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.telcel.carga.entry;

/**
 *
 * @author AMCruz
 */
//Clase validada en ambiente productivo 21/05/2021
public class AdmVO {
    private String vsCompania;
    private String vsSistema;
    private String vsNodo;
    private String vsRegion;
    private String vsPerfil;
    private String vsPrimerAName;
    private String vsPrimerANum;
    private String vsSegundoAName;
    private String vsSegundoANum;
    private String vsGrupo;
    private String vsGrupoSoporte;
    private String vsMensajeMail;

    public AdmVO() {
    }

    public AdmVO(String vsCompania, String vsSistema, String vsNodo, String vsRegion, String vsPerfil, String vsPrimerAName, String vsPrimerANum, String vsSegundoAName, String vsSegundoANum, String vsGrupo, String vsMensajeMail) {
        this.vsCompania = vsCompania;
        this.vsSistema = vsSistema;
        this.vsNodo = vsNodo;
        this.vsRegion = vsRegion;
        this.vsPerfil = vsPerfil;
        this.vsPrimerAName = vsPrimerAName;
        this.vsPrimerANum = vsPrimerANum;
        this.vsSegundoAName = vsSegundoAName;
        this.vsSegundoANum = vsSegundoANum;
        this.vsGrupo = vsGrupo;
        this.vsMensajeMail = vsMensajeMail;
    }

    public String getVsCompania() {
        return vsCompania;
    }

    public void setVsCompania(String vsCompania) {
        this.vsCompania = vsCompania;
    }

    public String getVsSistema() {
        return vsSistema;
    }

    public void setVsSistema(String vsSistema) {
        this.vsSistema = vsSistema;
    }

    public String getVsNodo() {
        return vsNodo;
    }

    public void setVsNodo(String vsNodo) {
        this.vsNodo = vsNodo;
    }

    public String getVsRegion() {
        return vsRegion;
    }

    public void setVsRegion(String vsRegion) {
        this.vsRegion = vsRegion;
    }

    public String getVsPerfil() {
        return vsPerfil;
    }

    public void setVsPerfil(String vsPerfil) {
        this.vsPerfil = vsPerfil;
    }

    public String getVsPrimerAName() {
        return vsPrimerAName;
    }

    public void setVsPrimerAName(String vsPrimerAName) {
        this.vsPrimerAName = vsPrimerAName;
    }

    public String getVsPrimerANum() {
        return vsPrimerANum;
    }

    public void setVsPrimerANum(String vsPrimerANum) {
        this.vsPrimerANum = vsPrimerANum;
    }

    public String getVsSegundoAName() {
        return vsSegundoAName;
    }

    public void setVsSegundoAName(String vsSegundoAName) {
        this.vsSegundoAName = vsSegundoAName;
    }

    public String getVsSegundoANum() {
        return vsSegundoANum;
    }

    public void setVsSegundoANum(String vsSegundoANum) {
        this.vsSegundoANum = vsSegundoANum;
    }

    public String getVsGrupo() {
        return vsGrupo;
    }

    public void setVsGrupo(String vsGrupo) {
        this.vsGrupo = vsGrupo;
    }

    public String getVsGrupoSoporte() {
        return vsGrupoSoporte;
    }

    public void setVsGrupoSoporte(String vsGrupoSoporte) {
        this.vsGrupoSoporte = vsGrupoSoporte;
    }
    
    
    
    public String getVsMensajeMail() {
        return vsMensajeMail;
    }

    public void setVsMensajeMail(String vsMensajeMail) {
        this.vsMensajeMail = vsMensajeMail;
    }

    @Override
    public String toString() {
        return "AdmVO{" +
                "vsCompania='" + vsCompania + '\'' +
                ", vsSistema='" + vsSistema + '\'' +
                ", vsNodo='" + vsNodo + '\'' +
                ", vsRegion='" + vsRegion + '\'' +
                ", vsPerfil='" + vsPerfil + '\'' +
                ", vsPrimerAName='" + vsPrimerAName + '\'' +
                ", vsPrimerANum='" + vsPrimerANum + '\'' +
                ", vsSegundoAName='" + vsSegundoAName + '\'' +
                ", vsSegundoANum='" + vsSegundoANum + '\'' +
                ", vsGrupo='" + vsGrupo + '\'' +
                ", vsGrupoSoporte='" + vsGrupoSoporte + '\'' +
                ", vsMensajeMail='" + vsMensajeMail + '\'' +
                '}';
    }
}
