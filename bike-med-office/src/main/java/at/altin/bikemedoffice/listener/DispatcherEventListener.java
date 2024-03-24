package at.altin.bikemedoffice.listener;

import at.altin.bikemed.commons.config.ExchangeConfig;
import at.altin.bikemed.commons.dto.OfficeDataEventDTO;
import at.altin.bikemed.commons.helper.JsonHelper;
import at.altin.bikemed.commons.listener.CommonEventListener;
import at.altin.bikemedoffice.service.api.OfficeDataCollectorService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
@Slf4j
public class DispatcherEventListener implements CommonEventListener {
    private final OfficeDataCollectorService officeDataCollectorService;

    @RabbitListener(queues = ExchangeConfig.DISPATCHER_OFFICE_EXCHANGE)
    public void handleMessage(String diagnoseEventDTO) {
        try {
            log.info("Received diagnose: {}", diagnoseEventDTO);
            officeDataCollectorService.collectOfficeData(JsonHelper.convertJsonToObject(diagnoseEventDTO,
                    OfficeDataEventDTO.class));
        } catch (Exception e) {
            log.error("Error handling message", e);
        }
    }
}
