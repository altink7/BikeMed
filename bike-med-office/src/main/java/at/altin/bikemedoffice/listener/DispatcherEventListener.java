package at.altin.bikemedoffice.listener;

import at.altin.bikemedcommons.dto.EventDTO;
import at.altin.bikemedcommons.helper.JsonHelper;
import at.altin.bikemedcommons.listener.CommonEventListener;
import at.altin.bikemeddispatcher.dto.OfficeDataEventDTO;
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

    @Value("${queue.office.name}")
    private String queueName;

    @Value("${queue.dispatcher.name}")
    private String dispatcherQueueName;

    public DispatcherEventListener(RabbitTemplate rabbitTemplate,
                                          OfficeDataCollectorService officeDataCollectorService) {
        this.rabbitTemplate = rabbitTemplate;
        this.officeDataCollectorService = officeDataCollectorService;
    }

    @RabbitListener(queues = "${queue.dispatcher.name}")
    public void handleMessage(String diagnoseEventDTO) {
        try {
            log.info("Received diagnose: {}", diagnoseEventDTO);
            EventDTO baseEvent = JsonHelper.convertJsonToObject(diagnoseEventDTO, EventDTO.class);

            if (baseEvent.getTo() != null && !baseEvent.getTo().equals(queueName)) {
                log.info("Ignoring event for other service");
                rabbitTemplate.convertAndSend(dispatcherQueueName, diagnoseEventDTO);
                return;
            }
            officeDataCollectorService.collectOfficeData(JsonHelper.convertJsonToObject(diagnoseEventDTO, OfficeDataEventDTO.class));

        } catch (Exception e){
            log.error("Error handling message", e);
        }
    }
}
