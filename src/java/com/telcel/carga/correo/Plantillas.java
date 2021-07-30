/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.telcel.carga.correo;

public class Plantillas {
    
    public Plantillas(){}
    
    public static String loadPlantilla(String memSup, String memInf, String[] config, String[] content){
        String compilado = "";
        if(config.length == content.length){
            compilado += PLANT_APERTURA;
            compilado += PLANT_MEMBRETE_SUP.replace("%membretesuperior%", memSup);
            compilado += PLANT_APERTURA_CUERPO;
            for(int i = 0; i < config.length; i++){
                switch(config[i]){
                    case "titulo":
                        compilado += PLANT_TITULO.replace("%titulo%", content[i]);
                    break;
                    case "subtitulo":
                        compilado += PLANT_SUBTITULO.replace("%subtitulo%", content[i]);
                    break;
                    case "contenido":
                        compilado += PLANT_CUERPO.replace("%cuerpo%", content[i]);
                    break;
                    case "accion":
                        compilado += PLANT_ACCION.replace("%nombreaccion%",content[i].split("_")[0]).replace("%accion%", content[i].split("_")[1]);
                    break;
                }
                compilado += PLANT_INTER_CLASE;
            }
            compilado += PLANT_CIERRE_CUERPO + PLANT_MEMBRETE_INF.replace("%membreteinferior%", memInf);
            compilado += PLANT_CIERRE;
        }else{
            return "ERR: Error en el numero de parametros";
        }
        return compilado;
    }
    
