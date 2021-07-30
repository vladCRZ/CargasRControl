package com.telcel.carga.Utileria;

import com.infomedia.utils.PropertyLoader;
import com.telcel.carga.entry.AdmVO;
import com.telcel.carga.entry.CiVO;
import com.telcel.carga.entry.OrdenTrabajoVO;
import com.telcel.carga.entry.RutinaVO;
import com.telcel.rcontrol.services.remedy.generic.Field;
import com.telcel.rcontrol.services.remedy.generic.FormEntry;
import com.telcel.rcontrol.services.remedy.generic.InsertStatus;
import com.telcel.rcontrol.services.remedy.generic.ListOfFields;
import com.telcel.rcontrol.services.remedy.generic.RemedyFault;
import com.telcel.rcontrol.services.remedy.generic.RemedyPort;
import com.telcel.rcontrol.services.remedy.generic.RemedyUtilsPort;
import com.telcel.rcontrol.services.remedy.generic.UpdateStatus;
import java.io.PrintStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Properties;
import java.util.logging.Level;
import javax.xml.namespace.QName;
import javax.xml.ws.Service;

//Clase validada en ambiente productivo 21/05/2021
public class RemedyControl
{
  private static final org.apache.log4j.Logger log = org.apache.log4j.Logger.getLogger(RemedyControl.class);
  Properties prop = PropertyLoader.load("cargarm.properties");
  private final String vsUsuarioRC = this.prop.getProperty("USER_RM");
  private final String vsUsuarioADM = this.prop.getProperty("USER_ADM");
  private final String vsUsuarioCI = this.prop.getProperty("USER_CI");
  private final String vsFormularioRC = this.prop.getProperty("FORM_WO");
  private final String vsFormularioRM = this.prop.getProperty("FORM_RM");
  private final String vsFormularioWOI = this.prop.getProperty("FORM_DESCARGA");
  private final String vsFomularioAMX = this.prop.getProperty("FORM_AMX");
  private final String vsEntryID = this.prop.getProperty("ENTRY_ID_DESCARGA");
  private final String vsFielID = this.prop.getProperty("FIEL_ID_DESCARGA");
  private final String vsFormularioCMDB = this.prop.getProperty("FORM_CMDB");
  private final String vsFormularioGrupo = this.prop.getProperty("FORM_SUPG");
  private final String vsFormularioResp = this.prop.getProperty("FORM_RESP");
  private final String vsFormularioNomina = this.prop.getProperty("FORM_NOMINA");
  private final String vsFormularioWOInfo = this.prop.getProperty("FORM_WOINFO");
  private final String vsFormularioSistemas = this.prop.getProperty("FORM_SISTEMAS");
  private final String vsFormularioNodo = this.prop.getProperty("FORM_NODO");
  private final String vsFormularioSITEP = this.prop.getProperty("FORM_SITEP");
  private final String vsFormularioPerfiles = this.prop.getProperty("FORM_PERFILES");
  private final String vsFormularioAuto = this.prop.getProperty("FORM_AUTORIZADORES");
  private static List<String> voErrores = new ArrayList();
  private static String vsError = "";
  
  public List<String> getVoErrores()
  {
    return voErrores;
  }
  
  public void setVoErrores(List<String> voErrores)
  {
    this.voErrores = voErrores;
  }
  
  public boolean fncInsertComentario(String psTitulo, String psNota, String psWOID)
  {
    boolean vbRes = false;
    try
    {
      RemedyPort voRP = getPort(this.prop.getProperty("wsdlURL"));
      ListOfFields voFields = new ListOfFields();
      voFields.addField(new Field(1000000000, psTitulo));
      voFields.addField(new Field(1000000151, psNota));
      voFields.addField(new Field(1000000761, "Public"));
      voFields.addField(new Field(1000000655, "System Assignment"));
      voFields.addField(new Field(1000000182, psWOID));
      voFields.addField(new Field(1000000076, "NEW"));
      voFields.addField(new Field(1000002607, psWOID));
      InsertStatus Is = voRP.rmdInsert(this.vsUsuarioADM, this.vsFormularioWOInfo, voFields, null);
      
      voFields = new ListOfFields();
      voFields.addField(new Field(1000001476, "Yes"));
      voRP.rmdUpdate(this.vsUsuarioADM, this.vsFormularioWOInfo, Is.getCreatedEntry(), voFields);
      vbRes = true;
    }
    catch (Exception e)
    {
      System.out.println(e);
    }
    return vbRes;
  }
  
  public boolean fncInsertRutinas(List<RutinaVO> poListRM, String psIdWO)
  {
    boolean vbStatus = false;
    int cont = 4;
    try
    {
      RemedyPort voRP = getPort(this.prop.getProperty("wsdlURL"));
      for (RutinaVO rutinaVO : poListRM)
      {
        vsError = "Fila #"+ cont +": Error al generar la rutina " + rutinaVO.getVsRutina() + "\nSitio: " + rutinaVO.getVsCI() + "\nFecha de creación: " + rutinaVO.getVsFechaC() + "\nCompañía: " + rutinaVO.getVsCompa\u00F1ia() + "\nRegión " + rutinaVO.getVsRegion() + " debido a: ";
        if ((fncValidarTipoRutina(rutinaVO.getVsTipo()) & fncValidarCI(rutinaVO.getVsCI()) & fncValidarGrupo(rutinaVO.getVsGrupoS()) & fncValidarResponsable(rutinaVO.getVsResponsable()) & fncValidarFechasC(rutinaVO.getVsFechaC()) & fncValidarFechaI(rutinaVO.getVsFechaI()) & fncValidarFechas(rutinaVO.getVsFechaC(), rutinaVO.getVsHoraC(), rutinaVO.getVsFechaI())))
        {
          ListOfFields voFields = new ListOfFields();
          voFields.addField(new Field(7, "1"));
          voFields.addField(new Field(536870915, rutinaVO.getVsRutina()));
          
          voFields.addField(new Field(8, rutinaVO.getVsTipo()));
          if (rutinaVO.getVsFrecuencia().equals("0")) {
            voFields.addField(new Field(536870916, "1"));
          } else {
            voFields.addField(new Field(536870916, rutinaVO.getVsFrecuencia()));
          }
          voFields.addField(new Field(536870933, rutinaVO.getVsInstrucciones()));
          voFields.addField(new Field(536870922, rutinaVO.getVsDuracion()));
          voFields.addField(new Field(536870923, rutinaVO.getVsResponsable()));
          voFields.addField(new Field(536870932, rutinaVO.getVsRegion()));
          voFields.addField(new Field(536870914, rutinaVO.getVsCI()));
          voFields.addField(new Field(536870913, rutinaVO.getVsGrupoS()));
          voFields.addField(new Field(536870920, rutinaVO.getVsFechaC()));
          
          voFields.addField(new Field(536870921, rutinaVO.getVsFechaI()));
          
          voFields.addField(new Field(1000000001, rutinaVO.getVsCompa\u00F1ia()));
          if ((rutinaVO.getVsNombreArchivo().equals("")) || (rutinaVO.getVsNombreArchivo() == null)) {
            voFields.addField(new Field(536870918, "N/A"));
          } else {
            voFields.addField(new Field(536870918, rutinaVO.getVsNombreArchivo()));
          }
          voFields.addField(new Field(536870931, psIdWO));
          try
          {
            InsertStatus localInsertStatus = voRP.rmdInsert(this.vsUsuarioRC, this.vsFormularioRM, voFields, null);
          }
          catch (Exception e)
          {
            vsError += " REGISTRO DUPLICADO \n\n";
            voErrores.add(vsError);
          }
          vbStatus = true;
        }
        else
        {
          System.out.println(vsError);
          vsError += "\n";
          voErrores.add(vsError);
        }
        cont = cont + 1;
      }
    }
    catch (Exception e)
    {
      RemedyPort voRP;
      log.error(e);
    }
    return vbStatus;
  }
  
