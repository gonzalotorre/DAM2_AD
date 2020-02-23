package Modelo;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/**
 *
 * @author Gonzalo
 */
public class MetodosConexion {

    Connection conexion;

    public Connection conexionBBDD() throws ClassNotFoundException, SQLException {
        Class.forName("com.mysql.jdbc.Driver");
        conexion = DriverManager.getConnection("jdbc:mysql://localhost:32777/Pinturas", "root", "alumno");
        return conexion;
    }

    public ResultSet crearResultSet() {
        return null;
    }

    public void cerrarConexion() throws SQLException {
        if (conexion != null) {
            conexion.close();
        }
    }

    //cierra una conexionBBDD de la BBDD para PreparedStatement
    public void cerrarPreparedStatement(PreparedStatement pstmtSQL) {
        try {
            if (pstmtSQL != null) {
                pstmtSQL.close();
            }
        } catch (SQLException ex) {
            //Logger.getLogger(MetodosGestionBBDD.class.getName()).log(Level.SEVERE, null, ex);
            System.out.println("Error al cerrar la preparedStatemen.");
        }
    }
    
    //cierra una conexionBBDD de la BBDD para Statement
    public void cerrarStatement(Statement stmtSQL) {
        try {
            if (stmtSQL != null) {
                stmtSQL.close();
            }
        } catch (SQLException ex) {
            //Logger.getLogger(MetodosGestionBBDD.class.getName()).log(Level.SEVERE, null, ex);
            System.out.println("Error al cerrar la Statemen.");
        }
    }
    
    //metodo para ejecutar una Statement SELECT
    public ResultSet ejecutarStatementSELECT(String sql, int tipoResultado, int tipoActualizacion) {
        Statement stmtSQL;
        ResultSet juegoResultados = null;
        try {
            // Para crear un Statement
            if (conexion == null) {
                System.out.println("conexion null");
            } else {
                stmtSQL = conexion.createStatement(tipoResultado, tipoActualizacion);
                juegoResultados = stmtSQL.executeQuery(sql);
            }
        } catch (SQLException ex) {
            System.out.println("Error en la consulta Statement, comprobar la SQL.");
        }
        return juegoResultados;
    }
    
    //metodo para ejecutar una Statement UPDATE, INSERT, UPDATE
    public int ejecutarStatementNOSELECT(String sql, int tipoResultado, int tipoActualizacion) {
        int numFilasResultadoConsulta = 0;
        Statement stmtSQL;

        try {
            // Para crear un Statement
            stmtSQL = conexion.createStatement(tipoResultado, tipoActualizacion);
            numFilasResultadoConsulta = stmtSQL.executeUpdate(sql);
        } catch (SQLException ex) {
            System.out.println("Error en la consulta Statement, comprobar la SQL.");
        }
        return numFilasResultadoConsulta;
    }

    //metodo para crear una preparedStatement, que se completará en el DAO correspondiente 
    //usa para ello el sql genérico de la sentencia dinámica que recibe como parámetro
    public PreparedStatement crearPreparedStatement(String sql, int tipoResultado, int tipoActualizacion) {
        PreparedStatement pstmtSQL = null;
        try {
            // Para crear un PreparedStatement para ejecutar posteriormente
            if (conexion == null) {
                System.out.println("conexion null");
            } else {
                pstmtSQL = conexion.prepareStatement(sql, tipoResultado, tipoActualizacion);

            }
        } catch (SQLException ex) {
            System.out.println("Error en la consulta Statement, comprobar la SQL.");
        }
        //si tiene exito devolvemos la pstmt precompilada, para completar los ??
        return pstmtSQL;
    }

    //metodo para ejecutar una PreparedStatement SELECT
    //ejecutamos la preparedStatement completada en el DAO correspondiente
    public ResultSet ejecutarPreparedStatementSELECT(PreparedStatement pstmtSQL) {
        ResultSet resultadoConsulta = null;
        try {
            resultadoConsulta = pstmtSQL.executeQuery();
        } catch (SQLException ex) {
            System.out.println("Error en la consulta Statement, comprobar la SQL.");
        }
        return resultadoConsulta;
    }

    //metodo para ejecutar una PreparedStatement UPDATE, INSERT, UPDATE
    //ejecutamos la preparedStatement completada en el DAO correspondiente
    public int ejecutarPreparedStatementNOSELECT(PreparedStatement pstmtSQL) {
        int numFilasAfectadasSQL = 0;
        try {
            numFilasAfectadasSQL = pstmtSQL.executeUpdate();
        } catch (SQLException ex) {
            System.out.println("Error en la consulta Statement, comprobar la SQL.");
        }
        return numFilasAfectadasSQL;
    }
    
}
