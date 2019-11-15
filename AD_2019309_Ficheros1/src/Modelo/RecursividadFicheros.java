package Modelo;

import java.io.File;

/**
 *
 * @author Gonzalo
 */
public class RecursividadFicheros {

    public static void recursividad(String ruta) {
        File ficheros = new File(ruta);
        File[] listaFicheros = ficheros.listFiles();
        for (File file : listaFicheros) {
            if (file.isDirectory()) {
                System.out.println(file.getName());
                for(int i = 0; i < file.getName().length(); i++){
                    System.out.print("-");
                }
                recursividad(file.getAbsolutePath());
            } else {
                System.out.println(file.getName());
            }
        }

    }

}