  public List<OrdenTrabajoVO> fncConsultaOrdenesT(String psTipoWO)
  {
    List<OrdenTrabajoVO> voListRes = new ArrayList();
    
    String usuario = this.prop.getProperty("USER_" + psTipoWO);
    try
    {
      RemedyPort voRP = getPort(this.prop.getProperty("wsdlURL"));
      if (!psTipoWO.equals("ADM")) {
          
        if (psTipoWO.equals("CAMBIO"))
            {
                for (ListOfFields lofpeop : voRP.rmdSelect(usuario, this.vsFormularioRC, "'1000000181'=2000 AND '1000001272'=\"REMEDY\" AND '7'=0 AND '1000000000' LIKE \"%CLOUD:REMEDY_CAMBIO DE PERFILES EN ADM%\"", new int[] { 301572100, 1000000182, 1000000048, 1000000019, 1000000020, 1000000018 }, null).getEntry())
                    {
                      String vsNombreCliente = "";
                      vsNombreCliente = vsNombreCliente + lofpeop.getValue(1000000019);
                      if (!lofpeop.getValue(1000000020).equals("")) {
                        vsNombreCliente = vsNombreCliente + " " + lofpeop.getValue(1000000020);
                      }
                      vsNombreCliente = vsNombreCliente + " " + lofpeop.getValue(1000000018);
                      OrdenTrabajoVO voOrden = new OrdenTrabajoVO();
                      voOrden.setViIDOrden(lofpeop.getValue(1000000182));
                      voOrden.setViIDRequerimiento(lofpeop.getValue(301572100));
                      voOrden.setVsClienteCorreo(lofpeop.getValue(1000000048));
                      voOrden.setVsClienteNombre(vsNombreCliente);
                      voListRes.add(voOrden);
                    }
            
            }
        else
            {
                for (ListOfFields lofpeop : voRP.rmdSelect(usuario, this.vsFormularioRC, "'1000000181'=2000 AND '1000001272'=\"REMEDY\" AND '7'=0 AND '1000000151' LIKE \"%ALTA DE " + psTipoWO + "%\"", new int[] { 301572100, 1000000182, 1000000048, 1000000019, 1000000020, 1000000018 }, null).getEntry())
                    {
                      String vsNombreCliente = "";
                      vsNombreCliente = vsNombreCliente + lofpeop.getValue(1000000019);
                      if (!lofpeop.getValue(1000000020).equals("")) {
                        vsNombreCliente = vsNombreCliente + " " + lofpeop.getValue(1000000020);
                      }
                      vsNombreCliente = vsNombreCliente + " " + lofpeop.getValue(1000000018);
                      OrdenTrabajoVO voOrden = new OrdenTrabajoVO();
                      voOrden.setViIDOrden(lofpeop.getValue(1000000182));
                      voOrden.setViIDRequerimiento(lofpeop.getValue(301572100));
                      voOrden.setVsClienteCorreo(lofpeop.getValue(1000000048));
                      voOrden.setVsClienteNombre(vsNombreCliente);
                      voListRes.add(voOrden);
                    }
            
            }

      } else {
        for (ListOfFields lofpeop : voRP.rmdSelect(usuario, this.vsFormularioRC, "'1000000181'=2000 AND '1000001272'=\"REMEDY\" AND '7'=0 AND '1000000000' LIKE \"%CLOUD:REMEDY_ALTA DE PERFILES EN ADM%\"", new int[] { 301572100, 1000000182, 1000000048, 1000000019, 1000000020, 1000000018 }, null).getEntry())
        {
          String vsNombreCliente = "";
          vsNombreCliente = vsNombreCliente + lofpeop.getValue(1000000019);
          if (!lofpeop.getValue(1000000020).equals("")) {
            vsNombreCliente = vsNombreCliente + " " + lofpeop.getValue(1000000020);
          }
          vsNombreCliente = vsNombreCliente + " " + lofpeop.getValue(1000000018);
          OrdenTrabajoVO voOrden = new OrdenTrabajoVO();
          voOrden.setViIDOrden(lofpeop.getValue(1000000182));
          voOrden.setViIDRequerimiento(lofpeop.getValue(301572100));
          voOrden.setVsClienteCorreo(lofpeop.getValue(1000000048));
          voOrden.setVsClienteNombre(vsNombreCliente);
          voListRes.add(voOrden);
        }
      }
    }
    catch (MalformedURLException e)
    {
      log.error(e);
    }
    catch (RemedyFault ex)
    {
      java.util.logging.Logger.getLogger(RemedyControl.class.getName()).log(Level.SEVERE, null, ex);
    }
    return voListRes;
  }
  
  public void fncCerrarWO(String psIDWO, String psStatus)
  {
    UpdateStatus voStatus = new UpdateStatus();
    try
    {
      RemedyPort voRP = getPort(this.prop.getProperty("wsdlURL"));
      
      ListOfFields voListOfFields = new ListOfFields();
      
      voListOfFields.addField(new Field(1000003228, "TELCEL"));
      
      voListOfFields.addField(new Field(1000003229, "CORP-TD-DIGITALIZACION DE SERVICIOS"));
      voListOfFields.addField(new Field(1000003234, "SGP000000000344"));
      voListOfFields.addField(new Field(1000003227, "TRANSFORMACION DIGITAL"));
      
      //voListOfFields.addField(new Field(1000003227, "OYM_SISTEMAS TI"));
      //voListOfFields.addField(new Field(1000003229, "CORP-OPMA-GESTION DE PROCESOS"));
      //voListOfFields.addField(new Field(1000003234, "SGP000000000344"));
      
      voListOfFields.addField(new Field(1000003230, "CUENTA GENERICA RCONTROL ADM"));
      voStatus = voRP.rmdUpdate(this.vsUsuarioADM, this.vsFormularioRC, psIDWO, voListOfFields);
      
      voListOfFields = new ListOfFields();
      voListOfFields.addField(new Field(7, "4"));
      voStatus = voRP.rmdUpdate(this.vsUsuarioADM, this.vsFormularioRC, psIDWO, voListOfFields);
      if (voStatus.getStatus().equals("UPDATED"))
      {
        voListOfFields = new ListOfFields();
        voListOfFields.addField(new Field(1000000881, psStatus));
        voListOfFields.addField(new Field(1000000150, psStatus));
        voListOfFields.addField(new Field(7, "5"));
        voRP.rmdUpdate(this.vsUsuarioADM, this.vsFormularioRC, psIDWO, voListOfFields);
      }
    }
    catch (Exception e)
    {
      log.error(e);
    }
  }
  
