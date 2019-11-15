package Modelo;

import java.io.File;
import java.util.List;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBElement;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import javax.xml.transform.stream.StreamSource;
import jaxb.clientes.Clientes;
import jaxb.clientes.Clientes.Cliente.Nombre;
import jaxb.clientes.TipoDireccion;

/**
 *
 * @author Gonzalo
 */
public class ManejoClientes implements MetodosInterfaz {

    @Override
    public JAXBElement unmarshalizable(File ficheroXML) throws JAXBException {
        //Creamos una instancia de JAXBContext para manipular las clases generadas en jaxb.albaran
        JAXBContext jaxbContext = JAXBContext.newInstance("jaxb.clientes");
        //Objeto unmarshall para 
        Unmarshaller unmashall = jaxbContext.createUnmarshaller();
        //
        JAXBElement jaxbElement = unmashall.unmarshal(new StreamSource(new File("clientes.xml")), Clientes.class);
        return jaxbElement;
    }

    @Override
    public void marshalizar(JAXBElement jaxbElement) throws JAXBException {
        JAXBContext jaxbContext = JAXBContext.newInstance("jaxb.clientes");
        Marshaller m = jaxbContext.createMarshaller();
        m.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);
        m.marshal(jaxbElement, System.out);
    }

    @Override
    public int numeroClientes(Clientes cliente) {
        return cliente.getCliente().size();
    }

    @Override
    public int numeroClientesProvincia(Clientes cliente, int codigoPostal) {
        int contador = 0;
        for (Clientes.Cliente clienteLista : cliente.getCliente()) {
            for (TipoDireccion tipoDireccion : clienteLista.getDireccion()) {
                if (codigoPostal == tipoDireccion.getCp()) {
                    contador++;
                }
            }
        }
        return contador;
    }

    @Override
    public boolean borrarCliente(Clientes cliente, String apellido1, String apellido2) {
        Clientes.Cliente c = new Clientes.Cliente();
        boolean borrado = false;
        for (Clientes.Cliente cliente1 : cliente.getCliente()) {
            if (cliente1.getApellido().get(0).equalsIgnoreCase(apellido1) && cliente1.getApellido().get(1).equalsIgnoreCase(apellido2)) {
                c = cliente1;
                borrado = true;
            }
        }
        cliente.getCliente().remove(c);
        return borrado;
    }

    @Override
    public boolean anadirCliente(Clientes cliente, String apellido1, String apellido2, TipoDireccion direccion, String numTelefono, Nombre nombre) {
        boolean anadido = false;
        Clientes.Cliente c = new Clientes.Cliente();
        c.getApellido().add(apellido1);
        c.getApellido().add(apellido2);
        c.getDireccion().add(direccion);
        c.setTelefono(numTelefono);
        c.setNombre(nombre);
        cliente.getCliente().add(c);
        if (cliente.getCliente().contains(c)) {
            anadido = true;
        }
        return anadido;
    }

    @Override
    public boolean anadirDireccion(Clientes cliente, TipoDireccion direccion, String nombre, String apellido1, String apellido2) {
        boolean dirAnadida = false;
        Clientes.Cliente c = new Clientes.Cliente();
        for (Clientes.Cliente cliente2 : cliente.getCliente()) {
            if (cliente2.getNombre().getLenguaje().equalsIgnoreCase(nombre)
                    && cliente2.getApellido().get(0).equalsIgnoreCase(apellido1)
                    && cliente2.getApellido().get(1).equalsIgnoreCase(apellido2)) {
                c.getDireccion().add(direccion);
                dirAnadida = true;
            }
        }
        return dirAnadida;
    }

    @Override
    public boolean modificarDireccionCliente(Clientes cliente, TipoDireccion direccion, String nombre, String apellidos) {

        return false;
    }

    /**
     * Borrar direcciones que no tengan codigoPostal
     *
     * @param cliente
     * @return
     */
    @Override
    public int borrarDirecciones(Clientes cliente) {
        int contador = 0;
        for (Clientes.Cliente cliente1 : cliente.getCliente()) {
            for (TipoDireccion direccion : cliente1.getDireccion()) {
                if (direccion.getCp() == 0) {
                    cliente1.getDireccion().remove(direccion);
                    contador++;
                }
            }
        }
        return contador;
    }

    @Override
    public File crearHTML(Clientes cliente) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

}
