package Controlador;

import Modelo.MetodosDiccionario2JSON;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.text.ParseException;
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
            JsonObject jsonDiccionario = MetodosDiccionario2JSON.leerFichero("diccionario2.json");
            //----------------------------------------------------------------//
            int numSinAnt = MetodosDiccionario2JSON.totalSinonimosAntonimos(jsonDiccionario, "String1");
            System.out.println("Hay un total de " + numSinAnt + " sinónimos y antónimos para la palabra String1");
            //----------------------------------------------------------------//
            Map<String, Integer> listaPorcentaje = MetodosDiccionario2JSON.porcentajeSimilitudMayorrOchenta(jsonDiccionario);
            System.out.println("Lista de palabras con un porcentaje de similitud del 80%: " + listaPorcentaje);
            //----------------------------------------------------------------//
            JsonArray grafias = MetodosDiccionario2JSON.generarFicheroJSON(jsonDiccionario);
            MetodosDiccionario2JSON.grabarFicheroJSON(grafias, "GrafiasJSON.json");
        } catch (FileNotFoundException | ParseException ex) {
            Logger.getLogger(MainPruebasJSON.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(MainPruebasJSON.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
}