  public void fncPendienteWO(String psIDWO)
  {
    UpdateStatus voStatus = new UpdateStatus();
    try
    {
      RemedyPort voRP = getPort(this.prop.getProperty("wsdlURL"));
      ListOfFields voListOfFields = new ListOfFields();
      voListOfFields.addField(new Field(7, "1"));
      voListOfFields.addField(new Field(1000000881, "34000"));
      
      voStatus = voRP.rmdUpdate(this.vsUsuarioRC, this.vsFormularioRC, psIDWO, voListOfFields);
    }
    catch (Exception e)
    {
      log.error(e);
    }
  }
  
  public void fncCancelarWO(String psIDWO)
  {
    UpdateStatus voStatus = new UpdateStatus();
    try
    {
      RemedyPort voRP = getPort(this.prop.getProperty("wsdlURL"));
      ListOfFields voListOfFields = new ListOfFields();
      
      voListOfFields.addField(new Field(1000003228, "TELCEL"));
      
      voListOfFields.addField(new Field(1000003227, "TRANSFORMACION DIGITAL"));
      voListOfFields.addField(new Field(1000003229, "CORP-TD-DIGITALIZACION DE SERVICIOS"));
      voListOfFields.addField(new Field(1000003234, "SGP000000000344"));
      
      //voListOfFields.addField(new Field(1000003227, "OYM_SISTEMAS TI"));
      //voListOfFields.addField(new Field(1000003229, "CORP-OPMA-GESTION DE PROCESOS"));
      //voListOfFields.addField(new Field(1000003234, "SGP000000000344"));
      
      
      voListOfFields.addField(new Field(1000003230, "CUENTA GENERICA RCONTROL ADM"));
      voStatus = voRP.rmdUpdate(this.vsUsuarioADM, this.vsFormularioRC, psIDWO, voListOfFields);
      
      voListOfFields = new ListOfFields();
      
      voListOfFields.addField(new Field(7, "7"));
      voListOfFields.addField(new Field(1000000881, "Cancelled by Support"));
      voListOfFields.addField(new Field(1000000150, "Cancelled by Support"));
      
      voRP.rmdUpdate(this.vsUsuarioADM, this.vsFormularioRC, psIDWO, voListOfFields);
    }
    catch (Exception e)
    {
      log.error("ERROR fncCancelarWO " + e);
    }
  }
  
/*     */   public boolean fncDescargarArchivo(String psRequerimiento) {
/* 264 */     boolean vbRes = false;
/*     */     try {
/* 266 */       RemedyUtilsPort voUtilsPort = getUtilPort(this.prop.getProperty("portUtils"));
/* 267 */       RemedyPort voRP = getPort(this.prop.getProperty("wsdlURL"));
/* 268 */       String vsIDWorkInfo = "";
/* 269 */       for (String campo : this.vsFielID.split(",")) {
/* 270 */         System.out.println(this.vsUsuarioRC);
/* 271 */         System.out.println(this.vsFormularioWOI);
/* 272 */         System.out.println(this.vsEntryID);
/* 273 */         System.out.println(campo);
/* 274 */         for (ListOfFields lofpeop : voRP.rmdSelect(this.vsUsuarioRC, this.vsFormularioWOI, "'1000000829'=\"" + psRequerimiento + "\"", new int[] { 1 }, null).getEntry())
/*     */         {
    
/*     */ 
/* 277 */           vsIDWorkInfo = lofpeop.getValue(1);
                    
/*     */         
/* 279 */         System.out.println("vsIDWorkInfo" + vsIDWorkInfo);
/* 280 */         if (voUtilsPort.rmdDownloadAttachment("RM", this.vsFormularioWOI, vsIDWorkInfo, Integer.parseInt(campo))) {
/* 281 */           vbRes = true;
                    }
/*     */         }
/*     */       }
/*     */     }
/*     */     catch (Exception e) {
/* 286 */       log.error(e);
/*     */     }
/* 288 */     System.out.println("?????????????#####" + vbRes);
/*     */     
/* 290 */     return vbRes;
/*     */   }
  
  
  public String fncRecuperarNombreArchivo(String psRequerimiento)
  {
    String filename = "";
    try
    {
      RemedyPort voRP = getPort(this.prop.getProperty("wsdlURL"));
      String vsIDWorkInfo = "";
      
      for (String campo : this.vsFielID.split(","))
      {
        System.out.println(this.vsUsuarioRC);
        System.out.println(this.vsFormularioWOI);
        System.out.println(this.vsEntryID);
        System.out.println(campo);
        for (ListOfFields lofpeop : voRP.rmdSelect(this.vsUsuarioRC, this.vsFormularioWOI, "'1000000829'=\"" + psRequerimiento + "\"", new int[] { 1 , Integer.parseInt(campo)}, null).getEntry()) {
          vsIDWorkInfo = lofpeop.getValue(1);
          filename = lofpeop.getValue(Integer.parseInt(campo));
          if(!filename.isEmpty())
              return filename;
        }
        
      }
    }
    catch (Exception e)
    {
      log.error(e);
    }
    System.out.println("filename: " + filename);
    
    return filename;
  }
  
  public boolean fncValidarCI(String psCI)
  {
    boolean vbRes = false;
    try
    {
      RemedyPort voRP = getPort(this.prop.getProperty("wsdlURL"));
      for (ListOfFields lofpeop : voRP.rmdSelect(this.vsUsuarioRC, this.vsFormularioCMDB, "('200000020'=\"" + psCI + "\" AND '400127400'=\"BMC.ASSET\" AND '7'=3) OR ('200000020'=\"" + psCI + "\" AND '400127400'=\"BMC.ASSET\" AND '7'=5)", new int[] { 200000020 }, null).getEntry()) {
        if (lofpeop.getValue(200000020).equals(psCI)) {
          vbRes = true;
        }
      }
    }
    catch (Exception e)
    {
      log.error(e);
      System.out.println("ERROR fncValidarCI");
    }
    if (!vbRes) {
      vsError = vsError + " SITIO " + psCI + " NO SE ENCUENTRA EN ESTATUS ACTIVO \n";
    }
    return vbRes;
  }
  
  public boolean fncValidarTipoRutina(String psTipoRutina)
  {
    boolean vbRes = true;
    List<String> voListTipos = new ArrayList();
    voListTipos.add("CALENDARIZADA");
    voListTipos.add("LABORAL");
    voListTipos.add("DIARIA");
    voListTipos.add("SEMANAL");
    voListTipos.add("MENSUAL");
    if (!voListTipos.contains(psTipoRutina))
    {
      vsError = vsError + " EL TIPO DE RUTINA " + psTipoRutina + " NO EXISTE \n";
      vbRes = false;
    }
    return vbRes;
  }
  
