package at.altin.bikemeddispatcher.publisher;

import at.altin.bikemed.commons.dto.LagerEventDTO;
import at.altin.bikemed.commons.dto.OfficeDataEventDTO;
import at.altin.bikemed.commons.dto.WerkstattEventDTO;
import at.altin.bikemed.commons.helper.JsonHelper;
import at.altin.bikemed.commons.publisher.CommonEventPublisher;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class OfficeEventPublisher implements CommonEventPublisher {

    private final RabbitTemplate rabbitTemplate;

    @Value("${queue.dispatcher.name}")
    private String from;

    @Value("${queue.office.name}")
    private String toOffice;

    public OfficeEventPublisher(RabbitTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
    }

    @Override
    public void publish(String officeDataDTO) {
        log.info("Publishing event {} ", officeDataDTO);
        rabbitTemplate.convertAndSend(from, officeDataDTO);
    }

    public void buildAndPublish(WerkstattEventDTO werkstattEventDTO, LagerEventDTO lagerEventDTO) {
        if(werkstattEventDTO == null && lagerEventDTO == null) {
            log.error("Both werkstatt and lager events are null");
            return;
        }

        publish(JsonHelper.convertObjectToJson(buildDiagnoseEvent(werkstattEventDTO, lagerEventDTO, this.toOffice)));

    }

    private OfficeDataEventDTO buildDiagnoseEvent(WerkstattEventDTO werkstattEventDTO,
                                                  LagerEventDTO lagerEventDTO,
                                                  String to) {
        log.info("Building event to {}",  to);
        OfficeDataEventDTO eventDTO = new OfficeDataEventDTO();

        if(werkstattEventDTO != null) {
            log.info("Werkstatt event is not null");
            eventDTO.setEventId(werkstattEventDTO.getEventId());
        }

        if(lagerEventDTO != null) {
            log.info("Lager event is not null");
            eventDTO.setEventId(lagerEventDTO.getEventId());
        }

        eventDTO.setWerkstattEventDTO(werkstattEventDTO);
        eventDTO.setLagerEventDTO(lagerEventDTO);

        eventDTO.setFrom(this.from);
        eventDTO.setTo(to);

        return eventDTO;
    }
}
