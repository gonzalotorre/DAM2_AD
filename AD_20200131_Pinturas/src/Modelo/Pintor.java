package Modelo;

/**
 *
 * @author Gonzalo
 */
public class Pintor {
    private String idPintor;
    private String nombre;
    private int anioNacimiento;
    private String estilo;

    public Pintor(String idPintor, String nombre, int anioNacimiento, String estilo) {
        this.idPintor = idPintor;
        this.nombre = nombre;
        this.anioNacimiento = anioNacimiento;
        this.estilo = estilo;
    }

    public String getIdPintor() {
        return idPintor;
    }

    public void setIdPintor(String idPintor) {
        this.idPintor = idPintor;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public int getAnioNacimiento() {
        return anioNacimiento;
    }

    public void setAnioNacimiento(int anioNacimiento) {
        this.anioNacimiento = anioNacimiento;
    }

    public String getEstilo() {
        return estilo;
    }

    public void setEstilo(String estilo) {
        this.estilo = estilo;
    }

    @Override
    public String toString() {
        return "Pintor{" + "idPintor=" + idPintor + ", nombre=" + nombre + ", anioNacimiento=" + anioNacimiento + ", estilo=" + estilo + '}';
    }
    
}
