/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.telcel.carga.control;

import com.infomedia.utils.PropertyLoader;
import com.telcel.carga.Utileria.LecturaArchivo;
import com.telcel.carga.Utileria.RemedyControl;
import com.telcel.carga.correo.Correos;
import com.telcel.carga.entry.CiVO;
import com.telcel.carga.entry.OrdenTrabajoVO;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import org.apache.log4j.Logger;
/**
 *
 * @author ELopez
 */
public class CargaCI {

    private static Properties prop = PropertyLoader.load("cargarm.properties");
    private static final Logger log = Logger.getLogger(CargaRM.class);
    private static final RemedyControl voRemedyControl = new RemedyControl();
    private static final LecturaArchivo voLectura = new LecturaArchivo();
    private static final Correos voCorreo = new Correos();
    
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        List<OrdenTrabajoVO> voOrdenesT = voRemedyControl.fncConsultaOrdenesT("CI");
        List<CiVO> voCI = new ArrayList<>();
        boolean exito;
        for (OrdenTrabajoVO ordenTrabajoVO : voOrdenesT) {
            System.out.println(ordenTrabajoVO.toString());
        }
        for (OrdenTrabajoVO ordenTrabajoVO : voOrdenesT) {
            exito = true;
            voRemedyControl.setVoErrores(new ArrayList<String>());
            
            System.out.println(ordenTrabajoVO.getViIDRequerimiento());
            voRemedyControl.fncDescargarArchivo(ordenTrabajoVO.getViIDRequerimiento());
            voCI = voLectura.fncLeerArchivoCI(prop.getProperty("PATH_CI"));
            if (!voCI.isEmpty()) {
                for(CiVO voCIu: voCI){
                    if(!voCIu.getNombre().isEmpty()){
                        if(voRemedyControl.fncInsertarCI(voCIu)){
                            log.info("Sitio creado: " + voCIu.toString());
                            System.out.println("Sitio creado: " + voCIu.toString());
                            exito &= true;
                        }else{
                            exito &= false;
                            log.error("Error creando sitio: " + voCIu.toString());
                            System.out.println("Error creando sitio: " + voCIu.toString());
                            voRemedyControl.fncInsertComentario("Error al registrar sitio", "Error al intentar generar el registro: " + voCIu.toString(), ordenTrabajoVO.getViIDOrden());
                            //voOrdenesT.remove(ordenTrabajoVO);
                        }
                    }
                }
            }
            if(exito){
                voRemedyControl.fncInsertComentario("Sitios creados", "Los sitios fueron creados con exito", ordenTrabajoVO.getViIDOrden());
                voRemedyControl.fncCerrarWO( ordenTrabajoVO.getViIDOrden(), "Successful");    
            }else{
                voRemedyControl.fncInsertComentario("Sitios creados parcialmente", "No todos los sitios se registraron o pasaron a status operando", ordenTrabajoVO.getViIDOrden());
                voRemedyControl.fncCerrarWO( ordenTrabajoVO.getViIDOrden(), "Unsuccessful");
            }
        }
    }
    
}
