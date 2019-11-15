package Controlador;

import Modelo.MetodosDiccionario;
import java.io.File;
import java.io.IOException;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.xml.bind.JAXBElement;
import javax.xml.bind.JAXBException;
import jaxb.diccionario.DiccionarioEspanol;
import jaxb.diccionario.ObjectFactory;
import jaxb.diccionario.PalabraType;

/**
 *
 * @author Gonzalo
 */
public class MainDiccionario {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        try {
            MetodosDiccionario manejo = new MetodosDiccionario();
            File archivoXML = new File("diccionario.xml");
            JAXBElement jaxb = manejo.unmarshalizable(archivoXML);
            manejo.marshalizar(jaxb);
            //Creamos una fabrica de objetos para obtener el pedido y luego poder añadir un artículo
            ObjectFactory fabrica = new ObjectFactory();
            //Creamos un pedidoType con los datos obtenidos de la fabrica
            DiccionarioEspanol diccionario = fabrica.createDiccionarioEspanol();
            //Coge los valores que hay en el jaxb element y los mete al pedido
            diccionario = (DiccionarioEspanol) jaxb.getValue();

            PalabraType palabra = diccionario.getPalabra().get(0);
            int numeroDefiniciones = manejo.numeroDefiniciones(diccionario, palabra);
            System.out.println("Numero definiciones: " + numeroDefiniciones);
            
            manejo.borrarTraducciones(diccionario, palabra.getTraducciones().getTraduccion().get(0).getIdiomaTraduccion());
            Map<String, Integer> map = manejo.numeroTraduccionesIdioma(diccionario);
            
            
            
            //manejo.marshalizar(jaxb);
        } catch (JAXBException ex) {
            Logger.getLogger(MainDiccionario.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(MainDiccionario.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

}
