package at.altin.bikemedwerkstatt.service.api;

import at.altin.bikemeddispatcher.dto.DiagnoseEventDTO;
import at.altin.bikemeddispatcher.dto.WerkstattEventDTO;

public interface WerkstattService {
    WerkstattEventDTO buildWerkstattEvent(DiagnoseEventDTO event);
}
