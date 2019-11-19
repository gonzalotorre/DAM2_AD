/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Modelo;

/**
 *
 * @author Gonzalo
 */
public class MiExcepcion {

    public static class entradaDuplicada extends Exception {

        public entradaDuplicada(String message) {
            super("Se han encontrado dos contactos con el mismo nombre.");
        }
    }
    
}
