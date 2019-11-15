package Modelo;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.RandomAccessFile;

/**
 *
 * @author Gonzalo
 */
public class NumerosPrimos {

    public static void numerosPrimos() throws FileNotFoundException, IOException {
        File ficheroGrabar = new File("C:\\Users\\gonza\\Downloads\\Clase\\CienNumPrimos.dat");
        RandomAccessFile ficheroRAF = new RandomAccessFile(ficheroGrabar, "rw");
        int numero = 2;
        int comprobadorPrimo = 0;
        int contadorCien = 0;
        int contadorSeek = 0;

        while (contadorCien <= 100) {
            for (int j = 1; j <= numero; j++) {
                if (numero % j == 0) {
                    comprobadorPrimo++;
                }
            }
            if (comprobadorPrimo == 2) {
                ficheroRAF.seek(contadorSeek);
                ficheroRAF.write(numero);
                contadorSeek++;
                contadorCien++;
            }
            comprobadorPrimo = 0;
            numero++;
        }
        ficheroRAF.close();
    }

    public static int leerFichero(int posicion) throws FileNotFoundException, IOException {
        RandomAccessFile ficheroRAF = new RandomAccessFile("C:\\Users\\gonza\\Downloads\\Clase\\CienNumPrimos.dat", "rw");
        int numero;
        ficheroRAF.seek(posicion - 1);
        numero = ficheroRAF.read();
        ficheroRAF.close();
        return numero;
    }

    public static File ficheroHTML() throws IOException {

        File fichero = new File("C:\\Users\\gonza\\Downloads\\Clase\\ficherohtml.html");
        FileWriter fw = new FileWriter(fichero);
        BufferedWriter bfw = new BufferedWriter(fw);

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
        bfw.write(cadenaGrabar);
        for (int i = 0; i < 100; i++) {
            bfw.write(i + ", ");
        }
        String cadenaGrabar2 = "],\n"
                + "                    datasets: [{\n"
                + "                            label: \"Histograma Letras\",\n"
                + "                            backgroundColor: 'rgb(255, 0, 132)',\n"
                + "                            borderColor: 'rgb(255, 99, 132)',\n"
                + "                            data: [";
        bfw.write(cadenaGrabar2);
        for (int i = 1; i < 100; i++) {
            bfw.write(leerFichero(i) + ", ");
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
        bfw.write(cadenaGrabar3);
        bfw.close();
        fw.close();
        return fichero;
    }

}
