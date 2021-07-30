/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.telcel.carga.correo;

import java.util.Arrays;
import java.util.Objects;

/**
 *
 * @author ELopez
 */
public class MailConfig {
    private String host;
    private String user;
    private String password;
    private String protocol;
    private String sendPartial;
    private String debug;
    private String asunto;
    private String[] destinatarios;
    private String[] copia;
    private String membretes;
    private String configuracion;
    private String contenido;
    private String from;
    
    public MailConfig(String host, String user, String protocol, String sendPartial, String debug, String asunto, String[] destinatarios, String[] copia, String membretes, String configuracion, String contenido, String password, String from) {
        this.host = host;
        this.user = user;
        this.protocol = protocol;
        this.sendPartial = sendPartial;
        this.debug = debug;
        this.asunto = asunto;
        this.destinatarios = destinatarios;
        this.copia = copia;
        this.membretes = membretes;
        this.configuracion = configuracion;
        this.contenido = contenido;
        this.password = password;
        this.from = from;
    }

    public String getFrom() {
        return from;
    }

    public void setFrom(String from) {
        this.from = from;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
    
    public String getHost() {
        return host;
    }

    public String getUser() {
        return user;
    }

    public String getProtocol() {
        return protocol;
    }

    public String getSendPartial() {
        return sendPartial;
    }

    public String getDebug() {
        return debug;
    }

    public String getAsunto() {
        return asunto;
    }

    public String[] getDestinatarios() {
        return destinatarios;
    }

    public String[] getCopia() {
        return copia;
    }

    public void setHost(String host) {
        this.host = host;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public void setProtocol(String protocol) {
        this.protocol = protocol;
    }

    public void setSendPartial(String sendPartial) {
        this.sendPartial = sendPartial;
    }

    public void setDebug(String debug) {
        this.debug = debug;
    }

    public void setAsunto(String asunto) {
        this.asunto = asunto;
    }

    public void setDestinatarios(String[] destinatarios) {
        this.destinatarios = destinatarios;
    }

    public void setCopia(String[] copia) {
        this.copia = copia;
    }

    public String getMembretes() {
        return membretes;
    }

    public String getConfiguracion() {
        return configuracion;
    }

    public String getContenido() {
        return contenido;
    }

    public void setMembretes(String membretes) {
        this.membretes = membretes;
    }

    public void setConfiguracion(String configuracion) {
        this.configuracion = configuracion;
    }

    public void setContenido(String contenido) {
        this.contenido = contenido;
    }

    @Override
    public String toString() {
        return "mailconfig{" + "host=" + host + ", user=" + user + ", password=" + password + ", protocol=" + protocol + ", sendPartial=" + sendPartial + ", debug=" + debug + ", asunto=" + asunto + ", destinatarios=" + destinatarios + ", copia=" + copia + ", membretes=" + membretes + ", configuracion=" + configuracion + ", contenido=" + contenido + ", from=" + from + '}';
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 59 * hash + Objects.hashCode(this.host);
        hash = 59 * hash + Objects.hashCode(this.user);
        hash = 59 * hash + Objects.hashCode(this.password);
        hash = 59 * hash + Objects.hashCode(this.protocol);
        hash = 59 * hash + Objects.hashCode(this.sendPartial);
        hash = 59 * hash + Objects.hashCode(this.debug);
        hash = 59 * hash + Objects.hashCode(this.asunto);
        hash = 59 * hash + Arrays.deepHashCode(this.destinatarios);
        hash = 59 * hash + Arrays.deepHashCode(this.copia);
        hash = 59 * hash + Objects.hashCode(this.membretes);
        hash = 59 * hash + Objects.hashCode(this.configuracion);
        hash = 59 * hash + Objects.hashCode(this.contenido);
        hash = 59 * hash + Objects.hashCode(this.from);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final MailConfig other = (MailConfig) obj;
        if (!Objects.equals(this.host, other.host)) {
            return false;
        }
        if (!Objects.equals(this.user, other.user)) {
            return false;
        }
        if (!Objects.equals(this.password, other.password)) {
            return false;
        }
        if (!Objects.equals(this.protocol, other.protocol)) {
            return false;
        }
        if (!Objects.equals(this.sendPartial, other.sendPartial)) {
            return false;
        }
        if (!Objects.equals(this.debug, other.debug)) {
            return false;
        }
        if (!Objects.equals(this.asunto, other.asunto)) {
            return false;
        }
        if (!Objects.equals(this.membretes, other.membretes)) {
            return false;
        }
        if (!Objects.equals(this.configuracion, other.configuracion)) {
            return false;
        }
        if (!Objects.equals(this.contenido, other.contenido)) {
            return false;
        }
        if (!Objects.equals(this.from, other.from)) {
            return false;
        }
        if (!Arrays.deepEquals(this.destinatarios, other.destinatarios)) {
            return false;
        }
        if (!Arrays.deepEquals(this.copia, other.copia)) {
            return false;
        }
        return true;
    }
}
