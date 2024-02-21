package at.altin.bikemedoffice.service.api;

import at.altin.bikemeddispatcher.dto.OfficeDataEventDTO;

public interface OfficeDataCollectorService {

    void collectOfficeData(OfficeDataEventDTO event);
}
