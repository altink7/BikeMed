package at.altin.bikemedlager.service.api;


import at.altin.bikemed.commons.dto.DiagnoseEventDTO;
import at.altin.bikemed.commons.dto.LagerEventDTO;

public interface LagerService {

    LagerEventDTO buildLagerEvent(DiagnoseEventDTO event);
}
