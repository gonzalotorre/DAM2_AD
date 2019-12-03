package Modelo;

import java.io.File;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBElement;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import javax.xml.transform.stream.StreamSource;
import jaxb.diccionario2.AntonimoType;
import jaxb.diccionario2.DiccionarioEspanol;
import jaxb.diccionario2.PalabraType;
import jaxb.diccionario2.SinonimoType;

/**
 * Hacer el clean and build para poder ejecutar los métodos.
 *
 * @author Gonzalo
 */
public class MetodosDiccionario2JAXB {

    /**
     * Método para leer el ficher xml y retornar un elemento de dicho xml. Crear
     * una instancia de la clase JAXBContext para poder manipular las clases
     * generadas en el paquete jaxb.albumesFotos. unmarshal: consiste en
     * convertir datos XML en un árbol de objetos Java
     *
     * @param ficheroXML
     * @return
     * @throws JAXBException
     */
    public static JAXBElement unmarshalizable(File ficheroXML) throws JAXBException {
        //Creamos una instancia de JAXBContext para manipular las clases generadas en jaxb.albumesFotos
        JAXBContext jaxbContext = JAXBContext.newInstance("jaxb.diccionario2");
        //Objeto unmarshall para leer el fichero xml.
        Unmarshaller unmashall = jaxbContext.createUnmarshaller();
        //Creamos un jaxbElement que representa un elemento del XML del documento fotos.xml
        JAXBElement jaxbElement = unmashall.unmarshal(new StreamSource(new File("diccionario2.xml")), DiccionarioEspanol.class);
        return jaxbElement;
    }

    /**
     * marshal: consiste en convertir un árbol de objetos Java a datos XML
     *
     * @param jaxbElement
     * @throws JAXBException
     */
    public static void marshalizar(JAXBElement jaxbElement) throws JAXBException {
        //Creamos una instancia de JAXBContext para manipular las clases generadas en jaxb.albumesFotos
        JAXBContext jaxbContext = JAXBContext.newInstance("jaxb.diccionario2");
        //Crear un objeto de tipo Marshaller para posteriormente convertir un el árbol de objetos Java a datos XML
        Marshaller m = jaxbContext.createMarshaller();
        m.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);
        m.marshal(jaxbElement, System.out);
    }

    /**
     * Método que cuenta el número de sinónimos y antonimos de una determinada
     * palabra que se le pasa por parámetro.
     *
     * @param diccionario para recorrer la lista de palabras y después realizar
     * la comprobación.
     * @param palabra de la cual se quiere saber el número de antónimos y
     * sinónimos que tiene.
     * @return el número de sinónimos y antónimos de dicha palabra.
     */
    public static int contarSinonimosYAntonimos(DiccionarioEspanol diccionario, String palabra) {
        int contador = 0;
        for (PalabraType palabraType : diccionario.getPalabras().getPalabra()) {
            if (palabraType.getGrafia().equalsIgnoreCase(palabra)) {
                contador += palabraType.getSinonimos().getSinonimo().size();
                contador += palabraType.getAntonimos().getAntonimo().size();
            }
        }
        return contador;
    }

    /**
     * Método para borrar una determinada palabra o palabras que en su grafía
     * contienen una determinada cadena de texto.
     *
     * @param diccionario para recorrer la lista de palabras y después realizar
     * eliminar la palabra o palabras en caso de que hubiera más de una.
     * @param grafia con la que comprobaremos que palabras tienen esa grafía y
     * después las borraremos.
     * @return true en caso de que se haya borrado, false en caso contrario.
     */
    public static boolean borrarPalabra(DiccionarioEspanol diccionario, String grafia) {
        boolean borrado = false;
        //Hay que hacerlo con el iterator ya que al intentar borrar un elemento de una lista que se está recorriendo nos lanza la excepcion ConcurrentExceptio
        for (Iterator<PalabraType> it = diccionario.getPalabras().getPalabra().iterator(); it.hasNext();) {
            PalabraType palabraType = it.next();
            if (palabraType.getGrafia().equalsIgnoreCase(grafia)) {
                it.remove();
                borrado = true;
            }
        }
        return borrado;
    }

    /**
     * Método para saber el número de sinónimos con un porcentaje de similitud
     * mayor del 80% que contiene una palabra. Para ello usamos una lista
     * Map<String, Integer>.
     *
     * @param diccionario con la que comprobaremos que sinónimos tienen un
     * porcentaje de similitud mayor al 80% para añadir el total a la lista Map.
     * @return la lista con la palabra y el número de sinónimos que tienen un
     * porcentaje de similitud superior al 80%.
     */
    public static Map<String, Integer> sinonimosSimilitudMayorOchenta(DiccionarioEspanol diccionario) {
        Map<String, Integer> listaSinonimos = new HashMap<>();
        int contador = 0;
        for (PalabraType palabraType : diccionario.getPalabras().getPalabra()) {
            for (SinonimoType sinonimoType : palabraType.getSinonimos().getSinonimo()) {
                if (sinonimoType.getPorcentajeSimilitud() > 0.8) {
                    contador++;
                }
            }
            if (listaSinonimos.containsKey(palabraType.getGrafia())) {
                listaSinonimos.replace(palabraType.getGrafia(), contador);
                contador = 0;
            } else {
                listaSinonimos.put(palabraType.getGrafia(), contador);
                contador = 0;
            }
        }
        return listaSinonimos;
    }

    /**
     * PREGUNTAR Método que cuenta el número de veces que aparece la grafía de
     * una palabra en la lista de sinónimos.
     *
     * @param diccionario con la que comprobaremos el número de veces que
     * aparece a grafía de una palabra en su lista de sinónimos.
     * @param palabra
     * @return el número de veces que aparece la palabra.
     */
    public static int numeroVecesApareceGrafia(DiccionarioEspanol diccionario, String palabra) {
        int contador = 0;
        for (PalabraType palabraType : diccionario.getPalabras().getPalabra()) {
            System.out.println(palabraType.getGrafia());
            for (SinonimoType sinonimoType : palabraType.getSinonimos().getSinonimo()) {
                System.out.println(sinonimoType.getGrafia());
                if (sinonimoType.getGrafia().equalsIgnoreCase(palabra)) {
                    contador++;
                }
            }
        }
        return contador;
    }

}
