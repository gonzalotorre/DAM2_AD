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
     * @param jsonObject
     * @param rutaFichero
     * @throws IOException
     */
    public static void grabarFicheroJSON(JsonObject jsonObject, String rutaFichero) throws IOException {
        FileWriter fileWriter = new FileWriter(rutaFichero);
        JsonWriter jsonWriter = Json.createWriter(fileWriter);
        jsonWriter.writeObject(jsonObject);
        fileWriter.flush();
        fileWriter.close();
    }

    public static JsonObject generarJSON(JsonObject jsonObject) {
        JsonArray listaLibros = jsonObject.getJsonObject("biblioteca").getJsonObject("libros").getJsonArray("libro");
        JsonArrayBuilder jsonLibros = Json.createArrayBuilder();
        for (int i = 0; i < listaLibros.size(); i++) {
            String isbn = listaLibros.getJsonObject(i).getString("ISBN");
            String titulo = listaLibros.getJsonObject(i).getString("titulo");
            String autor = listaLibros.getJsonObject(i).getString("titulo");
            int numeroPagias = listaLibros.getJsonObject(i).getInt("numeroPaginas");;
            String fechaPublicacion = listaLibros.getJsonObject(i).getString("fechaPublicacion");
            String prestadoSiNo = listaLibros.getJsonObject(i).getString("prestadoSiNo");
            String fechaDevolucion = listaLibros.getJsonObject(i).getString("fechaDevolucion");

            JsonObject libro = Json.createObjectBuilder()
                    .add("ISBN", isbn)
                    .add("titulo", titulo)
                    .add("autor", autor)
                    .add("numeroPagias", numeroPagias)
                    .add("fechaPublicacion", fechaPublicacion)
                    .add("prestadoSiNo", prestadoSiNo)
                    .add("fechaDevolucion", fechaDevolucion).build();
            jsonLibros.add(libro);
        }
        JsonArray libros = jsonLibros.build();
        JsonObject biblioteca2 = Json.createObjectBuilder()
                .add("biblioteca", Json.createObjectBuilder()
                        .add("libros", libros)
                ).build();
        return biblioteca2;
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
