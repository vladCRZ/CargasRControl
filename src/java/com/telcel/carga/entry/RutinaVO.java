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
public class RutinaVO {
    private String vsRutina;
    private String vsCI;
    private String vsRegion;
    private String vsDuracion;
    private String vsFrecuencia;
    private String vsGrupoS;
    private String vsFechaC;
    private String vsHoraC;
    private String vsFechaI;
    private String vsResponsable;
    private String vsTipo;
    private String vsInstrucciones;
    private String vsNombreArchivo;
    private String vsCompañia;

    public RutinaVO() {
    }

    public RutinaVO(String vsRutina, String vsCI, String vsRegion, String vsDuracion, String vsFrecuencia, String vsGrupoS, String vsFechaC, String vsHoraC, String vsFechaI, String vsResponsable, String vsTipo, String vsInstrucciones, String vsNombreArchivo) {
        this.vsRutina = vsRutina;
        this.vsCI = vsCI;
        this.vsRegion = vsRegion;
        this.vsDuracion = vsDuracion;
        this.vsFrecuencia = vsFrecuencia;
        this.vsGrupoS = vsGrupoS;
        this.vsFechaC = vsFechaC;
        this.vsHoraC = vsHoraC;
        this.vsFechaI = vsFechaI;
        this.vsResponsable = vsResponsable;
        this.vsTipo = vsTipo;
        this.vsInstrucciones = vsInstrucciones;
        this.vsNombreArchivo = vsNombreArchivo;
    }

    public String getVsRutina() {
        return vsRutina;
    }

    public void setVsRutina(String vsRutina) {
        this.vsRutina = vsRutina;
    }

    public String getVsCI() {
        return vsCI;
    }

    public void setVsCI(String vsCI) {
        this.vsCI = vsCI;
    }

    public String getVsRegion() {
        return vsRegion;
    }

    public void setVsRegion(String vsRegion) {
        this.vsRegion = vsRegion;
    }

    public String getVsDuracion() {
        return vsDuracion;
    }

    public void setVsDuracion(String vsDuracion) {
        this.vsDuracion = vsDuracion;
    }

    public String getVsFrecuencia() {
        return vsFrecuencia;
    }

    public void setVsFrecuencia(String vsFrecuencia) {
        this.vsFrecuencia = vsFrecuencia;
    }

    public String getVsGrupoS() {
        return vsGrupoS;
    }

    public void setVsGrupoS(String vsGrupoS) {
        this.vsGrupoS = vsGrupoS;
    }

    public String getVsFechaC() {
        return vsFechaC;
    }

    public void setVsFechaC(String vsFechaC) {
        this.vsFechaC = vsFechaC;
    }

    public String getVsHoraC() {
        return vsHoraC;
    }

    public void setVsHoraC(String vsHoraC) {
        this.vsHoraC = vsHoraC;
    }

    public String getVsFechaI() {
        return vsFechaI;
    }

    public void setVsFechaI(String vsFechaI) {
        this.vsFechaI = vsFechaI;
    }

    public String getVsResponsable() {
        return vsResponsable;
    }

    public void setVsResponsable(String vsResponsable) {
        this.vsResponsable = vsResponsable;
    }

    public String getVsTipo() {
        return vsTipo;
    }

    public void setVsTipo(String vsTipo) {
        this.vsTipo = vsTipo;
    }

    public String getVsInstrucciones() {
        return vsInstrucciones;
    }

    public void setVsInstrucciones(String vsInstrucciones) {
        this.vsInstrucciones = vsInstrucciones;
    }

    public String getVsNombreArchivo() {
        return vsNombreArchivo;
    }

    public void setVsNombreArchivo(String vsNombreArchivo) {
        this.vsNombreArchivo = vsNombreArchivo;
    }

    public String getVsCompañia() {
        return vsCompañia;
    }

    public void setVsCompañia(String vsCompañia) {
        this.vsCompañia = vsCompañia;
    }
    
    
    
}
