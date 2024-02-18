package at.altin.bikemedapi.controller.api;

import at.altin.bikemedapi.dto.DiagnoseDTO;
import org.springframework.http.ResponseEntity;

public interface DiagnoseController {

    ResponseEntity<Boolean> addDiagnose(DiagnoseDTO diagnoseDTO);
}