  public boolean fncValidarGrupo(String psGrupo)
  {
    boolean vbRes = false;
    try
    {
      RemedyPort voRP = getPort(this.prop.getProperty("wsdlURL"));
      for (ListOfFields lofpeop : voRP.rmdSelect(this.vsUsuarioRC, this.vsFormularioGrupo, "'1000000015'=\"" + psGrupo + "\"", new int[] { 1000000015 }, null).getEntry()) {
        if (lofpeop.getValue(1000000015).equals(psGrupo)) {
          vbRes = true;
        }
      }
    }
    catch (Exception e)
    {
      log.error(e);
      System.out.println("ERROR fncValidarGrupo");
    }
    if (!vbRes)
    {
      vsError = vsError + "GRUPO DE SOPORTE " + psGrupo + " INCORRECTO \n";
      voErrores.add("GRUPO DE SOPORTE " + psGrupo + " INCORRECTO \n");
    }
    return vbRes;
  }
  
  public boolean fncValidarResponsable(String psUsuario)
  {
    System.out.println("********************* fncValidarResponsable" + psUsuario);
    log.info("********************* fncValidarResponsable" + psUsuario);
    boolean vbRes = false;
    try
    {
      RemedyPort voRP = getPort(this.prop.getProperty("wsdlURL"));
      for (ListOfFields lofpeop : voRP.rmdSelect(this.vsUsuarioRC, this.vsFormularioNomina, "'8'=\"" + psUsuario + "\"", new int[] { 8, 536870929 }, null).getEntry()) {
        if ((lofpeop.getValue(8).equals(psUsuario)) && (lofpeop.getValue(536870929).equals("0"))) {
          vbRes = true;
        }
      }
    }
    catch (Exception e)
    {
      log.error(e);
    }
    if (!vbRes) {
      vsError += "RESPONSABLE DE LA RUTINA INCORRECTO \n";
    }
    return vbRes;
  }
  
  public boolean fncValidarFechasC(String psFechaC)
    throws ParseException
  {
    boolean vbRes = false;
    SimpleDateFormat voFormatod = new SimpleDateFormat("dd/MM/yyyy HH:mm");
    try
    {
      Date voDate = new Date();
      Date voFechaHoy = voFormatod.parse(voFormatod.format(voDate));
      Date vdFechaC = voFormatod.parse(psFechaC);
      if (voFechaHoy.compareTo(vdFechaC) <= 0) {
        vbRes = true;
      }
    }
    catch (Exception e)
    {
      log.error(e);
    }
    if (!vbRes) {
      vsError += "LA FECHA DE CREACION NO PUEDE SER MENOR A LA ACTUAL\n";
    }
    return vbRes;
  }
  
  public boolean fncValidarHoraC(String psHoraC)
  {
    boolean vbRes = false;
    SimpleDateFormat voFormatoh = new SimpleDateFormat("HH:mm");
    try
    {
      voFormatoh.parse(psHoraC);
      vbRes = true;
    }
    catch (Exception e)
    {
      log.error(e);
    }
    if (!vbRes) {
      vsError += "FORMATO DE LA HORA DE CREACION INCORRECTO\n";
    }
    return vbRes;
  }
  
  public boolean fncValidarFechaI(String psFechaI)
  {
    boolean vbRes = false;
    SimpleDateFormat voFormatodh = new SimpleDateFormat("dd/MM/yyyy HH:mm");
    try
    {
      voFormatodh.parse(psFechaI);
      vbRes = true;
    }
    catch (Exception e)
    {
      log.error(e);
    }
    if (!vbRes) {
      vsError += "FORMATO DE LA FECHA DE INICIO INCORRECTO\n";
    }
    return vbRes;
  }
  
  public boolean fncValidarFechas(String psFechaC, String psHoraC, String psFechaI)
  {
    boolean vbRes = false;
    String vsFechaC = psFechaC + " " + psHoraC;
    SimpleDateFormat voFormatodh = new SimpleDateFormat("dd/MM/yyyy HH:mm");
    try
    {
      Date vdFechaC = voFormatodh.parse(vsFechaC);
      Date vdFechaI = voFormatodh.parse(psFechaI);
      if (vdFechaC.compareTo(vdFechaI) <= 0) {
        vbRes = true;
      }
    }
    catch (Exception e)
    {
      log.error(e);
    }
    if (!vbRes) {
      vsError += "LA FECHA Y HORA DE CREACION NO PUEDEN SER MENOR A LA FECHA DE INICIO\n";
    }
    return vbRes;
  }
  
  public AdmVO fncValidarAutorizador(AdmVO voAuth)
  {
    String vsNombreCAux = "";
    AdmVO voAutorizador = new AdmVO();
    try
    {
      RemedyPort voRP = getPort(this.prop.getProperty("wsdlURL"));
      for (ListOfFields voLofPeop : voRP.rmdSelect(this.vsUsuarioADM, this.vsFormularioResp, "('4'=\"" + voAuth.getVsPrimerANum() + "\" OR '4'=\"" + voAuth.getVsSegundoANum() + "\") AND ('1000000023' LIKE \"%JEFE%\" OR '1000000023' LIKE \"%GERENTE%\" OR '1000000023' LIKE \"%SUBDIRECTOR%\") AND '7'=1", new int[] { 4, 1000000001, 1000000018, 1000000019, 1000000020, 1000000023, 1000000054 }, null).getEntry())
      {
        if (voAuth.getVsPrimerANum().equals(voLofPeop.getValue(4)))
        {
          vsNombreCAux = voLofPeop.getValue(1000000019);
          if (!voLofPeop.getValue(1000000020).isEmpty()) {
            vsNombreCAux = vsNombreCAux + " " + voLofPeop.getValue(1000000020);
          }
          vsNombreCAux = vsNombreCAux + " " + voLofPeop.getValue(1000000018);
          voAutorizador.setVsPrimerAName(vsNombreCAux);
          voAutorizador.setVsPrimerANum(voLofPeop.getValue(4));
        }
        if (voAuth.getVsSegundoANum().equals(voLofPeop.getValue(4)))
        {
          vsNombreCAux = voLofPeop.getValue(1000000019);
          if (!voLofPeop.getValue(1000000020).isEmpty()) {
            vsNombreCAux = vsNombreCAux + " " + voLofPeop.getValue(1000000020);
          }
          vsNombreCAux = vsNombreCAux + " " + voLofPeop.getValue(1000000018);
          voAutorizador.setVsSegundoAName(vsNombreCAux);
          voAutorizador.setVsSegundoANum(voLofPeop.getValue(4));
        }
      }
    }
    catch (Exception e)
    {
      log.error(e);
    }
    if ((voAutorizador.getVsPrimerAName() == null) && (voAutorizador.getVsPrimerANum() == null)) {
      voErrores.add("EL PRIMER AUTORIZADOR " + voAuth.getVsPrimerANum() + " NO ES VALIDO \n");
    }
    if ((voAutorizador.getVsSegundoAName() == null) || (voAutorizador.getVsSegundoANum() == null)) {
      voErrores.add("EL SEGUNDO AUTORIZADOR " + voAuth.getVsSegundoANum() + " NO ES VALIDO \n");
    }
    if ((voAutorizador.getVsPrimerAName() == null) || (voAutorizador.getVsPrimerANum() == null) || (voAutorizador.getVsSegundoAName() == null) || (voAutorizador.getVsSegundoANum() == null)) {
      return null;
    }
    return voAutorizador;
  }
  
