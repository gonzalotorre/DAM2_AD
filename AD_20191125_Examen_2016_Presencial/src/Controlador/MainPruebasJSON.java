package Controlador;

import Modelo.MetodosAlbumFotosJSON;
import java.io.FileNotFoundException;
import java.text.ParseException;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.json.JsonObject;

/**
 * Importante añadir la libreria javax.json para poder trabajar con JSON.
 * @author Gonzalo
 */
public class MainPruebasJSON {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        try {
            JsonObject albumFotos = MetodosAlbumFotosJSON.leerFichero("fotos.json");
            int numFotos = MetodosAlbumFotosJSON.totalFotosISO(albumFotos, "800");
            System.out.println("Para la ISO 800 hay un total de: " + numFotos + " fotos");
            Map<String, Integer> totalFotos = MetodosAlbumFotosJSON.totalFotosAlbum(albumFotos);
            System.out.println("Total de fotos en cada album: " + totalFotos);
            List<String> lista = MetodosAlbumFotosJSON.titulos(albumFotos);
            System.out.println("----Fotos hechas antes del 2000----");
            for (String titulo : lista) {
                System.out.println("Fecha: " + titulo);
            }
        } catch (FileNotFoundException | ParseException ex) {
            Logger.getLogger(MainPruebasJSON.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
}
