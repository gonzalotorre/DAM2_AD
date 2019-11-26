package Modelo;

import java.io.File;
import java.util.HashMap;
import java.util.Map;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBElement;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import javax.xml.transform.stream.StreamSource;
import jaxb.albumesFotos.AlbumesFotos;
import jaxb.albumesFotos.FotoType;

/**
 * Importante hacer clean and build una vez creado el javaBinding.
 *
 * @author Gonzalo
 */
public class MetodosAlbumFotosJAXB {

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
        JAXBContext jaxbContext = JAXBContext.newInstance("jaxb.albumesFotos");
        //Objeto unmarshall para leer el fichero xml.
        Unmarshaller unmashall = jaxbContext.createUnmarshaller();
        //Creamos un jaxbElement que representa un elemento del XML del documento fotos.xml
        JAXBElement jaxbElement = unmashall.unmarshal(new StreamSource(new File("fotos.xml")), AlbumesFotos.class);
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
        JAXBContext jaxbContext = JAXBContext.newInstance("jaxb.albumesFotos");
        //Crear un objeto de tipo Marshaller para posteriormente convertir un el árbol de objetos Java a datos XML
        Marshaller m = jaxbContext.createMarshaller();
        m.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);
        m.marshal(jaxbElement, System.out);
    }

    /**
     * Metodo que cuente el total de fotografías con una determinada ISO.
     *
     * @param album que es el JsonObject del cual obtenemos los JsonArray para
     * las fotos y los albumes.
     * @param iso la cual queremos saber cuantas fotos con essa iso hay de ese
     * tipo.
     * @return el numero de fotos con una determinada iso.
     */
    public static int totalAlbumes(AlbumesFotos album, String iso) {
        int totalFotos = 0;
        for (AlbumesFotos.AlbumFotos albumFoto : album.getAlbumFotos()) {
            for (FotoType fotoType : albumFoto.getFoto()) {
                if (fotoType.getISO().equalsIgnoreCase(iso)) {
                    totalFotos++;
                }
            }
        }
        return totalFotos;
    }

    /**
     * Método para modificar un email y marshalizarlo (escribir).
     *
     * @param album para poder acceder a los emails y a los demás datos del XML.
     * @param nombreAutor para buscar el nombre de ese autor en la lista y
     * modificar su email.
     * @param email que es el nuevo email que vamos a escribir.
     * @param jaxbElement Que contiene los datos XML para poder marshalizaar.
     * @return true en caso de que se haya modificado, false en caso contrario.
     * @throws javax.xml.bind.JAXBException
     */
    public static boolean modificarEmail(AlbumesFotos album, String nombreAutor, String email, JAXBElement jaxbElement) throws JAXBException {
        boolean modificado = false;
        for (AlbumesFotos.AlbumFotos albumFoto : album.getAlbumFotos()) {
            if (albumFoto.getAutor().getNombre().equals(nombreAutor)) {
                albumFoto.getAutor().setEmail(email);
                modificado = true;
            }
        }
        //--------------------------Falta marshalizar-------------------------//
        marshalizar(jaxbElement);
        return modificado;
    }

    /**
     * Método que introduce en un Map<String, Integer> el número de fotos que
     * hay por cada título de album.
     *
     * @param album para acceder a la lista de albumes y poder añadir los datos
     * al Map.
     * @return una lista Map con el título de cada album y el número de fotos
     * que tiene.
     */
    public static Map<String, Integer> totalFotos(AlbumesFotos album) {
        Map<String, Integer> totalFotos = new HashMap<>();
        for (AlbumesFotos.AlbumFotos albumFoto : album.getAlbumFotos()) {
            if (totalFotos.containsKey(albumFoto.getTitulo())) {
                totalFotos.replace(albumFoto.getTitulo(), albumFoto.getFoto().size());
            } else {
                totalFotos.put(albumFoto.getTitulo(), albumFoto.getFoto().size());
            }
        }
        return totalFotos;
    }

}