  public boolean fncValidarLongitud(AdmVO voAdmVO)
  {
    if (voAdmVO.getVsNodo().length() > 40)
    {
      voErrores.add("EL NODO " + voAdmVO.getVsNodo() + "SUPERA LA LONGITUD PERMITIDA\n");
      return false;
    }
    if (voAdmVO.getVsRegion().length() > 40)
    {
      voErrores.add("LA REGION " + voAdmVO.getVsRegion() + "SUPERA LA LONGITUD PERMITIDA\n");
      return false;
    }
    if (voAdmVO.getVsPerfil().length() > 40)
    {
      voErrores.add("EL PERFIL " + voAdmVO.getVsPerfil() + " SUPERA LA LONGITUD PERMITIDA\n");
      return false;
    }
    return true;
  }
  
  public void comentarioSistemas(List<String> voListaSistemas)
  {
    for (String sistema : voListaSistemas)
    {
      voErrores.add("NO SE ENCUENTRAN REGISTROS DEL SISTEMA " + sistema + ", FAVOR DE SOLICITAR PRIMERO SU ALTA \n");
      System.out.println(" Insert" + sistema + " ");
    }
  }
  
  public boolean fncValidarSistema(String psCompany, String psSistema)
  {
    boolean vbRes = false;
    try
    {
      RemedyPort voRP = getPort(this.prop.getProperty("wsdlURL"));
      for (ListOfFields lofSistemas : voRP.rmdSelect(this.vsUsuarioADM, this.vsFormularioSistemas, "'1000000001'=\"" + psCompany + "\" AND '8'=\"" + psSistema + "\"", new int[] { 112 }, null).getEntry()) {
        vbRes = true;
      }
    }
    catch (RemedyFault|MalformedURLException e)
    {
      log.error(e);
    }
    return vbRes;
  }
  
  public boolean fncInsertarNodo(AdmVO voAdmVO)
  {
    boolean vbRes = false;
    try
    {
      System.out.println("fncInsertarNodo");
      RemedyPort voRP = getPort(this.prop.getProperty("wsdlURL"));
      ListOfFields voDatos = new ListOfFields();
      voDatos.addField(new Field(1000000001, voAdmVO.getVsCompania()));
      voDatos.addField(new Field(8, voAdmVO.getVsSistema()));
      voDatos.addField(new Field(112, voAdmVO.getVsGrupoSoporte()));
      voDatos.addField(new Field(536870914, voAdmVO.getVsNodo()));
      voDatos.addField(new Field(536870913, voAdmVO.getVsNodo()));
      voDatos.addField(new Field(536870918, voAdmVO.getVsMensajeMail()));
      
      voDatos.addField(new Field(7, "0"));
      
      InsertStatus vsStatus = voRP.rmdInsert(this.vsUsuarioADM, this.vsFormularioNodo, voDatos, "'1000000001'=\"" + voAdmVO.getVsCompania() + "\" AND" + " '8'=\"" + voAdmVO.getVsSistema() + "\" AND" + " '536870914'=\"" + voAdmVO.getVsNodo() + "\"");
      if ((vsStatus.getStatus().equals("CREATED")) || (vsStatus.getStatus().equals("EXISTS")))
      {
        System.out.println(vsStatus.getStatus());
        vbRes = true;
      }
    }
    catch (RemedyFault|MalformedURLException e)
    {
      log.error(e);
    }
    return vbRes;
  }
  
  public boolean fncInsertarCI(CiVO voCI)
  {
    boolean vbRes = false;
    try
    {
      RemedyPort voRP = getPort(this.prop.getProperty("wsdlURL"));
      ListOfFields tecFields = new ListOfFields();
      List<ListOfFields> tecFieldsL = voRP.rmdSelect(this.vsUsuarioCI, this.vsFomularioAMX, "'536870913'=\"" + voCI.getTecnologia() + "\" AND '536870923'=\"" + voCI.getTipo() + "\"", new int[] { 536870918, 536870920, 536870921, 536870922, 536870914 }, null).getEntry();
      
      System.out.println(voCI.getTecnologia() + " - " + voCI.getTipo() + "\n");
      if (tecFieldsL.isEmpty())
      {
        System.out.println("No encontro nada");
        return false;
      }
      tecFields = (ListOfFields)tecFieldsL.get(0);
      
      ListOfFields voDatos = new ListOfFields();
      voDatos.addField(new Field(536870925, voCI.getNombre()));
      voDatos.addField(new Field(8, voCI.getNombre()));
      voDatos.addField(new Field(536870989, voCI.getConectado()));
      voDatos.addField(new Field(536871141, voCI.getIp()));
      voDatos.addField(new Field(1000000001, voCI.getCompania()));
      voDatos.addField(new Field(536878321, voCI.getSitioAlrma()));
      voDatos.addField(new Field(536870914, voCI.getRegion()));
      voDatos.addField(new Field(730000001, voCI.getTecnologia()));
      voDatos.addField(new Field(536870974, voCI.getTipo()));
      voDatos.addField(new Field(536871003, voCI.getGrupo()));
      voDatos.addField(new Field(536878271, tecFields.getValue(536870922)));
      voDatos.addField(new Field(536878272, tecFields.getValue(536870914)));
      voDatos.addField(new Field(536878317, tecFields.getValue(536870918)));
      voDatos.addField(new Field(536878269, tecFields.getValue(536870921)));
      voDatos.addField(new Field(536878270, tecFields.getValue(536870920)));
      
      InsertStatus vsStatus = voRP.rmdInsert(this.vsUsuarioCI, this.vsFormularioSITEP, voDatos, "");
      if ((vsStatus.getStatus().equals("CREATED")) || (vsStatus.getStatus().equals("EXISTS")))
      {
        vbRes = true;
        voDatos = new ListOfFields();
        voDatos.addField(new Field(7, "4"));
        String idSitio = "";
        System.out.println("Sitio creado: " + voCI.toString());
        for (ListOfFields fields : voRP.rmdSelect(this.vsUsuarioCI, this.vsFormularioSITEP, "'536870925'=\"" + voCI.getNombre() + "\" AND '1000000001'=\"" + voCI.getCompania() + "\"", new int[] { 1 }, null).getEntry()) {
          idSitio = fields.getValue(1);
        }
        UpdateStatus vsUStatus = voRP.rmdUpdate(this.vsUsuarioCI, this.vsFormularioSITEP, idSitio, voDatos);
        vbRes = vsUStatus.getStatus().equals("UPDATED");
        System.out.println(vbRes ? "Pase a operando correcto" : "Pase a operando incorrecto");
      }
      else
      {
        System.out.println("Error al creaar el sitio");
        vbRes = false;
      }
    }
    catch (RemedyFault|MalformedURLException e)
    {
      log.error(e);
      e.printStackTrace();
      System.out.println("Error de insercion: " + e.toString());
      vbRes = false;
    }
    return vbRes;
  }
  
