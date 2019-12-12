package Modelo;

import java.io.File;
import java.util.Comparator;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBElement;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import javax.xml.datatype.XMLGregorianCalendar;
import javax.xml.transform.stream.StreamSource;
import jax.chat.MensajeType;
import jax.chat.ObjectFactory;
import jax.chat.QuickChat;
import jax.chat.QuickChat.Chats;
import jax.chat.QuickChat.Chats.Chat;
import jax.chat.QuickChat.Chats.Chat.MensajesArchivados;

/**
 *
 * @author Gonzalo
 */
public class MetodosChatJAXB implements Comparator<MensajeType> {

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
        JAXBContext jaxbContext = JAXBContext.newInstance("jax.chat");
        //Objeto unmarshall para leer el fichero xml.
        Unmarshaller unmashall = jaxbContext.createUnmarshaller();
        //Creamos un jaxbElement que representa un elemento del XML del documento fotos.xml
        JAXBElement jaxbElement = unmashall.unmarshal(new StreamSource(new File("charusuarios.xml")), QuickChat.class);
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
        JAXBContext jaxbContext = JAXBContext.newInstance("jax.chat");
        //Crear un objeto de tipo Marshaller para posteriormente convertir un el árbol de objetos Java a datos XML
        Marshaller m = jaxbContext.createMarshaller();
        m.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);
        m.marshal(jaxbElement, System.out);
    }

    /**
     * Método para añadir un mensaje a las lista de mensajes enviados y como
     * último mensaje dentro de un chat, y se añade también en la lista de
     * mensajes emnviados del usuario.
     *
     * @param quickChat Que sería el nodo padre para acceder a las listas
     * correspondientes.
     * @param mensajeType el mensaje que se quiere insertar.
     * @param uniqueID id para comprobar el chat donde se quiere insertar el
     * mensaje.
     * @param nombreUsuario Nombre del usuario para insertar el mensaje.
     * @return true si se ha añadido el mensaje en las distintas ramas
     */
    public static boolean insertarChat(QuickChat quickChat, MensajeType mensajeType, String uniqueID, String nombreUsuario) {
        boolean anadido = false;
        //Se añade el mensaje a la lista de mensajes enviados del usuario.
        //Si hubiera una lista de ususarios, se recorrería igual que la de chats y se haría esta comprobación
        for (QuickChat.Usuarios.Usuario usuario : quickChat.getUsuarios().getUsuario()) {
            if (usuario.getNombre().equalsIgnoreCase(nombreUsuario)) {
                usuario.getMensajeEnviados().getMensaje().add(mensajeType);
                anadido = true;
            }
        }

        //Recorremos la lista de chats para añadir el mensaje en ultimo mensaje y en la lista de mensajes.
        for (QuickChat.Chats.Chat chat : quickChat.getChats().getChat()) {
            if (chat.getUniqueID().equalsIgnoreCase(uniqueID)) {
                //Se crea el último mensaje enviado.
                chat.setUltimoMensaje(mensajeType);
                //Se añade el mensaje a la lista de mensajes enviados.
                chat.getMensajes().getMensaje().add(mensajeType);
                anadido = true;
            }
        }
        return anadido;
    }

    /**
     * Método que recorre la lista de chats y busca uno en concreto, despúes se
     * recorre la lista de los mensajes y si el año de los mensjaes coincide con
     * el que se pasa por parámetro lo archiva, sino continúa la ejecución del
     * mensaje.
     *
     * @param quickChat El nodo padre para acceder a la lista de chats.
     * @param anio el año para poder archivar los mensajes.
     * @param uniqueID para comprobar el chat que buscamos.
     * @return el número de mensajes archivados.
     */
    public static int archivarMensajes(QuickChat quickChat, int anio, String uniqueID) {
        int contadorMensajes = 0;
        for (QuickChat.Chats.Chat chat : quickChat.getChats().getChat()) {
            if (chat.getUniqueID().equalsIgnoreCase(uniqueID)) {
                for (MensajeType mensajeType : chat.getMensajes().getMensaje()) {
                    //Obtengo la fecha de publicación del año, si es igual al que se le pasa por parámetros lo archiva sino salta al siguiente mensaje.
                    XMLGregorianCalendar fechaPublicacion = mensajeType.getFechaPublicacion();
                    //Compuebo si el año coincide.
                    if (fechaPublicacion.getYear() == anio) {
                        chat.getMensajesArchivados().getMensaje().add(mensajeType);
                        contadorMensajes++;
                    }
                }
            }
        }
        return contadorMensajes;
    }

    /**
     * Método que recorre la lista de chats para obtener el título y la lista de
     * mensajes del chat y guardarlos en una colección Map<String,
     * List<MensajeType>> donde el String es el título del chat y la lista, es
     * la lista con todos los mensajes del chat.
     *
     * @param quickChat El nodo padre para acceder a la lista de chats.
     * @return una coleccion con una lista de mensajes para cada chat.
     */
    public static Map<String, List<MensajeType>> listaMesajesTitulo(QuickChat quickChat) {
        Map<String, List<MensajeType>> listaMesajesPorTitulo = new HashMap<>();
        for (QuickChat.Chats.Chat chat : quickChat.getChats().getChat()) {
            //Se crea una lista de mensajes para cada chat, se obtiene la lista de mensajes y se añaden a la lista de MensajeType.
            List<MensajeType> listaMensajes = chat.getMensajes().getMensaje();
            String titulo = chat.getTitulo();
            listaMesajesPorTitulo.put(titulo, listaMensajes);
        }
        return listaMesajesPorTitulo;
    }

    /**
     * Método en el que se obtiene la lista de los mensajes archivados y los
     * guarda en el atributo lista de la clase MensajesArchivados
     *
     * @param quickChat Nodo padre para acceder a los mensajes archivados.
     * @return el objeto MensajesArchivados con la lista de mensajes.
     */
    public static MensajesArchivados createQuickChatChatsChatMensajesArchivados(QuickChat quickChat) {
        MensajesArchivados mensajesArchivado = new ObjectFactory().createQuickChatChatsChatMensajesArchivados();
        for (QuickChat.Chats.Chat chat : quickChat.getChats().getChat()) {
            List<MensajeType> listaMensajesArchivados = chat.getMensajesArchivados().getMensaje();
            mensajesArchivado.getMensaje().addAll(listaMensajesArchivados);
        }
        return mensajesArchivado;
    }

    /**
     * Método que devuelve el objecto chat que solo contiene como atributo los
     * mensajesArchivados
     *
     * @param quickChat Nodo padre para acceder a los mensajes archivados.
     * @return un objeto chat.
     */
    public static Chat createQuickChatChatsChat(QuickChat quickChat) {
        Chat chat = new ObjectFactory().createQuickChatChatsChat();
        MensajesArchivados mensajesArchivado = createQuickChatChatsChatMensajesArchivados(quickChat);
        chat.setMensajesArchivados(mensajesArchivado);
        return chat;
    }

    /**
     * Método que devuelve el objeto chats hijo del nodo quickChat.
     *
     * @param quickChat Nodo padre para acceder a los mensajes archivados.
     * @return el objeto Chats que contiene la lista de chats
     */
    public static Chats createQuickChatChats(QuickChat quickChat) {
        Chats chats = new ObjectFactory().createQuickChatChats();
        Chat chat = createQuickChatChatsChat(quickChat);
        chats.getChat().add(chat);
        return chats;
    }

    /**
     * Método que crea el objeto padre QuickChat
     *
     * @param quickChat Nodo padre para acceder a los mensajes archivados.
     * @return el objeto padre con los chats y mensajes archivados
     */
    public static QuickChat createQuickChat(QuickChat quickChat) {
        QuickChat quickchat = new ObjectFactory().createQuickChat();
        Chats chats = createQuickChatChats(quickChat);
        quickchat.setChats(chats);
        return quickchat;
    }

    /**
     * Método que marshaliza el documento xml con los uevos datos
     *
     * @param quickChat Nodo padre para acceder a los mensajes archivados.
     * @param paquete el paquete que contiene las clases del binding
     * @return
     */
    public static File marshalizarNuevoFichero(QuickChat quickChat, String paquete) {
        File ficheroXML = new File("NuevoFichero.xml");
        try {
            JAXBContext jaxbContext = JAXBContext.newInstance(paquete);
            Marshaller m = jaxbContext.createMarshaller();
            m.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);
            m.marshal(quickChat, System.out);
        } catch (JAXBException ex) {
        }
        return ficheroXML;
    }

    /**
     * Método para crear un fichero XML que contenga un backUp con los mensajes
     * archivados. Para ello creo los distintos objetcos que tiene como
     * atributos el elemento QuickChat en distintos métodos.
     *
     * @param quickChat Nodo padre para acceder a los mensajes archivados.
     * @return el fichero con el XML.
     */
    public static File crearBackUp(QuickChat quickChat) {
        QuickChat general = createQuickChat(quickChat);
        File ficheroXML = marshalizarNuevoFichero(general, "jax.chat");
        for (Chat chat : quickChat.getChats().getChat()) {
            for (Iterator<MensajeType> it = chat.getMensajesArchivados().getMensaje().iterator(); it.hasNext();) {
                MensajeType mensajeType = it.next();
                it.remove();
            }
        }
        return ficheroXML;
    }

    /**
     * Método para comprobar si los mensajes de los usuarios coinciden con los
     * de los chats.
     *
     * Nota: Lo hice así por la falte de tiempo, debajo de este método empecé a
     * hacerlo implementando el compareto pero no me dió tiempo a acabarlo.
     *
     * @param quickChat
     * @return
     */
    public static int verificarCoherencia(QuickChat quickChat) {
        int contador = 0;
        for (QuickChat.Usuarios.Usuario usuario : quickChat.getUsuarios().getUsuario()) {
            for (Chat chat : quickChat.getChats().getChat()) {
                for (MensajeType mensajeType1 : usuario.getMensajeEnviados().getMensaje()) {
                    for (MensajeType mensajeType2 : chat.getMensajes().getMensaje()) {
                        String id1 = String.valueOf(mensajeType1.getMensajeID());
                        String id2 = String.valueOf(mensajeType2.getMensajeID());
                        XMLGregorianCalendar fecha1 = mensajeType1.getFechaPublicacion();
                        XMLGregorianCalendar fecha2 = mensajeType2.getFechaPublicacion();
                        GregorianCalendar fechaGC1 = fecha1.toGregorianCalendar();
                        GregorianCalendar fechaGC2 = fecha2.toGregorianCalendar();
                        if (id1.equalsIgnoreCase(id2)
                                && mensajeType1.getNombreUsuario().equalsIgnoreCase(mensajeType2.getNombreUsuario())
                                && mensajeType1.getTextoMensaje().equalsIgnoreCase(mensajeType2.getTextoMensaje())
                                && fechaGC1.equals(fechaGC2)) {
                            contador++;
                        }
                    }
                }
            }
        }
        return contador;
    }

    /**
     * Método que compara los mensakes de los usuarios con los de los chats.
     * Nota: lo estaba haciendo con el compareto pero no me dió tiempo a
     * acabarlo, te mando los dos. El comparator está abajo.
     *
     * @param quickChat
     */
    /*public static void verificarCoherencia(QuickChat quickChat){
        for (QuickChat.Usuarios.Usuario usuario : quickChat.getUsuarios().getUsuario()) {
            for (Chat chat : quickChat.getChats().getChat()) {
                for (MensajeType mensajeType1 : usuario.getMensajeEnviados().getMensaje()) {
                    for (MensajeType mensajeType2 : chat.getMensajes().getMensaje()) {
                        if(){
                            
                        }
                    }
                }
            }
        }
    }*/
    /**
     *
     * @param mensaje1
     * @param mensaje2
     * @return
     */
    @Override
    public int compare(MensajeType mensaje1, MensajeType mensaje2) {
        String id1 = String.valueOf(mensaje1.getMensajeID());
        String id2 = String.valueOf(mensaje2.getMensajeID());
        XMLGregorianCalendar fecha1 = mensaje1.getFechaPublicacion();
        XMLGregorianCalendar fecha2 = mensaje2.getFechaPublicacion();
        GregorianCalendar fechaGC1 = fecha1.toGregorianCalendar();
        GregorianCalendar fechaGC2 = fecha2.toGregorianCalendar();
        if (id1.equalsIgnoreCase(id2)
                && mensaje1.getNombreUsuario().equalsIgnoreCase(mensaje2.getNombreUsuario())
                && mensaje1.getTextoMensaje().equalsIgnoreCase(mensaje2.getTextoMensaje())
                && fechaGC1.compareTo(fechaGC2) == 1) {
            return 1;
        } else {
            return -1;
        }
    }

}
