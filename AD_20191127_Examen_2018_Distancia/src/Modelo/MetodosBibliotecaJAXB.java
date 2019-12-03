package Modelo;

import java.io.File;
import java.math.BigInteger;
import java.util.Iterator;
import java.util.List;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBElement;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import javax.xml.datatype.XMLGregorianCalendar;
import javax.xml.transform.stream.StreamSource;
import jaxb.biblioteca.Biblioteca;
import jaxb.biblioteca.PrestamoType;
import jaxb.biblioteca.SocioType;

/**
 *
 * @author Gonzalo
 */
public class MetodosBibliotecaJAXB {

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
        JAXBContext jaxbContext = JAXBContext.newInstance("jaxb.biblioteca");
        //Objeto unmarshall para leer el fichero xml.
        Unmarshaller unmashall = jaxbContext.createUnmarshaller();
        //Creamos un jaxbElement que representa un elemento del XML del documento fotos.xml
        JAXBElement jaxbElement = unmashall.unmarshal(new StreamSource(new File("BIBLIOTECA.xml")), Biblioteca.class);
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
        JAXBContext jaxbContext = JAXBContext.newInstance("jaxb.biblioteca");
        //Crear un objeto de tipo Marshaller para posteriormente convertir un el árbol de objetos Java a datos XML
        Marshaller m = jaxbContext.createMarshaller();
        m.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);
        m.marshal(jaxbElement, System.out);
    }

    /**
     *
     * @param biblioteca
     * @param codigo
     * @param nombre
     * @param apellidos
     * @param telefono
     * @param prestamo
     * @return
     */
    public static boolean crearSocio(Biblioteca biblioteca, String codigo, String nombre, List<String> apellidos, BigInteger telefono, SocioType.LibrosPrestados prestamo) {
        boolean creado = true;
        SocioType socio = new SocioType();
        socio.setCodigoSocio(codigo);
        socio.setNombreSocio(nombre);
        socio.getApellidoSocio().addAll(apellidos);
        socio.setTelefono(telefono);
        socio.setLibrosPrestados(prestamo);
        biblioteca.getSocios().getSocio().add(socio);
        return creado;
    }

    /**
     *
     * @param biblioteca
     * @param codigo
     * @return
     */
    public static SocioType buscarSocio(Biblioteca biblioteca, String codigo) {
        SocioType socio = null;
        for (SocioType socioType : biblioteca.getSocios().getSocio()) {
            if (socioType.getCodigoSocio().equalsIgnoreCase(codigo)) {
                socio = socioType;
            }
        }
        return socio;
    }

    /**
     *
     * @param biblioteca
     * @param nombre
     * @return
     */
    public static boolean borrarPrestamos(Biblioteca biblioteca, String nombre) {
        boolean borradoListaSocios = false;
        boolean borradoListaPrestamos = false;
        boolean borrado = false;
        for (SocioType socioType : biblioteca.getSocios().getSocio()) {
            if (socioType.getNombreSocio().equalsIgnoreCase(nombre)) {
                for (Iterator<PrestamoType> it = socioType.getLibrosPrestados().getPrestamo().iterator(); it.hasNext();) {
                    PrestamoType prestamoType = it.next();
                    it.remove();
                }
                borradoListaSocios = true;
            }
        }
        for (Iterator<PrestamoType> it = biblioteca.getPrestamos().getPrestamo().iterator(); it.hasNext();) {
            PrestamoType prestamoType = it.next();
            if (prestamoType.getNombreSocio().equalsIgnoreCase(nombre)) {
                it.remove();
                borradoListaPrestamos = true;
            }
        }
        if(borradoListaPrestamos && borradoListaSocios){
            borrado = true;
        }
        return borrado;
    }

    /**
     *
     * @param biblioteca
     * @param codigo
     * @return
     */
    public static boolean renovarFechaSocio(Biblioteca biblioteca, String codigo) {
        boolean renovada = false;
        for (PrestamoType prestamoType : biblioteca.getPrestamos().getPrestamo()) {
            if (prestamoType.getCodigoSocio().equalsIgnoreCase(codigo)) {
                //Obtenemos la fecha que es un XMLGregorianCalendar guardado como yyyy-MM-dd
                XMLGregorianCalendar fecha = prestamoType.getFechaDevolucion();
                fecha.setDay(fecha.getDay() + 14);
                renovada = true;
            }
        }
        
        return renovada;
    }
}
