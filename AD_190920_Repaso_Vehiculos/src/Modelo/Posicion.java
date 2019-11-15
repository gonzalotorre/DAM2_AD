package Modelo;

import java.util.Comparator;

/**
 *
 * @author Gonzalo
 */
public class Posicion {
    
    private float posicionX;
    private float posicionY;
    private float posicionZ;

    public Posicion(float posicionX, float posicionY, float posicionZ) {
        this.posicionX = posicionX;
        this.posicionY = posicionY;
        this.posicionZ = posicionZ;
    }

    public float getPosicionX() {
        return posicionX;
    }

    public void setPosicionX(float posicionX) {
        this.posicionX = posicionX;
    }

    public float getPosicionY() {
        return posicionY;
    }

    public void setPosicionY(float posicionY) {
        this.posicionY = posicionY;
    }

    public float getPosicionZ() {
        return posicionZ;
    }

    public void setPosicionZ(float posicionZ) {
        this.posicionZ = posicionZ;
    }

    @Override
    public String toString() {
        return "Posicion{" + "posicionX=" + posicionX + ", posicionY=" + posicionY + ", posicionZ=" + posicionZ + '}';
    }

}
