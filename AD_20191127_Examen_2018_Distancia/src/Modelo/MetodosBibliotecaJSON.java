package Modelo;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.json.Json;
import javax.json.JsonArray;
import javax.json.JsonArrayBuilder;
import javax.json.JsonObject;
import javax.json.JsonReader;
import javax.json.JsonWriter;

/**
 *
 * @author Gonzalo
 */
public class MetodosBibliotecaJSON {

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
     *
     * @param jsonObject
     * @return
     */
    public static JsonArray generarNuevoJSON(JsonObject jsonObject) {
        //Obtengo el JsonObject general, en este caso es biblioteca.
        JsonObject biblioteca = jsonObject.getJsonObject("biblioteca");
        //Guardamos en un JsonArray la lista de libros leida del archivo json.
        JsonArray socios = biblioteca.getJsonObject("socios").getJsonArray("socio");
        //Creamos el arrayBuilder para poder grabar el JSON.
        JsonArrayBuilder jsonArrayBuilder = Json.createArrayBuilder();
        //Recorremos el jsonArray para luego poder grabar los datos en un nuevo fichero
        for (int i = 0; i < socios.size(); i++) {
            //JsonArrayBuilder para la lista de titulos.
            JsonArrayBuilder listaTitulosBuilder = Json.createArrayBuilder();
            //Obtener un JsonObect con los títulos de os libros prestados y cr
            for (int j = 0; j < socios.getJsonObject(i).getJsonObject("librosPrestados").getJsonArray("prestamo").size(); j++) {
                JsonObject titulo = Json.createObjectBuilder()
                        .add("titulo", socios.getJsonObject(i).getJsonObject("librosPrestados").getJsonArray("prestamo").getJsonObject(j).getString("titulo"))
                        .build();
                listaTitulosBuilder.add(titulo);
            }
            JsonArray listaTitulos = listaTitulosBuilder.build();
            String nombre = socios.getJsonObject(i).getString("nombreSocio");
            JsonArray apellidos = socios.getJsonObject(i).getJsonArray("apellidoSocio");
            
            /*JsonObject socio = Json.createObjectBuilder()
                    .add("nombre", nombre)
                    .add("apellido", apellidos)
                    .add("librosPrestados", Json.createArrayBuilder()
                            .add(Json.createObjectBuilder()
                                    .add("libros", listaTitulos)))
                    .build();
            jsonArrayBuilder.add(socio);*/
            
            JsonObject socio2 = Json.createObjectBuilder()
                    .add("nombre", nombre)
                    .add("apellido", apellidos)
                    .add("listaPrestamos", Json.createObjectBuilder()
                            .add("prestamo", listaTitulos))
                    .build();
            jsonArrayBuilder.add(socio2);
        }
        JsonArray listaSocios = jsonArrayBuilder.build();
        return listaSocios;
    }

    /**
     * 
     * @param jsonObject
     * @return 
     */
    public static Map<String, List<String>> mapaCodigoTitulos(JsonObject jsonObject) {
        Map<String, List<String>> listaTitulosPorSocio = new HashMap<>();
        //Obtengo el JsonObject general, en este caso es biblioteca.
        JsonObject biblioteca = jsonObject.getJsonObject("biblioteca");
        //Guardamos en un JsonArray la lista de libros leida del archivo json.
        JsonArray socios = biblioteca.getJsonObject("socios").getJsonArray("socio");
        //Recorremos el JsonArray de socios
        for (int i = 0; i < socios.size(); i++) {
            //Se crea un ArrayList<String> para los títulos de cada libro prestado.
            List<String> listaTitulos = new ArrayList();
            for (int j = 0; j < socios.getJsonObject(i).getJsonObject("librosPrestados").getJsonArray("prestamo").size(); j++) {
                String titulo = socios.getJsonObject(i).getJsonObject("librosPrestados").getJsonArray("prestamo").getJsonObject(j).getString("titulo");
                listaTitulos.add(titulo);
            }
            String nombre = socios.getJsonObject(i).getString("nombreSocio");
            listaTitulosPorSocio.put(nombre, listaTitulos);
        }
        return listaTitulosPorSocio;
    }

}
