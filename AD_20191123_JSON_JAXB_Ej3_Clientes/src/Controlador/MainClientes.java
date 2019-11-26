package Controlador;

import Modelo.JSONMetodosCliente;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.json.JsonArray;

/**
 *
 * @author Gonzalo
 */
public class MainClientes {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {

        try {
            JsonArray listaDirecciones = JSONMetodosCliente.crearFicheroDireccion();
            JsonArray listaCliente = JSONMetodosCliente.crearFicheroCliente(listaDirecciones);
            JsonArray clientesCompleto = JSONMetodosCliente.crearFicheroClientes(listaCliente);
            JSONMetodosCliente.escibirFicheroJSON("ficheroJSON.json", clientesCompleto);
        } catch (IOException ex) {
            Logger.getLogger(MainClientes.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

}
