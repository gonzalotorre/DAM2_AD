package DAO;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

/**
 *
 * @author Gonzalo
 */
public class MetodosLecturaGrabacionCharts {

    private FileWriter fw;
    private BufferedWriter bfw;
    private FileReader fr;
    private BufferedReader bfr;

    public void abrirLectura(File fichero) throws FileNotFoundException{
        fr = new FileReader(fichero);
        bfr = new BufferedReader(fr);
    }
    
    public String leerLinea() throws IOException{
        String lineaLeida = bfr.readLine();
        return lineaLeida;
    }
    
    public void cerrarLectura() throws IOException{
        bfr.close();
        fr.close();
    }
    
    
    public void abrirGrabacion(File fichero) throws IOException{
        fw = new FileWriter(fichero);
        bfw = new BufferedWriter(fw);
    }
    
    public void grabarLinea(String linea) throws IOException{
        bfw.write(linea);
    }
    
    public void cerrarGrabacion() throws IOException{
        bfw.close();
        fw.close();
    }
    
}
