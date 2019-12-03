package Controlador;

import Modelo.MetodosDiccionario;
import java.io.File;
import java.io.IOException;
import java.util.Collection;
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
            //Creamos una fabrica de objetos para obtener el diccionario
            ObjectFactory fabrica = new ObjectFactory();
            //Creamos un DiccionarioEspañol con los datos obtenidos de la fabrica
            DiccionarioEspanol diccionario = fabrica.createDiccionarioEspanol();
            //Coge los valores que hay en el jaxb element y los mete al objeto diccionario, crea un diccionario
            diccionario = (DiccionarioEspanol) jaxb.getValue();

            PalabraType palabra = diccionario.getPalabra().get(0);
            
            int numeroDefiniciones = manejo.numeroDefiniciones(diccionario, palabra);
            System.out.println("Numero definiciones: " + numeroDefiniciones);
            
            manejo.borrarTraducciones(diccionario, palabra.getTraducciones().getTraduccion().get(0).getIdiomaTraduccion());
            Map map = manejo.numeroTraduccionesIdioma(diccionario);
            
            Map m = manejo.sinonimosDefiniciones(diccionario, palabra);
            System.out.println(m);
            
            Collection c = m.values();
            Object[] o = c.toArray();
            System.out.println(o[0].toString()); //Muestra el sinónimo 1
            System.out.println(o[1].toString()); //Muestra el sinónimo 2
            System.out.println(o[2].toString()); //Muestra el sinónimo 3
            
            //manejo.marshalizar(jaxb);
        } catch (JAXBException ex) {
            Logger.getLogger(MainDiccionario.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(MainDiccionario.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

}
