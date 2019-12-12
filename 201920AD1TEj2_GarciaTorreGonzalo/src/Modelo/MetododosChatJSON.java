package Modelo;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.GregorianCalendar;
import java.util.List;
import javax.json.Json;
import javax.json.JsonArray;
import javax.json.JsonObject;
import javax.json.JsonReader;
import javax.json.JsonWriter;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.datatype.DatatypeConfigurationException;
import javax.xml.datatype.DatatypeFactory;
import javax.xml.datatype.XMLGregorianCalendar;
import jaxb.chat.MensajeType;
import jaxb.chat.ObjectFactory;
import jaxb.chat.QuickChat;

/**
 *
 * @author Gonzalo
 */
public class MetododosChatJSON {

    /**
     * Método para leer un fichero JSON, en este caso devuelve un JsonObject,
     * pero puede devolver un JsonArray.
     *
     * @param rutaFichero Ruta donde se encuentra el fichero Json
     * @return el objeto Json con los datos del fichero leido.
     * @throws FileNotFoundException
     */
    public static JsonObject leerFichero(String rutaFichero) throws FileNotFoundException {
        FileReader leerFichero = new FileReader(rutaFichero);
        JsonReader jsonReader = Json.createReader(leerFichero);
        JsonObject jsonObject = jsonReader.readObject();
        return jsonObject;
    }

    /**
     *
     * @param rutaFichero
     * @param nombre
     * @param numeroTel
     * @param mensajeID
     * @param nombreUsu
     * @param texto
     * @param fecha
     * @throws IOException
     */
    public static void crearFichero(String rutaFichero, String nombre, String numeroTel, String mensajeID, String nombreUsu, String texto, String fecha) throws IOException {
        JsonObject ususario = Json.createObjectBuilder()
                .add("usuario", Json.createObjectBuilder()
                        .add("nombre", nombre)
                        .add("numeroTelefono", numeroTel)
                        .add("mensajeEnviados", Json.createObjectBuilder()
                                .add("mensaje", Json.createArrayBuilder()
                                        .add(Json.createObjectBuilder()
                                                .add("mensajeID", mensajeID)
                                                .add("nombreUsuario", nombreUsu)
                                                .add("textoMensaje", texto)
                                                .add("fechaPublicacion", fecha))
                                )))
                .build();
        FileWriter escribirFichero = new FileWriter(rutaFichero);
        JsonWriter jsonWriter = Json.createWriter(escribirFichero);
        jsonWriter.writeObject(ususario);
        escribirFichero.flush();
        escribirFichero.close();
    }

    /**
     * 
     * @param general
     * @return
     * @throws ParseException 
     */
    public static List<QuickChat.Usuarios.Usuario.MensajeEnviados> createQuickChatUsuariosUsuarioMensajeEnviados(JsonObject general) throws ParseException {
        JsonArray usuarios = general.getJsonObject("quickChat").getJsonObject("usuarios").getJsonArray("usuario");
        List<QuickChat.Usuarios.Usuario.MensajeEnviados> mensajesEnviados = new ArrayList<>();
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        for (int i = 0; i < usuarios.size(); i++) {
            JsonArray mensajes = usuarios.getJsonObject(i).getJsonObject("mensajeEnviados").getJsonArray("mensaje");
            List<MensajeType> listaMensajes = new ArrayList<>();
            for (int j = 0; j < mensajes.size(); j++) {
                MensajeType mensaje = new ObjectFactory().createMensajeType();
                //------------------------------------------------------------//
                Object mensajeID = mensajes.getJsonObject(j).getString("mensajeID");
                mensaje.setMensajeID(mensajeID);
                //------------------------------------------------------------//
                String nombre = mensajes.getJsonObject(j).getString("nombreUsuario");
                mensaje.setNombreUsuario(nombre);
                //------------------------------------------------------------//
                String texto = mensajes.getJsonObject(j).getString("textoMensaje");
                mensaje.setTextoMensaje(texto);
                //------------------------------------------------------------//
                String fecha = mensajes.getJsonObject(j).getString("fechaPublicacion");
                GregorianCalendar fechaGC = new GregorianCalendar();
                fechaGC.setTime(format.parse(fecha));
                XMLGregorianCalendar fechaXML = null;
                try {
                    fechaXML = DatatypeFactory.newInstance().newXMLGregorianCalendar(fechaGC);
                } catch (DatatypeConfigurationException ex) {
                }
                mensaje.setFechaPublicacion(fechaXML);
                //------------------------------------------------------------//
                listaMensajes.add(mensaje);
            }
            QuickChat.Usuarios.Usuario.MensajeEnviados mensaje = new ObjectFactory().createQuickChatUsuariosUsuarioMensajeEnviados();
            mensaje.getMensaje().addAll(listaMensajes);
        }
        return mensajesEnviados;
    }

