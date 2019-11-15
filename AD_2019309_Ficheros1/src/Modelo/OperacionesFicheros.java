package Modelo;

import java.io.File;
import java.io.FilenameFilter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.StringTokenizer;

/**
 *
 * @author Gonzalo
 */
public class OperacionesFicheros implements Comparator<File> {

    /**
     * Método listarFichero, recibe una ruta por parámetro y se muestran los
     * archivos o direcctorios que hay en la ruta especificada.
     *
     * @param ruta --> de donde se quiere mirar que ficheros o direcctorios hay
     * @param ordenadosPorTamanio --> si está a true, los archivos deberán
     * aparecer ordenados por tamaño.
     * @param soloDirectorios --> si está a true, se deberán mostrar solo los
     * nombres de los archivos.
     * @return un array con los archivos que hay en la ruta que se recibe por
     * parámetro.
     * @throws Modelo.MiExcepcion.noEsUnDirecctorio
     * @throws Modelo.MiExcepcion.carpetaVacia
     * @throws Modelo.MiExcepcion
     */
    public ArrayList<File> listarFicheros(String ruta, boolean ordenadosPorTamanio, boolean soloDirectorios)
            throws MiExcepcion.noEsUnDirecctorio, MiExcepcion.carpetaVacia {
        File file = new File(ruta);
        String[] arrayDirectorios = null;

        ArrayList<File> listaDirectorios = new ArrayList<>();
        File[] roots = null;

        if (!ruta.isEmpty()) {
            arrayDirectorios = file.list();
            if (ordenadosPorTamanio) {
                //Una vez están los archivos que hay en la ruta introducida por
                //teclado, se recorre y se meten en el ArrayList
                for (int i = 0; i < arrayDirectorios.length; i++) {
                    listaDirectorios.add(file.listFiles()[i]);
                }
                //Ordenamos los archivos por tamaño.
                Collections.sort(listaDirectorios, this);

            } else if (soloDirectorios) {
                //Cuando solo se quieren mostrar los directorios, creo un File[] con todos los
                //archivos que hay en la ruta introducida, la recorro y los muestro.
                for (int i = 0; i < arrayDirectorios.length; i++) {
                    listaDirectorios.add(file.listFiles()[i]);
                }
            } else {
                arrayDirectorios = new String[0];
                for (int i = 0; i < arrayDirectorios.length; i++) {
                    listaDirectorios.add(file.listFiles()[i]);
                }
            }
        } else {
            //Si la ruta está vacía, mostramos los roots del equipo.
            roots = File.listRoots();
            File fichero = roots[0];

            //Si el fichero no es directorio, salta la excepción.
            if (!fichero.isDirectory()) {
                throw new MiExcepcion.noEsUnDirecctorio(ruta);
            }

            //Creamos un array de File con todos los files que hay en fichero para después recorrerlo y 
            //añadir al array.
            //ArrayList<String> ficherosEnRoot = new ArrayList<String>();
            File[] ficherosRoot = fichero.listFiles();

            //Si la ruta está vacía, salta la excepción carpetaVacía.
            if (ficherosRoot.length == 0) {
                throw new MiExcepcion.carpetaVacia(ruta);
            }

            for (File file1 : ficherosRoot) {
                if (file1.isDirectory()) {
                    listaDirectorios.add(file1);
                }
            }

        }
        return listaDirectorios;
    }

    /**
     *
     * @param rutaOrigen
     * @param listaDirectorios
     * @return
     * @throws Modelo.MiExcepcion.directorioExistente
     * @throws Modelo.MiExcepcion.rutaNoexiste
     */
    public int crearDirectrios(File rutaOrigen, ArrayList<File> listaDirectorios)
            throws MiExcepcion.directorioExistente, MiExcepcion.rutaNoexiste {

        int contadorFicherosCreados = 0;
        System.out.println(rutaOrigen);
        if (!rutaOrigen.exists()) {
            //Si la ruta de origen no existe, salta la excepción
            throw new MiExcepcion.rutaNoexiste();
        } else {
            /*if (!rutaOrigen.isDirectory()) {
                rutaOrigen.mkdir();
                System.out.println("dsjbv " + rutaOrigen.mkdir());
            }*/
            for (File fichero : listaDirectorios) {
                File crearFichero = new File(rutaOrigen.getAbsolutePath() + File.separator + fichero.getName());
                if (crearFichero.mkdir()) {
                    contadorFicherosCreados++;
                }
            }

        }
        return contadorFicherosCreados;
    }

    /**
     * 
     * @param ruta
     * @param extensionAntigua
     * @param extensionNueva
     * @return
     * @throws Modelo.MiExcepcion.rutaNoexiste 
     */
    public int cambiarExtensionFicheros(String ruta, String extensionAntigua, String extensionNueva) throws MiExcepcion.rutaNoexiste {
        File ficheroCambiarExtension = new File(ruta);
        StringTokenizer stoken = null;
        int contador = 0;

        if (!ficheroCambiarExtension.exists()) {
            throw new MiExcepcion.rutaNoexiste();
        } else {
            FilenameFilter filtro = new FilenameFilter() {
                @Override
                public boolean accept(File dir, String name) {
                    return name.endsWith(extensionAntigua);
                }
            };

            File[] listaFicherosExtension = ficheroCambiarExtension.listFiles(filtro);

            for (File fichero : listaFicherosExtension) {
                stoken = new StringTokenizer(fichero.getName(), ".");
                String nombre = stoken.nextToken();
                File ficheroNuevo = new File(ruta + "\\" + nombre + "." + extensionNueva);
                fichero.renameTo(ficheroNuevo);
                contador++;
            }
        }
        return contador;
    }

    /**
     * Método compare para ordenar los ficheros por tamaño.
     *
     * @param fichero1
     * @param fichero2
     * @return --> si retorna 1, el fichero1 es mayor que el fichero 2, si
     * retorna -1, el fichero2 es mayor que el fichero 2, si retotna 0, el
     * tamaño de ambos ficheros es igual.
     */
    @Override
    public int compare(File fichero1, File fichero2) {
        if (fichero1.length() > fichero2.length()) {
            return 1;
        } else if (fichero1.length() < fichero2.length()) {
            return -1;
        } else {
            return 0;
        }
    }

}
