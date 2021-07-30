/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.telcel.carga.correo;

import com.infomedia.utils.PropertyLoader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.activation.DataHandler;
import javax.activation.FileDataSource;
import javax.mail.Authenticator;
import javax.mail.BodyPart;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;

/**
 *
 * @author AMCruz
 */
public class Correos {

    private Properties prop = PropertyLoader.load("cargarm.properties");
    Properties voPropertiesproperties;
    javax.mail.Session veEmailSession;
    //ConsultasRemedyCloud voConsultasR = new ConsultasRemedyCloud();

    private void init() {
        voPropertiesproperties = new Properties();
        voPropertiesproperties.put("mail.host", prop.getProperty("MAIL_HOST"));
        //DRP
        //voPropertiesproperties.put("mail.host", "100.127.11.3");
        voPropertiesproperties.put("mail.user", prop.get("MAIL_USER"));
        voPropertiesproperties.put("mail.transport.protocol", "smtp");
        voPropertiesproperties.put("mail.smtp.sendpartial", "true");
        voPropertiesproperties.put("mail.debug", "true");
        veEmailSession = javax.mail.Session.getInstance(voPropertiesproperties,
                new Authenticator() {
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(prop.getProperty("MAIL_USER"), prop.getProperty("MAIL_PASS"));
            }
        });
        veEmailSession.setDebug(true);
    }

    private String fncMensajeHTML(String psCliente, List<String> voSistemas) {
        String vsMensajeHTML;
        vsMensajeHTML = "<html>\n"
                + "<body>\n"
                + "<div style=\"margin:0!important;padding:0!important;width:100%!important;background:#ffffff\" bgcolor=\"#ffffff\">\n"
                + "<table style=\"table-layout:fixed\" width=\"100%\" cellspacing=\"0\" cellpadding=\"0\" border=\"0\" bgcolor=\"#ffffff\">\n"
                + "<tbody>\n"
                + "<tr>\n"
                + "<td width=\"100%\" bgcolor=\"#ffffff\" align=\"center\">\n"
                + "<table class=\"m_8824328230994569334table\" width=\"650\" cellspacing=\"0\" cellpadding=\"0\" border=\"0\" align=\"center\">\n"
                + "<tbody>\n"
                + "<tr>\n"
                + "<td class=\"m_8824328230994569334cell\" style=\"padding:\" width=\"650\">\n"
                + "<div class=\"m_8824328230994569334eloqua-editable-content\">\n"
                + "<table class=\"m_8824328230994569334table\" style=\"width:100%;min-width:100%;\" width=\"100%\" cellspacing=\"0\" cellpadding=\"0\" border=\"0\" align=\"center\">\n"
                + "<tbody>\n"
                + "<tr>\n"
                + "<td style=\"color:#aaaaaa;font-family:'Roboto',Arial,sans-serif;font-weight:300;font-size:12px;line-height:16px\" align=\"center\">\n"
                + "Notificaci&oacute;n de Alta de Perfiles ADM\n"
                + "</td>\n"
                + "</tr>\n"
                + "</tbody>\n"
                + "</table>\n"
                + "</div>\n"
                + "</td>\n"
                + "</tr>\n"
                + "</tbody>\n"
                + "</table>\n"
                + "</td>\n"
                + "</tr>\n"
                + "</tbody>\n"
                + "</table>\n"
                + "<div class=\"m_8824328230994569334eloqua-editable-content\" style=\"text-align:center;color:#ffffff;font-family:'Roboto',Arial,sans-serif;font-weight:300;font-size:10px;line-height:10px;\">\n"
                + "<br>\n"
                + "</div>\n"
                + "<table bgcolor=\"#ffffff\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\" style=\"table-layout:fixed;width:100%;min-width:100%\" width=\"100%\">\n"
                + "<tbody>\n"
                + "<tr>\n"
                + "<td align=\"center\" bgcolor=\"#ffffff\" width=\"100%\">\n"
                + "<table align=\"center\" bgcolor=\"#2b3d51\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\" class=\"m_8824328230994569334table\" style=\"width:650px;height:20px;margin:auto\" width=\"650\" height=\"20\">\n"
                + "<tbody>\n"
                + "<tr>\n"
                + "<td>\n"
                + "&nbsp;\n"
                + "</td>\n"
                + "<td style=\"text-align:center;color:#ffffff;font-family:'Roboto',Arial,sans-serif;font-weight:300;font-size:11px;line-height:20px;\">\n"
                + "<table class=\"m_8824328230994569334table\" style=\"width:100%;min-width:100%\" width=\"100%\" cellspacing=\"0\" cellpadding=\"0\" border=\"0\" align=\"center\">\n"
                + "<tbody>\n"
                + "<tr>\n"
                + "<td class=\"m_8824328230994569334gap\" width=\"40\"></td>\n"
                + "<td valign=\"middle\" height=\"80\"><img alt=\"\" class=\"m_8824328230994569334logo-top CToWUd\" src=\"http://100.127.3.13:8080/SCVM/assets/rcloud.png\" width=\"100\" style=\"margin: 10px;\"></td>\n"
                + "<td class=\"m_8824328230994569334gap\" width=\"75\"></td>\n"
                + "</tr>\n"
                + "</tbody>\n"
                + "</table>\n"
                + "</td>\n"
                + "<td>\n"
                + "&nbsp;\n"
                + "</td>\n"
                + "</tr>\n"
                + "</tbody>\n"
                + "</table>\n"
                + "</td>\n"
                + "</tr>\n"
                + "</tbody>\n"
                + "</table>\n"
                + "<table style=\"table-layout:fixed\" width=\"100%\" cellspacing=\"0\" cellpadding=\"0\" border=\"0\" bgcolor=\"#ffffff\">\n"
                + "<tbody>\n"
                + "<tr>\n"
                + "<td width=\"100%\" bgcolor=\"#ffffff\" align=\"center\">\n"
                + "<table class=\"m_8824328230994569334table\" width=\"650\" cellspacing=\"0\" cellpadding=\"0\" border=\"0\" bgcolor=\"#f5f8f9\" align=\"center\">\n"
                + "<tbody>\n"
                + "<tr>\n"
                + "<td class=\"m_8824328230994569334cell\" width=\"650\" bgcolor=\"#f5f8f9\">"
                + "<div class=\"m_3471701221422051735eloqua-editable-content\">\n"
                + "<table style=\"width:100%;min-width:100%\" class=\"m_3471701221422051735table\" width=\"100%\" cellspacing=\"0\" cellpadding=\"0\" border=\"0\" align=\"center\">\n"
                + "<tbody>\n"
                + "<tr>\n"
                + "<td class=\"m_3471701221422051735eloqua-editable-content\" style=\"padding:0px 0 20px 0;color:#2b3d51;font-family:'Roboto',Arial,sans-serif;font-weight:100;font-size:42px;line-height:42px;margin:0;text-align:center\">\n"
                + "<strong style=\"padding:0px 0 20px 0;color:#2b3d51;font-family:'Roboto',Arial,sans-serif;font-weight:100;font-size:20px;line-height:20px;margin:0;text-align:center\">\n"
                + "<br>\n"
                + "</strong>\n"
                + "Alta de perfiles ADM"
                + "</td>\n"
                + "</tr>\n"
                + "</tbody>\n"
                + "</table>\n"
                + "</div>\n"
                //+ "Estimado Usuario: " + voResp.getVsResponsable()
                //+ "Se le notifica que las Ordenes de Trabajo de las siguientes Rutinas de mantenimiento no se generaron:"
                + "<div class=\"m_3471701221422051735eloqua-editable-content\">\n"
                + "<table style=\"width:100%;min-width:100%\" class=\"m_3471701221422051735table\" width=\"100%\" cellspacing=\"0\" cellpadding=\"0\" border=\"0\" align=\"center\">\n"
                + "<tbody>\n"
                + "<tr>\n"
                + "<td class=\"m_8824328230994569334gap\" width=\"40\">&nbsp;</td>\n"
                + "<td class=\"m_8824328230994569334eloqua-editable-content\" style=\"padding:0 0 20px 0;color:#000000;font-family:'Roboto',Arial,sans-serif;font-weight:300;font-size:14px;line-height:24px;margin:0\">"
                //+ vsMensaje

                + "Estimado usuario " + psCliente
                + "<br>"
                + "<br>"
                + "Se le notifica que algunos perfiles no se dieron de alta debido a que el sistema no existe, para realizar el alta de favor de levantar un requerimiento para alta de sistema llenando el formato que se anexa en este correo"
                + "<br>"
                + "Sistemas que no existen: "
                + "</td>\n"
                + "<td class=\"m_8824328230994569334gap\" width=\"40\">&nbsp;</td>\n"
                + "</tr>\n"
                + "</tbody>\n"
                + "</table>\n"
                + "</div>\n"
                + "<div class=\"m_8824328230994569334eloqua-editable-content\"></div>\n"
                + "<div class=\"m_3471701221422051735eloqua-editable-content\">\n"
                + "<table style=\"width:100%;min-width:100%\" class=\"m_3471701221422051735table\" width=\"100%\" cellspacing=\"0\" cellpadding=\"0\" border=\"0\" align=\"center\">\n"
                + "<tbody>\n"
                + "<tr>\n"
                + "<td class=\"m_8824328230994569334gap\" width=\"40\">&nbsp;</td>\n"
                + "<td style=\"padding:0 0 25px 0\">\n"
                + "<table cellspacing=\"0\" cellpadding=\"0\" border=\"0\">\n"
                + "<tbody>\n";
        for (String voSistema : voSistemas) {
            vsMensajeHTML += "<tr class=\"m_8824328230994569334eloqua-editable-content\">\n"
                    + "<td width=\"16\" valign=\"top\">\n"
                    + "<strong style=\"color:#2b3d51;font-family:'Roboto',Arial,sans-serif;font-weight:100;font-size:30px;line-height:20px;margin:0;\">&#8226;</strong>\n"
                    + "</td>\n"
                    + "<td class=\"m_8824328230994569334eloqua-editable-content\" style=\"padding:0 0 10px 6px;color:#000000;font-family:'Roboto',Arial,sans-serif;font-weight:300;font-size:14px;line-height:20px;margin:0\">\n"
                    + voSistema + "\n"
                    + "</td>\n"
                    + "</tr>\n";
        }

        vsMensajeHTML += "</tr>\n"
                + "</tbody>\n"
                + "</table>\n"
                + "</div>\n"
                + "<div class=\"m_8824328230994569334eloqua-editable-content\"></div>\n"
                + "<div class=\"m_8824328230994569334eloqua-editable-content\"></div>\n"
                + "</td>\n"
                + "</tr>\n"
                + "</tbody>\n"
                + "</table>\n"
                + "</td>\n"
                + "</tr>\n"
                + "</tbody>\n"
                + "</table>\n"
                + "<table bgcolor=\"#ffffff\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\" style=\"table-layout:fixed;width:100%;min-width:100%\" width=\"100%\">\n"
                + "<tbody>\n"
                + "<tr>\n"
                + "<td align=\"center\" bgcolor=\"#ffffff\" width=\"100%\">\n"
                + "<table align=\"center\" bgcolor=\"#2b3d51\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\" class=\"m_8824328230994569334table\" style=\"width:650px;margin:auto\" width=\"650\">\n"
                + "<tbody>\n"
                + "<tr>\n"
                + "<td>&nbsp;</td>\n"
                + "<td style=\"text-align:center;color:#ffffff;font-family:'Roboto',Arial,sans-serif;font-weight:300;font-size:11px;line-height:20px;margin:0;padding:10px 0 20px 0\">\n"
                + "<font color=\"#909090\">\n"
                + "Atentamente:<br>\n"
                + "Direcci&oacute;n de Operaci&oacute;n y Mantenimiento<br>\n"
                + "Sistemas de Gesti&oacute;n de Procesos<br>\n"
                + "</font>\n"
                + "</td>\n"
                + "<td>&nbsp;</td>\n"
                + "</tr>\n"
                + "</tbody>\n"
                + "</table>\n"
                + "</td>\n"
                + "</tr>\n"
                + "</tbody>\n"
                + "</table>\n"
                + "</div>\n"
                + "</body>\n"
                + "</html>";
        return vsMensajeHTML;
    }

    public void fncEnviarCorreo(String psClienteNom, String psClienteCorreo, List<String> psSistemas) throws MessagingException {
        try {
            //String vsCorreoResponsable = voConsultasR.fncCorreoUsuario(voResponsables.getVsResponsable());
            //if (vsCorreoResponsable != null && !vsCorreoResponsable.equals("")) {
            init();

            MimeMultipart voMultipart = new MimeMultipart();
            BodyPart voBodyPart = new MimeBodyPart();
            BodyPart voFormato = new MimeBodyPart();            
            voBodyPart.setContent(fncMensajeHTML(psClienteNom, psSistemas), "text/html");
            voFormato.setDataHandler(new DataHandler(new FileDataSource("c:/Logs/Alta_de_Categoria_de_Producto.xlsx")));
            voFormato.setFileName("Formato_Alta_De_Perfiles_ADM.xlsx");
            voMultipart.addBodyPart(voBodyPart);
            voMultipart.addBodyPart(voFormato);
            

            MimeMessage voMensaje = new MimeMessage(veEmailSession);
            voMensaje.setFrom(new InternetAddress(prop.getProperty("MAIL_CORREO")));
            voMensaje.addRecipient(Message.RecipientType.TO, new InternetAddress(psClienteCorreo));
            //voMensaje.addRecipient(Message.RecipientType.CC, new InternetAddress("soporte.tid@mail.telcel.com"));
            voMensaje.addRecipient(Message.RecipientType.TO, new InternetAddress("masiel.cruz@infomedia.com.mx"));
            voMensaje.setSubject("Notificaciones Rutinas de Mantenimiento");
            voMensaje.setContent(voMultipart);

            Transport voTransport = veEmailSession.getTransport("smtp");
            voTransport.connect();
            voTransport.sendMessage(voMensaje, voMensaje.getAllRecipients());
            voTransport.close();

        } catch (AddressException ex) {
            Logger.getLogger(Correos.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void mailSender(final MailConfig config) throws MessagingException{
        String asunto;
        Plantillas plant = new Plantillas();
        List<String> destinatarios = new ArrayList<String>();
        List<String> copia = new ArrayList<String>();
        MimeBodyPart html = new MimeBodyPart();
        Properties propiedades = new Properties();
        propiedades.put("mail.host", config.getHost());
        propiedades.put("mail.user", config.getUser());
        propiedades.put("mail.transport.protocol", config.getProtocol());
        propiedades.put("mail.smtp.sendpartial", config.getSendPartial());
        propiedades.put("mail.debug", config.getDebug());
        Session sesion = javax.mail.Session.getInstance(propiedades,
                new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(config.getUser(), config.getPassword());
            }
        });
        sesion.setDebug(config.getDebug().equals("true"));
        asunto = config.getAsunto();
        html.setContent(plant.loadPlantilla(config.getMembretes().split(":")[0], config.getMembretes().split(":")[1], config.getConfiguracion().split("-"), config.getContenido().split("-")), "text/html" );
        destinatarios = Arrays.asList(config.getDestinatarios());
        copia = Arrays.asList(config.getCopia());
        
        MimeMultipart multiPart = new MimeMultipart();
        multiPart.addBodyPart(html);

        MimeMessage mensaje = new MimeMessage(sesion);
        mensaje.setFrom(new InternetAddress(config.getFrom()));
        for(String recipient: destinatarios){
            mensaje.addRecipient(Message.RecipientType.TO, new InternetAddress(recipient));
        }
        for(String recipient: copia){
            mensaje.addRecipient(Message.RecipientType.TO, new InternetAddress(recipient));
        }
        mensaje.setSubject(asunto);
        mensaje.setContent(multiPart);

        Transport voTransport = sesion.getTransport(config.getProtocol());
        voTransport.connect();
        voTransport.sendMessage(mensaje, mensaje.getAllRecipients());
        voTransport.close();
    }
}
