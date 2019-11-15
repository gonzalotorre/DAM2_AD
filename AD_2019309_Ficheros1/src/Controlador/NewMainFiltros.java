/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controlador;

import Filtros.FiltrarDirecctoriosTamano;
import Filtros.FiltrarDirectorios;
import Filtros.FiltrarDirectoriosUltModifi;
import Filtros.FiltrarVideos;
import java.io.File;

/**
 *
 * @author Gonzalo
 */
public class NewMainFiltros {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        //FILTRAR IMAGENES
        File ficheroRuta = new File("C:\\Users\\gonza\\Pictures\\Camara");
        FiltrarVideos filtroI = new FiltrarVideos("JPG");
        File[] imagenesJPG = ficheroRuta.listFiles(filtroI);
        for (File file : imagenesJPG) {
            if (filtroI.accept(file)) {
                System.out.println(file);
            }
        }

        System.out.println("-------------------------------------------------");

        //FILTRAR VIDEOS
        File ficheroRutaVideos = new File("C:\\Users\\gonza\\Videos");
        FiltrarVideos filtroV = new FiltrarVideos("avi");
        File[] videosAVI = ficheroRutaVideos.listFiles(filtroV);
        for (File file : videosAVI) {
            if (filtroV.accept(file)) {
                System.out.println(file);
            }
        }

        System.out.println("-------------------------------------------------");

        //FILTRAR DIRECTORIOS
        File ficheroRutaDirectorios = new File("C:\\Users\\gonza\\Pictures\\");
        FiltrarDirectorios filtroDirecorios = new FiltrarDirectorios();
        File[] directorios = ficheroRutaDirectorios.listFiles(filtroDirecorios);
        for (File directorio : directorios) {
            if (filtroDirecorios.accept(directorio)) {
                System.out.println(directorio);
            }
        }

        System.out.println("-------------------------------------------------");

        //FILTRAR DIRECTORIOS POR TAMAÑO
        File ficheroRutaDirectoriosTamano = new File("C:\\Users\\gonza\\Pictures\\");
        FiltrarDirecctoriosTamano filtroDirecoriosTamano = new FiltrarDirecctoriosTamano(4096);
        File[] directoriosTamano = ficheroRutaDirectoriosTamano.listFiles(filtroDirecoriosTamano);
        for (File directorio : directoriosTamano) {
            if (filtroDirecoriosTamano.accept(directorio)) {
                System.out.println(directorio);
            }
        }

        System.out.println("-------------------------------------------------");

        //FILTRAR DIRECTORIOS POR TAMAÑO
        File ficheroRutaUltModifi = new File("C:\\Users\\gonza\\Downloads\\");
        FiltrarDirectoriosUltModifi ultimaModificacion = new FiltrarDirectoriosUltModifi(0);
        File[] ficherosModificados = ficheroRutaUltModifi.listFiles(ultimaModificacion);
        for (File ficherosModificado : ficherosModificados) {
            if (ultimaModificacion.accept(ficherosModificado)) {
                System.out.println(ficherosModificado);
            }
        }

    }

}
