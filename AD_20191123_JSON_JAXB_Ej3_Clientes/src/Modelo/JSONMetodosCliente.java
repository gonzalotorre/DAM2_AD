package Modelo;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.json.Json;
import javax.json.JsonArray;
import javax.json.JsonObject;
import javax.json.JsonReader;
import javax.json.JsonWriter;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import jaxb.clientes.Clientes;
import jaxb.clientes.ObjectFactory;
import jaxb.clientes.TipoDireccion;

/**
 *
 * @author Gonzalo
 */
public class JSONMetodosCliente {

    //-------------------------------APARTADO 1-------------------------------//
    /**
     *
     * @return @throws IOException
     */
    public static JsonArray crearFicheroDireccion() throws IOException {
        JsonObject direccion1 = Json.createObjectBuilder()
                .add("direccion", Json.createArrayBuilder()
                        .add(Json.createObjectBuilder()
                                .add("calle", "calle1")
                                .add("numero", "numero1")
                                .add("piso", 1)
                                .add("escalera", "a")
                                .add("cp", 33930)
                                .add("ciudad", "Asturias")
                        )
                ).build();

        JsonObject direccion2 = Json.createObjectBuilder()
                .add("direccion", Json.createArrayBuilder()
                        .add(Json.createObjectBuilder()
                                .add("calle", "calle2")
                                .add("numero", "numero2")
                                .add("piso", 2)
                                .add("escalera", "b")
                                .add("cp", 33935)
                                .add("ciudad", "Asturias")
                        )
                ).build();
        JsonArray jsonArrayDirecciones = Json.createArrayBuilder()
                .add(direccion1)
                .add(direccion2).build();
        return jsonArrayDirecciones;
    }

    /**
     *
     * @param direcciones
     * @return
     * @throws IOException
     */
    public static JsonArray crearFicheroCliente(JsonArray direcciones) throws IOException {
        JsonObject cliente1 = Json.createObjectBuilder()
                .add("cliente", Json.createArrayBuilder()
                        .add(Json.createObjectBuilder()
                                .add("apellido1", "garcia")
                                .add("apellido2", "torre")
                                .add("direcciones", direcciones)
                                .add("telefono", "648876541")
                                .add("nombre", "")
                        )
                ).build();
        JsonObject cliente2 = Json.createObjectBuilder()
                .add("cliente", Json.createArrayBuilder()
                        .add(Json.createObjectBuilder()
                                .add("apellido1", "Torre")
                                .add("apellido2", "Fernández")
                                .add("direcciones", direcciones)
                                .add("telefono", "648876541")
                                .add("nombre", "")
                        )
                ).build();
        JsonArray jsonArrayClientes = Json.createArrayBuilder()
                .add(cliente1)
                .add(cliente2).build();
        return jsonArrayClientes;
    }

    /**
     *
     * @param clientes
     * @return
     * @throws IOException
     */
    public static JsonArray crearFicheroClientes(JsonArray clientes) throws IOException {
        JsonObject cliente = Json.createObjectBuilder()
                .add("clientes", clientes)
                .build();
        JsonArray jsonArrayClientes = Json.createArrayBuilder().add(cliente).build();
        return jsonArrayClientes;
    }

    //-------------------------------APARTADO 2-------------------------------//
    /**
     *
     * @param clientes
     * @return
     * @throws IOException
     */
    public static JsonArray crarJSONCompleto(JsonArray clientes) throws IOException {
        JsonArray jsonArrayClientes = clientes;
        return jsonArrayClientes;
    }

    /**
     *
     * @param rutaFichero
     * @param clientes
     * @return
     * @throws IOException
     */
    public static JsonArray escibirFicheroJSON(String rutaFichero, JsonArray clientes) throws IOException {
        JsonArray jsonArrayClientes = crarJSONCompleto(clientes);
        FileWriter fileWriter = new FileWriter(rutaFichero);
        JsonWriter jsonWriter = Json.createWriter(fileWriter);
        jsonWriter.writeArray(jsonArrayClientes);
        fileWriter.flush();
        fileWriter.close();
        return jsonArrayClientes;
    }

    //-------------------------------APARTADO 3-------------------------------//
    public static List<TipoDireccion> crearDireccionXML(JsonArray direcciones) {
        List<TipoDireccion> listaDirecciones = new ArrayList<>();
        for (int i = 0; i < direcciones.size(); i++) {
            JsonObject direccion = direcciones.getJsonObject(i);
            String calle = direccion.getString("calle");
            String numero = direccion.getString("numero");
            int piso = direccion.getInt("piso");
            String escalera = direccion.getString("escalera");
            int cp = direccion.getInt("cp");
            String ciudad = direccion.getString("ciudad");
            TipoDireccion direccionJAXB = new ObjectFactory().createTipoDireccion(calle, numero, piso, escalera, cp, ciudad);
            listaDirecciones.add(direccionJAXB);
        }
        return listaDirecciones;
    }

    public static List<Clientes.Cliente> crearClientes(JsonArray clientes) {
        List<Clientes.Cliente> listaClientes = new ArrayList<>();
        for (int i = 0; i < clientes.size(); i++) {
            JsonObject cliente = clientes.getJsonObject(i);
            String apellido = cliente.getString("apellido1");
            String apellido2 = cliente.getString("apellido2");
            List<String> apellidos = new ArrayList<>();
            apellidos.add(apellido);
            apellidos.add(apellido2);
            JsonArray arrayDireccionesJSON = cliente.getJsonArray("direcciones");
            List<TipoDireccion> direcciones = crearDireccionXML(arrayDireccionesJSON);
            String telefono = cliente.getString("telefono");
            //Esto va a cascar porque en la clase CLientes.Cliente.Nombre no hay ningún atributo para
            //guardar el nombre, con lo cual no se puede hacer.
            String nombre = cliente.getString("nombre");
            Clientes.Cliente clienteJAXB = new ObjectFactory().createClientesCliente(apellidos, direcciones, telefono, nombre);
            listaClientes.add(clienteJAXB);
        }
        return listaClientes;
    }

    public static File crearXML(File ficheroJSON) throws FileNotFoundException {
        JsonArray clientes = leerFicheroJSON(ficheroJSON);
        List<Clientes.Cliente> ListaCliente = crearClientes(clientes);
        Clientes clientesJAXB = new ObjectFactory().createClientes(ListaCliente);
        File ficheroXML = marshalizar(clientesJAXB, "jaxb.clientes");
        return ficheroXML;
    }

    //----------------------------------Otros---------------------------------//
    public static JsonArray leerFicheroJSON(File fichero) throws FileNotFoundException {
        FileReader leerFichero = new FileReader(fichero);
        JsonReader jsonReader = Json.createReader(leerFichero);
        JsonArray jsonArray = jsonReader.readArray();
        return jsonArray;
    }

    public static File marshalizar(Clientes clientesJAXB, String paquete){
        File ficheroXML = new File("ficheroPruebaXML.xml");
        try {
            JAXBContext jaxbContext = JAXBContext.newInstance(paquete);
            Marshaller m = jaxbContext.createMarshaller();
            m.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);
            m.marshal(clientesJAXB, System.out);
        } catch (JAXBException ex) {
            Logger.getLogger(JSONMetodosCliente.class.getName()).log(Level.SEVERE, null, ex);
        }
        return ficheroXML;
    }
    
}
