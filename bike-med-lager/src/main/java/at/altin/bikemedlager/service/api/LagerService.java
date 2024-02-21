package at.altin.bikemedlager.service.api;

import at.altin.bikemeddispatcher.dto.DiagnoseEventDTO;
import at.altin.bikemeddispatcher.dto.LagerEventDTO;

public interface LagerService {

    LagerEventDTO buildLagerEvent(DiagnoseEventDTO event);
}
