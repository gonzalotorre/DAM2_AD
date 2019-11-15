/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Modelo;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBElement;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import javax.xml.transform.stream.StreamSource;
import jaxb.diccionario.DiccionarioEspanol;
import jaxb.diccionario.PalabraType;
import jaxb.diccionario.TraduccionType;

/**
 *
 * @author Gonzalo
 */
public class MetodosDiccionario implements InterfaceDiccionario {

    @Override
    public JAXBElement unmarshalizable(File ficheroXML) throws JAXBException {
        //Creamos una instancia de JAXBContext para manipular las clases generadas en jaxb.albaran
        JAXBContext jaxbContext = JAXBContext.newInstance("jaxb.diccionario");
        //Objeto unmarshall para 
        Unmarshaller unmashall = jaxbContext.createUnmarshaller();
        //
        JAXBElement jaxbElement = unmashall.unmarshal(new StreamSource(new File("diccionario.xml")), DiccionarioEspanol.class);
        return jaxbElement;
    }

    @Override
    public void marshalizar(JAXBElement jaxbElement) throws JAXBException {
        JAXBContext jaxbContext = JAXBContext.newInstance("jaxb.diccionario");
        Marshaller m = jaxbContext.createMarshaller();
        m.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);
        m.marshal(jaxbElement, System.out);
    }

    @Override
    public int numeroDefiniciones(DiccionarioEspanol diccionario, PalabraType palabra) {
        int contador = 0;
        for (PalabraType palabraType : diccionario.getPalabra()) {
            if (palabraType.equals(palabra)) {
                contador = palabraType.getDefinicion().size();
            }
        }
        return contador;
    }

    @Override
    public File borrarTraducciones(DiccionarioEspanol diccionario, String idioma) throws IOException {
        int contador = 0;
        File fichero = new File("Traducciones.txt");
        FileWriter fr = new FileWriter(fichero);
        BufferedWriter bfr = new BufferedWriter(fr);
        for (PalabraType palabra : diccionario.getPalabra()) {
            for (Iterator<TraduccionType> it = palabra.getTraducciones().getTraduccion().iterator(); it.hasNext();) {
                TraduccionType traduccion = it.next();
                if (traduccion.getIdiomaTraduccion().equals(idioma)) {
                    bfr.write(traduccion.getGrafia() + "\n");
                    it.remove();
                }
                contador++;
            }
        }
        bfr.close();
        fr.close();
        return fichero;
    }

    @Override
    public Map<String, Integer> numeroTraduccionesIdioma(DiccionarioEspanol diccionario) {
        Map<String, Integer> mapTraducciones = new HashMap<>();
        for (PalabraType palabraType : diccionario.getPalabra()) {
            for (TraduccionType traduccionType : palabraType.getTraducciones().getTraduccion()) {
                if (mapTraducciones.containsKey(traduccionType.getIdiomaTraduccion())) {
                    int numTraducciones = mapTraducciones.get(traduccionType.getIdiomaTraduccion());
                    mapTraducciones.replace(traduccionType.getIdiomaTraduccion(), numTraducciones);
                } else {
                    mapTraducciones.put(traduccionType.getIdiomaTraduccion(), 1);
                }
            }
        }
        return mapTraducciones;
    }

    @Override
    public ArrayList<String> sinonimos(DiccionarioEspanol diccionario, PalabraType palabra) {
        String sinonimo;
        for (PalabraType palabraType : diccionario.getPalabra()) {
            if (palabraType.getGrafia().equalsIgnoreCase(palabra.getGrafia())) {
                for (String string : palabraType.getDefinicion()) {
                    
                }
            }
        }return null;
    }

}
