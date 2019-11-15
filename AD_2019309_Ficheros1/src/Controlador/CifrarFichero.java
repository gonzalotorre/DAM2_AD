package Controlador;

import java.io.File;
import Modelo.CifrarFicheros;
import java.util.Map;

/**
 *
 * @author Gonzalo
 */
public class CifrarFichero {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {

        File ficheroSinCifrar = new File("C:\\Users\\gonza\\Documents\\DAM\\2DAM\\Asignaturas\\AD\\mensaje.txt");
        CifrarFicheros.cifrarFichero(ficheroSinCifrar);
        
        File ficheroCifrado = new File("C:\\Users\\gonza\\Documents\\DAM\\2DAM\\Asignaturas\\AD\\mensajeCifrado.txt");
        CifrarFicheros.descifrarFichero(ficheroCifrado);
        
        File ficheroABuscar = new File("C:\\Users\\gonza\\Documents\\DAM\\2DAM\\Asignaturas\\AD\\mensaje.txt");
        Map<String, Integer> letras = CifrarFicheros.numeroLetras(ficheroABuscar);
        System.out.println(letras);
    }

}
