package Modelo;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import javax.json.Json;
import javax.json.JsonArray;
import javax.json.JsonObject;
import javax.json.JsonReader;
import javax.json.JsonWriter;

/**
 *
 * @author Gonzalo
 */
public class JSONMetodosWebService {

    /**
     *
     * @param rutaFichero
     * @throws IOException
     */
    public static void crearFicheroJSON(String rutaFichero) throws IOException {
        JsonObject webService = Json.createObjectBuilder()
                .add("results", Json.createArrayBuilder()
                        .add(Json.createObjectBuilder()
                                .add("elevation", 1608.637939453125)
                                .add("location", Json.createArrayBuilder()
                                        .add(Json.createObjectBuilder()
                                                .add("lat", 39.73915360)
                                                .add("lng", -104.98470340)))
                                .add("resolution", 4.771975994110107)))
                .add("status", "OK").build();
        
        JsonObject webServiceError = Json.createObjectBuilder()
                .add("error_message", "Invalidrequest. Invalid 'locations' parameter.")
                .add("results", Json.createArrayBuilder()
                        .add(Json.createObjectBuilder()))
                .add("status", "REQUEST_DENIED").build();

        JsonArray jsonArray = Json.createArrayBuilder()
                .add(webService)
                .add(webServiceError).build();

        FileWriter fileWriter = new FileWriter(rutaFichero);
        JsonWriter jsonWriter = Json.createWriter(fileWriter);
        jsonWriter.writeArray(jsonArray);
        fileWriter.flush();
        fileWriter.close();
    }

    /**
     * 
     * @param rutaFichero
     * @return
     * @throws FileNotFoundException 
     */
    public static JsonArray leerFichero(String rutaFichero) throws FileNotFoundException{
        FileReader leerFichero = new FileReader(rutaFichero);
        JsonReader jsonReader = Json.createReader(leerFichero);
        JsonArray jsonArray = jsonReader.readArray();
        return jsonArray;
    }
    
    /**
     * 
     * @param rutaFichero
     * @return 
     * @throws java.io.FileNotFoundException 
     */
    public static double mostrarElevacion(String rutaFichero) throws FileNotFoundException{
        JsonArray jsonArray = leerFichero(rutaFichero);
        double elevation = 0;
        for(int i = 0; i < jsonArray.size(); i++){
            String status = jsonArray.getJsonObject(i).getString("status");
            if(status.equalsIgnoreCase("OK")){
                for (int j = 0; j < jsonArray.getJsonObject(i).getJsonArray("results").size(); j++) {
                    elevation = jsonArray.getJsonObject(i).getJsonArray("results").getJsonObject(j).getInt("elevation");
                }                
            }            
        }
        return elevation;
    }
    
    public static double mostrarResolucion(String rutaFichero) throws FileNotFoundException{
        JsonArray jsonArray = leerFichero(rutaFichero);
        double elevation = 0;
        for(int i = 0; i < jsonArray.size(); i++){
            String status = jsonArray.getJsonObject(i).getString("status");
            if(status.equalsIgnoreCase("OK")){
                for (int j = 0; j < jsonArray.getJsonObject(i).getJsonArray("results").size(); j++) {
                    elevation = jsonArray.getJsonObject(i).getJsonArray("results").getJsonObject(j).getInt("resolution");
                }                
            }            
        }
        return elevation;
    }
    
}
