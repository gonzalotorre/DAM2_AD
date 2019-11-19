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
import jaxb.diccionario.SinonimoType;
import jaxb.diccionario.TraduccionType;

/**
 *
 * @author Gonzalo
 */
public class MetodosDiccionario implements InterfaceDiccionario {

    /**
     * Método unmarhal (leer) para poder leer el fichero xml y luego hacer las
     * operaciones pedidas.
     *
     * @param ficheroXML del que se van a obtener los datos.
     * @return
     * @throws JAXBException
     */
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

    /**
     * Método marshal (escribir) para aplicar los cambios al documento XML.
     *
     * @param jaxbElement
     * @throws JAXBException
     */
    @Override
    public void marshalizar(JAXBElement jaxbElement) throws JAXBException {
        JAXBContext jaxbContext = JAXBContext.newInstance("jaxb.diccionario");
        Marshaller m = jaxbContext.createMarshaller();
        m.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);
        m.marshal(jaxbElement, System.out);
    }

    /**
     * Método que devuelve el número de definiciones de una determinada palabra.
     *
     * @param diccionario para acceder a la lista de palabras.
     * @param palabra de la que se quiere saber el número de definiciones.
     * @return el número de definiciones.
     */
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

    /**
     * Método para borrar las traducciones de un idioma.
     *
     * @param diccionario para acceder a la lista de palabars y a continuación a
     * la lista de traducciones para buscar el idioma
     * @param idioma del que se quieren borrar las traducciones.
     * @return un fichero con las traducciones que se borraron.
     * @throws IOException
     */
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

    /**
     * Método que muestre para un idioma el número de traducciones quetiene,
     * para ello uso un Map<String(idioma), Integer(numTraducciones)>
     *
     * @param diccionario para acceder a la lista de palabras y a su vez a la
     * lista de traducciones.
     * @return una lista Map con el idioma y el número de traducciomes.
     */
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

    /**
     * Método que para una determinada palabra muestre los sinónimos seguidos de
     * sus definiciones, para ello uso una lista Map<String(sinonimo),
     * ArrayLista<String(definición)>(listaDefiniciones)>
     *
     * @param diccionario para acceder a la lista de palabras y a su vez a la
     * lista de sinónimos y de definiciones.
     * @param palabra de la que se quieren ver los sinónimos seguidos de las definiciones.
     * @return una lista con el sinónimo y sus definiciones.
     */
    @Override
    public Map<String, ArrayList<String>> sinonimosDefiniciones(DiccionarioEspanol diccionario, PalabraType palabra) {
        Map<String, ArrayList<String>> lista = new HashMap<>();
        for (PalabraType palabraType : diccionario.getPalabra()) {
            if (palabraType.getGrafia().equalsIgnoreCase(palabra.getGrafia())) {
                ArrayList<String> definiciones = (ArrayList<String>) palabraType.getDefinicion();
                for (SinonimoType sinonimoType : palabra.getSinonimos().getSinonimo()) {
                    lista.put(sinonimoType.getGrafia(), definiciones);
                }
            }
        }
        return lista;
    }

}
