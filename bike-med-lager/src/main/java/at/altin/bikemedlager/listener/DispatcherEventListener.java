package at.altin.bikemedlager.listener;

import at.altin.bikemed.commons.dto.DiagnoseEventDTO;
import at.altin.bikemed.commons.dto.EventDTO;
import at.altin.bikemed.commons.helper.JsonHelper;
import at.altin.bikemed.commons.listener.CommonEventListener;
import at.altin.bikemedlager.service.api.LagerService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class DispatcherEventListener implements CommonEventListener {

    private final RabbitTemplate rabbitTemplate;
    private final LagerService lagerService;

    @Value("${queue.lager.name}")
    private String queueName;

    @Value("${queue.dispatcher.name}")
    private String dispatcherQueueName;

    public DispatcherEventListener(RabbitTemplate rabbitTemplate, LagerService lagerService) {
        this.rabbitTemplate = rabbitTemplate;
        this.lagerService = lagerService;
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

            DiagnoseEventDTO event = JsonHelper.convertJsonToObject(diagnoseEventDTO, DiagnoseEventDTO.class);

            rabbitTemplate.convertAndSend(queueName, JsonHelper.convertObjectToJson(lagerService.buildLagerEvent(event)));


        } catch (Exception e){
            log.error("Error handling message", e);
        }
    }
}
