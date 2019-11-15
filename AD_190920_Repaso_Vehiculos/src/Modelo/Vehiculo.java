package Modelo;

import java.util.Comparator;

/**
 *
 * @author Gonzalo
 */
public class Vehiculo implements Acelerable {

    //Atributos de la clase padre Vehículo.
    protected float velocidadMaxima;
    protected float velocidadMinima;
    protected float velocidadActual;
    protected String matricula;
    protected static int totalVehiculos;
    protected Posicion posicion;

    /**
     * Método acelerar sobreescrito que proviene de la interfaz acelerable.
     * @param incrementoVelocidad --> la velocidad que se quiere incrementar.
     * @return la velocidad incrementada.
     */
    @Override
    public float acelerar(float incrementoVelocidad) {
        if (velocidadActual + incrementoVelocidad > 150){
            return velocidadActual + incrementoVelocidad;
        } else{
            return velocidadActual = 150;
        }
    }

    /**
     * Método parar sobreescrito que proviene de la interfaz acelerable.
     * @param decrementoVelocidad --> la velocidad que se quiere decrementar.
     * @return la velocidad decremenrada.
     */
    @Override
    public float frenar(float decrementoVelocidad) {
        if (velocidadActual - decrementoVelocidad < 0){
            return velocidadActual - decrementoVelocidad;
        } else{
            return velocidadActual = 0;
        }
    }

    protected enum tipoCombustible {
        gasolina, diesel
    };

    //Constructor de la clase padre que se hereda en las clases hijas.
    public Vehiculo(float velocidadMaxima, float velocidadMinima, float velocidadActual, String matricula, Posicion posicion) {
        this.velocidadMaxima = velocidadMaxima;
        this.velocidadMinima = velocidadMinima;
        this.velocidadActual = velocidadActual;
        this.matricula = matricula;
        this.posicion = posicion;
    }

    //GETTERS Y SETTER
    public float getVelocidadMaxima() {
        return velocidadMaxima;
    }

    public void setVelocidadMaxima(float velocidadMaxima) {
        if (velocidadMaxima > 150) {
            this.velocidadActual = 150;
        } else {
            this.velocidadMaxima = velocidadMaxima;
        }
    }

    public float getVelocidadMinima() {
        return velocidadMinima;
    }

    public void setVelocidadMinima(float velocidadMinima) {
        if (velocidadMinima < 0) {
            this.velocidadActual = 0;
        } else {
            this.velocidadMinima = velocidadMinima;
        }
    }

    public float getVelocidadActual() {
        return velocidadActual;
    }

    public void setVelocidadActual(float velocidadActual) {
        if (velocidadActual > 150) {
            this.velocidadActual = 150;
        } else {
            this.velocidadActual = velocidadActual;
        }
    }

    public String getMatricula() {
        return matricula;
    }

    public void setMatricula(String matricula) {
        this.matricula = matricula;
    }

    public int getTotalVehiculos() {
        return totalVehiculos;
    }

    public void setTotalVehiculos(int totalVehiculos) {
        this.totalVehiculos = totalVehiculos;
    }

    public Posicion getPosicion() {
        return posicion;
    }

    public void setPosicion(Posicion posicion) {
        this.posicion = posicion;
    }

    //Método toString.
    @Override
    public String toString() {
        return "Vehiculo{" + "velocidadMaxima=" + velocidadMaxima + ", velocidadMinima=" + velocidadMinima + ", velocidadActual=" + velocidadActual + ", matricula=" + matricula + ", totalVehiculos=" + totalVehiculos + ", posicion=" + posicion + '}';
    }
    
    Comparator<Object> comparator = new Comparator<Object>() {
        @Override
        public int compare(Object o1, Object o2) {
            throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
        }
    };

}
