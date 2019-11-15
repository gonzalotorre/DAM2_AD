package Modelo;

import java.io.File;
import java.math.BigDecimal;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBElement;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import javax.xml.datatype.XMLGregorianCalendar;
import javax.xml.transform.stream.StreamSource;
import jaxb.albaran.Articulos;
import jaxb.albaran.Direccion;
import jaxb.albaran.PedidoType;

/**
 *
 * @author Gonzalo
 */
public class ManejoAlbaran implements InterfaceMetodos {

    @Override
    public JAXBElement unmarshalizable(File ficheroXML) throws JAXBException {
        //Creamos una instancia de JAXBContext para manipular las clases generadas en jaxb.albaran
        JAXBContext jaxbContext = JAXBContext.newInstance("jaxb.albaran");
        //Objeto unmarshall para 
        Unmarshaller unmashall = jaxbContext.createUnmarshaller();
        //
        JAXBElement jaxbElement = unmashall.unmarshal(new StreamSource(new File("albaran.xml")), PedidoType.class);
        return jaxbElement;
    }

    @Override
    public void marshalizar(JAXBElement jaxbElement) throws JAXBException {
        JAXBContext jaxbContext = JAXBContext.newInstance("jaxb.albaran");
        Marshaller m = jaxbContext.createMarshaller();
        m.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);
        m.marshal(jaxbElement, System.out);
    }

    @Override
    public PedidoType anadirArticulo(PedidoType pedido, String nombreProducto, int cantidad, BigDecimal precio, String comentario, XMLGregorianCalendar fechaEnvio, String codigo) {
        Articulos.Articulo articulo = new Articulos.Articulo();
        articulo.setNombreProducto(nombreProducto);
        articulo.setCantidad(cantidad);
        articulo.setPrecio(precio);
        articulo.setComentario(comentario);
        articulo.setFechaEnvio(fechaEnvio);
        articulo.setCodigo(codigo);
        pedido.getArticulos().getArticulo().add(articulo);
        return pedido;
    }

    @Override
    public boolean modificarDireccionPedido(PedidoType pedido, Direccion direccionNueva) {
        if (direccionNueva == null) {
            return false;
        } else {
            pedido.setFacturarA(direccionNueva);
            return true;
        }
    }

    @Override
    public double calcularImporte(PedidoType pedido, Articulos.Articulo articulo) {
        double importe = 0;
        for (Articulos.Articulo articulo2 : pedido.getArticulos().getArticulo()) {
            importe += articulo2.getPrecio().floatValue() * articulo2.getCantidad();
        }
        return importe;
    }

    @Override
    public boolean borrarArticulo(PedidoType pedido, String nombre) {
        boolean borrado = false;
        for (Articulos.Articulo articulo3 : pedido.getArticulos().getArticulo()) {
            if(articulo3.getNombreProducto().equalsIgnoreCase(nombre)){
                pedido.getArticulos().getArticulo().remove(articulo3);
                borrado = true;
            }
        }
        return borrado;
    }

}
