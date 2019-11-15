package Modelo;

/**
 *
 * @author Gonzalo
 */
public class Aeronave extends Vehiculo implements Volable, Repostable {

    private float alturaMaxima;
    private float alturaMinima;
    private float capacidadDeposito;
    private float litrosCombustible;

    public Aeronave(float alturaMaxima, float alturaMinima, float capacidadDeposito, float litrosCombustible, float velocidadMaxima, float velocidadMinima, float velocidadActual, String matricula, Posicion posicion) {
        super(velocidadMaxima, velocidadMinima, velocidadActual, matricula, posicion);
        this.alturaMaxima = alturaMaxima;
        this.alturaMinima = alturaMinima;
        this.capacidadDeposito = capacidadDeposito;
        this.litrosCombustible = litrosCombustible;
    }

    public Aeronave(float velocidadMaxima, float velocidadMinima, float velocidadActual, String matricula, Posicion posicion) {
        super(velocidadMaxima, velocidadMinima, velocidadActual, matricula, posicion);
    }

    /**
     *
     * @param ascenso
     * @return
     */
    @Override
    public float ascender(Posicion ascenso) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    /**
     *
     * @param descenso
     * @return
     */
    @Override
    public float descender(Posicion descenso) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public float repostar(float cantidad) throws MiExcepcionVehiculos {
        try {
            if ((cantidad + capacidadDeposito) > capacidadDeposito) {
                throw new MiExcepcionVehiculos.MiExcepcionRepostar();
            } else {
                capacidadDeposito = capacidadDeposito + cantidad;
            }
        } catch (Exception e) {
            System.out.println("Error. El deposito no puede superar los 200 litos. " + e.getMessage());
            capacidadDeposito = 200;
        }
        return capacidadDeposito;
    }

    public float getAlturaMaxima() {
        return alturaMaxima;
    }

    public void setAlturaMaxima(float alturaMaxima) {
        this.alturaMaxima = alturaMaxima;
    }

    public float getAlturaMinima() {
        return alturaMinima;
    }

    public void setAlturaMinima(float alturaMinima) {
        this.alturaMinima = alturaMinima;
    }

    public float getCapacidadDeposito() {
        return capacidadDeposito;
    }

    public void setCapacidadDeposito(float capacidadDeposito) {
        this.capacidadDeposito = capacidadDeposito;
    }

    public float getLitrosCombustible() {
        return litrosCombustible;
    }

    public void setLitrosCombustible(float litrosCombustible) {
        this.litrosCombustible = litrosCombustible;
    }

    @Override
    public String toString() {
        return "Aeronave{" + "alturaMaxima=" + alturaMaxima + ", alturaMinima=" + alturaMinima + ", capacidadDeposito=" + capacidadDeposito + ", litrosCombustible=" + litrosCombustible + '}';
    }

}