  public boolean fncInsertarPerfil(AdmVO voAdm)
  {
    boolean vbRes = false;
    try
    {
      System.out.println("fncInsertarPerfil");
      RemedyPort voRP = getPort(this.prop.getProperty("wsdlURL"));
      
      ListOfFields voDatos = new ListOfFields();
      voDatos.addField(new Field(1000000001, voAdm.getVsCompania()));
      voDatos.addField(new Field(536870914, voAdm.getVsSistema()));
      
      voDatos.addField(new Field(536870919, voAdm.getVsNodo()));
      voDatos.addField(new Field(536870915, voAdm.getVsRegion()));
      voDatos.addField(new Field(8, voAdm.getVsPerfil()));
      voDatos.addField(new Field(536870920, voAdm.getVsPerfil()));
      voDatos.addField(new Field(536870913, voAdm.getVsPerfil()));
      voDatos.addField(new Field(112, voAdm.getVsGrupoSoporte()));
      voDatos.addField(new Field(7, "0"));
      
      InsertStatus vsStatus = voRP.rmdInsert(this.vsUsuarioADM, this.vsFormularioPerfiles, voDatos, "'1000000001'=\"" + voAdm.getVsCompania() + "\" AND" + " '536870914'=\"" + voAdm.getVsSistema() + "\" AND" + " '536870915'=\"" + voAdm.getVsRegion() + "\" AND" + " '8'=\"" + voAdm.getVsPerfil() + "\" AND" + " '536870919'=\"" + voAdm.getVsNodo() + "\"");
      
      System.out.println("vsStatus.getStatus() " + vsStatus.getStatus());
      if ((vsStatus.getStatus().equals("CREATED")) || (vsStatus.getStatus().equals("EXISTS"))) {
        vbRes = true;
      }
    }
    catch (RemedyFault|MalformedURLException e)
    {
      log.error(e);
    }
    return vbRes;
  }
  
  public boolean fncValidarRegion(String psCompa\u00F1ia, String psRegion)
  {
    boolean vbRes = false;
    if ((psCompa\u00F1ia.equals("TELCEL")) && (psRegion.equals("CORP"))) {
      return true;
    }
    if ((!psCompa\u00F1ia.equals("TELCEL")) && (psRegion.equals("NACIONAL"))) {
      return true;
    }
    if (psRegion.equals("TODAS")) {
      return true;
    }
    try
    {
      RemedyPort voRP = getPort(this.prop.getProperty("wsdlURL"));
      for (ListOfFields lofRegion : voRP.rmdSelect(this.vsUsuarioADM, this.prop.getProperty("FORM_REGION"), "'1000000001'=\"" + psCompa\u00F1ia + "\"", new int[] { 200000012 }, null).getEntry()) {
        if (lofRegion.getValue(200000012).equals(psRegion)) {
          return true;
        }
      }
    }
    catch (RemedyFault|MalformedURLException e)
    {
      log.error(e);
    }
    voErrores.add("LA REGION " + psRegion + " PARA COMPAÑIA " + psCompa\u00F1ia + " ES INVALIDA\n");
    return vbRes;
  }
  
  private RemedyUtilsPort getUtilPort(String psEndPoint)
    throws MalformedURLException
  {
    QName serviceQN = new QName(this.prop.getProperty("namespace"), this.prop.getProperty("serviceNameUtil"));
    QName portQN = new QName(this.prop.getProperty("namespace"), this.prop.getProperty("portNameUtil"));
    Service service = Service.create(new URL(psEndPoint), serviceQN);
    return (RemedyUtilsPort)service.getPort(portQN, RemedyUtilsPort.class);
  }
  
  private RemedyPort getPort(String psEndPoint)
    throws MalformedURLException
  {
    QName serviceQN = new QName(this.prop.getProperty("namespace"), this.prop.getProperty("serviceName"));
    QName portQN = new QName(this.prop.getProperty("namespace"), this.prop.getProperty("portName"));
    Service service = Service.create(new URL(psEndPoint), serviceQN);
    return (RemedyPort)service.getPort(portQN, RemedyPort.class);
  }
  
  public boolean fncInsertarAprobador(AdmVO voAdmVO)
  {
    boolean vbRes = false;
    try
    {
      System.out.println("fncInsertarAprobador");
      RemedyPort voRP = getPort(this.prop.getProperty("wsdlURL"));
      ListOfFields voDatos = new ListOfFields();
      voDatos.addField(new Field(1000000001, voAdmVO.getVsCompania()));
      voDatos.addField(new Field(536870916, voAdmVO.getVsSistema()));
      voDatos.addField(new Field(590000003, voAdmVO.getVsNodo()));
      voDatos.addField(new Field(539000000, voAdmVO.getVsRegion()));
      voDatos.addField(new Field(536870931, voAdmVO.getVsPerfil()));
      voDatos.addField(new Field(536870943, voAdmVO.getVsMensajeMail()));
      
      voDatos.addField(new Field(536870919, voAdmVO.getVsGrupo()));
      voDatos.addField(new Field(112, voAdmVO.getVsGrupoSoporte()));
      voDatos.addField(new Field(7, "0"));
      
      ListOfFields voAutorizadorUno = voDatos;
      voAutorizadorUno.addField(new Field(536870917, voAdmVO.getVsPrimerAName()));
      voAutorizadorUno.addField(new Field(536870918, voAdmVO.getVsPrimerANum()));
      voAutorizadorUno.addField(new Field(536870922, "0"));
      
      InsertStatus vsStatus = voRP.rmdInsert(this.vsUsuarioADM, this.vsFormularioAuto, voAutorizadorUno, "'1000000001'=\"" + voAdmVO.getVsCompania() + "\" AND" + " '536870916'=\"" + voAdmVO.getVsSistema() + "\" AND" + " '590000003'=\"" + voAdmVO.getVsNodo() + "\" AND" + " '536870922'=0 AND" + " '536870931'=\"" + voAdmVO.getVsPerfil() + "\"");
      
      System.out.println("vsStatus.getStatus() " + vsStatus.getStatus());
      if ((vsStatus.getStatus().equals("CREATED")) || (vsStatus.getStatus().equals("EXISTS")))
      {
        //update autorizador 1
        if (vsStatus.getStatus().equals("EXISTS"))
        {
            String idAutorizadorUno = vsStatus.getCreatedEntry();
            System.out.println(" idAutorizadorUno=" + idAutorizadorUno);
            ListOfFields voAutorizadorUnoUp = new ListOfFields();
            voAutorizadorUnoUp.addField(new Field(536870943, voAdmVO.getVsMensajeMail()));
            voAutorizadorUnoUp.addField(new Field(536870919, voAdmVO.getVsGrupo()));
            voAutorizadorUnoUp.addField(new Field(536870917, voAdmVO.getVsPrimerAName()));
            voAutorizadorUnoUp.addField(new Field(536870918, voAdmVO.getVsPrimerANum()));
            UpdateStatus upStatus2= voRP.rmdUpdate(this.vsUsuarioADM, this.vsFormularioAuto, idAutorizadorUno, voAutorizadorUnoUp);
            System.out.println(" AutorizadorUnoUp=" + upStatus2.getStatus());
        }
        
        ListOfFields voAutorizadorDos = voDatos;
        voAutorizadorDos.addField(new Field(536870917, voAdmVO.getVsSegundoAName()));
        voAutorizadorDos.addField(new Field(536870918, voAdmVO.getVsSegundoANum()));
        voAutorizadorDos.addField(new Field(536870922, "1"));
        
        InsertStatus vsStatusDos = voRP.rmdInsert(this.vsUsuarioADM, this.vsFormularioAuto, voAutorizadorDos, "'1000000001'=\"" + voAdmVO.getVsCompania() + "\" AND" + " '536870916'=\"" + voAdmVO.getVsSistema() + "\" AND" + " '590000003'=\"" + voAdmVO.getVsNodo() + "\" AND" + " '536870922'=1 AND" + " '536870931'=\"" + voAdmVO.getVsPerfil() + "\"");
        
        System.out.println("vsStatusDos.getStatus() " + vsStatusDos.getStatus());
        if ((vsStatusDos.getStatus().equals("CREATED")) || (vsStatusDos.getStatus().equals("EXISTS"))) {
            //update autorizador 2
            if (vsStatusDos.getStatus().equals("EXISTS"))
            {
                String idAutorizadorDos = vsStatusDos.getCreatedEntry();
                System.out.println(" idAutorizadorDos=" + idAutorizadorDos);
                ListOfFields voAutorizadorDosUp = new ListOfFields();
                voAutorizadorDosUp.addField(new Field(536870943, voAdmVO.getVsMensajeMail()));
                voAutorizadorDosUp.addField(new Field(536870919, voAdmVO.getVsGrupo()));
                voAutorizadorDosUp.addField(new Field(536870917, voAdmVO.getVsSegundoAName()));
                voAutorizadorDosUp.addField(new Field(536870918, voAdmVO.getVsSegundoANum()));
                UpdateStatus upStatus3= voRP.rmdUpdate(this.vsUsuarioADM, this.vsFormularioAuto, idAutorizadorDos, voAutorizadorDosUp);
                System.out.println(" AutorizadorDosUp=" + upStatus3.getStatus());
            }
            vbRes = true;
        }
      }
    }
    catch (RemedyFault|MalformedURLException e)
    {
      log.error(e);
    }
    return vbRes;
  }
  
