package at.altin.bikemeddispatcher.publisher;

import at.altin.bikemedapi.dto.DiagnoseDTO;
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

    public void publishEvent(DiagnoseEventDTO diagnoseEventDTO) {
        log.info("Publishing event");
        rabbitTemplate.convertAndSend(from, diagnoseEventDTO);
    }

    public void buildAndPublish(DiagnoseDTO diagnoseDTO) {
        publishEvent(buildDiagnoseEvent(diagnoseDTO, this.toWerkstatt));
        publishEvent(buildDiagnoseEvent(diagnoseDTO, this.toLager));
    }

    private DiagnoseEventDTO buildDiagnoseEvent(DiagnoseDTO diagnoseDTO, String to) {
        log.info("Building event");
        return DiagnoseEventDTO.builder()
                .eventId(UUID.randomUUID())
                .diagnoseDTO(diagnoseDTO)
                .from(from)
                .to(to).build();
    }
}
