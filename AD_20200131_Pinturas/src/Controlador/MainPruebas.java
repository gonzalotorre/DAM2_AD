package Controlador;

import Modelo.Metodos;
import Modelo.MetodosConexion;
import Modelo.Pintor;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Gonzalo
 */
public class MainPruebas {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        Metodos m = new Metodos();
        MetodosConexion mc = new MetodosConexion();        
        try {
            mc.conexionBBDD();
            String sentencia = "SELECT * FROM Pintor";
            List<Pintor> pintores = new ArrayList();
            //m.retornarPintores(sentencia);
            pintores = m.obtenerPintores(sentencia);
            System.out.println(pintores);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(MainPruebas.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException ex) {
            Logger.getLogger(MainPruebas.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
}
