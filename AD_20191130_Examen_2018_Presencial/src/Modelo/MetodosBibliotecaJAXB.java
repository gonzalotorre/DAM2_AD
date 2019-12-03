package Modelo;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import javax.json.Json;
import javax.json.JsonArray;
import javax.json.JsonArrayBuilder;
import javax.json.JsonObject;
import javax.json.JsonWriter;
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
     * @param jsonObject
     * @param rutaFichero
     * @throws IOException
     */
    public static void grabarFicheroJSON(JsonObject jsonObject, String rutaFichero) throws IOException {
        FileWriter fileWriter = new FileWriter(rutaFichero);
        JsonWriter jsonWriter = Json.createWriter(fileWriter);
        jsonWriter.writeObject(jsonObject);
        fileWriter.flush();
        fileWriter.close();
    }

    /**
     *
     * @param biblioteca
     * @param isbn
     * @param titulo
     * @param codigoSocio
     * @param nombreSocio
     * @param apellidoSocio
     * @param fechaDevolucion
     * @return
     */
    public static PrestamoType crearPrestamo(Biblioteca biblioteca, String isbn, String titulo, String codigoSocio, String nombreSocio, String apellidoSocio, XMLGregorianCalendar fechaDevolucion) {
        PrestamoType prestamo = new PrestamoType();
        prestamo.setISBN(isbn);
        prestamo.setTitulo(titulo);
        prestamo.setCodigoSocio(codigoSocio);
        prestamo.setNombreSocio(nombreSocio);
        prestamo.setApellidoSocio(apellidoSocio);
        prestamo.setFechaDevolucion(fechaDevolucion);
        return prestamo;
    }

    /**
     *
     * @param biblioteca
     * @param prestamo
     * @return
     */
    public static boolean insertarPrestamo(Biblioteca biblioteca, PrestamoType prestamo) {
        biblioteca.getPrestamos().getPrestamo().add(prestamo);
        for (SocioType socioType : biblioteca.getSocios().getSocio()) {
            socioType.getLibrosPrestados().getPrestamo().add(prestamo);
        }
        return true;
    }

    /**
     *
     * @param biblioteca
     * @param codigoSocio
     * @return
     */
    public static boolean darBajaSocio(Biblioteca biblioteca, String codigoSocio) {
        boolean borrado = false;
        for (Iterator<SocioType> it = biblioteca.getSocios().getSocio().iterator(); it.hasNext();) {
            SocioType socioType = it.next();
            if (socioType.getCodigoSocio().equalsIgnoreCase(codigoSocio)) {
                if (socioType.getLibrosPrestados().getPrestamo().isEmpty()) {
                    it.remove();
                    borrado = true;
                }
            }
        }
        return borrado;
    }

    /**
     *
     * @param biblioteca
     * @return
     */
    public static Map<String, List<String>> listaLibrosPorSocio(Biblioteca biblioteca) {
        Map<String, List<String>> lista = new HashMap();
        for (SocioType socioType : biblioteca.getSocios().getSocio()) {
            List<String> listaTitulos = new ArrayList<>();
            for (PrestamoType prestamoType : socioType.getLibrosPrestados().getPrestamo()) {
                listaTitulos.add(prestamoType.getTitulo());
            }
            String codigo = socioType.getCodigoSocio();
            lista.put(codigo, listaTitulos);
        }
        return lista;
    }

    /**
     *
     * @param biblioteca
     * @param fecha
     * @param codigoSocio
     * @return
     */
    public static List<String> listaLibrosRetrasados(Biblioteca biblioteca, GregorianCalendar fecha, String codigoSocio) {
        List<String> listaISBN = new ArrayList<>();
        for (SocioType socioType : biblioteca.getSocios().getSocio()) {
            if (socioType.getCodigoSocio().equalsIgnoreCase(codigoSocio)) {
                for (PrestamoType prestamoType : socioType.getLibrosPrestados().getPrestamo()) {
                    XMLGregorianCalendar fechaDevolucion = prestamoType.getFechaDevolucion();
                    GregorianCalendar fechaDevolucionGregoria = fechaDevolucion.toGregorianCalendar();
                    if (fechaDevolucionGregoria.before(fecha)) {
                        listaISBN.add(prestamoType.getISBN());
                    }
                }
            }
        }
        return listaISBN;
    }

    /**
     *
     * @param biblioteca
     * @return
     */
    public static JsonObject generarJSON(Biblioteca biblioteca) {
        JsonArrayBuilder listaDatosSocios = Json.createArrayBuilder();
        for (SocioType socioType : biblioteca.getSocios().getSocio()) {
            String nombre = socioType.getNombreSocio();
            JsonArrayBuilder listaApellidos = Json.createArrayBuilder();
            for (String apellido : socioType.getApellidoSocio()) {
                listaApellidos.add(apellido);
            }
            JsonArray apellidos = listaApellidos.build();
            BigInteger telefono = socioType.getTelefono();

            JsonObject datosSocio = Json.createObjectBuilder()
                    .add("nombreSocio", nombre)
                    .add("apellidos", apellidos)
                    .add("telefono", telefono).build();
            listaDatosSocios.add(datosSocio);
        }
        JsonArray listaDatosSociosArray = listaDatosSocios.build();
        JsonObject socio = Json.createObjectBuilder()
                .add("Socios", Json.createObjectBuilder()
                        .add("Socio", listaDatosSociosArray))
                .build();
        return socio;
    }

}
