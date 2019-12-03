package Controlador;

import Modelo.MetodosBibliotecaJSON;
import java.io.FileNotFoundException;
import java.io.IOException;
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
            JsonObject jsonBiblioteca = MetodosBibliotecaJSON.leerFichero("biblioteca.json");
            //--------------------Ejecución de los métodos--------------------//
            //----------------------------------------------------------------//
            JsonObject biblioteca = MetodosBibliotecaJSON.generarJSON(jsonBiblioteca);
            MetodosBibliotecaJSON.grabarFicheroJSON(biblioteca, "JSONPrimeraRama.json");
        } catch (FileNotFoundException ex) {
            Logger.getLogger(MainPruebasJSON.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(MainPruebasJSON.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
}
