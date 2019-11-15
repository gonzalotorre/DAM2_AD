package Modelo;

import DAO.MetodosLecturaGrabacion;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.HashMap;
import java.util.StringTokenizer;

/**
 *
 * @author Gonzalo
 */
public class EjercicioQuijote {

    /**
     * Método para buscar un fichero cuyo nombre y ruta se introducen por
     * teclado.
     *
     * @param ruta donde se va a buscar el fichero.
     * @param nombre del fichero que se busca.
     * @return el fichero encontrado.
     */
    public static File buscarQuijote(String ruta, String nombre) {
        File discoDuro = new File(ruta);
        File[] directorios = discoDuro.listFiles();
        for (File directorio : directorios) {
            if (directorio.isDirectory()) {
                discoDuro = new File(buscarQuijote(directorio.getAbsolutePath(), nombre).getAbsolutePath());
                if (discoDuro.getName().equalsIgnoreCase(nombre)) {
                    return discoDuro;
                }
            } else {
                if (directorio.getName().equalsIgnoreCase(nombre)) {
                    discoDuro = new File(directorio.getAbsolutePath());
                    return discoDuro;
                }
            }
        }
        return discoDuro;
    }

    /**
     * Método para contar cuantas líneas contiene el texto de un archivo.
     *
     * @param ruta donde se busca el archivo del cual se quiere saber cuantas
     * lineas tine.
     * @return el número de lineas que hay en el archivo.
     * @throws java.io.FileNotFoundException
     * @throws java.io.IOException
     */
    public static int numeroLineas(String ruta) throws FileNotFoundException, IOException {
        File quijote = buscarQuijote(ruta, "quijote.txt");
        MetodosLecturaGrabacion lectura = new MetodosLecturaGrabacion(quijote);
        int contador = 0;
        String linea = null;
        lectura.abrirParaLeer();
        while ((linea = lectura.leer()) != null) {
            contador++;
        }
        lectura.cerrarLectura();
        return contador;
    }

    /**
     * Método que busca una palabra introducida por parámetro en un fichero y
     * cuenta el número de vesces que se repite dicha palabra.
     *
     * @param palabra que se quiere buscar en el texto, en este caso el Quijote.
     * @return el numero de veces que se repite la palabra introducida.
     * @throws java.io.FileNotFoundException
     * @throws java.io.IOException
     */
    public static int palabraQuijote(String palabra) throws FileNotFoundException, IOException {
        StringTokenizer tokenizer = null;
        MetodosLecturaGrabacion lectura = new MetodosLecturaGrabacion(buscarQuijote("C:\\Users\\gonza\\Downloads", "quijote.txt"));
        String lineaLeida = null;
        int contador = 0;
        //String[] signos = {"\\.", "\\,", "\\;", "\\:", "\\!", "\\¡", "\\¿", "\\?"};
        lectura.abrirParaLeer();
        while ((lineaLeida = lectura.leer()) != null) {
            /*for (String signo : signos) {
                    lineaLeida = lineaLeida.replaceAll(signo, "");
                }*/
            tokenizer = new StringTokenizer(lineaLeida, " ");
            while (tokenizer.hasMoreTokens()) {
                if (palabra.toUpperCase().contains(tokenizer.nextToken().toUpperCase())) {
                    contador++;
                }
            }
        }
        return contador;
    }

    /**
     * Método para leer el Quijote y contar cuantas letras hay en total.
     *
     * @return el número de leras que hay en el Quijote.
     * @throws java.io.FileNotFoundException
     * @throws java.io.IOException
     */
    public static int numeroCaracteres() throws FileNotFoundException, IOException {
        MetodosLecturaGrabacion lectura = new MetodosLecturaGrabacion(buscarQuijote("C:\\Users\\gonza\\Downloads", "quijote.txt"));
        String lineaLeida = null;
        int contador = 0;
        /*char[] caracteres;
        char[] especiales = {'á', 'é', 'í', 'ó', 'ú', 'ñ', 'Á', 'É', 'Í', 'Ó', 'Ú'};*/
        lectura.abrirParaLeer();
        while ((lineaLeida = lectura.leer()) != null) {
            /*caracteres = lineaLeida.toCharArray();
                for (int i = 0; i < caracteres.length; i++) {
                    for (int j = 0; j < especiales.length; j++) {
                        if (caracteres[i] == especiales[j]
                                || caracteres[i] >= 'a' && caracteres[i] <= 'z'
                                || caracteres[i] >= 'A' && caracteres[i] <= 'Z') {
                            contador++;
                        }
                    }
                }*/
            contador = contador + lineaLeida.length();
        }
        return contador;
    }

    /**
     * Método para leer el Quijote y grabar las líneas al revés, para ello uso
     * los métodos de lectura y grabación de la clase FileReader y FileWriter y
     * para leer y escribir los envuelvo en el BufferedReader y BufferedWriter.
     *
     * @return Las líneas del Quijote escritas al revés.
     * @throws java.io.FileNotFoundException
     * @throws java.io.IOException
     */
    public static File quijoteAlReves() throws FileNotFoundException, IOException {
        File quijote = buscarQuijote("C:\\Users\\gonza\\Downloads", "quijote.txt");
        MetodosLecturaGrabacion lectura = new MetodosLecturaGrabacion(quijote);
        String lineaLeida = null;
        File ficheroReves = new File(quijote.getAbsolutePath() + "\\..\\" + "QuijoteInvertido.txt");
        lectura.abrirParaLeer();
        lectura.abrirParaGrabar(ficheroReves);
        while ((lineaLeida = lectura.leer()) != null) {
            StringBuilder darVuelta = new StringBuilder(lineaLeida);
            String lineaReves = darVuelta.reverse().toString();
            lectura.grabar(lineaReves + "\n");
        }
        lectura.cerrarGrabacion();
        lectura.cerrarLectura();
        return ficheroReves;
    }

