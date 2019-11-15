/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controlador;

import Modelo.Coche;
import Modelo.MiExcepcionVehiculos;
import Modelo.Posicion;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Gonzalo
 */
public class NewMain {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        Posicion p = new Posicion(0, 0, 0);
        Coche c = new Coche("Seat", "Ibiza", 80, 0, 0, 0, 0, "AAA", p);
        try {
            c.repostar(50);
        } catch (MiExcepcionVehiculos ex) {
            Logger.getLogger(NewMain.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }
    
}
