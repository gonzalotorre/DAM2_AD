package Filtros;

import java.io.File;
import java.io.FileFilter;

/**
 *
 * @author Gonzalo
 */
public class FiltrarVideos implements FileFilter {

    String extension;

    public FiltrarVideos() {
    }
    
    public FiltrarVideos(String extension) {
        this.extension = extension;
    }

    @Override
    public boolean accept(File dir) {
        return dir.getName().endsWith(extension);
    }

}
