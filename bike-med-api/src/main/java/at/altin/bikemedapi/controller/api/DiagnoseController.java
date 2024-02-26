package at.altin.bikemedapi.controller.api;

import at.altin.bikemed.commons.dto.DiagnoseEventDTO;
import org.springframework.http.ResponseEntity;

import java.util.UUID;

public interface DiagnoseController {

    ResponseEntity<UUID> addDiagnose(DiagnoseEventDTO diagnoseEventDTO);
}
