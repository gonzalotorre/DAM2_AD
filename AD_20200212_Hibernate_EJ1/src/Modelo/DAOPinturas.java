package Modelo;

import Hibernate.HibernateUtil;
import Pinturas.Pintor;
import java.util.List;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;

/**
 *
 * @author Gonzalo
 */
public class DAOPinturas {
    
    //Parte 1: Persistiendo Objetos Simples usando Mapeos en XML
    private Session sesion;
    private Transaction transaction;

    public void iniciarOperacion() throws HibernateException {
        sesion = HibernateUtil.getSessionFactory().openSession();
        transaction = sesion.beginTransaction();
    }

    public void manejarExcepcion(HibernateException exception) throws HibernateException {
        transaction.rollback();
        throw new HibernateException("Ocurrió un error en la capa de acceso a datos", exception);
    }

    /**
     *
     * @param pintor
     * @return
     */
    public int guardarPintor(Pintor pintor) {
        int id = 0;
        try {
            iniciarOperacion();
            id = (int) sesion.save(pintor);
            transaction.commit();
        } catch (HibernateException exception) {
            manejarExcepcion(exception);
            throw exception;
        } finally {
            sesion.close();
        }
        return id;
    }
    
    public void acrualizarPintor(Pintor pintor){
        try{
            iniciarOperacion();
            sesion.update(pintor);
            transaction.commit();
        } catch (HibernateException exception) {
            manejarExcepcion(exception);
            throw exception;
        } finally {
            sesion.close();
        }
    }
    
    public void borrarPintor(Pintor pintor){
        try{
            iniciarOperacion();
            sesion.delete(pintor);
            transaction.commit();
        } catch (HibernateException exception) {
            manejarExcepcion(exception);
            throw exception;
        } finally {
            sesion.close();
        }
    }
    
    public Pintor buscarPintor(String idPintor){
        Pintor pintor = null;
        try{
            iniciarOperacion();
            pintor = (Pintor) sesion.get(Pintor.class, idPintor);
        } finally {
            sesion.close();
        }
        return pintor;
    }
    
    public List<Pintor> listaPintores(){
        List<Pintor> listaPintores = null;
        try{
            iniciarOperacion();
            listaPintores = sesion.createQuery("from Pintor").list();
        }finally {
            sesion.close();
        }
        return listaPintores;
    }
    
    
}
