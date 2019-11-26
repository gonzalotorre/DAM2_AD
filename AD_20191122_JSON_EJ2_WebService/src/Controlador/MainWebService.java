/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controlador;

import Modelo.JSONMetodosWebService;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Gonzalo
 */
public class MainWebService {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        
        try {
            //JSONMetodosWebService.crearFicheroJSON("FicheroJSON.json");
            double elevation = JSONMetodosWebService.mostrarElevacion("FicheroJSON.json");
            System.out.println("Elevación en caso de status OK: "+elevation);
            //----------------------------------------------------------------//
            double resolution = JSONMetodosWebService.mostrarResolucion("FicheroJSON.json");
            System.out.println("Resolución en caso de status OK: " + resolution);
        } catch (IOException ex) {
            Logger.getLogger(MainWebService.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        
    }
    
}
