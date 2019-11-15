package DAO;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 *
 * @author Gonzalo
 */
public class DAO_File {

    private File file = null;
    private FileInputStream fis = null;
    private FileOutputStream fos = null;

    /**
     * Constructor
     * @param file
     */
    public DAO_File(File file) {
        this.file = file;
    }

    /**
     * Abrir lectura
     *
     * @throws FileNotFoundException
     */
    public void abrirParaLeer() throws FileNotFoundException {
        fis = new FileInputStream(file);
    }

    /**
     * Leer un caracter del archivo.
     *
     * @return @throws IOException
     */
    public int leer() throws IOException {
        int caracter = fis.read();
        return caracter;
    }

    /**
     * Cerrar lectura
     *
     * @throws IOException
     */
    public void cerrarLectura() throws IOException {
        fis.close();
    }

    /**
     * Para escribir al final de un fichero existente.
     *
     * @throws FileNotFoundException
     */
    public void abrirParaGrabar() throws FileNotFoundException {
        fos = new FileOutputStream(file, true);
    }

    /**
     * Abrir para grabar sobreescribiendo lo que ya estaba en el fichero
     *
     * @throws FileNotFoundException
     */
    public void abrirParaGrabarSobreescribir() throws FileNotFoundException {
        fos = new FileOutputStream(file);
    }

    /**
     * Grabar un entero en el archivo
     *
     * @param enteroAGrabar
     * @throws IOException
     */
    public void grabarRegistro(int enteroAGrabar) throws IOException {
        fos.write(enteroAGrabar);
    }

    /**
     * Cerrar grabación
     *
     * @throws IOException
     */
    public void cerrarGrabar() throws IOException {
        fos.close();
    }

}
