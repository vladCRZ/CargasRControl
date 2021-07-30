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
public class OrdenTrabajoVO {
    private String viIDOrden;
    private String viIDRequerimiento;
    private String vsClienteNombre;
    private String vsClienteCorreo;

    public OrdenTrabajoVO() {
    
    }

    public OrdenTrabajoVO(String viIDOrden, String viIDRequerimiento) {
        this.viIDOrden = viIDOrden;
        this.viIDRequerimiento = viIDRequerimiento;
    }

    
    public String getViIDOrden() {
        return viIDOrden;
    }

    public void setViIDOrden(String viIDOrden) {
        this.viIDOrden = viIDOrden;
    }

    public String getViIDRequerimiento() {
        return viIDRequerimiento;
    }

    public void setViIDRequerimiento(String viIDRequerimiento) {
        this.viIDRequerimiento = viIDRequerimiento;
    }

    public String getVsClienteNombre() {
        return vsClienteNombre;
    }

    public void setVsClienteNombre(String vsClienteNombre) {
        this.vsClienteNombre = vsClienteNombre;
    }

    public String getVsClienteCorreo() {
        return vsClienteCorreo;
    }

    public void setVsClienteCorreo(String vsClienteCorreo) {
        this.vsClienteCorreo = vsClienteCorreo;
    }

    @Override
    public String toString() {
        return "OrdenTrabajoVO{" + "viIDOrden=" + viIDOrden + ", viIDRequerimiento=" + viIDRequerimiento + ", vsClienteNombre=" + vsClienteNombre + ", vsClienteCorreo=" + vsClienteCorreo + '}';
    }
    
}
