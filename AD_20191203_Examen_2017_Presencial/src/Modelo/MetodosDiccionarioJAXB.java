package Modelo;

import java.io.File;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBElement;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import javax.xml.transform.stream.StreamSource;
import jaxb.diccionario.DiccionarioEspanol;

/**
 *
 * @author Gonzalo
 */
public class MetodosDiccionarioJAXB {

    /**
     * Método para leer el ficher xml y retornar un elemento de dicho xml. Crear
     * una instancia de la clase JAXBContext para poder manipular las clases
     * generadas en el paquete jaxb.albumesFotos. unmarshal: consiste en
     * convertir datos XML en un árbol de objetos Java
     *
     * @param ficheroXML
     * @return
     * @throws JAXBException
     */
    public static JAXBElement unmarshalizable(File ficheroXML) throws JAXBException {
        //Creamos una instancia de JAXBContext para manipular las clases generadas en jaxb.albumesFotos
        JAXBContext jaxbContext = JAXBContext.newInstance("jaxb.diccionario");
        //Objeto unmarshall para leer el fichero xml.
        Unmarshaller unmashall = jaxbContext.createUnmarshaller();
        //Creamos un jaxbElement que representa un elemento del XML del documento fotos.xml
        JAXBElement jaxbElement = unmashall.unmarshal(new StreamSource(new File("diccionario.xml")), DiccionarioEspanol.class);
        return jaxbElement;
    }

    /**
     * marshal: consiste en convertir un árbol de objetos Java a datos XML
     *
     * @param jaxbElement
     * @throws JAXBException
     */
    public static void marshalizar(JAXBElement jaxbElement) throws JAXBException {
        //Creamos una instancia de JAXBContext para manipular las clases generadas en jaxb.albumesFotos
        JAXBContext jaxbContext = JAXBContext.newInstance("jaxb.diccionario");
        //Crear un objeto de tipo Marshaller para posteriormente convertir un el árbol de objetos Java a datos XML
        Marshaller m = jaxbContext.createMarshaller();
        m.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);
        m.marshal(jaxbElement, System.out);
    }
    
    
    
    
    
    
}
