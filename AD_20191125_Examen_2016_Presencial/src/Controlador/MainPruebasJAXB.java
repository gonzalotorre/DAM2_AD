package Controlador;

import Modelo.MetodosAlbumFotosJAXB;
import java.io.File;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.xml.bind.JAXBElement;
import javax.xml.bind.JAXBException;
import jaxb.albumesFotos.AlbumesFotos;
import jaxb.albumesFotos.ObjectFactory;

/**
 * Importante hacer clean and build una vez creado el javaBinding.
 *
 * @author Gonzalo
 */
public class MainPruebasJAXB {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {

        try {
            //Creamos el File fichero que es el XML para obtener los datos y trabajar con ellos.
            File ficheroXML = new File("fotos.xml");
            //Creamos un JAXBElement que contendrá elementos leidos del archivo XML.
            JAXBElement jaxbElement = MetodosAlbumFotosJAXB.unmarshalizable(ficheroXML);
            //Creamos el objeto ObjectFactory para después crear el objeto AlbumesFotos.
            ObjectFactory fabrica = new ObjectFactory();
            //Creamos el objeto AlbumesFotos a través del ObjectFactory.
            AlbumesFotos albumes = fabrica.createAlbumesFotos();
            //Obtenemos los valores correspondientes al objeto AlbumesFotos a través del objeto JAXBElement.
            albumes = (AlbumesFotos) jaxbElement.getValue();
            //--------------------Ejecución de los métodos--------------------//
            //----------------------------------------------------------------//
            int numFotos = MetodosAlbumFotosJAXB.totalAlbumes(albumes, "800");
            System.out.println("Para la ISOO 800 hay un total de " + numFotos + " fotos");
            //----------------------------------------------------------------//
            boolean modificado = MetodosAlbumFotosJAXB.modificarEmail(albumes, "String1", "StringEmail", jaxbElement);
            System.out.println("Email modificado: " + modificado);
            //----------------------------------------------------------------//
            Map<String, Integer> totalFotos = MetodosAlbumFotosJAXB.totalFotos(albumes);
            System.out.println("Para cada título hay un total de fotos de: \n" + totalFotos);
        } catch (JAXBException ex) {
            Logger.getLogger(MainPruebasJAXB.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

}
