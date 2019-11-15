package Modelo;

/**
 *
 * @author Gonzalo
 */
public class Coche extends Vehiculo implements Repostable {

    private String marca;
    private String modelo;
    private float capacidadDeposito;
    private float litrosCombustible;

    public Coche(String marca, String modelo, float capacidadDeposito, float litrosCombustible, float velocidadMaxima, float velocidadMinima, float velocidadActual, String matricula, Posicion posicion) {
        super(velocidadMaxima, velocidadMinima, velocidadActual, matricula, posicion);
        this.marca = marca;
        this.modelo = modelo;
        this.capacidadDeposito = capacidadDeposito;
        this.litrosCombustible = litrosCombustible;
    }

    public Coche(float velocidadMaxima, float velocidadMinima, float velocidadActual, String matricula, Posicion posicion) {
        super(velocidadMaxima, velocidadMinima, velocidadActual, matricula, posicion);
    }

    /**
     *
     * @param cantidad
     * @return
     * @throws MiExcepcionVehiculos
     */
    @Override
    public float repostar(float cantidad) throws MiExcepcionVehiculos {
        try {
            if ((cantidad + capacidadDeposito) > capacidadDeposito) {
                throw new MiExcepcionVehiculos.MiExcepcionRepostar();
            } else {
                capacidadDeposito = capacidadDeposito + cantidad;
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
            capacidadDeposito = 80;
        }
        return capacidadDeposito;
    }

    //GETTER Y SETTER.
    public String getMarca() {
        return marca;
    }

    public void setMarca(String marca) {
        this.marca = marca;
    }

    public String getModelo() {
        return modelo;
    }

    public void setModelo(String modelo) {
        this.modelo = modelo;
    }

    public float getCapacidadDeposito() {
        return capacidadDeposito;
    }

    public void setCapacidadDeposito(float capacidadDeposito) {
        if (capacidadDeposito > this.capacidadDeposito) {
            this.capacidadDeposito = 80;
        } else {
            this.capacidadDeposito = capacidadDeposito;
        }
    }

    public float getLitrosCombustible() {
        return litrosCombustible;
    }

    public void setLitrosCombustible(float litrosCombustible) {
        this.litrosCombustible = litrosCombustible;
    }

    /**
     * Método toString
     * @return 
     */
    @Override
    public String toString() {
        return "Coche{" + "marca=" + marca + ", modelo=" + modelo + ", capacidadDeposito=" + capacidadDeposito + ", litrosCombustible=" + litrosCombustible + '}';
    }

}
