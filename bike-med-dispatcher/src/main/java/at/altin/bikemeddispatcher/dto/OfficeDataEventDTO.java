package at.altin.bikemeddispatcher.dto;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@EqualsAndHashCode(callSuper = true)
@Data
@NoArgsConstructor
public class OfficeDataEventDTO extends EventDTO implements Serializable {
    private LagerEventDTO lagerEventDTO;
    private WerkstattEventDTO werkstattEventDTO;
}
