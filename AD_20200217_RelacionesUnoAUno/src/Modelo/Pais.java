package Modelo;

import java.io.Serializable;
import javax.persistence.CascadeType;
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
public class Pais implements Serializable {

    @Id
    //@GeneratedValue(strategy = GenerationType.IDENTITY)
    private int idPais;
    private String nombre;
    @OneToOne(cascade = {CascadeType.PERSIST, CascadeType.REMOVE})
    private Presidente presidente;

    public Pais() {
    }

    public int getId() {
        return idPais;
    }

    public void setId(int id) {
        this.idPais = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public Presidente getPresidente() {
        return presidente;
    }

    public void setPresidente(Presidente presidente) {
        this.presidente = presidente;
    }
}
