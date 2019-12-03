package Controlador;

import Modelo.MetodosDiccionario2JAXB;
import java.io.File;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.xml.bind.JAXBElement;
import javax.xml.bind.JAXBException;
import jaxb.diccionario2.DiccionarioEspanol;
import jaxb.diccionario2.ObjectFactory;

/**
 *
 * @author Gonzalo
 */
public class MainPruebasJAXB {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        
        try {
            //Creamos el File fichero que es el XML para obtener los datos y trabajar con ellos.
            File ficheroXML = new File("diccionario2.xml");
            //Creamos un JAXBElement que contendrá elementos leidos del archivo XML.
            JAXBElement jaxbElement = MetodosDiccionario2JAXB.unmarshalizable(ficheroXML);
            //Creamos el objeto ObjectFactory para después crear el objeto AlbumesFotos.
            ObjectFactory fabrica = new ObjectFactory();
            //Creamos el objeto AlbumesFotos a través del ObjectFactory.
            DiccionarioEspanol diccionario = fabrica.createDiccionarioEspanol();
            //Obtenemos los valores correspondientes al objeto AlbumesFotos a través del objeto JAXBElement.
            diccionario = (DiccionarioEspanol) jaxbElement.getValue();
            //--------------------Ejecución de los métodos--------------------//
            //----------------------------------------------------------------//
            int numero = MetodosDiccionario2JAXB.contarSinonimosYAntonimos(diccionario, "String1");
            System.out.println("Hay un total de " + numero + " sinónimos y antónimos");
            //----------------------------------------------------------------//
            boolean borrado = MetodosDiccionario2JAXB.borrarPalabra(diccionario, "String1");
            System.out.println("Se ha borrado la palabra con la grafía 'String1': " + borrado);
            //----------------------------------------------------------------//
            Map<String, Integer> listaPalabras = MetodosDiccionario2JAXB.sinonimosSimilitudMayorOchenta(diccionario);
            System.out.println("Lista de palabras con un porcentaje de similitud del 80%: " + listaPalabras);
            //----------------------------------------------------------------//
            int numVeces = MetodosDiccionario2JAXB.numeroVecesApareceGrafia(diccionario, "String");
            System.out.println("El número de veces que aparece la grafía de una palbra en lalista de sinónimos es de: " + numVeces);
        } catch (JAXBException ex) {
            Logger.getLogger(MainPruebasJAXB.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        
        
    }
    
}
