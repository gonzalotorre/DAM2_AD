package Modelo;

import DAO.DAO_File;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.StringTokenizer;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Clase para el cifrado, el descifrado de archivos con el cifrado César y
 * recuento de caracteres de un tipo, es decir, cuantas veces se repite el
 * carcter a, el b...
 *
 * @author Gonzalo
 */
public class CifrarFicheros {

    /**
     * Cifrar un archivo txt con el método Cifrado César que consiste en cambiar
     * una letra por la 3a después de ella, es decir, A --> D, B --> E...
     *
     * @param fichero que vamos a cifrar.
     */
    public static void cifrarFichero(File fichero) {
        DAO_File daoStream = new DAO_File(fichero);
        String ruta = rutaFichero(fichero);
        File ficheroNuevo = new File(ruta + "Cifrado.txt");
        DAO_File daoStreamNuevo = new DAO_File(ficheroNuevo);
        try {
            daoStream.abrirParaLeer();
            daoStreamNuevo.abrirParaGrabar();
            int byteLeido = 0;
            do {
                byteLeido = daoStream.leer();
                int byteCifrado = byteLeido + 3;
                daoStreamNuevo.grabarRegistro(byteCifrado);
            } while (byteLeido != -1);

            daoStream.cerrarLectura();
            daoStreamNuevo.cerrarGrabar();
        } catch (FileNotFoundException ex) {
            Logger.getLogger(CifrarFicheros.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(CifrarFicheros.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    /**
     * Descifrar un fichero cifrado con el Cifrado César.
     *
     * @param fichero que vamos a descifrar.
     */
    public static void descifrarFichero(File fichero) {
        DAO_File daoStream = new DAO_File(fichero);
        String ruta = rutaFichero(fichero);
        File ficheroNuevo = new File(ruta + "DesCifrado.txt");
        DAO_File daoStreamNuevo = new DAO_File(ficheroNuevo);
        try {
            daoStream.abrirParaLeer();
            daoStreamNuevo.abrirParaGrabar();
            int byteLeido = 0;
            do {
                byteLeido = daoStream.leer();
                int byteCifrado = byteLeido - 3;
                daoStreamNuevo.grabarRegistro(byteCifrado);
            } while (byteLeido != -1);

            daoStream.cerrarLectura();
            daoStreamNuevo.cerrarGrabar();
        } catch (FileNotFoundException ex) {
            Logger.getLogger(CifrarFicheros.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(CifrarFicheros.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    /**
     * Método para contar el número de caracteres que hay de cada tipo.
     *
     * @param fichero en el que vamos a contar el número de caracteres de cada
     * tipo.
     * @return una Map con todos los caracteres y el número que hay de cada.
     */
    public static Map<String, Integer> numeroLetras(File fichero) {
        HashMap<String, Integer> hashMap = new HashMap();
        DAO_File daoStream = new DAO_File(fichero);
        int contador = 0;
        try {
            daoStream.abrirParaLeer();
            int byteLeido = 0;
            do {
                byteLeido = daoStream.leer();
                String letra = String.valueOf((char) byteLeido);
                if (hashMap.containsKey(letra)) {
                    contador = hashMap.get(letra);
                    hashMap.put(letra, contador + 1);
                } else {
                    hashMap.put(letra, 1);
                }
            } while (byteLeido != -1);
            daoStream.cerrarLectura();
        } catch (IOException ex) {
            Logger.getLogger(CifrarFicheros.class.getName()).log(Level.SEVERE, null, ex);
        }
        return hashMap;
    }

    /**
     * Método para sacar la ruta del fichero que vamos a modificar cifrando o
     * descifrando sin la extensi´n
     *
     * @param file para sacar la ruta con el fichero son la extensión
     * @return la ruta y nombre del fichero.
     */
    public static String rutaFichero(File file) {
        String ruta = null;
        try {
            StringTokenizer st = new StringTokenizer(file.getCanonicalPath(), ".");
            ruta = st.nextToken();

        } catch (IOException ex) {
            Logger.getLogger(CifrarFicheros.class.getName()).log(Level.SEVERE, null, ex);
        }
        return ruta;
    }

}
