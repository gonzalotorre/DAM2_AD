package Modelo;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.Map;
import javax.json.Json;
import javax.json.JsonArray;
import javax.json.JsonArrayBuilder;
import javax.json.JsonObject;
import javax.json.JsonReader;
import javax.json.JsonWriter;

/**
 * Importante añadir la libreria jaxb.json.
 *
 * @author Gonzalo
 */
public class MetodosDiccionario2JSON {

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
     *
     * @param jsonArray
     * @param rutaFichero
     * @throws IOException
     */
    public static void grabarFicheroJSON(JsonArray jsonArray, String rutaFichero) throws IOException {
        FileWriter fileWriter = new FileWriter(rutaFichero);
        JsonWriter jsonWriter = Json.createWriter(fileWriter);
        jsonWriter.writeArray(jsonArray);
        fileWriter.flush();
        fileWriter.close();
    }

    /**
     * Método que cuenta el número de sinónimos y antonimos de una determinada
     * palabra que se le pasa por parámetro.
     *
     * @param jsonObject general que se obtiene al leer el fichero JSON.
     * @param palabra de la cual se quiere saber el número de antónimos y
     * sinónimos que tiene.
     * @return el número de sinónimos y antónimos de dicha palabra.
     */
    public static int totalSinonimosAntonimos(JsonObject jsonObject, String palabra) {
        JsonObject diccionario2 = jsonObject.getJsonObject("diccionarioEspanol");
        JsonArray palabras = diccionario2.getJsonObject("palabras").getJsonArray("palabra");
        //JsonArray palabras = jsonObject.getJsonObject("diccionarioEspanol").getJsonObject("palabras").getJsonArray("palabra")
        int contador = 0;
        for (int i = 0; i < palabras.size(); i++) {
            if (palabras.getJsonObject(i).getString("grafia").equalsIgnoreCase(palabra)) {
                contador += palabras.getJsonObject(i).getJsonObject("sinonimos").getJsonArray("sinonimo").size();
                contador += palabras.getJsonObject(i).getJsonObject("antonimos").getJsonArray("antonimo").size();
            }
        }
        return contador;
    }

    /**
     * Método para saber el número de sinónimos con un porcentaje de similitud
     * mayor del 80% que contiene una palabra. Para ello usamos una lista
     * Map<String, Integer>.
     *
     * @param jsonObject general que se obtiene al leer el fichero JSON.
     * @return la lista con la palabra y el número de sinónimos que tienen un
     * porcentaje de similitud superior al 80%.
     */
    public static Map<String, Integer> porcentajeSimilitudMayorrOchenta(JsonObject jsonObject) {
        JsonObject diccionario2 = jsonObject.getJsonObject("diccionarioEspanol");
        JsonArray palabras = diccionario2.getJsonObject("palabras").getJsonArray("palabra");
        //También valdría: 
        //JsonArray palabras = jsonObject.getJsonObject("diccionarioEspanol").getJsonObject("palabras").getJsonArray("palabra");
        Map<String, Integer> listaPalabras = new HashMap<>();
        int totalSinonimos = 0;
        for (int i = 0; i < palabras.size(); i++) {
            for (int j = 0; j < palabras.getJsonObject(i).getJsonObject("sinonimos").getJsonArray("sinonimo").size(); j++) {
                if (palabras.getJsonObject(i).getJsonObject("sinonimos").getJsonArray("sinonimo").getJsonObject(j).getInt("porcentajeSimilitud") > 0.8) {
                    totalSinonimos++;
                }
            }
            if (listaPalabras.containsKey(palabras.getJsonObject(i).getString("grafia"))) {
                listaPalabras.replace(palabras.getJsonObject(i).getString("grafia"), totalSinonimos);
                totalSinonimos = 0;
            } else {
                listaPalabras.put(palabras.getJsonObject(i).getString("grafia"), totalSinonimos);
                totalSinonimos = 0;
            }
        }
        return listaPalabras;
    }

    /**
     * Método que crea un fichero JSON con las grafías de las palabras que hayam
     * sido añadidas hace menos de 3 días.
     *
     * @param jsonObject general que se obtiene al leer el fichero JSON.
     * @return un fichero JSON con las grafías de las palabras modificadas hace
     * 3 días.
     * @throws java.text.ParseException
     * @throws java.io.IOException
     */
    public static JsonArray generarFicheroJSON(JsonObject jsonObject) throws ParseException, IOException {
        JsonObject diccionario2 = jsonObject.getJsonObject("diccionarioEspanol");
        JsonArray palabras = diccionario2.getJsonObject("palabras").getJsonArray("palabra");
        //También valdría: 
        //JsonArray palabras = jsonObject.getJsonObject("diccionarioEspanol").getJsonObject("palabras").getJsonArray("palabra");
        SimpleDateFormat formateador = new SimpleDateFormat("yyyy-MM-dd");
        JsonArrayBuilder jsonArrayBuilder = Json.createArrayBuilder();
        for (int i = 0; i < palabras.size(); i++) {
            String fecha = palabras.getJsonObject(i).getString("fechaInsercion");
            GregorianCalendar fechaLimite = new GregorianCalendar();
            GregorianCalendar fechaInsercion = new GregorianCalendar();
            //Obtenemos la fecha actual para saber cuales de las palabras se introdujeron hace 3 días
            fechaLimite.setTime(new Date());
            //Restamos tres día a la fecha de hoy para ver cuales se han modificado
            fechaLimite.add(fechaLimite.DATE, -(3 + 1));
            //Formateamos la fecha obtenida del fichero JSON al formato yyyy-MM-dd
            fechaInsercion.setTime(formateador.parse(fecha));
            //comprobamos si la fecha de inserción es de hace tres días o no y grabamos.
            if (fechaInsercion.after(fechaLimite)) {
                //Grabamos el fichero JSON con las grafías.
                JsonObject grafias = Json.createObjectBuilder()
                        .add("grafiasFechas", Json.createArrayBuilder()
                                .add(Json.createObjectBuilder()
                                        .add("grafia", palabras.getJsonObject(i).getString("grafia"))
                                )
                        ).build();
                jsonArrayBuilder.add(grafias);
            }
        }
        JsonArray jsonArrayGrafias = jsonArrayBuilder.build();
        return jsonArrayGrafias;
    }

}
