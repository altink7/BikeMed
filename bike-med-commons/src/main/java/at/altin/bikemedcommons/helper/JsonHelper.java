package at.altin.bikemedcommons.helper;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.RuntimeJsonMappingException;
import lombok.extern.slf4j.Slf4j;

/**
 * Utility class for converting objects to JSON and vice versa.
 *
 * @author Altin
 */
@Slf4j
public class JsonHelper {

    private JsonHelper() {
        // utility class
    }

    /**
     * This method is used to convert the object to JSON.
     * @param object the object to be converted to JSON
     * @return the JSON representation of the object
     */
    public static String convertObjectToJson(Object object) {
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            log.info("Converting object to JSON: {}", object);
            return objectMapper.writeValueAsString(object);
        } catch (JsonProcessingException e) {
            log.error("Error converting object to JSON", e);
            throw new RuntimeJsonMappingException("Error converting object to JSON");
        }
    }

    /**
     * This method is used to convert the JSON to object.
     * @param json the JSON to be converted to object
     * @param clazz the class of the object
     * @param <T> the type of the object
     * @return the object representation of the JSON
     */
    public static <T> T convertJsonToObject(String json, Class<T> clazz) {
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            log.info("Converting JSON to object: {}", json);
            return objectMapper.readValue(json, clazz);
        } catch (JsonProcessingException e) {
            log.error("Error converting JSON to object", e);
            throw new RuntimeJsonMappingException("Error converting JSON to object");
        }
    }
}
