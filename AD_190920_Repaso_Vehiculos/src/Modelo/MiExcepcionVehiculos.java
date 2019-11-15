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
public class MiExcepcionVehiculos extends Exception{
    /*
    public MiExcepcionVehiculos(){
        super("Error, Comprueba si los valores introducidos son correctos y no superan las limitaciones.");
    }
    */

    public static class MiExcepcionRepostar extends Exception{
        public MiExcepcionRepostar() {
            super("Error, No se puede superar la capacidad del deposito.");
        }
    }

    public static class MiExcepcionVelocidad extends Exception{
        public MiExcepcionVelocidad() {
            super("Error, No se puede superar la velocidad del permitida.");
        }
    }
    
}
