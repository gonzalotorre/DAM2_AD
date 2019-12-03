package Modelo;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.json.Json;
import javax.json.JsonArray;
import javax.json.JsonObject;
import javax.json.JsonReader;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.datatype.DatatypeConfigurationException;
import javax.xml.datatype.DatatypeFactory;
import javax.xml.datatype.XMLGregorianCalendar;
import jaxb.diccionario.DiccionarioEspanol;
import jaxb.diccionario.ObjectFactory;
import jaxb.diccionario.PalabraType;
import jaxb.diccionario.SinonimoType;
import jaxb.diccionario.TraduccionType;

/**
 *
 * @author Gonzalo
 */
public class MetodosDiccionarioJSON {

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

    public static int totalDefiniciones(JsonObject jsonObject, String grafia) {
        int contador = 0;
        JsonArray listaPalabras = jsonObject.getJsonObject("diccionarioEspanol").getJsonArray("palabra");
        for (int i = 0; i < listaPalabras.size(); i++) {
            if (listaPalabras.getJsonObject(i).getString("grafia").equalsIgnoreCase(grafia)) {
                contador = listaPalabras.getJsonObject(i).getJsonArray("definicion").size();
            }
        }
        return contador;
    }

    public static Map<String, Integer> numTraducciones(JsonObject jsonObject) {
        Map<String, Integer> listaTraducciones = new HashMap<>();
        JsonArray listaPalabras = jsonObject.getJsonObject("diccionarioEspanol").getJsonArray("palabra");
        for (int i = 0; i < listaPalabras.size(); i++) {
            JsonArray traducciones = listaPalabras.getJsonObject(i).getJsonObject("traducciones").getJsonArray("traduccion");
            String traduccion = listaPalabras.getJsonObject(i).getJsonObject("traducciones").getJsonArray("traduccion").getJsonObject(i).getString("idiomaTraduccion");
            System.out.println(traduccion);
            listaTraducciones.put(traduccion, traducciones.size());
        }
        return listaTraducciones;
    }

    public static List<String> listaPalabras(JsonObject jsonObject) throws ParseException {
        List<String> lista = new ArrayList<>();
        JsonArray listaPalabras = jsonObject.getJsonObject("diccionarioEspanol").getJsonArray("palabra");
        SimpleDateFormat formateador = new SimpleDateFormat("yyyy-MM-dd");
        for (int i = 0; i < listaPalabras.size(); i++) {
            String fecha = listaPalabras.getJsonObject(i).getString("fechaInsercion");
            GregorianCalendar fechaInsercion = new GregorianCalendar();
            GregorianCalendar fechaLimite = new GregorianCalendar();
            //Para obtener la fecha límite que es hace tres días lo primero es obtener el día de hoy.
            fechaLimite.setTime(new Date());
            //Obtenemos la fecha límite que sería hace tres días
            fechaLimite.add(fechaLimite.DATE, -(3 + 1));
            //Formateamos la fecha obtenida del fichero JSON para tener el mismo formato y poder comparar.
            fechaInsercion.setTime(formateador.parse(fecha));
            if (fechaInsercion.after(fechaLimite)) {
                lista.add(listaPalabras.getJsonObject(i).getString("grafia"));
            }
        }
        return lista;
    }

    //------------------------------------------------------------------------//
    public static List<SinonimoType> createSinonimoType(JsonArray listaSinonimos) {
        List<SinonimoType> listaSinonimoType = new ArrayList<>();
        for (int j = 0; j < listaSinonimos.size(); j++) {
            SinonimoType sinonimo = new ObjectFactory().createSinonimoType();
            String grafia = listaSinonimos.getJsonObject(j).getString("grafia");
            sinonimo.setGrafia(grafia);
            listaSinonimoType.add(sinonimo);
        }
        return listaSinonimoType;
    }

    public static PalabraType.Sinonimos createPalabraTypeSinonimos(JsonArray listaSinonimos) {
        PalabraType.Sinonimos sinonimos = new ObjectFactory().createPalabraTypeSinonimos();
        List<SinonimoType> listaSinonimoType = createSinonimoType(listaSinonimos);
        sinonimos.getSinonimo().addAll(listaSinonimoType);
        return sinonimos;
    }