  public String fncConsultarGrupo(String psCompa\u00F1ia)
  {
    String vsGrupo = "";
    try
    {
      RemedyPort voRP = getPort(this.prop.getProperty("wsdlURL"));
      for (ListOfFields lofRegion : voRP.rmdSelect(this.vsUsuarioADM, this.prop.getProperty("FORM_GRUPO"), "'8'=\"" + psCompa\u00F1ia + "\"", new int[] { 106 }, null).getEntry()) {
        vsGrupo = lofRegion.getValue(106);
      }
    }
    catch (Exception e)
    {
      log.error(e);
    }
    System.out.println("****vsGrupo " + vsGrupo);
    return vsGrupo;
  }
  
  public boolean fnCompaniaGrupo(String psGrupo, String psCompa\u00F1ia)
  {
    boolean vbRes = false;
    System.out.println("psGrupo " + psGrupo);
    System.out.println("psCompa�ia " + psCompa\u00F1ia);
    try
    {
      RemedyPort voRP = getPort(this.prop.getProperty("wsdlURL"));
      for (ListOfFields lofGrupo : voRP.rmdSelect(this.vsUsuarioADM, this.prop.getProperty("FORM_GRUPO"), "'8' LIKE \"%" + psGrupo + "\"", new int[] { 8 }, null).getEntry()) {
        if (lofGrupo.getValue(8).startsWith(psCompa\u00F1ia)) {
          vbRes = true;
        }
      }
    }
    catch (Exception e)
    {
      log.error(e);
    }
    return vbRes;
  }
  
  public String fncValidarAltaSistema(AdmVO voAdmVO, String viIDOrden)
  {
    String vbRes = "0";
    String vbTa = null;
    boolean vbComment = false;
    boolean categoriasValidas = false;
    try
    {
      RemedyPort voRP = getPort(this.prop.getProperty("wsdlURL"));
      for (ListOfFields lofSistemas : voRP.rmdSelect(this.vsUsuarioADM, "PCT:ProductCompanyAssocLookup", "'1000000001'=\"" + voAdmVO.getVsCompania() + "\" AND '200000005'=\"" + voAdmVO.getVsSistema() + "\" AND '240001002'=$NULL$", new int[] { 1, 7, 1000000179, 200000003, 200000004, 240001002 }, null).getEntry())
      {
        log.info("Sistema " + voAdmVO.getVsSistema() + "  encontrado para la compa�ia " + voAdmVO.getVsCompania() + ", Producto=NULL\n");
        categoriasValidas = ((lofSistemas.getValue(200000003).equals("SOFTWARE")) && (lofSistemas.getValue(200000004).equals("APLICACION"))) || ((lofSistemas.getValue(200000003).equals("SERVICIO")) && (lofSistemas.getValue(200000004).equals("TECNICO"))) || ((lofSistemas.getValue(200000003).equals("SERVICIO")) && (lofSistemas.getValue(200000004).equals("NEGOCIO")));
        
        if ((lofSistemas.getValue(7).equals("1")) && (lofSistemas.getValue(1000000179).equals("1")) && categoriasValidas)
        {
          log.info("Estatus relacion OK\nEstatus categoria OK\nCategoria 1 OK\nCategoria 2 OK");
          UpdateStatus voStatus = new UpdateStatus();
          try
          {
            ListOfFields voListOfFields = new ListOfFields();
            voListOfFields.addField(new Field(536880001, "0"));
            voListOfFields.addField(new Field(377771011, "0"));
            voStatus = voRP.rmdUpdate(this.vsUsuarioADM, "PCT:ProductCompanyAssocLookup", lofSistemas.getValue(1), voListOfFields);
            vbRes = "1";
            log.info("Gestion de accesos OK\nGestion de ordenes de trabajo OK\n");
          }
          catch (Exception e)
          {
            log.error(e);
            
            voErrores.add("ERROR AL MOMENTO DE VALIDAR EL SISTEMA " + voAdmVO.getVsSistema() + "  PARA LA FILIAL " + voAdmVO.getVsCompania() + "\n");
          }
        }
      }
      if (!vbRes.equals("1")) {
        for (ListOfFields lofSistemas : voRP.rmdSelect(this.vsUsuarioADM, "PCT:ProductCompanyAssocLookup", "'1000000001'=\"" + voAdmVO.getVsCompania() + "\" AND '200000005'=\"" + voAdmVO.getVsSistema() + "\"", new int[] { 1, 7, 1000000179, 200000003, 200000004, 240001002 }, null).getEntry())
        {
          log.info("Sistema " + voAdmVO.getVsSistema() + "  encontrado para la compañia " + voAdmVO.getVsCompania() + ", Producto!=NULL\n");
          
          categoriasValidas = ((lofSistemas.getValue(200000003).equals("SOFTWARE")) && (lofSistemas.getValue(200000004).equals("APLICACION"))) || ((lofSistemas.getValue(200000003).equals("SERVICIO")) && (lofSistemas.getValue(200000004).equals("TECNICO"))) || ((lofSistemas.getValue(200000003).equals("SERVICIO")) && (lofSistemas.getValue(200000004).equals("NEGOCIO")));          
          if ((lofSistemas.getValue(7).equals("1")) && (lofSistemas.getValue(1000000179).equals("1")) && categoriasValidas && (!vbComment))
          {
            log.info("Estatus relacion OK\nEstatus categoria OK\nCategoria 1 OK\nCategoria 2 OK");
            
            vbRes = "2";
            RemedyControl voRControl = new RemedyControl();
            if (voRControl.fncInsertComentario("Notas del Sistema", "Se asigna la WO al grupo de GESTION DE PROCESOS para dar de alta la Categoria del Producto Nivel 3  " + voAdmVO.getVsSistema() + " .Una vez que el sistema este dado de alta la WO se finalizara automaticamente", viIDOrden)) {
              vbComment = true;
            }
            voRControl.fncAsignarWO(viIDOrden);
          }
        }
      }
    }
    catch (RemedyFault|MalformedURLException e)
    {
      log.error(e);
    }
    if (vbRes.equals("0")) {
      voErrores.add("AUN NO SE HA HABILITADO EL SISTEMA " + voAdmVO.getVsSistema() + "  PARA LA FILIAL " + voAdmVO.getVsCompania() + ". PARA MAS DETALLES CONSULTAR CON EL ADMINISTRADOR\n");
    }
    return vbRes;
  }
  
