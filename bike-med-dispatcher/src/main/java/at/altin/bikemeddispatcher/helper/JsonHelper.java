package at.altin.bikemeddispatcher.helper;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class JsonHelper {

    private JsonHelper() {
    }

    public String convertObjectToJson(Object object) {
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            return objectMapper.writeValueAsString(object);
        } catch (JsonProcessingException e) {
            log.error("Error converting object to JSON", e);
            throw new RuntimeException("Error converting object to JSON", e);
        }
    }

    @JsonCreator
    public <T> T convertJsonToObject(String json, Class<T> valueType) {
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            return objectMapper.readValue(json, valueType);
        } catch (JsonProcessingException e) {
            log.error("Error converting JSON to object", e);
            throw new RuntimeException("Error converting JSON to object", e);
        }
    }
}
