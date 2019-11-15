package Controlador;

import Modelo.EjercicioQuijote;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Gonzalo
 */
public class Quijote {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {

        try {
            File quijote = EjercicioQuijote.buscarQuijote("C:\\Users\\gonza\\Downloads", "quijote.txt");
            System.out.println(quijote);

            int lineas = EjercicioQuijote.numeroLineas("C:\\Users\\gonza\\Downloads");
            System.out.println("Se han leido " + lineas + " lineas");

            int palabra = EjercicioQuijote.palabraQuijote("quijote");
            System.out.println("La palabra Quijote aparece: " + palabra + " veces");

            int letras = EjercicioQuijote.numeroCaracteres();
            System.out.println("Hay un total de " + letras + " letras");

            File ficheroReves = EjercicioQuijote.quijoteAlReves();
            System.out.println(ficheroReves);

            HashMap<String, Integer> palabras = EjercicioQuijote.listarPalabra("C:\\Users\\gonza\\Downloads");
            System.out.println(palabras);

            EjercicioQuijote.dividirEnCapitulos();

            File fichero = new File("C:\\Users\\gonza\\Downloads\\Quijote");
            HashMap<File, HashMap<String, Integer>> ficheros = EjercicioQuijote.listarFicherosDirectorio(fichero);
            System.out.println(ficheros);
        } catch (IOException ex) {
            Logger.getLogger(Quijote.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

}
