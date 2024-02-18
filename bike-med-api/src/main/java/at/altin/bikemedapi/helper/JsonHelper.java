package at.altin.bikemedapi.helper;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.RuntimeJsonMappingException;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class JsonHelper {

    private JsonHelper() {
    }

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
}
