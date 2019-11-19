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

    public JAXBElement unmarshalizable(File ficheroXML) throws JAXBException;

    public void marshalizar(JAXBElement jaxbElement) throws JAXBException;

    //a) Contar el número de las definiciones de una determinada palabra
    public int numeroDefiniciones(DiccionarioEspanol diccionario, PalabraType palabra);

    //a2) Contar el total de de definiciones de cada palabra.
    
    //b) Borrartodas las traducciones de un determinado idioma (ej. PT)y lo grabe en otro fichero
    public File borrarTraducciones(DiccionarioEspanol diccionario, String idioma) throws IOException;

    //c) Generarun Map<K,V> donde K es el idioma de traducción y V es el total de traducciones que hay
    public Map<String, Integer> numeroTraduccionesIdioma(DiccionarioEspanol diccionario);

    //d) Retornarpara una determinada palabra todos los sinónimos seguidos de sus definiciones
    public Map<String,ArrayList<String>> sinonimosDefiniciones(DiccionarioEspanol diccionario, PalabraType palabra);
    
    //Añadir dos nuevas:
    //e) Numero de traducciones de una determinada palabra
    
    //f) Para una determinada palabra, todas las definiciones seguidas de su traducción.
    
}
