package com.telcel.carga.Utileria;

import com.telcel.carga.control.CargaRM;
import com.telcel.carga.entry.AdmVO;
import com.telcel.carga.entry.CiVO;
import com.telcel.carga.entry.RutinaVO;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintStream;
import java.nio.charset.Charset;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;

import org.apache.log4j.Logger;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class LecturaArchivo
{
  private static final Logger log = Logger.getLogger(LecturaArchivo.class);
  
  public List<RutinaVO> fncLeerArchivo(String psRuta)
  {
    log.debug("RUTA: " + psRuta);
    
    int contador = 0;
    
    boolean vbFilaVacia = true;
    
    SimpleDateFormat vsFormatofh = new SimpleDateFormat("dd/MM/yyyy HH:mm");
    
    List<RutinaVO> voListRes = new ArrayList();
    try
    {
      InputStream viExcel = new FileInputStream(psRuta);
      XSSFWorkbook vhLibro = new XSSFWorkbook(viExcel);
      XSSFSheet vhHoja = vhLibro.getSheetAt(1);
      
      Iterator viFilas = vhHoja.iterator();
      viFilas.next();
      viFilas.next();
      viFilas.next();
      while ((viFilas.hasNext()) && (vbFilaVacia))
      {
        XSSFRow vhFila = (XSSFRow)viFilas.next();
        Iterator viFila = vhFila.cellIterator();
        RutinaVO voRutina = new RutinaVO();
        XSSFCell vhCelda = (XSSFCell)viFila.next();
        if (fncLeerCelda(vhCelda).equals("")) {
          vbFilaVacia = false;
        }
        if (vhFila.getLastCellNum() > 1) {
          voRutina.setVsCompañia(fncLeerCelda(vhCelda));
          vhCelda = (XSSFCell)viFila.next();
          System.out.println(fncLeerCelda(vhCelda));
          voRutina.setVsRutina(fncLeerCelda(vhCelda));
          if (!voRutina.getVsRutina().equals(""))
          {
            vhCelda = (XSSFCell)viFila.next();
            voRutina.setVsTipo(fncLeerCelda(vhCelda));
            vhCelda = (XSSFCell)viFila.next();
            voRutina.setVsFrecuencia(fncLeerCelda(vhCelda));
            vhCelda = (XSSFCell)viFila.next();
            voRutina.setVsDuracion(fncLeerCelda(vhCelda));
            vhCelda = (XSSFCell)viFila.next();
            if (vhCelda.getCellTypeEnum().toString().equals("STRING")) {
              voRutina.setVsFechaC(fncLeerCelda(vhCelda));
            } else {
              voRutina.setVsFechaC(vsFormatofh.format(vhCelda.getDateCellValue()));
            }
            vhCelda = (XSSFCell)viFila.next();
            if (vhCelda.getCellTypeEnum().toString().equals("STRING")) {
              voRutina.setVsFechaI(fncLeerCelda(vhCelda));
            } else {
              voRutina.setVsFechaI(vsFormatofh.format(vhCelda.getDateCellValue()));
            }
            vhCelda = (XSSFCell)viFila.next();
            voRutina.setVsCI(fncLeerCelda(vhCelda));
            vhCelda = (XSSFCell)viFila.next();
            voRutina.setVsRegion(fncLeerCelda(vhCelda));
            vhCelda = (XSSFCell)viFila.next();
            voRutina.setVsGrupoS(fncLeerCelda(vhCelda));
            vhCelda = (XSSFCell)viFila.next();
            voRutina.setVsResponsable(fncLeerCelda(vhCelda));
            vhCelda = (XSSFCell)viFila.next();
            voRutina.setVsInstrucciones(fncLeerCelda(vhCelda));
            //vhCelda = (XSSFCell)viFila.next();
            //voRutina.setVsNombreArchivo(fncLeerCelda(vhCelda));
            voRutina.setVsNombreArchivo("");
            voListRes.add(voRutina);
            contador = 0;
          }
        } else {
          contador++;
          if (contador > 2) { //3 oportunidades de fila vacia antes de salir
            break;
          }
        }
      }
    }
    catch (IOException e)
    {
      log.error(e);
      System.out.println(e);
    }
    return voListRes;
  }
  
  private String fncLeerCelda(XSSFCell phCelda)
  {
    String vsRes = "";
    switch (phCelda.getCellTypeEnum().toString())
    {
    case "NUMERIC": 
      vsRes = String.valueOf((int)phCelda.getNumericCellValue()).trim();
      break;
    default: 
      vsRes = new String(phCelda.getStringCellValue().getBytes(Charset.forName("UTF-8"))).trim();
    }
    return vsRes;
  }
  
  private String fncLeerCeldaDate(XSSFCell phCelda)
  {
    String vsRes = "";
    switch (phCelda.getCellTypeEnum().toString())
    {
    case "STRING": 
      vsRes = new String(phCelda.getStringCellValue().getBytes(Charset.forName("UTF-8")));
      break;
    }
    return vsRes;
  }
  
  public List<AdmVO> fncLeerArchivoAdm(String psRuta)
  {
    log.debug("RUTA: " + psRuta);
    
    int contador = 0;
    System.out.println("Ruta" + psRuta);
    
    List<AdmVO> voListRes = new ArrayList();
    try
    {
      InputStream viExcel = new FileInputStream(psRuta);
      XSSFWorkbook vhLibro = new XSSFWorkbook(viExcel);
      
      XSSFSheet vhHoja = vhLibro.getSheetAt(1);
      
      Iterator viFilas = vhHoja.iterator();
      
      viFilas.next();
      viFilas.next();
      viFilas.next();
      while (viFilas.hasNext())
      {
        XSSFRow vhFila = (XSSFRow)viFilas.next();
        Iterator viFila = vhFila.cellIterator();
        if (viFila.hasNext())
        {
          //System.out.println("Numero de filas: " + vhFila.getLastCellNum());
          if (vhFila.getLastCellNum() > 1) {
            AdmVO voAdm = new AdmVO();
            XSSFCell vhCelda = (XSSFCell)viFila.next();
            voAdm.setVsCompania(fncLeerCelda(vhCelda).toUpperCase());
            vhCelda = (XSSFCell)viFila.next();
            voAdm.setVsSistema(fncLeerCelda(vhCelda).toUpperCase());
            vhCelda = (XSSFCell)viFila.next();
            voAdm.setVsNodo(fncLeerCelda(vhCelda).toUpperCase());
            vhCelda = (XSSFCell)viFila.next();
            voAdm.setVsRegion(fncLeerCelda(vhCelda).toUpperCase());
            vhCelda = (XSSFCell)viFila.next();
            voAdm.setVsPerfil(fncLeerCelda(vhCelda).toUpperCase());
            vhCelda = (XSSFCell)viFila.next();
            voAdm.setVsPrimerANum(fncLeerCelda(vhCelda).toLowerCase());
            vhCelda = (XSSFCell)viFila.next();
            voAdm.setVsSegundoANum(fncLeerCelda(vhCelda).toLowerCase());
            vhCelda = (XSSFCell)viFila.next();
            voAdm.setVsGrupo(fncLeerCelda(vhCelda).toUpperCase());
            vhCelda = (XSSFCell)viFila.next();
            voAdm.setVsMensajeMail(fncLeerCelda(vhCelda));
            voListRes.add(voAdm);
            contador = 0;
          } else {
            contador++;
            if (contador > 2) { //Limite de 3 filas vacias antes de parar
              //System.out.println("Fin del archivo");
              break;
            }
          }
        }
      }
    }
    catch (IOException e) {
      log.error(e);
      System.out.println(e);
    }
    return voListRes;
  }
  
  public List<CiVO> fncLeerArchivoCI(String psRuta)
  {
    log.info("RUTA: " + psRuta);
    int i = 0;
    System.out.println("Ruta" + psRuta);
    
    List<CiVO> voListRes = new ArrayList();
    try
    {
      InputStream viExcel = new FileInputStream(psRuta);
      XSSFWorkbook vhLibro = new XSSFWorkbook(viExcel);
      
      XSSFSheet vhHoja = vhLibro.getSheetAt(1);
      
      Iterator viFilas = vhHoja.iterator();
      
      viFilas.next();
      viFilas.next();
      viFilas.next();
      while (viFilas.hasNext())
      {
        XSSFRow vhFila = (XSSFRow)viFilas.next();
        Iterator viFila = vhFila.cellIterator();
        if (viFila.hasNext())
        {
          CiVO voCI = new CiVO();
          XSSFCell vhCelda = (XSSFCell)viFila.next();
          voCI.setCompania(fncLeerCelda(vhCelda));
          vhCelda = (XSSFCell)viFila.next();
          voCI.setSitioAlrma(fncLeerCelda(vhCelda));
          vhCelda = (XSSFCell)viFila.next();
          voCI.setNombre(fncLeerCelda(vhCelda));
          vhCelda = (XSSFCell)viFila.next();
          voCI.setDescripcion(fncLeerCelda(vhCelda));
          vhCelda = (XSSFCell)viFila.next();
          voCI.setRegion(fncLeerCelda(vhCelda));
          vhCelda = (XSSFCell)viFila.next();
          voCI.setConectado(fncLeerCelda(vhCelda));
          vhCelda = (XSSFCell)viFila.next();
          voCI.setTecnologia(fncLeerCelda(vhCelda));
          vhCelda = (XSSFCell)viFila.next();
          voCI.setTipo(fncLeerCelda(vhCelda));
          vhCelda = (XSSFCell)viFila.next();
          voCI.setGrupo(fncLeerCelda(vhCelda));
          vhCelda = (XSSFCell)viFila.next();
          voCI.setIp(fncLeerCelda(vhCelda));
          voListRes.add(voCI);
        }
      }
    }
    catch (IOException e)
    {
      log.error(e);
      System.out.println(e);
    }
    return voListRes;
  }
  
  public static void main(String[] args)
  {
    LecturaArchivo voArchivo = new LecturaArchivo();
    RemedyControl voRControl = new RemedyControl();
    CargaRM voCargaRM = new CargaRM();
    
    int i = 0;
    
    List<AdmVO> voList = voArchivo.fncLeerArchivoAdm("C:\\Users\\Daniel\\Desktop\\Trabajo\\Java\\CargasRControl\\Prueba\\Formato+Alta+de+Perfiles+ADM.xlsx");

    for (AdmVO indexADM : voList) {
      System.out.println(indexADM.toString());
    }

    /*for (AdmVO rutina : voList)
    {
      System.out.println(i++);
      System.out.println(rutina.getVsCompania());
      System.out.println(rutina.getVsSistema());
      System.out.println(rutina.getVsNodo());
      System.out.println(rutina.getVsRegion());
      System.out.println(rutina.getVsPerfil());
      System.out.println(rutina.getVsPrimerAName());
      System.out.println("rutina.getVsPrimerANum()" + rutina.getVsPrimerANum());
      System.out.println(rutina.getVsSegundoAName());
      System.out.println("rutina.getVsSegundoANum()" + rutina.getVsSegundoANum());
      System.out.println(rutina.getVsGrupo());
      System.out.println(rutina.getVsMensajeMail());
    }*/
  }
}
