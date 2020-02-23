package Modelo;

import Pinturas.Pintor;

/**
 *
 * @author Gonzalo
 */
public interface DAO_Interface_Metodos {
    
    public Pintor buscarPintor(int id_pintor);
    public Pintor buscarPintorCuadro(int id_pintor);
    public void usuariosComentanCuadro(int id_cuadro);
    
    
}
