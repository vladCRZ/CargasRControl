package com.telcel.carga.control;

import com.infomedia.utils.PropertyLoader;
import com.telcel.carga.Utileria.LecturaArchivo;
import com.telcel.carga.Utileria.RemedyControl;
import com.telcel.carga.correo.Correos;
import com.telcel.carga.entry.AdmVO;
import com.telcel.carga.entry.OrdenTrabajoVO;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import org.apache.log4j.Logger;

//Clase validada en ambiente productivo 21/05/2021
public class CargaADM
{
  private static Properties prop = PropertyLoader.load("cargarm.properties");
  private static final Logger log = Logger.getLogger(CargaRM.class);
  private static final RemedyControl voRemedyControl = new RemedyControl();
  private static final LecturaArchivo voLectura = new LecturaArchivo();
  private static final Correos voCorreo = new Correos();
  
  public static void main(String[] args)
  {
    CargaRM voCarga = new CargaRM();
    List<OrdenTrabajoVO> voOrdenesT = voRemedyControl.fncConsultaOrdenesT("ADM");
    List<AdmVO> voAdm = new ArrayList();
    //auxiliar 0=false 1=true 2=true y tarea insertada
    String aux = "";
    
    boolean vbStatus = false;
 
    for (OrdenTrabajoVO ordenTrabajoVO : voOrdenesT)
    {
      voRemedyControl.setVoErrores(new ArrayList());
      List<String> voListaSistemas = new ArrayList();
      boolean cerrarWO = true;
      aux = "";
      vbStatus = false;
      boolean DescargaArchivo = false;
      String listaCom = "";
      
      
      System.out.println(ordenTrabajoVO.getViIDRequerimiento());
      //voCarga.ExcCommand("rm " + prop.getProperty("PATH_ADM"));
      DescargaArchivo = voRemedyControl.fncDescargarArchivo(ordenTrabajoVO.getViIDRequerimiento());
      

      //Recuperar el nombre del archivo
      String nombreArchivo = "";
      nombreArchivo = voRemedyControl.fncRecuperarNombreArchivo(ordenTrabajoVO.getViIDRequerimiento());
      //nombreArchivo = nombreArchivo.replace(" ", "\\ ");
      nombreArchivo = prop.getProperty("RUTA_ADM")+nombreArchivo;
      //Verificar en el archivo properties la propiedad RUTA_ADM   
      System.out.println(nombreArchivo);
      voAdm = voLectura.fncLeerArchivoAdm(nombreArchivo);
            
      
      
      if (!voAdm.isEmpty() & DescargaArchivo)
      {
        boolean insertLinea = false;
        int contadorPerfil = 4;
        int contadorErrores = 0;
        for (AdmVO admVO : voAdm)
        {
          insertLinea = false;
          AdmVO voAdmVOAux = voRemedyControl.fncValidarAutorizador(admVO);
          if ((voAdmVOAux != null & voRemedyControl.fncValidarLongitud(admVO) & voRemedyControl.fncValidarRegion(admVO.getVsCompania(), admVO.getVsRegion()) & voRemedyControl.fncValidarGrupo(admVO.getVsGrupo())))
          {
            admVO.setVsPrimerAName(voAdmVOAux.getVsPrimerAName());
            admVO.setVsPrimerANum(voAdmVOAux.getVsPrimerANum());
            admVO.setVsSegundoAName(voAdmVOAux.getVsSegundoAName());
            admVO.setVsSegundoANum(voAdmVOAux.getVsSegundoANum());
            
            admVO.setVsPerfil(admVO.getVsPerfil().toUpperCase());
            if (voRemedyControl.fncValidarSistema(admVO.getVsCompania(), admVO.getVsSistema()))
            {
              admVO.setVsSistema(voRemedyControl.fncGetSistema(admVO.getVsCompania(), admVO.getVsSistema()));
              admVO.setVsGrupoSoporte(voRemedyControl.fncConsultarGrupo(admVO.getVsCompania()));
              if ((voRemedyControl.fncInsertarNodo(admVO)) && (voRemedyControl.fncInsertarPerfil(admVO)) && (voRemedyControl.fncInsertarAprobador(admVO))) {
                vbStatus = true;
                insertLinea = true;
              }
              System.out.println("########################################");
            }
            else
            {
              log.info("Entra al flujo par insertar sistema");
              aux = voRemedyControl.fncValidarAltaSistema(admVO, ordenTrabajoVO.getViIDOrden());
              if (aux.equals("2")) {
                cerrarWO = false;
              }
              if (aux.equals("1"))
              {
                log.info("fncValidarAltaSistema OK");
                if (voRemedyControl.fncInsertarSistema(admVO))
                {
                  admVO.setVsGrupoSoporte(voRemedyControl.fncConsultarGrupo(admVO.getVsCompania()));
                  if ((voRemedyControl.fncInsertarNodo(admVO)) && (voRemedyControl.fncInsertarPerfil(admVO)) && (voRemedyControl.fncInsertarAprobador(admVO))) {
                    vbStatus = true;
                    insertLinea = true;
                  }
                  System.out.println("%%%");
                }
              }
            }
          }
          if(insertLinea == true)
            {
                listaCom = listaCom + "Perfil localizado en la fila #" + contadorPerfil + " insertado\n";
            }
          else
            {
                contadorErrores = contadorErrores + 1;
                listaCom = listaCom + "No se pudo insertar el perfil en la fila #" + contadorPerfil + ", revisar detalle en ERROR #"+ contadorErrores + "\n";
            }
          contadorPerfil = contadorPerfil + 1;
        }
      }
      else
      {
        voRemedyControl.fncInsertComentario("Notas del Sistema", "No se incluyo archivo con el nombre o formato especifico", ordenTrabajoVO.getViIDOrden());
        voRemedyControl.fncCancelarWO(ordenTrabajoVO.getViIDOrden());
        continue;
      }
      log.info(Integer.valueOf(voListaSistemas.size()));
      if (!voListaSistemas.isEmpty())
      {
        voRemedyControl.comentarioSistemas(voListaSistemas);
        try
        {
          voCorreo.fncEnviarCorreo(ordenTrabajoVO.getVsClienteNombre(), ordenTrabajoVO.getVsClienteCorreo(), voListaSistemas);
        }
        catch (Exception e)
        {
          log.error(e);
        }
      }
      if (cerrarWO) {
        if (voRemedyControl.getVoErrores().isEmpty())
        {
          voRemedyControl.fncInsertComentario("Notas del Sistema", "Se insertaron todos los perfiles solicitados", ordenTrabajoVO.getViIDOrden());
          voRemedyControl.fncCerrarWO(ordenTrabajoVO.getViIDOrden(), "Successful");
        }
        else if (!vbStatus)
        {
          String vsErrores = getStringErrores();
          voRemedyControl.fncInsertComentario("Notas del Sistema", "Ningun perfil insertado, error en todos los registros del archivo \n" + listaCom + "\n" + vsErrores, ordenTrabajoVO.getViIDOrden());
          voRemedyControl.fncCancelarWO(ordenTrabajoVO.getViIDOrden());
        }
        else
        {
          String vsErrores = getStringErrores();
          voRemedyControl.fncInsertComentario("Notas del Sistema", listaCom + "\n" + vsErrores, ordenTrabajoVO.getViIDOrden());
          voRemedyControl.fncCerrarWO(ordenTrabajoVO.getViIDOrden(), "Unsuccessful");
        }
      }
      aux = "0";
    }
  }
  
  public static String getStringErrores()
  {
    String vsErrores = "";
    int cont = 0;
    for (String error : voRemedyControl.getVoErrores())
    {
      cont = cont + 1;
      error = "ERROR #" + cont + " : " + error;
      System.out.println(error);
      vsErrores = vsErrores + error;
    }
    return vsErrores;
  }
}
