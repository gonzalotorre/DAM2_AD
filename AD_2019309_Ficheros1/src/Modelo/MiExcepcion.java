package Modelo;

/**
 *
 * @author Gonzalo
 */
public class MiExcepcion {

    /**
     * Lanza una excepción cuando la carpeta está vacía.
     */
    public static class carpetaVacia extends Exception {

        public carpetaVacia(String message) {
            super("No hay direcctorios en" + message + " esta carpeta, está vacía.");
        }
    }

    /**
     * Lanza una excepción cuando la ruta no es un directorio.
     */
    public static class noEsUnDirecctorio extends Exception {

        public noEsUnDirecctorio(String message) {
            super(message + "No es un direcctorio.");
        }
    }

    /**
     *
     */
    public static class rutaNoexiste extends Exception {

        public rutaNoexiste() {
            super("La ruta introducida no existe, compruébala.");
        }
    }

    /**
     *
     */
    public static class directorioExistente extends Exception {

        public directorioExistente() {
            super("El directorio que intentas crear ya existe, introduce otro nombre.");
        }

    }

}
