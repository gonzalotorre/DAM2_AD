package Modelo;

import DAO.MetodosLecturaGrabacionCharts;
import java.io.File;
import java.io.IOException;
import java.util.StringTokenizer;

/**
 *
 * @author Gonzalo
 */
public class MetodosGrafica {

    public static File ficheroHTML() throws IOException {
        File ficheroGrabar = new File("C:\\Users\\gonza\\Downloads\\Clase\\ficheroGOOGhtml.html");
        File ficheroLeer = new File("C:\\Users\\gonza\\Downloads\\Clase\\Quijote\\GOOG.csv");
        MetodosLecturaGrabacionCharts lecturaGrabacion = new MetodosLecturaGrabacionCharts();
        StringTokenizer stokenizer;
        String linea;

        lecturaGrabacion.abrirLectura(ficheroLeer);
        lecturaGrabacion.abrirGrabacion(ficheroGrabar);

        String cadenaGrabar = "<!doctype html>\n"
                + "<html>\n"
                + "    <head>\n"
                + "        <title>Histograma Letras</title>\n"
                + "        <script src=\"https://cdnjs.cloudflare.com/ajax/libs/Chart.js/2.4.0/Chart.min.js\"></script>\n"
                + "        <style>\n"
                + "\n"
                + "        </style>\n"
                + "    </head>\n"
                + "    <body>\n"
                + "        <div id=\"container\" style=\"width: 100%;\">\n"
                + "            <canvas id=\"myChart\"></canvas>\n"
                + "        </div>    \n"
                + "        <script>\n"
                + "            var ctx = document.getElementById('myChart').getContext('2d');\n"
                + "            var chart = new Chart(ctx, {\n"
                + "                // The type of chart we want to create bar, line, \n"
                + "                type: 'line',\n"
                + "\n"
                + "                // The data for our dataset\n"
                + "                data: {\n"
                + "                    labels: [";
        lecturaGrabacion.grabarLinea(cadenaGrabar);

        lecturaGrabacion.leerLinea();
        while ((linea = lecturaGrabacion.leerLinea()) != null) {
            stokenizer = new StringTokenizer(linea, ",");
            for (int i = 0; i < 1; i++) {
                lecturaGrabacion.grabarLinea("\"" + stokenizer.nextToken() + "\"" + ", ");
            }
        }

        lecturaGrabacion.cerrarLectura();

        String cadenaGrabar2 = "],\n"
                + "                    datasets: [{\n"
                + "                            label: \"Histograma Letras\",\n"
                + "                            backgroundColor: 'rgb(255, 0, 132)',\n"
                + "                            borderColor: 'rgb(255, 99, 132)',\n"
                + "                            data: [";
        lecturaGrabacion.grabarLinea(cadenaGrabar2);

        lecturaGrabacion.abrirLectura(ficheroLeer);

        lecturaGrabacion.leerLinea();
        String token = null;
        while ((linea = lecturaGrabacion.leerLinea()) != null) {
            stokenizer = new StringTokenizer(linea, ",");
            for (int i = 0; i < 5; i++) {
                token = stokenizer.nextToken();
                
            }
            lecturaGrabacion.grabarLinea(token + ", ");
        }

        String cadenaGrabar3 = "],\n"
                + "                        }]\n"
                + "                },\n"
                + "\n"
                + "                // Configuration options go here\n"
                + "                options: {}\n"
                + "            });\n"
                + "        </script>\n"
                + "\n"
                + "    </body>\n"
                + "\n"
                + "</html>";
        lecturaGrabacion.grabarLinea(cadenaGrabar3);

        lecturaGrabacion.cerrarGrabacion();
        lecturaGrabacion.cerrarLectura();
        return ficheroGrabar;
    }

}
