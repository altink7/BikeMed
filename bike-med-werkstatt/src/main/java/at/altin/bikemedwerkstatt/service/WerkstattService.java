package at.altin.bikemedwerkstatt.service;

import at.altin.bikemedapi.helper.JsonHelper;
import at.altin.bikemeddispatcher.dto.DiagnoseEventDTO;
import at.altin.bikemeddispatcher.dto.WerkstattEventDTO;
import at.altin.bikemedwerkstatt.data.KonfigurationEntityDao;
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

    @Value("${queue.office.name}")
    private String officeQueueName;

    public WerkstattService(RabbitTemplate rabbitTemplate, KonfigurationEntityDao konfigurationEntityDao) {
        this.rabbitTemplate = rabbitTemplate;
        this.konfigurationEntityDao = konfigurationEntityDao;
    }

    @RabbitListener(queues = "${queue.dispatcher.name}")
    public void handleMessage(String diagnoseEventDTO) {
        try {
            log.info("Received diagnose: {}", diagnoseEventDTO);
            DiagnoseEventDTO event = JsonHelper.convertJsonToObject(diagnoseEventDTO, DiagnoseEventDTO.class);

            if(!event.getTo().equals(queueName)){
                log.info("ignoring event for other service");
            }

            rabbitTemplate.convertAndSend(officeQueueName, JsonHelper.convertObjectToJson(buildWerkstattEvent(event)));


        } catch (Exception e){
            log.error("Error handling message", e);
        }
    }

    private WerkstattEventDTO buildWerkstattEvent(DiagnoseEventDTO event) {
        WerkstattEventDTO werkstattEventDTO = new WerkstattEventDTO();
        if(event.getDiagnoseDTO().countTrueBooleans() <= 3) {
            werkstattEventDTO.setAnzahlMitarbeiter(1);
        } else {
            werkstattEventDTO.setAnzahlMitarbeiter(2);
        }

        werkstattEventDTO.setDiagnoseDTO(event.getDiagnoseDTO());
        werkstattEventDTO.setStundenSatz(konfigurationEntityDao.findAll().stream().findFirst().get().getStundenSatz());
        werkstattEventDTO.setEventId(event.getEventId());
        werkstattEventDTO.setWerkstattName(konfigurationEntityDao.findAll().stream().findFirst().get().getWerkstattName());
        werkstattEventDTO.setFrom(queueName);
        werkstattEventDTO.setTo(officeQueueName);

        return werkstattEventDTO;
    }
}
