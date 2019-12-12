package Controlador;

import Modelo.MetodosChatJAXB;
import java.io.File;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.xml.bind.JAXBElement;
import javax.xml.bind.JAXBException;
import jax.chat.MensajeType;
import jax.chat.ObjectFactory;
import jax.chat.QuickChat;

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
            File ficheroXML = new File("charusuarios.xml");
            //Creamos un JAXBElement que contendrá elementos leidos del archivo XML.
            JAXBElement jaxbElement = MetodosChatJAXB.unmarshalizable(ficheroXML);
            //Creamos el objeto ObjectFactory para después crear el objeto AlbumesFotos.
            ObjectFactory fabrica = new ObjectFactory();
            //Creamos el objeto AlbumesFotos a través del ObjectFactory.
            QuickChat chat = fabrica.createQuickChat();
            //Obtenemos los valores correspondientes al objeto AlbumesFotos a través del objeto JAXBElement.
            chat = (QuickChat) jaxbElement.getValue();
            //--------------------Ejecución de los métodos--------------------//
            //----------------------------------------------------------------//
            MensajeType mensajeType = chat.getChats().getChat().get(0).getMensajesArchivados().getMensaje().get(0);
            mensajeType.setTextoMensaje("String2");
            boolean anadido = MetodosChatJAXB.insertarChat(chat, mensajeType, "String1", "String1");
            System.out.println("Mensaje añadido: " + anadido);
            MetodosChatJAXB.crearBackUp(chat);
            int numero = MetodosChatJAXB.verificarCoherencia(chat);
            System.out.println(numero);
            //MetodosChatJAXB.marshalizar(jaxbElement);
        } catch (JAXBException ex) {
            Logger.getLogger(MainPruebasJAXB.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        
        
    }
    
}
