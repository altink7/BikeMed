package at.altin.bikemedwerkstatt.service;

import at.altin.bikemedcommons.dto.EventDTO;
import at.altin.bikemeddispatcher.dto.DiagnoseEventDTO;
import at.altin.bikemeddispatcher.dto.WerkstattEventDTO;
import at.altin.bikemedwerkstatt.data.KonfigurationEntityDao;
import at.altin.bikemedwerkstatt.model.KonfigurationEntity;
import at.altin.bikemedcommons.helper.JsonHelper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class WerkstattService {
    private final RabbitTemplate rabbitTemplate;

    private final KonfigurationEntityDao konfigurationEntityDao;

    @Value("${queue.werkstatt.name}")
    private String queueName;

    @Value("${queue.dispatcher.name}")
    private String dispatcherQueueName;

    public WerkstattService(RabbitTemplate rabbitTemplate, KonfigurationEntityDao konfigurationEntityDao) {
        this.rabbitTemplate = rabbitTemplate;
        this.konfigurationEntityDao = konfigurationEntityDao;
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

            rabbitTemplate.convertAndSend(queueName, JsonHelper.convertObjectToJson(buildWerkstattEvent(event)));


        } catch (Exception e){
            log.error("Error handling message", e);
        }
    }

    private WerkstattEventDTO buildWerkstattEvent(DiagnoseEventDTO event) {
        WerkstattEventDTO werkstattEventDTO = new WerkstattEventDTO();
        if(event.getDiagnoseDTO().countFalseBooleans() <= 3) {
            werkstattEventDTO.setAnzahlMitarbeiter(1);
        } else {
            werkstattEventDTO.setAnzahlMitarbeiter(2);
        }

        werkstattEventDTO.setDiagnoseEventDTO(event.getDiagnoseDTO());
        werkstattEventDTO.setStundenSatz(getKonfigurationEntity().getStundenSatz());
        werkstattEventDTO.setEventId(event.getEventId());
        werkstattEventDTO.setWerkstattName(getKonfigurationEntity().getWerkstattName());
        werkstattEventDTO.setFrom(queueName);
        werkstattEventDTO.setTo(dispatcherQueueName);

        return werkstattEventDTO;
    }

    private KonfigurationEntity getKonfigurationEntity() {
        return konfigurationEntityDao.findAll().stream().findFirst().orElseThrow(() -> new IllegalArgumentException("Konfiguration not found"));
    }
}
