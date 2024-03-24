package at.altin.bikemeddispatcher.publisher;

import at.altin.bikemed.commons.config.ExchangeConfig;
import at.altin.bikemed.commons.config.QueueConfig;
import at.altin.bikemed.commons.dto.LagerEventDTO;
import at.altin.bikemed.commons.dto.OfficeDataEventDTO;
import at.altin.bikemed.commons.dto.WerkstattEventDTO;
import at.altin.bikemed.commons.helper.JsonHelper;
import at.altin.bikemed.commons.publisher.CommonEventPublisher;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
@Slf4j
public class OfficeEventPublisher implements CommonEventPublisher {

    private final RabbitTemplate rabbitTemplate;
    
    @Override
    public void publish(String officeDataDTO) {
        log.info("Publishing event {} ", officeDataDTO);
        rabbitTemplate.convertAndSend(ExchangeConfig.DISPATCHER_OFFICE_EXCHANGE, officeDataDTO);
    }

    public void buildAndPublish(WerkstattEventDTO werkstattEventDTO, LagerEventDTO lagerEventDTO) {
        if (werkstattEventDTO == null && lagerEventDTO == null) {
            log.error("Both werkstatt and lager events are null");
            return;
        }

        publish(JsonHelper.convertObjectToJson(buildDiagnoseEvent(werkstattEventDTO, lagerEventDTO)));

    }

    private OfficeDataEventDTO buildDiagnoseEvent(WerkstattEventDTO werkstattEventDTO,
                                                  LagerEventDTO lagerEventDTO) {
        log.info("Building event to {}", QueueConfig.QUEUE_OFFICE);
        OfficeDataEventDTO eventDTO = new OfficeDataEventDTO();

        if (werkstattEventDTO != null) {
            log.info("Werkstatt event is not null");
            eventDTO.setEventId(werkstattEventDTO.getEventId());
        }

        if (lagerEventDTO != null) {
            log.info("Lager event is not null");
            eventDTO.setEventId(lagerEventDTO.getEventId());
        }

        eventDTO.setWerkstattEventDTO(werkstattEventDTO);
        eventDTO.setLagerEventDTO(lagerEventDTO);

        eventDTO.setFrom(QueueConfig.QUEUE_DISPATCHER);
        eventDTO.setTo(QueueConfig.QUEUE_OFFICE);

        return eventDTO;
    }
}
