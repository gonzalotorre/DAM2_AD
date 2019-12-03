package Controlador;

import Modelo.MetodosBibliotecaJSON;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.json.JsonArray;
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
            JsonArray listaSocios = MetodosBibliotecaJSON.generarNuevoJSON(jsonBiblioteca);
            MetodosBibliotecaJSON.grabarFicheroJSON(listaSocios, "listaSociosJSON.json");
            //----------------------------------------------------------------//
            Map<String, List<String>> listaTitulosPorSocio = MetodosBibliotecaJSON.mapaCodigoTitulos(jsonBiblioteca);
            System.out.println(listaTitulosPorSocio);
        } catch (FileNotFoundException ex) {
            Logger.getLogger(MainPruebasJSON.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(MainPruebasJSON.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

}
