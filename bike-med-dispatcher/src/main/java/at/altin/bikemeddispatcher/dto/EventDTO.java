package at.altin.bikemeddispatcher.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serial;
import java.io.Serializable;
import java.util.UUID;

@Data
@NoArgsConstructor
public class EventDTO implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;

    UUID eventId;
    String from;
    String to;
}
