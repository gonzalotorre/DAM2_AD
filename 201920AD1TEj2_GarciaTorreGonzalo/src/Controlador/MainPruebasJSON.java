package Controlador;

import Modelo.MetododosChatJSON;
import java.io.IOException;
import java.text.ParseException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.json.JsonObject;

/**
 *
 * @author Gonzalo
 */
public class MainPruebasJSON {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        try {
            //--------------------Ejecución de los métodos--------------------//
            //----------------------------------------------------------------//
            //MetododosChatJSON.crearFichero("chatusuariosNuevo.json");
            JsonObject jsonObject = MetododosChatJSON.leerFichero("chatusuarios.json");
            MetododosChatJSON.crearXML(jsonObject);
        } catch (IOException ex) {
            Logger.getLogger(MainPruebasJSON.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ParseException ex) {
            Logger.getLogger(MainPruebasJSON.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

}