    //------------------------------------------------------------------------//
    public static List<TraduccionType> createTraduccionType(JsonArray listaTraducciones) {
        List<TraduccionType> listaTraduccionType = new ArrayList<>();
        for (int j = 0; j < listaTraducciones.size(); j++) {
            TraduccionType traduccion = new ObjectFactory().createTraduccionType();
            String idioma = listaTraducciones.getJsonObject(j).getString("idiomaTraduccion");
            String grafia = listaTraducciones.getJsonObject(j).getString("grafia");
            String fonetica = listaTraducciones.getJsonObject(j).getString("fonetica");
            traduccion.setIdiomaTraduccion(idioma);
            traduccion.setGrafia(grafia);
            traduccion.setFonetica(fonetica);
            listaTraduccionType.add(traduccion);
        }
        return listaTraduccionType;
    }

    public static PalabraType.Traducciones createPalabraTypeTraducciones(JsonArray listaTraducciones) {
        PalabraType.Traducciones traducciones = new ObjectFactory().createPalabraTypeTraducciones();
        List<TraduccionType> listaTraduccionType = createTraduccionType(listaTraducciones);
        traducciones.getTraduccion().addAll(listaTraduccionType);
        return traducciones;
    }

    //------------------------------------------------------------------------//
    public static List<PalabraType> createPalabraType(JsonArray listaPalabras) throws ParseException {
        List<PalabraType> palabras = new ArrayList<>();
        List<String> definiciones = new ArrayList<>();
        SimpleDateFormat formateador = new SimpleDateFormat("yyyy-MM-dd");
        for (int i = 0; i < listaPalabras.size(); i++) {
            PalabraType palabra = new ObjectFactory().createPalabraType();
            //------------------
            String grafia = listaPalabras.getJsonObject(i).getString("grafia");
            palabra.setGrafia(grafia);
            //------------------
            String fonetica = listaPalabras.getJsonObject(i).getString("fonetica");
            palabra.setFonetica(fonetica);
            //------------------
            JsonArray listadefiniciones = listaPalabras.getJsonObject(i).getJsonArray("definicion");
            for (int j = 0; j < listadefiniciones.size(); j++) {
                definiciones.add(listadefiniciones.getString(j));
            }
            palabra.getDefinicion().addAll(definiciones);
            //------------------
            String fecha = listaPalabras.getJsonObject(i).getString("fechaInsercion");
            GregorianCalendar fechaGC = new GregorianCalendar();
            fechaGC.setTime(formateador.parse(fecha));
            XMLGregorianCalendar fechaXML = null;
            try {
                fechaXML = DatatypeFactory.newInstance().newXMLGregorianCalendar(fechaGC);
            } catch (DatatypeConfigurationException ex) {
            }
            palabra.setFechaInsercion(fechaXML);
            //--------
            JsonArray jsonTraducciones = listaPalabras.getJsonObject(i).getJsonObject("traducciones").getJsonArray("traduccion");
            PalabraType.Traducciones traducciones = createPalabraTypeTraducciones(jsonTraducciones);
            palabra.setTraducciones(traducciones);
            //---------
            JsonArray jsonSinonimos = listaPalabras.getJsonObject(i).getJsonObject("traducciones").getJsonArray("traduccion");
            PalabraType.Sinonimos sinonimos = createPalabraTypeSinonimos(jsonSinonimos);
            palabra.setSinonimos(sinonimos);
            //---------
            palabras.add(palabra);
        }
        return palabras;
    }

    public static DiccionarioEspanol createDiccionarioEspanol(JsonObject jsonObject) throws ParseException {
        DiccionarioEspanol dicionario = new ObjectFactory().createDiccionarioEspanol();
        JsonArray listaPalabras = jsonObject.getJsonObject("diccionarioEspanol").getJsonArray("palabra");
        List<PalabraType> palabras = createPalabraType(listaPalabras);
        dicionario.getPalabra().addAll(palabras);
        return dicionario;
    }

    public static File crearXML(File ficheroJSON, JsonObject jsonObject) throws FileNotFoundException, ParseException {
        DiccionarioEspanol diccionarioJAXB = createDiccionarioEspanol(jsonObject);
        File ficheroXML = marshalizar(diccionarioJAXB, "jaxb.diccionario");
        return ficheroXML;
    }

    public static File marshalizar(DiccionarioEspanol diccionario, String paquete) {
        File ficheroXML = new File("ficheroPruebaXML.xml");
        try {
            JAXBContext jaxbContext = JAXBContext.newInstance(paquete);
            Marshaller m = jaxbContext.createMarshaller();
            m.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);
            m.marshal(diccionario, System.out);
        } catch (JAXBException ex) {
            //Logger.getLogger(JSONMetodosCliente.class.getName()).log(Level.SEVERE, null, ex);
        }
        return ficheroXML;
    }

}
