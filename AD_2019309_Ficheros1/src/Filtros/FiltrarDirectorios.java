package Filtros;

import java.io.File;
import java.io.FileFilter;

/**
 *
 * @author Gonzalo
 */
public class FiltrarDirectorios implements FileFilter {

    public FiltrarDirectorios() {
    }

    @Override
    public boolean accept(File file) {
        return file.isDirectory();
    }

}
