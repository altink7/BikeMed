package at.altin.bikemedapi.helper;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.RuntimeJsonMappingException;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class JsonHelper {

    private JsonHelper() {
        // utility class
    }

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