  public boolean fncInsertarSistema(AdmVO voAdmVO)
  {
    boolean vbRes = false;
    try
    {
      System.out.println("fncInsertarSistema");
      RemedyPort voRP = getPort(this.prop.getProperty("wsdlURL"));
      ListOfFields voDatos = new ListOfFields();
      voDatos.addField(new Field(1000000001, voAdmVO.getVsCompania()));
      voDatos.addField(new Field(8, voAdmVO.getVsSistema()));
      voDatos.addField(new Field(536870913, voAdmVO.getVsSistema()));
      
      voDatos.addField(new Field(536870917, voAdmVO.getVsMensajeMail()));
      
      voDatos.addField(new Field(7, "0"));
      voDatos.addField(new Field(2, "rc_adm"));
      
      InsertStatus vsStatus = voRP.rmdInsert(this.vsUsuarioADM, this.vsFormularioSistemas, voDatos, "'1000000001'=\"" + voAdmVO.getVsCompania() + "\" AND" + " '8'=\"" + voAdmVO.getVsSistema() + "\"");
      if ((vsStatus.getStatus().equals("CREATED")) || (vsStatus.getStatus().equals("EXISTS")))
      {
        System.out.println(vsStatus.getStatus());
        vbRes = true;
      }
    }
    catch (RemedyFault|MalformedURLException e)
    {
      log.error(e);
      voErrores.add("ERROR AL MOMENTO DE INSERTAT EL SISTEMA " + voAdmVO.getVsSistema() + "  PARA LA FILIAL " + voAdmVO.getVsCompania() + "\n");
    }
    return vbRes;
  }
  
  public String fncInsertarTarea(AdmVO voAdmVO, String viIDOrden)
  {
    String vbRes = null;
    try
    {
      System.out.println("fncInsertarTarea");
      RemedyPort voRP = getPort(this.prop.getProperty("wsdlURL"));
      ListOfFields voDatos = new ListOfFields();
      voDatos.addField(new Field(1000000251, voAdmVO.getVsCompania()));
      voDatos.addField(new Field(10002506, "TRANSFORMACION DIGITAL"));
      voDatos.addField(new Field(10002506, "CORP-TD-PLATAFORMAS Y SERVICIOS DE DIGITALIZACION"));
      voDatos.addField(new Field(7, "2000"));
      voDatos.addField(new Field(8, "ALTA DE CATEGORIA DE PRODUCTO DESDE ARCHIVO ABJUNTO"));
      voDatos.addField(new Field(536900001, "Orden de Trabajo"));
      
      voDatos.addField(new Field(1000000063, "SOFTWARE"));
      voDatos.addField(new Field(1000000064, "APLICACION"));
      voDatos.addField(new Field(1000001270, "SOFTWARE"));
      voDatos.addField(new Field(1000001271, "APLICACION"));
      voDatos.addField(new Field(1000001272, voAdmVO.getVsSistema()));
      voDatos.addField(new Field(200000012, voAdmVO.getVsRegion()));
      voDatos.addField(new Field(10000006, viIDOrden));
      
      InsertStatus vsStatus = voRP.rmdInsert(this.vsUsuarioADM, "TMS:Task", voDatos, "'1000001270'=\"SOFTWARE\" AND '1000001271'=\"APLICACION\" AND '1000001272'=\"" + voAdmVO.getVsSistema() + "\"");
      if ((vsStatus.getStatus().equals("CREATED")) || (vsStatus.getStatus().equals("EXISTS")))
      {
        System.out.println(vsStatus.getStatus());
        vbRes = vsStatus.getCreatedEntry();
      }
    }
    catch (RemedyFault|MalformedURLException e)
    {
      log.error(e);
      voErrores.add("ERROR AL MOMENTO DE INSERTAR TAREA PARA CREAR CATEGORIA DE " + voAdmVO.getVsSistema() + "  PARA LA FILIAL " + voAdmVO.getVsCompania() + "\n");
    }
    return vbRes;
  }
  
  public void fncAsignarWO(String psIDWO)
  {
    UpdateStatus voStatus = new UpdateStatus();
    try
    {
      RemedyPort voRP = getPort(this.prop.getProperty("wsdlURL"));
      
      ListOfFields voListOfFields = new ListOfFields();
      voListOfFields.addField(new Field(1000003227, "TRANSFORMACION DIGITAL"));
      voListOfFields.addField(new Field(1000003228, "TELCEL"));
      voListOfFields.addField(new Field(1000003229, "CORP-TD-DIGITALIZACION DE SERVICIOS"));
      voListOfFields.addField(new Field(1000003234, "SGP000000000344"));
      voStatus = voRP.rmdUpdate(this.vsUsuarioADM, this.vsFormularioRC, psIDWO, voListOfFields);
    }
    catch (Exception e)
    {
      log.error(e);
    }
  }
  
  public String fncGetSistema(String psCompany, String psSistema)
  {
    String vbRes = "";
    try
    {
      RemedyPort voRP = getPort(this.prop.getProperty("wsdlURL"));
      for (ListOfFields lofSistemas : voRP.rmdSelect(this.vsUsuarioADM, this.vsFormularioSistemas, "'1000000001'=\"" + psCompany + "\" AND '8'=\"" + psSistema + "\"", new int[] { 112, 8 }, null).getEntry()) {
        vbRes = lofSistemas.getValue(8);
      }
    }
    catch (RemedyFault|MalformedURLException e)
    {
      log.error(e);
    }
    return vbRes;
  }
  
  public static void main(String[] args)
  {
    LecturaArchivo voArchivo = new LecturaArchivo();
    RemedyControl voRControl = new RemedyControl();
    
   
    //System.out.println(voRControl.fncRecuperarNombreArchivo("REQ000000596501"));
  }
}
