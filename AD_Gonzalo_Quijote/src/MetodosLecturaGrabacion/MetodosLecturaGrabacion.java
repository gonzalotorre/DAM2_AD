package MetodosLecturaGrabacion;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Gonzalo
 */
public class MetodosLecturaGrabacion {

    private File fichero;
    private FileReader fLectura;
    private BufferedReader bfLectura;
    private FileWriter fEscritura;
    private BufferedWriter bfEscritura;

    public MetodosLecturaGrabacion(File fichero) {
        this.fichero = fichero;
    }

    /**
     *
     * @throws java.io.FileNotFoundException
     */
    public void abrirParaLeer() throws FileNotFoundException {
        fLectura = new FileReader(fichero);
        bfLectura = new BufferedReader(fLectura);

    }

    /**
     *
     * @return
     * @throws java.io.IOException
     */
    public String leer() throws IOException {
        String lineaLeida = null;
        lineaLeida = bfLectura.readLine();
        return lineaLeida;
    }

    /**
     *
     * @throws java.io.IOException
     */
    public void cerrarLectura() throws IOException {
        bfLectura.close();
        fLectura.close();
    }

    /**
     *
     * @param grabar
     */
    public void abrirParaGrabar(File grabar) {
        try {
            fEscritura = new FileWriter(grabar);
            bfEscritura = new BufferedWriter(fEscritura);
        } catch (IOException ex) {
            Logger.getLogger(MetodosLecturaGrabacion.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     *
     * @param linea
     */
    public void grabar(String linea) {
        try {
            bfEscritura.write(linea);
        } catch (IOException ex) {
            Logger.getLogger(MetodosLecturaGrabacion.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     *
     */
    public void cerrarGrabacion() {
        try {
            bfEscritura.close();
            fLectura.close();
        } catch (IOException ex) {
            Logger.getLogger(MetodosLecturaGrabacion.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

}
