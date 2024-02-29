package at.altin.bikemedoffice.listener;

import at.altin.bikemed.commons.config.QueueConfig;
import at.altin.bikemed.commons.dto.EventDTO;
import at.altin.bikemed.commons.dto.OfficeDataEventDTO;
import at.altin.bikemed.commons.helper.JsonHelper;
import at.altin.bikemed.commons.listener.CommonEventListener;
import at.altin.bikemedoffice.service.api.OfficeDataCollectorService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class DispatcherEventListener implements CommonEventListener {
    private final RabbitTemplate rabbitTemplate;
    private final OfficeDataCollectorService officeDataCollectorService;

    public DispatcherEventListener(RabbitTemplate rabbitTemplate,
                                          OfficeDataCollectorService officeDataCollectorService) {
        this.rabbitTemplate = rabbitTemplate;
        this.officeDataCollectorService = officeDataCollectorService;
    }

    @RabbitListener(queues = QueueConfig.QUEUE_DISPATCHER)
    public void handleMessage(String diagnoseEventDTO) {
        try {
            log.info("Received diagnose: {}", diagnoseEventDTO);
            EventDTO baseEvent = JsonHelper.convertJsonToObject(diagnoseEventDTO, EventDTO.class);

            if (baseEvent.getTo() != null && !baseEvent.getTo().equals(QueueConfig.QUEUE_OFFICE)) {
                log.info("Ignoring event for other service");
                rabbitTemplate.convertAndSend(QueueConfig.QUEUE_DISPATCHER, diagnoseEventDTO);
                return;
            }
            officeDataCollectorService.collectOfficeData(JsonHelper.convertJsonToObject(diagnoseEventDTO, OfficeDataEventDTO.class));

        } catch (Exception e){
            log.error("Error handling message", e);
        }
    }
}
