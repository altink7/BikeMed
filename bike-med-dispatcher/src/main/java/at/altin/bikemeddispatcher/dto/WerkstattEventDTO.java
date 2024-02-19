package at.altin.bikemeddispatcher.dto;

import at.altin.bikemedapi.dto.DiagnoseDTO;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@EqualsAndHashCode(callSuper = true)
@Data
@NoArgsConstructor
public class WerkstattEventDTO extends EventDTO implements Serializable {
    private DiagnoseDTO diagnoseDTO;
    private String werkstattName;
    private int anzahlMitarbeiter;
    private double stundenSatz;
}
