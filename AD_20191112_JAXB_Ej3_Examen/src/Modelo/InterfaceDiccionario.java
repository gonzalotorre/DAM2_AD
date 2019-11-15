/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Modelo;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Map;
import javax.xml.bind.JAXBElement;
import javax.xml.bind.JAXBException;
import jaxb.diccionario.DiccionarioEspanol;
import jaxb.diccionario.PalabraType;

/**
 *
 * @author Gonzalo
 */
public interface InterfaceDiccionario {

    /**
     *
     * @param ficheroXML
     * @return
     * @throws javax.xml.bind.JAXBException
     */
    public JAXBElement unmarshalizable(File ficheroXML) throws JAXBException;

    /**
     *
     * @param jaxbElement
     * @throws javax.xml.bind.JAXBException
     */
    public void marshalizar(JAXBElement jaxbElement) throws JAXBException;

    public int numeroDefiniciones(DiccionarioEspanol diccionario, PalabraType palabra);

    public File borrarTraducciones(DiccionarioEspanol diccionario, String idioma) throws IOException;

    public Map<String, Integer> numeroTraduccionesIdioma(DiccionarioEspanol diccionario);

    public ArrayList<String> sinonimos(DiccionarioEspanol diccionario, PalabraType palabra);
}
