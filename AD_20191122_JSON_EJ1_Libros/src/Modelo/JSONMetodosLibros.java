package Modelo;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import javax.json.Json;
import javax.json.JsonArray;
import javax.json.JsonObject;
import javax.json.JsonReader;
import javax.json.JsonWriter;

/**
 *
 * @author Gonzalo
 */
public class JSONMetodosLibros {

    /**
     * Crear un fichero como la salida del archivo libros.json
     *
     * @param rutaFichero
     * @throws java.io.IOException
     */
    public static void crearFichero(String rutaFichero) throws IOException {
        //Creamos el primer libro 
        JsonObject libro1 = Json.createObjectBuilder() //Para crear el objeto JSON libro1
                .add("titulo", "Sueños IA")
                .add("totalPaginas", 210)
                .add("precio", 10)
                .add("autores", Json.createArrayBuilder() //Un array con dos objetos Autor
                        .add(Json.createObjectBuilder() //Primer objeto autor
                                .add("nombre", "Javier")
                                .add("apellido", "Perez")
                        ).add(Json.createObjectBuilder() //Segundo objeto autor
                                .add("nombre", "María")
                                .add("apellido", "Rodriduez")
                        )
                ).add("generos", Json.createArrayBuilder() //Un array con dos objetos Genero
                        .add(Json.createObjectBuilder()
                                .add("genero", "novela") //Primer objeto genero
                        )
                        .add(Json.createObjectBuilder()
                                .add("genero", "ficción") //segundo objeto genero
                        )
                ).build();

        //Creamos el segundo olibro
        JsonObject libro2 = Json.createObjectBuilder() //Para crear el objeto JSON libro2
                .add("titulo", "Json para todos")
                .add("totalPaginas", 310)
                .add("precio", 20)
                .add("autores", Json.createArrayBuilder() //Un array con dos objetos Autor
                        .add(Json.createObjectBuilder() //Primer objeto autor
                                .add("nombre", "Ana")
                                .add("apellido", "Cota")
                        ).add(Json.createObjectBuilder() //Segundo objeto autor
                                .add("nombre", "Mar")
                                .add("apellido", "Fernández")
                        )
                ).add("generos", Json.createArrayBuilder() //Un array con dos objetos Genero
                        .add(Json.createObjectBuilder()
                                .add("genero", "informática") //Primer objeto genero
                        )
                        .add(Json.createObjectBuilder()
                                .add("genero", "JSON") //segundo objeto genero
                        )
                ).build();

        JsonArray arrayLibros = Json.createArrayBuilder()
                .add(libro1)
                .add(libro2).build();

        //Creamos o escribimos en el fichero .json
        //Creamos un objeto FileWriter para escribir el fihero.
        FileWriter escribirFichero = new FileWriter(rutaFichero);
        //Creamos 
        JsonWriter jsonWriter = Json.createWriter(escribirFichero);
        jsonWriter.writeArray(arrayLibros);
        escribirFichero.flush();
        escribirFichero.close();

    }

    /**
     * Método para leer un fichero JSON y crear un JsonArray con los datos.
     *
     * @param rutaFichero que queremos leer.
     * @return
     * @throws FileNotFoundException
     */
    public static JsonArray leerFichero(String rutaFichero) throws FileNotFoundException {
        FileReader leerFichero = new FileReader(rutaFichero);
        JsonReader jsonReader = Json.createReader(leerFichero);
        JsonArray jsonArray = jsonReader.readArray();
        return jsonArray;
    }

    /**
     * Leer el fichero JSON y contar el total de libros.
     *
     * @param rutaFichero
     * @return
     * @throws FileNotFoundException
     */
    public static int cuentaLibros(String rutaFichero) throws FileNotFoundException {
        JsonArray jsonArray = leerFichero(rutaFichero);
        return jsonArray.size();
    }

    /**
     * Leer el fichero JSON y mostrar todos los títulos de los libros.
     *
     * @param rutaFichero
     * @return
     * @throws FileNotFoundException
     */
    public static List<String> mostrarTitulos(String rutaFichero) throws FileNotFoundException {
        List<String> listaTitulos = new ArrayList<>();
        JsonArray jsonArrayTitulos = leerFichero(rutaFichero);

        for (int i = 0; i < jsonArrayTitulos.size(); i++) {
            String tirulos = jsonArrayTitulos.getJsonObject(i).getString("titulo");
            listaTitulos.add(tirulos);
        }
        return listaTitulos;
    }

    /**
     * Leer el fichero JSON y calcular el valor de los libros en stock
     *
     * @param rutaFichero
     * @param posicionLibro
     * @param posicionAutor
     * @return
     * @throws FileNotFoundException
     */
    public static String nombreAutor(String rutaFichero, int posicionLibro, int posicionAutor) throws FileNotFoundException {
        JsonArray jsonArray = leerFichero(rutaFichero);
        return jsonArray.getJsonObject(posicionLibro).getJsonArray("autores").getJsonObject(posicionAutor).getString("nombre");
    }

    /**
     *  Leer el fichero JSON y calcular el valor de los libros en stock
     * @param rutaFichero
     * @return
     * @throws FileNotFoundException 
     */
    public static int valorLbrosEnStock(String rutaFichero) throws FileNotFoundException {
        JsonArray jsonArray = leerFichero(rutaFichero);
        int precioLibro = 0;
        int precioTotal = 0;
        for (int i = 0; i < jsonArray.size(); i++) {
            precioLibro = jsonArray.getJsonObject(i).getInt("precio");
            precioTotal += precioLibro;
        }
        return precioTotal;
    }

}
