package Controlador;

import Modelo.NumerosPrimos;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Gonzalo
 */
public class MainPrimos {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        try {
            NumerosPrimos.numerosPrimos();
            int numero = NumerosPrimos.leerFichero(5);
            System.out.println(numero);
            NumerosPrimos.ficheroHTML();
        } catch (IOException ex) {
            Logger.getLogger(MainPrimos.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }
}
