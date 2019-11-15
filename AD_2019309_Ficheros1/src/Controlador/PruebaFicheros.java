package Controlador;

import Modelo.MiExcepcion;
import Modelo.OperacionesFicheros;
import java.io.File;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Gonzalo
 */
public class PruebaFicheros {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {

        //Fichero para crear archivos en la ruta introducida, para crear directorios en C:\ se necesitan permisos.
        File ruta = new File("F:\\Prueba_Extensiones");
        OperacionesFicheros operaciones = new OperacionesFicheros();
        try {
            /*Ejercicio 1.A --> Listar ficheros que hay en una ruta y dependiendo de los parámetros
              introducidos deberá mostrar los elementos root si la ruta está vacía,
              si ordenados por tamaño está a true, mostrarlos ordenados y sino mostrar solo los ficheros
              que hay en la ruta.*/
            ArrayList<File> listaFicheros = null;
            listaFicheros = operaciones.listarFicheros("", true, false);
            for (File fichero : listaFicheros) {
                System.out.println(fichero);
            }

            /*Ejercicio 1.B --> Crear nuevos directorios o ficheros en una ruta determinada.*/
            ArrayList<File> ficherosACrear = new ArrayList<>();
            System.out.println("----------------------");
            listaFicheros = operaciones.listarFicheros("C:\\Program Files (x86)\\Bluetooth Suite", false, true);
            for (File fichero : listaFicheros) {
                ficherosACrear.add(fichero);
            }
            int numeroFicheros = operaciones.crearDirectrios(ruta, ficherosACrear);
            System.out.println("Ficheros copiados: " + numeroFicheros);
            
            /*Ejercicio 1.C --> Modificar la extensión de los ficheros de una determinada ruta.*/
            int ficheroCambiados = operaciones.cambiarExtensionFicheros("F:\\Prueba_Extensiones", ".dat", ".txt");
            System.out.println("Se ha cambiado la extensión de " + ficheroCambiados + " ficheros"); 
            
        } catch (NullPointerException ex) {
            System.out.println("Ruta inicial no valida");
        } catch (MiExcepcion.noEsUnDirecctorio ex) {
            System.err.println(ex.getMessage());
        } catch (MiExcepcion.carpetaVacia ex) {
            System.err.println(ex.getMessage());
        } catch (MiExcepcion.directorioExistente ex) {
            System.err.println(ex.getMessage());
        } catch (MiExcepcion.rutaNoexiste ex) {
            System.err.println(ex.getMessage());
        }        
        
    }

}
