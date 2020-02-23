package Modelo;

import Hibernate.HibernateUtil;
import Pinturas.Pintor;
import org.hibernate.Query;
import org.hibernate.Session;

/**
 *
 * @author Gonzalo
 */
public class DAO_Pinturas implements DAO_Interface_Metodos {
    
    @Override
    public Pintor buscarPintor(int id_pintor) {
        String hql = "from Pintor where idPintor like " + String.valueOf(id_pintor);
        Session sesion = HibernateUtil.getSessionFactory().openSession();
        Query query = sesion.createQuery(hql);
        Pintor pintor = (Pintor) query.uniqueResult();
        return pintor;
    }

    @Override
    public Pintor buscarPintorCuadro(int id_pintor) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public void usuariosComentanCuadro(int id_cuadro) {
        throw new UnsupportedOperationException("Not supported yet."); 
    }
    
}
