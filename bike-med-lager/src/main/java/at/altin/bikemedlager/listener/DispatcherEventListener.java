package at.altin.bikemedlager.listener;

import at.altin.bikemed.commons.config.QueueConfig;
import at.altin.bikemed.commons.dto.DiagnoseEventDTO;
import at.altin.bikemed.commons.dto.EventDTO;
import at.altin.bikemed.commons.helper.JsonHelper;
import at.altin.bikemed.commons.listener.CommonEventListener;
import at.altin.bikemedlager.service.api.LagerService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class DispatcherEventListener implements CommonEventListener {

    private final RabbitTemplate rabbitTemplate;
    private final LagerService lagerService;

    public DispatcherEventListener(RabbitTemplate rabbitTemplate, LagerService lagerService) {
        this.rabbitTemplate = rabbitTemplate;
        this.lagerService = lagerService;
    }

    @RabbitListener(queues = QueueConfig.QUEUE_DISPATCHER)
    public void handleMessage(String diagnoseEventDTO) {
        try {
            log.info("Received diagnose: {}", diagnoseEventDTO);

            EventDTO baseEvent = JsonHelper.convertJsonToObject(diagnoseEventDTO, EventDTO.class);

            if (baseEvent.getTo() != null && !baseEvent.getTo().equals(QueueConfig.QUEUE_LAGER)) {
                log.info("Ignoring event for other service");
                rabbitTemplate.convertAndSend(QueueConfig.QUEUE_DISPATCHER, diagnoseEventDTO);
                return;
            }

            DiagnoseEventDTO event = JsonHelper.convertJsonToObject(diagnoseEventDTO, DiagnoseEventDTO.class);

            rabbitTemplate.convertAndSend(QueueConfig.QUEUE_LAGER, JsonHelper.convertObjectToJson(lagerService.buildLagerEvent(event)));


        } catch (Exception e){
            log.error("Error handling message", e);
        }
    }
}
