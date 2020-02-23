package Modelo;

import java.io.Serializable;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;

/**
 *
 * @author Gonzalo
 */
@Entity
public class Presidente implements Serializable {

    @Id
    //@GeneratedValue(strategy = GenerationType.IDENTITY)
    private int idPresidente;
    private String nombre;
    @OneToOne
    private Pais pais;

    public Presidente() {
    }

    public int getId() {
        return idPresidente;
    }

    public void setId(int idPresidente) {
        this.idPresidente = idPresidente;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public Pais getPais() {
        return pais;
    }

    public void setPais(Pais pais) {
        this.pais = pais;
    }
}
