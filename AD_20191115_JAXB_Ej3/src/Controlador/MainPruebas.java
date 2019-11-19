/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controlador;

import Modelo.MetodosAgenda;
import Modelo.MiExcepcion;
import java.io.File;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.xml.bind.JAXBElement;
import javax.xml.bind.JAXBException;
import jaxb.agenda.Agenda;
import jaxb.agenda.ContactoType;
import jaxb.agenda.ObjectFactory;

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
            MetodosAgenda manejo = new MetodosAgenda();
            File archivoXML = new File("agenda.xml");
            JAXBElement jaxb = manejo.unmarshalizable(archivoXML);
            //Creamos una fabrica de objetos para obtener el diccionario
            ObjectFactory fabrica = new ObjectFactory();
            //Creamos un DiccionarioEspañol con los datos obtenidos de la fabrica
            Agenda agenda = fabrica.createAgenda();
            agenda = (Agenda) jaxb.getValue();

            boolean anadido = manejo.anadirAlarma(agenda, "algo1", "algo2", "algo3", null);
            System.out.println("Alarma añadida: " + anadido);

            ContactoType.Nombre nombre = agenda.getContactos().getContacto().get(0).getNombre();

            boolean anadido2 = manejo.anadirTelefono(agenda, nombre, " ");
            System.out.println("Telefono añadido: " + anadido2);

            Map correos = manejo.verCorreos(agenda);
            Collection c = correos.values();
            Object[] o = c.toArray();
            System.out.println(correos.keySet());
            System.out.println(o[0]);
            System.out.println(correos.size());

            List tlf = manejo.buscarTelefonos(agenda, nombre);
            for (Object object : tlf) {
                System.out.println(object);
            }

            boolean borrado = manejo.borrarContacto(agenda, nombre);
            System.out.println("Contacto borrado:" + borrado);
            
            Map totalEntrads = manejo.contarTotalEntradas(agenda);
            Collection c2 = totalEntrads.values();
            Object[] o2 = c2.toArray();
            System.out.println(o2[0].toString());
            
            

        } catch (JAXBException ex) {
            Logger.getLogger(MainPruebas.class.getName()).log(Level.SEVERE, null, ex);
        } catch (MiExcepcion.entradaDuplicada ex) {
            Logger.getLogger(MainPruebas.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

}