    private static String PLANT_APERTURA = "<div id=\":1s5\" class=\"ii gt\"><div id=\":1s4\" class=\"a3s aXjCH \"><div class=\"adM\">\n</div><div><div class=\"adM\">\n</div><div style=\"margin:0!important;padding:0!important;width:100%!important;background:#ffffff\" bgcolor=\"#ffffff\"><div class=\"adM\">\n</div>";
    private static String PLANT_MEMBRETE_SUP = "<table style=\"table-layout:fixed\" width=\"100%\" cellspacing=\"0\" cellpadding=\"0\" border=\"0\" bgcolor=\"#ffffff\">\n<tbody>\n<tr>\n<td width=\"100%\" bgcolor=\"#ffffff\" align=\"center\">\n<table class=\"m_6124330187735967420m_8824328230994569334table\" width=\"650\" cellspacing=\"0\" cellpadding=\"0\" border=\"0\" align=\"center\">\n<tbody>\n<tr>\n<td class=\"m_6124330187735967420m_8824328230994569334cell\" width=\"650\">\n<div class=\"m_6124330187735967420m_8824328230994569334eloqua-editable-content\">\n<table class=\"m_6124330187735967420m_8824328230994569334table\" style=\"width:100%;min-width:100%\" width=\"100%\" cellspacing=\"0\" cellpadding=\"0\" border=\"0\" align=\"center\">\n<tbody>\n<tr>\n<td style=\"color:#aaaaaa;font-family:'Roboto',Arial,sans-serif;font-weight:300;font-size:12px;line-height:16px\" align=\"center\">\n%membretesuperior%\n</td>\n</tr>\n</tbody>\n</table>\n</div>\n</td>\n</tr>\n</tbody>\n</table>\n</td>\n</tr>\n</tbody>\n</table>";
    private static String PLANT_APERTURA_CUERPO = "<div class=\"m_6124330187735967420m_8824328230994569334eloqua-editable-content\" style=\"text-align:center;color:#ffffff;font-family:'Roboto',Arial,sans-serif;font-weight:300;font-size:10px;line-height:10px\">\n<br>\n</div>\n<table bgcolor=\"#ffffff\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\" style=\"table-layout:fixed;width:100%;min-width:100%\" width=\"100%\">\n<tbody>\n<tr>\n<td align=\"center\" bgcolor=\"#ffffff\" width=\"100%\">\n<table align=\"center\" bgcolor=\"#2b3d51\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\" class=\"m_6124330187735967420m_8824328230994569334table\" style=\"width:650px;height:20px;margin:auto\" width=\"650\" height=\"20\">\n<tbody>\n<tr>\n<td>\n&nbsp;\n</td>\n<td style=\"text-align:center;color:#ffffff;font-family:'Roboto',Arial,sans-serif;font-weight:300;font-size:11px;line-height:20px\">\n<table class=\"m_6124330187735967420m_8824328230994569334table\" style=\"width:100%;min-width:100%\" width=\"100%\" cellspacing=\"0\" cellpadding=\"0\" border=\"0\" align=\"center\">\n<tbody>\n<tr>\n<td class=\"m_6124330187735967420m_8824328230994569334gap\" width=\"40\"></td>\n<td valign=\"middle\" height=\"80\">\n<td class=\"m_6124330187735967420m_8824328230994569334gap\" width=\"75\"></td>\n</tr>\n</tbody>\n</table>\n</td>\n<td>\n&nbsp;\n</td>\n</tr>\n</tbody>\n</table>\n</td>\n</tr>\n</tbody>\n</table>\n<table style=\"table-layout:fixed\" width=\"100%\" cellspacing=\"0\" cellpadding=\"0\" border=\"0\" bgcolor=\"#ffffff\">\n<tbody>\n<tr>\n<td width=\"100%\" bgcolor=\"#ffffff\" align=\"center\">\n<table class=\"m_6124330187735967420m_8824328230994569334table\" width=\"650\" cellspacing=\"0\" cellpadding=\"0\" border=\"0\" bgcolor=\"#f5f8f9\" align=\"center\">\n<tbody>\n<tr>\n<td class=\"m_6124330187735967420m_8824328230994569334cell\" width=\"650\" bgcolor=\"#f5f8f9\">";
    private static String PLANT_TITULO = "<div class=\"m_6124330187735967420m_3471701221422051735eloqua-editable-content\">\n<table style=\"width:100%;min-width:100%\" class=\"m_6124330187735967420m_3471701221422051735table\" width=\"100%\" cellspacing=\"0\" cellpadding=\"0\" border=\"0\" align=\"center\">\n<tbody>\n<tr>\n<td width=\"30\"></td>\n<td class=\"m_6124330187735967420m_3471701221422051735eloqua-editable-content\" style=\"padding:0px 0 20px 0;color:#2b3d51;font-family:'Roboto',Arial,sans-serif;font-weight:100;font-size:42px;line-height:42px;margin:0;text-align:center\">\n<strong style=\"padding:0px 0 20px 0;color:#2b3d51;font-family:'Roboto',Arial,sans-serif;font-weight:100;font-size:20px;line-height:20px;margin:0;text-align:center\">\n<br>\n</strong>\n%titulo%\n</td>\n<td width=\"30\"> </td>\n\n</tr>\n</tbody>\n</table>\n</div>";
    private static String PLANT_INTER_CLASE = "<div class=\"m_6124330187735967420m_8824328230994569334eloqua-editable-content\"></div>";
    private static String PLANT_SUBTITULO = "<div class=\"m_6124330187735967420m_3471701221422051735eloqua-editable-content\">\n<table style=\"width:100%;min-width:100%\" class=\"m_6124330187735967420m_3471701221422051735table\" width=\"100%\" cellspacing=\"0\" cellpadding=\"0\" border=\"0\" align=\"center\">\n<tbody>\n<tr>\n<td width=\"30\"> </td>\n<td class=\"m_6124330187735967420m_3471701221422051735eloqua-editable-content\" style=\"padding:0px 0 20px 0;color:#2b3d51;font-family:'Roboto',Arial,sans-serif;font-weight:100;font-size:19px;line-height:20px;margin:0;text-align:center\">\n%subtitulo%\n</td>\n<td width=\"30\"> </td>\n\n</tr>\n</tbody>\n</table>\n</div>";
    private static String PLANT_CUERPO = "<div class=\"m_6124330187735967420m_3471701221422051735eloqua-editable-content\">\n<table style=\"width:100%;min-width:100%\" class=\"m_6124330187735967420m_3471701221422051735table\" width=\"100%\" cellspacing=\"0\" cellpadding=\"0\" border=\"0\" align=\"center\">\n<tbody>\n<tr>\n<td class=\"m_6124330187735967420m_8824328230994569334gap\" width=\"40\">&nbsp;</td>\n<td class=\"m_6124330187735967420m_8824328230994569334eloqua-editable-content\" style=\"padding:0 0 20px 0;color:#000000;font-family:'Roboto',Arial,sans-serif;font-weight:300;font-size:16px;line-height:24px;margin:0\">\n%cuerpo%\n</td>\n<td class=\"m_6124330187735967420m_8824328230994569334gap\" width=\"40\">&nbsp;</td>\n\n</tr>\n</tbody>\n</table>\n</div>";
    private static String PLANT_ACCION = "<div class=\"m_6124330187735967420m_3471701221422051735eloqua-editable-content\">\n<table style=\"width:100%;min-width:100%\" class=\"m_6124330187735967420m_3471701221422051735table\" width=\"100%\" cellspacing=\"0\" cellpadding=\"0\" border=\"0\" align=\"center\">\n<tbody>\n<tr>\n<td width=\"30\"> </td>\n<td class=\"m_6124330187735967420m_3471701221422051735eloqua-editable-content\" style=\"padding:0px 0 20px 0;color:#2b3d51;font-family:'Roboto',Arial,sans-serif;font-weight:100;font-size:19px;line-height:20px;margin:0;text-align:center\">\n<a href=\"%accion%\" style=\"margin-bottom: 20px; background-color: #2b3d51; padding-left: 15px; padding-right: 15px; padding-top: 7px; padding-bottom: 7px; text-decoration: none; color: snow; border-radius: 5px;\">%nombreaccion%</a>\n</td>\n<td width=\"30\"> </td>\n\n</tr>\n</tbody>\n</table>\n</div>";
    private static String PLANT_CIERRE_CUERPO = "</td>\n</tr>\n</tbody>\n</table>\n</td>\n</tr>\n</tbody>\n</table>";
    private static String PLANT_MEMBRETE_INF = "<table bgcolor=\"#ffffff\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\" style=\"table-layout:fixed;width:100%;min-width:100%\" width=\"100%\">\n<tbody>\n<tr>\n<td align=\"center\" bgcolor=\"#ffffff\" width=\"100%\">\n<table align=\"center\" bgcolor=\"#2b3d51\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\" class=\"m_6124330187735967420m_8824328230994569334table\" style=\"width:650px;margin:auto\" width=\"650\">\n<tbody>\n<tr>\n<td>&nbsp;</td>\n<td style=\"text-align:center;color:#ffffff;font-family:'Roboto',Arial,sans-serif;font-weight:300;font-size:11px;line-height:20px;margin:0;padding:10px 0 20px 0\">\n<font color=\"#909090\">\n%membreteinferior%\n</font>\n</td>\n<td>&nbsp;</td>\n</tr>\n</tbody>\n</table>\n</td>\n</tr>\n</tbody>\n</table><div class=\"yj6qo\"></div>";
    private static String PLANT_CIERRE = "<div class=\"adL\">\n</div></div><div class=\"adL\">\n</div></div><div class=\"adL\">\n</div></div></div>";
}
