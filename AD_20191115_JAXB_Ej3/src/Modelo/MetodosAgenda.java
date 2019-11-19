package Modelo;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBElement;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import javax.xml.transform.stream.StreamSource;
import jaxb.agenda.Agenda;
import jaxb.agenda.AlarmaType;
import jaxb.agenda.ContactoType;
import jaxb.agenda.CorreoType;

/**
 *
 * @author Gonzalo
 */
public class MetodosAgenda implements InterfaceMetodos {

    @Override
    public JAXBElement unmarshalizable(File ficheroXML) throws JAXBException {
        //Creamos una instancia de JAXBContext para manipular las clases generadas en jaxb.albaran
        JAXBContext jaxbContext = JAXBContext.newInstance("jaxb.agenda");
        //Objeto unmarshall para 
        Unmarshaller unmashall = jaxbContext.createUnmarshaller();
        //
        JAXBElement jaxbElement = unmashall.unmarshal(new StreamSource(new File("agenda.xml")), Agenda.class);
        return jaxbElement;
    }

    @Override
    public void marshalizar(JAXBElement jaxbElement) throws JAXBException {
        JAXBContext jaxbContext = JAXBContext.newInstance("jaxb.agenda");
        Marshaller m = jaxbContext.createMarshaller();
        m.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);
        m.marshal(jaxbElement, System.out);
    }

    @Override
    public boolean anadirAlarma(Agenda agenda, String tono, String asunto, String prioridad, AlarmaType.DiaHora diaHora) {
        AlarmaType alarma = new AlarmaType();
        alarma.setTono(tono);
        alarma.setAsunto(asunto);
        alarma.setPrioridad(prioridad);
        alarma.setDiaHora(diaHora);
        agenda.getAlarmas().getAlarma().add(alarma);
        return true;
    }

    @Override
    public boolean anadirTelefono(Agenda agenda, ContactoType.Nombre nombre, String telefonoNuevo) {
        boolean anadido = false;
        for (ContactoType contactoType : agenda.getContactos().getContacto()) {
            if (contactoType.getNombre().equals(nombre)) {
                contactoType.getTelefono().add(telefonoNuevo);
                anadido = true;
            }
        }
        return anadido;
    }

    /**
     * Como solo hay un correo y la clase Correo no contiene ninguna lista de
     * correos, no hay que recorrer ninguna lista por lo que le añado de frente
     * el correo a la lista Map. Si hubiera una lista de correos, habría que
     * recorrerla y después añadir el correo.
     *
     * @param agenda
     * @return
     */
    @Override
    public Map<String, String> verCorreos(Agenda agenda) {
        Map<String, String> correos = new HashMap<>();
        correos.put(agenda.getCorreos().getCorreo().getAsunto(), agenda.getCorreos().getCorreo().getRemitente());
        return correos;
    }

    @Override
    public List<String> buscarTelefonos(Agenda agenda, ContactoType.Nombre nombre) {
        List<String> telefonos = new ArrayList<>();
        for (ContactoType contactoType : agenda.getContactos().getContacto()) {
            if (contactoType.getNombre().equals(nombre)) {
                telefonos = contactoType.getTelefono();
            }
        }
        return telefonos;
    }

    @Override
    public boolean borrarContacto(Agenda agenda, ContactoType.Nombre nombre) throws MiExcepcion.entradaDuplicada {
        int contador = 0;
        boolean borrado = false;
        for (ContactoType contactoType : agenda.getContactos().getContacto()) {
            if (contactoType.getNombre().equals(nombre)) {
                /*if(contador >= 1){
                    throw MiExcepcion.entradaDuplicada;
                }*/
                agenda.getContactos().getContacto().remove(contactoType);
                borrado = true;
            }
        }
        return borrado;
    }

    @Override
    public Map<Agenda, Integer> contarTotalEntradas(Agenda agenda) {
        int contador = 0;
        contador += agenda.getAlarmas().getAlarma().size();
        contador += agenda.getContactos().getContacto().size();
        //Como no hay una lista de correos y solo hay un correo le sumo uno al contador de frente,
        //si hubiera una lista lo haríamos como con las alarmas y los contactos, sacando el size de la lista.
        contador++;
        Map<Agenda, Integer> totalEntradas = new HashMap<>();
        totalEntradas.put(agenda, contador);
        return totalEntradas;
    }

    @Override
    public int alarmasPendientes(Agenda agenda, AlarmaType.DiaHora fecha) {
        int contador = 0;
        for (AlarmaType alarmaType : agenda.getAlarmas().getAlarma()) {
            if (fecha.getDia().getDia() < alarmaType.getDiaHora().getDia().getDia()
                    && fecha.getDia().getMes() < alarmaType.getDiaHora().getDia().getMes()
                    && fecha.getDia().getAño() < alarmaType.getDiaHora().getDia().getAño()) {
                contador++;
            }
            if (fecha.getHora().getHora() < alarmaType.getDiaHora().getHora().getHora()
                    && fecha.getHora().getMinuto() < alarmaType.getDiaHora().getHora().getMinuto()
                    && fecha.getHora().getMinuto() < alarmaType.getDiaHora().getHora().getMinuto()) {

            }
        }
        return contador;
    }

}
