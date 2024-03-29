/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.telcel.carga.control;

import com.infomedia.utils.PropertyLoader;
import com.telcel.carga.Utileria.LecturaArchivo;
import com.telcel.carga.Utileria.RemedyControl;
import com.telcel.carga.entry.OrdenTrabajoVO;
import com.telcel.carga.entry.RutinaVO;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.*;

import org.apache.log4j.Logger;

/**
 *
 * @author AMCruz
 */
public class CargaRM {

    private static Properties prop = PropertyLoader.load("cargarm.properties");

    public CargaRM() {
    }
    private static final Logger log = Logger.getLogger(CargaRM.class);
    private static final RemedyControl voRemedyControl = new RemedyControl();
    private static final LecturaArchivo VO__LECTURA = new LecturaArchivo();

    public void ExcCommand(String command) {

        String s = null;
        try {
            // Se lanza el ejecutable. 
            Process p = Runtime.getRuntime().exec(command);
            BufferedReader stdInput = new BufferedReader(new InputStreamReader(p.getInputStream()));
            // Leemos la salida del comando
            p.waitFor();

            while ((s = stdInput.readLine()) != null) {
                log.info(s);
            }
            log.info("COMADO:");
            log.info(command);
            if (p.exitValue() == 0) {
                log.info("Ejecutado exitosamente!!");
            } else {
                log.info("No executado!!");
            }

        } catch (Exception e) {

            log.error(e);
        }
    }

    public List<String> fncObtenerNomArchivo(List<RutinaVO> voListRutina) {
        String vsNomArchivo = "";
        List<String> listArchivos = new ArrayList<>();
        //System.out.println("Lecura de archivos: ");
        for (RutinaVO rutinaVO : voListRutina) {
            listArchivos.add(rutinaVO.getVsNombreArchivo());
            //System.out.println(rutinaVO.getVsNombreArchivo() + ", ");
        }
        //Quita todos los duplicados
        Set<String> set = new HashSet<>(listArchivos);
        listArchivos.clear();
        listArchivos.addAll(set);
        return listArchivos;
    }

    public static void main(String[] args) {
        CargaRM voCarga = new CargaRM();
        String vsNomArchivo = "";
        List<String> listArchivos;
        String vsComentario = "";
        List<OrdenTrabajoVO> voListWO = voRemedyControl.fncConsultaOrdenesT("RM");
        List<RutinaVO> voListRutina;
        for (OrdenTrabajoVO ordenTrabajoVO : voListWO) {
            log.info(ordenTrabajoVO.getViIDOrden());
            log.info(ordenTrabajoVO.getViIDRequerimiento());
            voRemedyControl.setVoErrores(new ArrayList<String>());
            voCarga.ExcCommand("rm " + prop.getProperty("PATH"));
            if (voRemedyControl.fncDescargarArchivo(ordenTrabajoVO.getViIDRequerimiento())) {
                //Se recupera el nombre del archivo que contiene las rutinas de remedy
                vsNomArchivo = voRemedyControl.fncRecuperarNombreArchivo(ordenTrabajoVO.getViIDRequerimiento());
                System.out.println("Aqui esta tu archivo: " + prop.getProperty("PATH") + vsNomArchivo);
                voListRutina = VO__LECTURA.fncLeerArchivo(prop.getProperty("PATH") + vsNomArchivo);
                //Archivos que se movera
                listArchivos = voCarga.fncObtenerNomArchivo(voListRutina);
                if (voRemedyControl.fncInsertRutinas(voListRutina, ordenTrabajoVO.getViIDOrden())) {
                    if (!voRemedyControl.getVoErrores().isEmpty()) {
                        for (String errores : voRemedyControl.getVoErrores()) {
                            System.out.println(errores);
                            vsComentario += errores;
                        }
                        voRemedyControl.fncInsertComentario("OBSERVACIONES DEL SISTEMA", vsComentario, ordenTrabajoVO.getViIDOrden());
                        /*System.out.println("Lista archivos: ");
                        for (String indexMover : listArchivos) {
                            System.out.print(indexMover + ", ");
                        }*/
                        for (String indexMover : listArchivos) {
                            //System.out.println("AQUI ES DONDE SE MUEVE TODO");
                            voCarga.ExcCommand("mv /home/remedy/download/RM/" + indexMover + " /home/remedy/archivos/" + indexMover);
                        }
                        voRemedyControl.fncCerrarWO(ordenTrabajoVO.getViIDOrden(), "Unsuccessful");
                    } else {
                        /*System.out.println("Lista archivos: ");
                        for (String indexMover : listArchivos) {
                            System.out.print(indexMover + ", ");
                        }*/
                        for (String indexMover : listArchivos) {
                            //System.out.println("AQUI ES DONDE SE MUEVE TODO");
                            voCarga.ExcCommand("mv /home/remedy/download/RM/" + indexMover + " /home/remedy/archivos/" + indexMover);
                        }
                        voRemedyControl.fncCerrarWO(ordenTrabajoVO.getViIDOrden(), "Successful");
                    }
                } else {
                    if (voRemedyControl.getVoErrores().isEmpty()) {
                        voRemedyControl.fncInsertComentario("OBSERVACIONES DEL SISTEMA",
                                "No se incluyo archivo con el nombre o formato especifico", ordenTrabajoVO.getViIDOrden());
                    } else {
                        for (String errores : voRemedyControl.getVoErrores()) {
                            System.out.println(errores);
                            vsComentario += errores;
                        }
                        voRemedyControl.fncInsertComentario("OBSERVACIONES DEL SISTEMA", vsComentario, ordenTrabajoVO.getViIDOrden());
                    }

                    voRemedyControl.fncCancelarWO(ordenTrabajoVO.getViIDOrden());
                }
            } else {
                voRemedyControl.fncInsertComentario("OBSERVACIONES DEL SISTEMA", "No se incluyo archivo para la carga",
                        ordenTrabajoVO.getViIDOrden());
                voRemedyControl.fncCancelarWO(ordenTrabajoVO.getViIDOrden());
            }
            vsComentario = "";
        }
    }

}
