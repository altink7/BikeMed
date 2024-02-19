package at.altin.bikemeddispatcher.dto;

import at.altin.bikemedapi.dto.DiagnoseDTO;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@EqualsAndHashCode(callSuper = true)
@Data
@NoArgsConstructor
public class DiagnoseEventDTO extends EventDTO implements Serializable {
    DiagnoseDTO diagnoseDTO;
}
