package Controlador;

import Modelo.ManejoClientes;
import java.io.File;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.xml.bind.JAXBElement;
import javax.xml.bind.JAXBException;
import jaxb.clientes.Clientes;
import jaxb.clientes.ObjectFactory;

/**
 *
 * @author Gonzalo
 */
public class MainPruebas {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {

        try {
            ManejoClientes manejo = new ManejoClientes();
            File ficheroXML = new File("clientes.xml");
            JAXBElement jaxb = manejo.unmarshalizable(ficheroXML);
            manejo.marshalizar(jaxb);

            ObjectFactory fabrica = new ObjectFactory();
            Clientes c = fabrica.createClientes();
            c = (Clientes) jaxb.getValue();

            int numClientes = manejo.numeroClientes(c);
            System.out.println("Numero clientes: " + numClientes);
            
            int numClietesProvincia = manejo.numeroClientesProvincia(c, 33930);
            System.out.println("Numero clientes con cp 33930: " + numClietesProvincia);
            
            boolean borrado = manejo.borrarCliente(c, "Garcia", "Torre");
            System.out.println("Cliente borrado: " + borrado);
            
            boolean anadido = manejo.anadirCliente(c, "Gonzalez", "Moreno", null, "123456789", null);
            System.out.println("Cliente añadido: " + anadido);
            
            int numDireccionesBorradas = manejo.borrarDirecciones(c);
            System.out.println("Borradas " + numDireccionesBorradas + " direcciones sin codigo postal");
            
        } catch (JAXBException ex) {
            Logger.getLogger(MainPruebas.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

}
