package Controlador;

import Modelo.DAOPinturas;
import Pinturas.Pintor;
import java.util.List;

/**
 *
 * @author Gonzalo
 */ 
public class MainPintores {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        DAOPinturas dao = new DAOPinturas();
        //Pintores que vamos a guardar en la base de datos
        Pintor p1 = new Pintor("235", "Jose Alberto", 1998, "neoliberal", null);
        Pintor p2 = new Pintor("098", "Luis Alberto", 1908, "liberal", null);
        dao.guardarPintor(p1);
        dao.guardarPintor(p2);
        //Modificar datos de un pintor
        p1.setEstilo("Ambiguo");
        dao.acrualizarPintor(p1);
        p2.setNombre("Juan Luis");
        dao.acrualizarPintor(p2);
        //Borrar pintores
        dao.borrarPintor(p1);
        dao.borrarPintor(p2);
        //BuscarPintor
        Pintor p = dao.buscarPintor("12345");
        System.out.println(p);
        List<Pintor> listaPintores = dao.listaPintores();
        System.out.println(listaPintores);
    }
    
}
