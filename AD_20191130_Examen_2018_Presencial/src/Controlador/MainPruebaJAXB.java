package Controlador;

import Modelo.MetodosBibliotecaJAXB;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.json.JsonArray;
import javax.json.JsonObject;
import javax.xml.bind.JAXBElement;
import javax.xml.bind.JAXBException;
import jaxb.biblioteca.Biblioteca;
import jaxb.biblioteca.ObjectFactory;

/**
 *
 * @author Gonzalo
 */
public class MainPruebaJAXB {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {

        try {
            //Creamos el File fichero que es el XML para obtener los datos y trabajar con ellos.
            File ficheroXML = new File("BIBLIOTECA.xml");
            //Creamos un JAXBElement que contendrá elementos leidos del archivo XML.
            JAXBElement jaxbElement = MetodosBibliotecaJAXB.unmarshalizable(ficheroXML);
            //Creamos el objeto ObjectFactory para después crear el objeto AlbumesFotos.
            ObjectFactory fabrica = new ObjectFactory();
            //Creamos el objeto AlbumesFotos a través del ObjectFactory.
            Biblioteca biblioteca = fabrica.createBiblioteca();
            //Obtenemos los valores correspondientes al objeto AlbumesFotos a través del objeto JAXBElement.
            biblioteca = (Biblioteca) jaxbElement.getValue();
            //--------------------Ejecución de los métodos--------------------//
            //----------------------------------------------------------------//
            Map<String, List<String>> lista = MetodosBibliotecaJAXB.listaLibrosPorSocio(biblioteca);
            System.out.println(lista);
            //----------------------------------------------------------------//
            GregorianCalendar fecha = new GregorianCalendar(1967, 8, 14);
            List<String> listaISBN = MetodosBibliotecaJAXB.listaLibrosRetrasados(biblioteca, fecha, "String");
            for (String string : listaISBN) {
                System.out.println("----->" + string);
            }
            //----------------------------------------------------------------//
            JsonObject listaSocios = MetodosBibliotecaJAXB.generarJSON(biblioteca);
            MetodosBibliotecaJAXB.grabarFicheroJSON(listaSocios, "listaSociosJSON.json");
        } catch (JAXBException ex) {
            Logger.getLogger(MainPruebaJAXB.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(MainPruebaJAXB.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

}
