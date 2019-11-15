package Modelo;

import java.io.File;
import javax.xml.bind.JAXBElement;
import javax.xml.bind.JAXBException;
import jaxb.clientes.Clientes;
import jaxb.clientes.Clientes.Cliente.Nombre;
import jaxb.clientes.TipoDireccion;

/**
 *
 * @author Gonzalo
 */
public interface MetodosInterfaz {
    
    public JAXBElement unmarshalizable(File ficheroXML) throws JAXBException;
    
    public void marshalizar(JAXBElement jaxbElement) throws JAXBException;

    public int numeroClientes(Clientes cliente);

    public int numeroClientesProvincia(Clientes cliente, int codigoPostal);

    public boolean borrarCliente(Clientes cliente, String apellido1, String apellido2);

    public boolean anadirCliente(Clientes cliente, String apellido1, String apellido2, TipoDireccion direccion, String numTelefono, Nombre nombre);

    public boolean anadirDireccion(Clientes cliente, TipoDireccion direccion, String nombre, String apellido1, String apellido2);

    public boolean modificarDireccionCliente(Clientes cliente, TipoDireccion direccion, String nombre, String apellidos);
    
    public int borrarDirecciones(Clientes cliente);
    
    public File crearHTML(Clientes cliente);

}
