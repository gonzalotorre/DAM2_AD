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
public interface Volable {

    public float ascender(Posicion ascenso) throws MiExcepcionVehiculos;

    public float descender(Posicion descenso) throws MiExcepcionVehiculos;

}
