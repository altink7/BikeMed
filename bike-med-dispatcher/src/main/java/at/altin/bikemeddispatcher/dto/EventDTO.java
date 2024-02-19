package at.altin.bikemeddispatcher.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serial;
import java.io.Serializable;
import java.util.UUID;

@Data
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class EventDTO implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;

    UUID eventId;
    String from;
    String to;
}
