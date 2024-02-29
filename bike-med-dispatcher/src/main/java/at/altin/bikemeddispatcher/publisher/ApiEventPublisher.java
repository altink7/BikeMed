package at.altin.bikemeddispatcher.publisher;

import at.altin.bikemed.commons.config.QueueConfig;
import at.altin.bikemed.commons.dto.DiagnoseEventDTO;
import at.altin.bikemed.commons.helper.JsonHelper;
import at.altin.bikemed.commons.publisher.CommonEventPublisher;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
@Slf4j
public class ApiEventPublisher implements CommonEventPublisher {
    private final RabbitTemplate rabbitTemplate;

    public ApiEventPublisher(RabbitTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
    }

    @Override
    public void publish(String diagnoseEventDTO) {
        log.info("Publishing event {} ", diagnoseEventDTO);
        rabbitTemplate.convertAndSend(QueueConfig.QUEUE_DISPATCHER, diagnoseEventDTO);
    }

    public void buildAndPublish(DiagnoseEventDTO diagnoseEventDTO) {
        UUID eventId = diagnoseEventDTO.getEventId();
        publish(JsonHelper.convertObjectToJson(buildDiagnoseEvent(diagnoseEventDTO, QueueConfig.QUEUE_WERKSTATT, eventId)));
        publish(JsonHelper.convertObjectToJson(buildDiagnoseEvent(diagnoseEventDTO, QueueConfig.QUEUE_LAGER, eventId)));
    }

    private DiagnoseEventDTO buildDiagnoseEvent(DiagnoseEventDTO diagnoseEventDTO, String to, UUID eventId) {
        log.info("Building event to {}",  to);
        diagnoseEventDTO.setEventId(eventId);
        diagnoseEventDTO.setFrom(QueueConfig.QUEUE_DISPATCHER);
        diagnoseEventDTO.setTo(to);
        return diagnoseEventDTO;
    }
}
