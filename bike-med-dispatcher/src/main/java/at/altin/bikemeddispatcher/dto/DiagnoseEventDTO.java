package at.altin.bikemeddispatcher.dto;

import at.altin.bikemedapi.dto.DiagnoseDTO;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serial;
import java.io.Serializable;
import java.util.UUID;

@Data
@Builder
@NoArgsConstructor
public class DiagnoseEventDTO implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;

    UUID eventId;
    DiagnoseDTO diagnoseDTO;
    String from;
    String to;
}
