package Modelo;

import java.io.File;
import java.util.List;
import java.util.Map;
import javax.xml.bind.JAXBElement;
import javax.xml.bind.JAXBException;
import jaxb.agenda.Agenda;
import jaxb.agenda.AlarmaType;
import jaxb.agenda.ContactoType;

/**
 *
 * @author Gonzalo
 */
public interface InterfaceMetodos {
    
    public JAXBElement unmarshalizable(File ficheroXML) throws JAXBException;
    
    public void marshalizar(JAXBElement jaxbElement) throws JAXBException;
    
    public boolean anadirAlarma(Agenda agenda, String tono, String asunto, String prioridad, AlarmaType.DiaHora diaHora);
    
    public boolean anadirTelefono(Agenda agenda, ContactoType.Nombre nombre, String telefonoNuevo);
    
    public Map<String, String> verCorreos(Agenda agenda);
    
    public List<String> buscarTelefonos(Agenda agenda, ContactoType.Nombre nombre);
    
    public boolean borrarContacto(Agenda agenda, ContactoType.Nombre nombre) throws MiExcepcion.entradaDuplicada;
    
    public Map<Agenda, Integer> contarTotalEntradas(Agenda agenda);
    
    public int alarmasPendientes(Agenda agenda, AlarmaType.DiaHora fecha);
    
}
