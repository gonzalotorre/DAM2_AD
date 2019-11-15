package Controlador;

import Modelo.ManejoAlbaran;
import java.io.File;
import java.math.BigDecimal;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.xml.bind.JAXBElement;
import javax.xml.bind.JAXBException;
import javax.xml.datatype.XMLGregorianCalendar;
import jaxb.albaran.ObjectFactory;
import jaxb.albaran.PedidoType;

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
            ManejoAlbaran manejo = new ManejoAlbaran();
            File archivoXML = new File("albara.xml");
            JAXBElement jaxb = manejo.unmarshalizable(archivoXML);
            manejo.marshalizar(jaxb);
            //Creamos una fabrica de objetos para obtener el pedido y luego poder añadir un artículo
            ObjectFactory fabrica = new ObjectFactory();
            //Creamos un pedidoType con los datos obtenidos de la fabrica
            PedidoType pedido = fabrica.createPedidoType();
            //Coge los valores que hay en el jaxb element y los mete al pedido
            pedido = (PedidoType) jaxb.getValue();
            //Añadimos un nuevo artículo
            XMLGregorianCalendar fechaEnvio;
            manejo.anadirArticulo(pedido, "Mesa", 5, BigDecimal.valueOf(10.5), "Mesa negra", null, "111C");
            manejo.marshalizar(jaxb);
        } catch (JAXBException ex) {
            Logger.getLogger(MainPruebas.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

}
