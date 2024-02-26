package at.altin.bikemedoffice.service.api;


import at.altin.bikemed.commons.dto.OfficeDataEventDTO;

public interface OfficeDataCollectorService {

    void collectOfficeData(OfficeDataEventDTO event);
}
