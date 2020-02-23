package Controlador;

import Hibernate.HibernateUtil;
import Modelo.Libro;
import Modelo.Persona;
import org.hibernate.Session;

/**
 *
 * @author Gonzalo
 */
public class MainUnoAMuchosBi {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        /*Primero creamos una persona y la asociamos con dos libros*/
        Libro libro1 = new Libro();
        libro1.setIdLibro(12);
        libro1.setTitulo("20000 leguas de viaje submarino");
        
        Libro libro2 = new Libro();
        libro2.setIdLibro(13);
        libro2.setTitulo("La maquina del tiempo");
        
        Persona persona1 = new Persona();
        persona1.setIdPersona(12);
        persona1.setNombre("Persona que se eliminara");
        
        persona1.addLibro(libro1);
        persona1.addLibro(libro2);
        libro1.setPersona(persona1);
        libro2.setPersona(persona1);

        /*Creamos una segunda persona, que sera eliminada, y la asociamos con otros dos libros*/
        Libro libro3 = new Libro();
        libro3.setIdLibro(14);
        libro3.setTitulo("Don Quijote de la Mancha");
        
        Libro libro4 = new Libro();
        libro4.setIdLibro(15);
        libro4.setTitulo("La Galatea");
        
        Persona persona2 = new Persona();
        persona2.setIdPersona(13);
        persona2.setNombre("Alex");
        
        persona2.addLibro(libro3);
        persona2.addLibro(libro4);
        libro3.setPersona(persona2);
        libro4.setPersona(persona2);

        /*En la primer sesion guardamos las dos personas (los libros correspondientes seran guardados en cascada*/
        Session sesion = HibernateUtil.getSessionFactory().openSession();
        sesion.beginTransaction();
        
        sesion.persist(persona1);
        sesion.persist(persona2);
        
        sesion.getTransaction().commit();
        sesion.close();

        /*En la segunda sesion eliminamos la persona1 (los dos primeros libros seran borrados en cascada)*/
        sesion = HibernateUtil.getSessionFactory().openSession();
        sesion.beginTransaction();
        
        sesion.delete(persona1);
        
        sesion.getTransaction().commit();
        sesion.close();
    }
    
}
