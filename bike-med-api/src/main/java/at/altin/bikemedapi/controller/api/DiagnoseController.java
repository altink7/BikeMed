package at.altin.bikemedapi.controller.api;

import at.altin.bikemedapi.dto.DiagnoseDTO;
import org.springframework.http.ResponseEntity;

import java.util.UUID;

public interface DiagnoseController {

    ResponseEntity<UUID> addDiagnose(DiagnoseDTO diagnoseDTO);
}
