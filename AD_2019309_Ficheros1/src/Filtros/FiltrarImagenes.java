package Filtros;

import java.io.File;
import java.io.FileFilter;

/**
 *
 * @author Gonzalo
 */
public class FiltrarImagenes implements FileFilter {

    String extension;

    public FiltrarImagenes() {
    }
    
    public FiltrarImagenes( String extension) {
        this.extension = extension;
    }

    @Override
    public boolean accept(File file) {
        return file.getName().endsWith(extension);
    }

}
