package ad_20191104_jaxb_demo;

import JAXBAlbaran.Direccion;
import JAXBAlbaran.PedidoType;
import java.io.File;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBElement;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import javax.xml.transform.stream.StreamSource;


/**
 *
 * @author Gonzalo
 */
public class AD_20191104_JAXB_DEMO {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        try {
 
            // Crear una instancia de la clase JAXBContext para
            // poder manipular las clases generadas en el paquete jaxb.albaran
            // La clase JAXBContext proporciona al cliente un punto de entrada a la API JAXB
            // Facilita una abstracción para manejar la información generada para
            // implementar las operaciones del JAXB binding framework como unmarshal y marshal
            // unmarshal: consiste en convertir datos XML en un árbol de objetos Java
            // marshal: consiste en convertir un árbol de objetos Java a datos XML
            JAXBContext jaxbContext = JAXBContext.newInstance("JAXBAlbaran");
 
            // Crear un objeto de tipo Unmarshaller para convertir datos XML en un árbol de objetos Java
            Unmarshaller u = jaxbContext.createUnmarshaller();
 
            // La clase JAXBElement representa a un elemento de un documento XML
            // en este caso a un elemento del documento albaran.xml
            JAXBElement jaxbElement =  u.unmarshal(new StreamSource(new File("albaran.xml")), PedidoType.class);
          
 
 
            //   JAXBElement jaxbElement = (JAXBElement) u.unmarshal(
           //         new FileInputStream("albaran.xml"));
 
            // El método getValue() retorna el modelo de contenido (content model) y el valor de los atributos del elemento
            PedidoType pedidoType = (PedidoType) jaxbElement.getValue();
 
            // Obtenemos una instancia de tipo PedidoType para obtener un Objeto de tipo Direccion
            Direccion direccion = pedidoType.getFacturarA();
 
 
            // Crear un objeto de tipo Marshaller para posteriormente convertir un
            // el árbol de objetos Java a datos XML
            Marshaller m = jaxbContext.createMarshaller();
 
            // El método setProperty(String nombrePropiedad, Object value) recibe en este
            // caso la propiedad "jaxb.formatted.output". Esta propiedad controla si al
            // realizar un marshal, debe formatear el resultado XML con saltos de linea
            // e indentaciones para que las personas podamos leerlo cómodamente. Por defecto
            // su valor es falso es decir el XML creado no está formateado
            // El argumento value en este caso tiene que ser concretamente de tipo Boolean
            // para indicar si queremos que el resultado XML esté formateado o no
            m.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);
 
            // El método marshall(Object elementoJAXB, OutputStream os) recibe un objeto
            // de tipo JAXBElement para que su contenido lo muestre en la salida estándar
            // debido a que este método está sobrecargo, si miramos la documentación de
            //la API podemos ver como podemos mostrar o escribir el resultado XML de
            //diferentes maneras
            m.marshal(jaxbElement, System.out);
 
        } catch (JAXBException je) {
            System.out.println(je.getCause());
        }
    }
    
}
