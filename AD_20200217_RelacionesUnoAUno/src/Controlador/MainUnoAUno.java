package Controlador;

import Modelo.Direccion;
import Modelo.Persona;
import org.hibernate.Session;
import Hibernate.HibernateUtil;

/**
 *
 * @author Gonzalo
 */
public class MainUnoAUno {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        Persona persona1 = new Persona();
        persona1.setIdPersona(1234);
        persona1.setNombre("Personaborrada");

        Persona persona2 = new Persona();
        persona2.setIdPersona(5678);
        persona2.setNombre("Personapermanece");

        Direccion direccion1 = new Direccion();
        direccion1.setIdDireccion(876);
        direccion1.setCalle("Calle 1");
        direccion1.setCodigoPostal(12345);

        Direccion direccion2 = new Direccion();
        direccion2.setIdDireccion(543);
        direccion2.setCalle("Calle 2");
        direccion2.setCodigoPostal(54321);

        persona1.setDireccion(direccion1);
        persona2.setDireccion(direccion2);

        Session sesion = HibernateUtil.getSessionFactory().openSession();

        /*Esta direccion se agrega para comprobar que las personas tomen el mismo 
    identificador que las direcciones*/
        Direccion d = new Direccion();
        d.setCalle("Calle de Prueba de identificadores");
        d.setCodigoPostal(21345);

        /*En la primer sesion a la base de datos almacenamos los dos objetos Persona 
    los objetos Direccion se almacenaran en cascada*/
        sesion.beginTransaction();

        sesion.persist(d);
        sesion.persist(persona1);
        sesion.persist(persona2);

        sesion.getTransaction().commit();
        sesion.close();

        /*En la segunda sesion eliminamos el objeto persona1, 
    la direccion1 sera borrada en cascada*/
        sesion = HibernateUtil.getSessionFactory().openSession();
        sesion.beginTransaction();

        sesion.delete(persona1);

        sesion.getTransaction().commit();
        sesion.close();
    }

}