    /**
     * 
     * @param general
     * @return
     * @throws ParseException 
     */
    public static List<QuickChat.Usuarios.Usuario> createQuickChatUsuariosUsuario(JsonObject general) throws ParseException {
        JsonArray usuarios = general.getJsonObject("quickChat").getJsonObject("usuarios").getJsonArray("usuario");
        List<QuickChat.Usuarios.Usuario> listaUsuarios = new ArrayList<>();
        for (int i = 0; i < usuarios.size(); i++) {
            QuickChat.Usuarios.Usuario usuario = new ObjectFactory().createQuickChatUsuariosUsuario();
            String nombre = usuarios.getJsonObject(i).getString("nombre");
            usuario.setNombre(nombre);
            String numeroTelefono = usuarios.getJsonObject(i).getString("numeroTelefono");
            usuario.setNumeroTelefono(numeroTelefono);
            List<QuickChat.Usuarios.Usuario.MensajeEnviados> listaMensajes = createQuickChatUsuariosUsuarioMensajeEnviados(general);
            for (QuickChat.Usuarios.Usuario.MensajeEnviados listaMensaje : listaMensajes) {
                usuario.setMensajeEnviados(listaMensaje);
            }
            listaUsuarios.add(usuario);
        }
        return listaUsuarios;
    }

    /**
     * 
     * @param general
     * @return
     * @throws ParseException 
     */
    public static QuickChat.Usuarios createQuickChatUsuarios(JsonObject general) throws ParseException {
        List<QuickChat.Usuarios.Usuario> listaUsuarios = createQuickChatUsuariosUsuario(general);
        QuickChat.Usuarios usuario = new ObjectFactory().createQuickChatUsuarios();
        for (QuickChat.Usuarios.Usuario listaUsuario : listaUsuarios) {
            usuario.getUsuario().add(listaUsuario);
        }
        return usuario;
    }
    
    /**
     * 
     * @param general
     * @return
     * @throws ParseException 
     */
    public static QuickChat createQuickChat(JsonObject general) throws ParseException{
        QuickChat chat = new ObjectFactory().createQuickChat();
        QuickChat.Usuarios usuario = createQuickChatUsuarios(general);
        chat.setUsuarios(usuario);
        return chat;
    }
    
    
    
    /**
     * 
     * @param chat
     * @param paquete
     * @return 
     */
    public static File marshalizar(QuickChat chat, String paquete) {
        File ficheroXML = new File("ficheroPruebaXML.xml");
        try {
            JAXBContext jaxbContext = JAXBContext.newInstance(paquete);
            Marshaller m = jaxbContext.createMarshaller();
            m.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);
            m.marshal(chat, System.out);
        } catch (JAXBException ex) {
        }
        return ficheroXML;
    }
    
    /**
     * 
     * @param jsonObject
     * @return
     * @throws FileNotFoundException
     * @throws ParseException 
     */
    public static File crearXML(JsonObject jsonObject) throws FileNotFoundException, ParseException {
        QuickChat chat = createQuickChat(jsonObject);
        File ficheroXML = marshalizar(chat, "jaxb.chat");
        return ficheroXML;
    }
    
}