    /**
     * Método que lee el Quijote entero y mete cada palabra y el numero de veces
     * que se repite en una colección map
     *
     * @param ruta
     * @return Una colección map con todas las palabras que hay en el Quijote y
     * el numero de veces que se repite cada una.
     * @throws java.io.FileNotFoundException
     * @throws java.io.IOException
     */
    public static HashMap<String, Integer> listarPalabra(String ruta) throws FileNotFoundException, IOException {
        MetodosLecturaGrabacion lectura = new MetodosLecturaGrabacion(buscarQuijote(ruta, "quijote.txt"));
        HashMap<String, Integer> listaPalabras = new HashMap<>();
        String[] signos = {"\\.", "\\,", "\\;", "\\:", "\\!", "\\¡", "\\¿", "\\?"};
        String lineaLeida = null;
        int numeroVeces = 0;
        lectura.abrirParaLeer();
        while ((lineaLeida = lectura.leer()) != null) {
            for (String signo : signos) {
                lineaLeida = lineaLeida.replaceAll(signo, "");
            }
            StringTokenizer tokenizer = new StringTokenizer(lineaLeida, " ");
            while (tokenizer.hasMoreTokens()) {
                String palabra = tokenizer.nextToken();
                if (listaPalabras.containsKey(palabra)) {
                    numeroVeces = listaPalabras.get(palabra);
                    numeroVeces++;
                    listaPalabras.replace(palabra, numeroVeces);
                } else {
                    listaPalabras.put(palabra, 1);
                }
            }
        }
        lectura.cerrarLectura();
        return listaPalabras;
    }

    /**
     * Método para separar el Quijote en diferentes archivos con los diferentes
     * capítulos, para ello uso los métodos de lectura y grabación de la clase
     * FileReader y FileWriter y para leer y escribir los envuelvo en el
     * BufferedReader y BufferedWriter.
     *
     * @throws java.io.FileNotFoundException
     * @throws java.io.IOException
     */
    public static void dividirEnCapitulos() throws FileNotFoundException, IOException {
        File quijote = buscarQuijote("C:\\Users\\gonza\\Downloads", "quijote.txt");
        MetodosLecturaGrabacion lecturaGrabacion = new MetodosLecturaGrabacion(quijote);
        String lineaLeida = null;
        File capitulo = null;
        int contador = 1;
        boolean hayFichero = false;
        lecturaGrabacion.abrirParaLeer();
        /**
         * Probé a hacerlo así, pero de esta forma tengo dos flujos de grabación
         * abiertos y solo cierro 1, por eso a la hora de grabar el título y el
         * autor me deja el fichero en blanco.
         * lecturaGrabacion.abrirParaGrabar(new File(quijote.getAbsolutePath() +
         * "\\..\\QuijoteTitulo.txt"));
         */
        while ((lineaLeida = lecturaGrabacion.leer()) != null) {
            if (lineaLeida.contains("Capítulo")) {
                capitulo = new File(quijote.getAbsolutePath() + "\\..\\QuijoteCapitulo" + contador + ".txt");
                lecturaGrabacion.abrirParaGrabar(capitulo);
                lecturaGrabacion.grabar(lineaLeida);
                hayFichero = true;
                contador++;
            } else {
                if (hayFichero) {
                    lecturaGrabacion.grabar(lineaLeida + "\n");
                }
            }
        }
        lecturaGrabacion.cerrarLectura();
        lecturaGrabacion.cerrarGrabacion();
    }

    /**
     *
     * @param ruta
     * @return
     * @throws java.io.IOException
     */
    public static HashMap<File, HashMap<String, Integer>> listarFicherosDirectorio(File ruta) throws IOException {
        HashMap<File, HashMap<String, Integer>> listaFicheros = new HashMap<>();
        File[] ficheros = ruta.listFiles();
        for (File fichero : ficheros) {
            if (!fichero.isDirectory()) {
                listaFicheros.put(fichero, densidadPalabras(fichero));
            }
        }
        return listaFicheros;
    }

    /**
     *
     * @param fichero
     * @return
     * @throws java.io.FileNotFoundException
     * @throws java.io.IOException
     */
    public static HashMap<String, Integer> densidadPalabras(File fichero) throws FileNotFoundException, IOException {
        MetodosLecturaGrabacion lectura = new MetodosLecturaGrabacion(fichero);
        HashMap<String, Integer> listaPalabras = new HashMap<>();
        String[] signos = {"\\.", "\\,", "\\;", "\\:", "\\!", "\\¡", "\\¿", "\\?"};
        String lineaLeida = null;
        int numeroVeces = 0;
        lectura.abrirParaLeer();
        while ((lineaLeida = lectura.leer()) != null) {
            for (String signo : signos) {
                lineaLeida = lineaLeida.replaceAll(signo, "");
            }
            StringTokenizer tokenizer = new StringTokenizer(lineaLeida, " ");
            while (tokenizer.hasMoreTokens()) {
                String palabra = tokenizer.nextToken();
                if (listaPalabras.containsKey(palabra)) {
                    numeroVeces = listaPalabras.get(palabra);
                    numeroVeces++;
                    listaPalabras.replace(palabra, numeroVeces);
                } else {
                    listaPalabras.put(palabra, 1);
                }
            }
        }
        lectura.cerrarLectura();
        return listaPalabras;
    }

}
