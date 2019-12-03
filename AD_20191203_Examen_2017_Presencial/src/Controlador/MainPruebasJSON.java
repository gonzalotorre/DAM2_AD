package Controlador;

import Modelo.MetodosDiccionarioJSON;
import java.io.File;
import java.io.FileNotFoundException;
import java.text.ParseException;
import java.util.List;
import java.util.Map;
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
            JsonObject jsonDiccionario = MetodosDiccionarioJSON.leerFichero("diccionario.json");
            //--------------------Ejecución de los métodos--------------------//
            //----------------------------------------------------------------//
            int numDefiniciones = MetodosDiccionarioJSON.totalDefiniciones(jsonDiccionario, "String1");
            System.out.println("La palabra String1 tiene: " + numDefiniciones + " definiciones.");
            //----------------------------------------------------------------//
            Map<String, Integer> lista = MetodosDiccionarioJSON.numTraducciones(jsonDiccionario);
            System.out.println("El numero de traducciones para cada idioma es: " + lista);
            //----------------------------------------------------------------//
            List<String> listaPalabras = MetodosDiccionarioJSON.listaPalabras(jsonDiccionario);
            System.out.println("Las palabras añadidas hace menos de tres días son: " + listaPalabras);
            //----------------------------------------------------------------//
            File ficheroJSON = new File("diccionario.json");
            MetodosDiccionarioJSON.crearXML(ficheroJSON, jsonDiccionario);
        } catch (FileNotFoundException ex) {
            Logger.getLogger(MainPruebasJSON.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ParseException ex) {
            Logger.getLogger(MainPruebasJSON.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

}
