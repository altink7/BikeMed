package at.altin.bikemedwerkstatt.service.api;


import at.altin.bikemed.commons.dto.DiagnoseEventDTO;
import at.altin.bikemed.commons.dto.WerkstattEventDTO;

public interface WerkstattService {
    WerkstattEventDTO buildWerkstattEvent(DiagnoseEventDTO event);
}
