package Controlador;

import Hibernate.HibernateUtil;
import Modelo.Pais;
import Modelo.Presidente;
import org.hibernate.Session;

/**
 *
 * @author Gonzalo
 */
public class MainUnoAUnoPais {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
    Pais pais1 = new Pais();
    pais1.setNombre("China");
    pais1.setId(123);

    Pais pais2 = new Pais();  
    pais2.setNombre("Corea");
    pais2.setId(456);

    Presidente presidente1 = new Presidente();  
    presidente1.setNombre("Jiang Zemin");
    presidente1.setId(123);

    Presidente presidente2 = new Presidente();  
    presidente2.setNombre("Kim Dae-Jung");
    presidente2.setId(456);

    pais1.setPresidente(presidente1);  
    pais2.setPresidente(presidente2);   

    presidente1.setPais(pais1);  
    presidente2.setPais(pais2);   

    Session sesion = HibernateUtil.getSessionFactory().openSession();    

    /*Este pais se agrega para comprobar que los presidentes tomen el mismo  
    identificador que los paises*/
    Pais p = new Pais();  
    p.setNombre("Chipre");
    p.setId(789);
    
    /*En la primer sesion a la base de datos almacenamos los dos objetos Pais  
    los objetos Presidente se almacenaran en cascada*/  
    sesion.beginTransaction();   
    
    sesion.persist(p);  
    sesion.persist(pais1);  
    sesion.persist(pais2);   

    sesion.getTransaction().commit();  
    sesion.close();    

    /*En la segunda sesion eliminamos el objeto pais1,  
    el presidente1 sera borrado en cascada*/  

    sesion = HibernateUtil.getSessionFactory().openSession();  
    sesion.beginTransaction();   

    sesion.delete(pais1);   

    sesion.getTransaction().commit();  
    sesion.close(); 
    }
    
}
