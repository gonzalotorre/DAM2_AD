package Modelo;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Gonzalo
 */
public class Metodos {

    Connection conexion = null;
    MetodosConexion metodos = null;

    public Metodos() {
        metodos = new MetodosConexion();
        try {
            conexion = metodos.conexionBBDD();
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(Metodos.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException ex) {
            Logger.getLogger(Metodos.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     *
     * @param sentencia
     * @return
     * @throws SQLException
     */
    public ResultSet retornarPintores(String sentencia) throws SQLException {
        System.out.println(conexion);
        Statement stmtSQL = conexion.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_READ_ONLY);
        ResultSet resultado = stmtSQL.executeQuery(sentencia);
        return resultado;
    }

    /**
     * 
     * @param consulta
     * @return
     * @throws SQLException 
     */
    public List<Pintor> obtenerPintores(String consulta) throws SQLException {
        List<Pintor> pintores = new ArrayList();
        ResultSet resultadoConsulta = retornarPintores(consulta);
        Pintor pintor = null;
        if (resultadoConsulta != null) {
            try {
                while (resultadoConsulta.next()) {
                    String id_pintor = resultadoConsulta.getString("idPintor");
                    String nombre = resultadoConsulta.getString("nombre");
                    int anio = resultadoConsulta.getInt("anioNacimiento");
                    String estilo = resultadoConsulta.getString("estilo");
                    pintor = new Pintor(id_pintor, nombre, anio, estilo);
                    pintores.add(pintor);
                }
            } catch (SQLException ex) {
                System.out.println("Problemas con el CD, no existe. --> " + ex.getMessage());
            }
        } else {
            pintor = null;
            pintores.add(pintor);
        }
        return pintores;
    }

    public void insertarPintor() {

    }

    public void borrarPintor(String codigo) {
        String sql = "DELETE FROM pintor WHERE idPintor=?";
        //String sql = "DELETE FROM pintor WHERE nombre=?";
        //PreparedStatement pstmt = 
    }

}
