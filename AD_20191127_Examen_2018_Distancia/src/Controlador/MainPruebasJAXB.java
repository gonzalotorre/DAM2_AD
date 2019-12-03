package Controlador;

import Modelo.MetodosBibliotecaJAXB;
import java.io.File;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.xml.bind.JAXBElement;
import javax.xml.bind.JAXBException;
import jaxb.biblioteca.Biblioteca;
import jaxb.biblioteca.ObjectFactory;
import jaxb.biblioteca.SocioType;

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
            List<String> apellidos = new ArrayList<>();
            apellidos.add("García");
            apellidos.add("Torre");
            BigInteger telefono = BigInteger.ONE;
            boolean anadido = MetodosBibliotecaJAXB.crearSocio(biblioteca, "1234", "Gonzalo", apellidos, telefono, null);
            System.out.println("Socio añadido: " + anadido);
            //----------------------------------------------------------------//
            SocioType socio = MetodosBibliotecaJAXB.buscarSocio(biblioteca, "1234");
            System.out.println("Socio: " + socio.toString());
            //----------------------------------------------------------------//
            boolean borrado = MetodosBibliotecaJAXB.borrarPrestamos(biblioteca, "String1");
            System.out.println("Borrados los prestamos del socio String1: " + borrado);
            //----------------------------------------------------------------//
            //Está hecho pero mal, habría que usar los GregorianCalendar
            boolean renovada = MetodosBibliotecaJAXB.renovarFechaSocio(biblioteca, "String");
            System.out.println("Renovada la fecha de los prestamos: " + renovada);
            //----------------------------------------------------------------//
            MetodosBibliotecaJAXB.marshalizar(jaxbElement);
        } catch (JAXBException ex) {
            Logger.getLogger(MainPruebasJAXB.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

}
