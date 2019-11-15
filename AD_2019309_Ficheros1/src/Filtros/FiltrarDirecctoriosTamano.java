package Filtros;

import java.io.File;
import java.io.FileFilter;

/**
 *
 * @author Gonzalo
 */
public class FiltrarDirecctoriosTamano implements FileFilter {

    private double tamanoMinimo;

    public FiltrarDirecctoriosTamano(double tamano) {
        this.tamanoMinimo = tamano;
    }

    @Override
    public boolean accept(File file) {
        return file.length() >= tamanoMinimo;
    }

}
