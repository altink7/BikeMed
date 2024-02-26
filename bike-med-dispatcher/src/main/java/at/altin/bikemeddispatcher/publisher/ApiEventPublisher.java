package at.altin.bikemeddispatcher.publisher;

import at.altin.bikemed.commons.dto.DiagnoseEventDTO;
import at.altin.bikemed.commons.helper.JsonHelper;
import at.altin.bikemed.commons.publisher.CommonEventPublisher;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
@Slf4j
public class ApiEventPublisher implements CommonEventPublisher {
    private final RabbitTemplate rabbitTemplate;

    @Value("${queue.dispatcher.name}")
    private String from;

    @Value("${queue.werkstatt.name}")
    private String toWerkstatt;

    @Value("${queue.lager.name}")
    private String toLager;

    public ApiEventPublisher(RabbitTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
    }

    @Override
    public void publish(String diagnoseEventDTO) {
        log.info("Publishing event {} ", diagnoseEventDTO);
        rabbitTemplate.convertAndSend(from, diagnoseEventDTO);
    }

    public void buildAndPublish(DiagnoseEventDTO diagnoseEventDTO) {
        UUID eventId = diagnoseEventDTO.getEventId();
        publish(JsonHelper.convertObjectToJson(buildDiagnoseEvent(diagnoseEventDTO, this.toWerkstatt, eventId)));
        publish(JsonHelper.convertObjectToJson(buildDiagnoseEvent(diagnoseEventDTO, this.toLager, eventId)));
    }

    private DiagnoseEventDTO buildDiagnoseEvent(DiagnoseEventDTO diagnoseEventDTO, String to, UUID eventId) {
        log.info("Building event to {}",  to);
        diagnoseEventDTO.setEventId(eventId);
        diagnoseEventDTO.setFrom(this.from);
        diagnoseEventDTO.setTo(to);
        return diagnoseEventDTO;
    }
}
