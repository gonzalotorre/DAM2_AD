package Controlador;

import Modelo.JSONMetodosLibros;
import java.io.IOException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Gonzalo
 */
public class MainLibros {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {

        try {
            //----------------------------------------------------------------//
            JSONMetodosLibros.crearFichero("FicheroJSON.json");
            //----------------------------------------------------------------//
            JSONMetodosLibros.leerFichero("FicheroJSON.json");
            //----------------------------------------------------------------//
            int totalLibros = JSONMetodosLibros.cuentaLibros("FicheroJSON.json");
            System.out.println("Total libros: " + totalLibros);
            //----------------------------------------------------------------//
            List<String> listaTitulos = JSONMetodosLibros.mostrarTitulos("FicheroJSON.json");
            for (String listaTitulo : listaTitulos) {
                System.out.println("Título --> " + listaTitulo);
            }
            //----------------------------------------------------------------//
            String nombre = JSONMetodosLibros.nombreAutor("FicheroJSON.json", 1, 0);
            System.out.println("Nombre del autor: " + nombre);
            //----------------------------------------------------------------//
            int precioTotal = JSONMetodosLibros.valorLbrosEnStock("FicheroJSON.json");
            System.out.println("Precio total libros en stock: " + precioTotal);
        } catch (IOException ex) {
            Logger.getLogger(MainLibros.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

}
