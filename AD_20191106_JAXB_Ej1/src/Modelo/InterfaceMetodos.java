package Modelo;


import java.io.File;
import java.math.BigDecimal;
import javax.xml.bind.JAXBElement;
import javax.xml.bind.JAXBException;
import javax.xml.datatype.XMLGregorianCalendar;
import jaxb.albaran.Articulos;
import jaxb.albaran.Direccion;
import jaxb.albaran.PedidoType;

/**
 *
 * @author Gonzalo
 */
public interface InterfaceMetodos {

    /**
     *
     * @param ficheroXML
     * @return
     * @throws javax.xml.bind.JAXBException
     */
    public JAXBElement unmarshalizable(File ficheroXML) throws JAXBException;

    /**
     *
     * @param jaxbElement
     * @throws javax.xml.bind.JAXBException
     */
    public void marshalizar(JAXBElement jaxbElement) throws JAXBException;

    /**
     *
     * @param pedido
     * @param nombreProducto
     * @param precio
     * @param cantidad
     * @param fechaEnvio
     * @param comentario
     * @param codigo
     * @return
     */
    public PedidoType anadirArticulo(PedidoType pedido, String nombreProducto, int cantidad, BigDecimal precio, String comentario, XMLGregorianCalendar fechaEnvio, String codigo);

    /**
     *
     * @param pedido
     * @param direccionNueva
     * @return
     */
    public boolean modificarDireccionPedido(PedidoType pedido, Direccion direccionNueva);

    /**
     *
     * @param pedido
     * @param articulo
     * @return
     */
    public double calcularImporte(PedidoType pedido, Articulos.Articulo articulo);

    /**
     *
     * @param pedido
     * @param articulo
     * @return
     */
    public boolean borrarArticulo(PedidoType pedido, String nombre);

}
