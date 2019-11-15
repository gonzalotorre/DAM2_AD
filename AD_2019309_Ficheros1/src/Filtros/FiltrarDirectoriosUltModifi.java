package Filtros;

import java.io.File;
import java.io.FileFilter;
import java.util.Calendar;

/**
 *
 * @author Gonzalo
 */
public class FiltrarDirectoriosUltModifi implements FileFilter {

    private int segundosUltimaModificacion;

    public FiltrarDirectoriosUltModifi(int segundosUltimaModificacion) {
        this.segundosUltimaModificacion = segundosUltimaModificacion;
    }

    @Override
    public boolean accept(File file) {
        Calendar fecha = Calendar.getInstance();
        //Le restamo los milisegundos introducidos
        fecha.add(Calendar.DAY_OF_YEAR, -segundosUltimaModificacion);
        //Sacamos los milisegundos que lleva modificado el fichero.
        long milisegundos = file.lastModified();
    return fecha.getTimeInMillis() < milisegundos;
    }

}
