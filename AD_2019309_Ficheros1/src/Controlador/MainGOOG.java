package Controlador;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */


import Modelo.MetodosGrafica;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Gonzalo
 */
public class MainGOOG {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        try {
            MetodosGrafica.ficheroHTML();
        } catch (IOException ex) {
            Logger.getLogger(MainGOOG.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
}
