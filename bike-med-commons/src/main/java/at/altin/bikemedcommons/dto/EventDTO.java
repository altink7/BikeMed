package at.altin.bikemedcommons.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serial;
import java.io.Serializable;
import java.util.UUID;


/**
 * EventDTO
 * <p>
 *     EventDTO is a data transfer object for the event.
 *     It is used to transfer the event data between the services.
 *     Each EventDTO should extend this class.
 * </p>
 *
 * @author Altin
 */
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
