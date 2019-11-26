package Modelo;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.json.Json;
import javax.json.JsonArray;
import javax.json.JsonObject;
import javax.json.JsonReader;

/**
 * Importante añadir la libreria javax.json para poder trabajar con JSON.
 *
 * @author Gonzalo
 */
public class MetodosAlbumFotosJSON {

    /**
     * Método para leer un fichero JSON, en este caso devuelve un JsonObject,
     * pero puede devolver un JsonArray.
     *
     * @param rutaFichero Ruta donde se encuentra el fichero Json
     * @return el objeto Json con los datos del fichero leido.
     * @throws FileNotFoundException
     */
    public static JsonObject leerFichero(String rutaFichero) throws FileNotFoundException {
        FileReader leerFichero = new FileReader(rutaFichero);
        JsonReader jsonReader = Json.createReader(leerFichero);
        JsonObject jsonObject = jsonReader.readObject();
        return jsonObject;
    }

    /**
     * Método que cuenta el total de fotos que tienen una determinada ISO que se
     * pasa por parámetros, para ello obtenemos el JsonObject leido y sacamos el
     * JsonArray albumFotos para acceder a la lista de fotos.
     *
     * @param listaAlbum JsonObject general que se obtiene al leer el fichero
     * JSON.
     * @param iso para saber el numero de fotos con esa ISO.
     * @return el numero de fotos con la misma ISO introducida por parámetro.
     */
    public static int totalFotosISO(JsonObject listaAlbum, String iso) {
        JsonObject albumesFotos = listaAlbum.getJsonObject("albumesFotos");
        JsonArray albumFotos = albumesFotos.getJsonArray("albumFotos");
        int contadorFotos = 0;
        for (int i = 0; i < albumFotos.size(); i++) {
            for (int j = 0; j < albumFotos.getJsonObject(i).getJsonArray("foto").size(); j++) {
                contadorFotos++;
            }
        }
        return contadorFotos;
    }

    /**
     * Método para obtener el número de fotos que hay en un album a través del
     * del título del album.
     *
     * @param listaAlbum JsonObject general que se obtiene al leer el fichero
     * JSON;
     * @return un Map con el título del album y el número de fotos que hay en
     * dicho album.
     */
    public static Map<String, Integer> totalFotosAlbum(JsonObject listaAlbum) {
        Map<String, Integer> listaFotos = new HashMap<>();
        JsonObject albumesFotos = listaAlbum.getJsonObject("albumesFotos");
        JsonArray albumFotos = albumesFotos.getJsonArray("albumFotos");
        for (int i = 0; i < albumFotos.size(); i++) {
            if (listaFotos.containsKey(albumFotos.getJsonObject(i).getString("titulo"))) {
                listaFotos.replace(albumFotos.getJsonObject(i).getString("titulo"), albumFotos.getJsonObject(i).getJsonArray("foto").size());
            } else {
                listaFotos.put(albumFotos.getJsonObject(i).getString("titulo"), albumFotos.getJsonObject(i).getJsonArray("foto").size());
            }
        }
        return listaFotos;
    }

    /**
     * Método que devuelve una lista con los títulos de las fotos posteriores a
     * 2000
     *
     * @param listaAlbum JsonObject general que se obtiene al leer el fichero
     * JSON
     * @return una lista con los títulos de las fotos hechas antes del 200
     * @throws java.text.ParseException
     */
    public static List<String> titulos(JsonObject listaAlbum) throws ParseException {
        JsonObject albumesFotos = listaAlbum.getJsonObject("albumesFotos");
        JsonArray albumFotos = albumesFotos.getJsonArray("albumFotos");
        List<String> listaFechas = new ArrayList<>();
        SimpleDateFormat formateador = new SimpleDateFormat("yyyy-MM-dd");
        for (int i = 0; i < albumFotos.size(); i++) {
            for (int j = 0; j < albumFotos.getJsonObject(i).getJsonArray("foto").size(); j++) {
                Date fecha = formateador.parse(albumFotos.getJsonObject(i).getJsonArray("foto").getJsonObject(j).getString("fechaCaptura"));
                if (fecha.getYear() <= 2000) {
                    listaFechas.add(albumFotos.getJsonObject(i).getString("titulo"));
                }
            }
        }
        return listaFechas;
    }

}
