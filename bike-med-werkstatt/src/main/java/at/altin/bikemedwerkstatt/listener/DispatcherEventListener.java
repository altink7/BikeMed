package at.altin.bikemedwerkstatt.listener;


import at.altin.bikemed.commons.config.QueueConfig;
import at.altin.bikemed.commons.dto.DiagnoseEventDTO;
import at.altin.bikemed.commons.dto.EventDTO;
import at.altin.bikemed.commons.helper.JsonHelper;
import at.altin.bikemed.commons.listener.CommonEventListener;
import at.altin.bikemedwerkstatt.service.api.WerkstattService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.querydsl.QuerydslUtils;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class DispatcherEventListener implements CommonEventListener {
    private final RabbitTemplate rabbitTemplate;
    private final WerkstattService werkstattService;

    public DispatcherEventListener(RabbitTemplate rabbitTemplate, WerkstattService werkstattService) {
        this.rabbitTemplate = rabbitTemplate;
        this.werkstattService = werkstattService;
    }

    @RabbitListener(queues = QueueConfig.QUEUE_DISPATCHER)
    public void handleMessage(String diagnoseEventDTO) {
        try {
            log.info("Received diagnose: {}", diagnoseEventDTO);
            EventDTO baseEvent = JsonHelper.convertJsonToObject(diagnoseEventDTO, EventDTO.class);

            if (baseEvent.getTo() != null && !baseEvent.getTo().equals(QueueConfig.QUEUE_WERKSTATT)) {
                log.info("Ignoring event for other service");
                rabbitTemplate.convertAndSend(QueueConfig.QUEUE_DISPATCHER, diagnoseEventDTO);
                return;
            }

            DiagnoseEventDTO event = JsonHelper.convertJsonToObject(diagnoseEventDTO, DiagnoseEventDTO.class);

            rabbitTemplate.convertAndSend(QueueConfig.QUEUE_WERKSTATT, JsonHelper.convertObjectToJson(werkstattService.buildWerkstattEvent(event)));

        } catch (Exception e){
            log.error("Error handling message", e);
        }
    }
}
