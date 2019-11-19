package Modelo;

import java.io.File;
import java.util.Iterator;
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

    /**
     * Método unmarhal (leer) para poder leer el fichero xml y luego hacer las
     * operaciones pedidas.
     *
     * @param ficheroXML del que se van a obtener los datos
     * @return
     * @throws JAXBException
     */
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

    /**
     * Método marshal (escribir) para aplicar los cambios al documento XML.
     *
     * @param jaxbElement
     * @throws JAXBException
     */
    @Override
    public void marshalizar(JAXBElement jaxbElement) throws JAXBException {
        JAXBContext jaxbContext = JAXBContext.newInstance("jaxb.clientes");
        Marshaller m = jaxbContext.createMarshaller();
        m.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);
        m.marshal(jaxbElement, System.out);
    }

    /**
     * Método que devuelve el número de clientes.
     *
     * @param clientes para acceder a la lista de clientes.
     * @return numero de clientes.
     */
    @Override
    public int numeroClientes(Clientes clientes) {
        return clientes.getCliente().size();
    }

    /**
     * Método que devuelve el número de clientes que hay pasandole el código
     * postal de una dirección.
     *
     * @param clientes para poder acceder a la lista de clientes.
     * @param codigoPostal para saber el numero de clientes.
     * @return el número de clientes en un determinado cp.
     */
    @Override
    public int numeroClientesProvincia(Clientes clientes, int codigoPostal) {
        int contador = 0;
        for (Clientes.Cliente clienteLista : clientes.getCliente()) {
            for (TipoDireccion tipoDireccion : clienteLista.getDireccion()) {
                if (codigoPostal == tipoDireccion.getCp()) {
                    contador++;
                }
            }
        }
        return contador;
    }

    /**
     * Método para borrar un cliente por los apellidos.
     *
     * @param clientes para acceder a la lista de clientes y buscar el
     * clietente.
     * @param apellido1 del cliente.
     * @param apellido2 del cliente.
     * @return true si se ha borrado, false si no se ha borrado.
     */
    @Override
    public boolean borrarCliente(Clientes clientes, String apellido1, String apellido2) {
        Clientes.Cliente c = new Clientes.Cliente();
        boolean borrado = false;
        for (Clientes.Cliente cliente1 : clientes.getCliente()) {
            if (cliente1.getApellido().get(0).equalsIgnoreCase(apellido1) && cliente1.getApellido().get(1).equalsIgnoreCase(apellido2)) {
                c = cliente1;
                borrado = true;
            }
        }
        clientes.getCliente().remove(c);
        return borrado;
    }

    /**
     * Método para añadir un nuevo cliente.
     *
     * @param clientes para acceder a la clase cliente y crear el cliente.
     * @param apellido1 del cliente a añadir.
     * @param apellido2 del cliente a añadir.
     * @param direccion del cliente a añadir.
     * @param numTelefono del cliente a añadir.
     * @param nombre del cliente a añadir.
     * @return true si se añadió el clietne, false en caso contrario.
     */
    @Override
    public boolean anadirCliente(Clientes clientes, String apellido1, String apellido2, TipoDireccion direccion, String numTelefono, Nombre nombre) {
        boolean anadido = false;
        Clientes.Cliente c = new Clientes.Cliente();
        c.getApellido().add(apellido1);
        c.getApellido().add(apellido2);
        c.getDireccion().add(direccion);
        c.setTelefono(numTelefono);
        c.setNombre(nombre);
        clientes.getCliente().add(c);
        if (clientes.getCliente().contains(c)) {
            anadido = true;
        }
        return anadido;
    }

    /**
     * Método para añadir una dirección a un cliente pasandole el nombre y los
     * apellidos.
     *
     * @param clientes para recorrer la lista de los clientes.
     * @param direccion nueva que se quiere añadir al cliente.
     * @param nombre del clietne al que se le quiere añadir la dirección.
     * @param apellido1 del clietne al que se le quiere añadir la dirección.
     * @param apellido2 del clietne al que se le quiere añadir la dirección.
     * @return true si se ha añadido la direccoón, false en caso contrario
     */
    @Override
    public boolean anadirDireccion(Clientes clientes, TipoDireccion direccion, String nombre, String apellido1, String apellido2) {
        boolean dirAnadida = false;
        Clientes.Cliente c = new Clientes.Cliente();
        for (Clientes.Cliente cliente2 : clientes.getCliente()) {
            if (cliente2.getNombre().getLenguaje().equalsIgnoreCase(nombre)
                    && cliente2.getApellido().get(0).equalsIgnoreCase(apellido1)
                    && cliente2.getApellido().get(1).equalsIgnoreCase(apellido2)) {
                c.getDireccion().add(direccion);
                dirAnadida = true;
            }
        }
        return dirAnadida;
    }

    /**
     * Método para modficar la dirección de un cliente.
     *
     * @param clientes para acceder a la lista de clientes, buscar el cliente y
     * cambiarle la direccoón.
     * @param direccion que se quiere modificar
     * @param nombre del cliente al que se le quiere cambiar la dirección.
     * @param apellido1 del cliente al que se le quiere cambiar la dirección.
     * @param apellido2 del cliente al que se le quiere cambiar la dirección.
     * @return true si se ha modificado la dirección del cliente, false en caso
     * contrario.
     */
    @Override
    public boolean modificarDireccionCliente(Clientes clientes, TipoDireccion direccion, String nombre, String apellido1, String apellido2) {
        boolean hecho = false;
        for (Clientes.Cliente cliente : clientes.getCliente()) {
            if (cliente.getNombre().getLenguaje().equalsIgnoreCase(nombre) && cliente.getApellido().get(0).equalsIgnoreCase(apellido1) && cliente.getApellido().get(1) == null) {
                for (TipoDireccion tipoDireccion : cliente.getDireccion()) {
                    if (tipoDireccion.equals(direccion)) {
                        hecho = true;
                        cliente.getDireccion().remove(tipoDireccion);
                        cliente.getDireccion().add(direccion);
                    }
                }
            } else if (cliente.getNombre().getLenguaje().equalsIgnoreCase(nombre) && cliente.getApellido().get(0).equalsIgnoreCase(apellido1) && cliente.getApellido().get(1).equalsIgnoreCase(apellido2)) {
                for (TipoDireccion tipoDireccion : cliente.getDireccion()) {
                    if (tipoDireccion.equals(direccion)) {
                        hecho = true;
                        cliente.getDireccion().remove(tipoDireccion);
                        cliente.getDireccion().add(direccion);
                    }
                }
            }
        }
        return hecho;
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
            for (Iterator<TipoDireccion> it = cliente1.getDireccion().iterator(); it.hasNext();) {
                TipoDireccion direccion = it.next();
                if (direccion.getCp() == 0) {
                    it.remove();
                    contador++;
                }
            }
        }

        /* De esta forma me lanza una excepción concurrent exception.
        int contador = 0;
        for (Clientes.Cliente cliente1 : cliente.getCliente()) {
            for (TipoDireccion direccion : cliente1.getDireccion()) {
                if (direccion.getCp() == 0) {
                    cliente1.getDireccion().remove(direccion);
                    contador++;
                }
            }
        }*/
        return contador;
    }

    /**
     *
     * @param cliente
     * @return
     */
    @Override
    public File crearHTML(Clientes cliente) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

}
