package at.altin.bikemeddispatcher.publisher;

import at.altin.bikemedapi.dto.DiagnoseDTO;
import at.altin.bikemedapi.helper.JsonHelper;
import at.altin.bikemeddispatcher.dto.DiagnoseEventDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
@Slf4j
public class ApiEventPublisher {
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

    public void publishEvent(String diagnoseEventDTO) {
        log.info("Publishing event {} ", diagnoseEventDTO);
        rabbitTemplate.convertAndSend(from, diagnoseEventDTO);
    }

    public void buildAndPublish(DiagnoseDTO diagnoseDTO) {
        UUID eventId = diagnoseDTO.getId();
        publishEvent(JsonHelper.convertObjectToJson(buildDiagnoseEvent(diagnoseDTO, this.toWerkstatt, eventId)));
        publishEvent(JsonHelper.convertObjectToJson(buildDiagnoseEvent(diagnoseDTO, this.toLager, eventId)));
    }

    private DiagnoseEventDTO buildDiagnoseEvent(DiagnoseDTO diagnoseDTO, String to, UUID eventId) {
        log.info("Building event to {}",  to);
        DiagnoseEventDTO eventDTO = new DiagnoseEventDTO();
        eventDTO.setEventId(eventId);
        eventDTO.setDiagnoseDTO(diagnoseDTO);
        eventDTO.setFrom(this.from);
        eventDTO.setTo(to);
        return eventDTO;
    }
}
