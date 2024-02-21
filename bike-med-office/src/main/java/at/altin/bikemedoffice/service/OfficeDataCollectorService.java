package at.altin.bikemedoffice.service;

import at.altin.bikemedcommons.dto.EventDTO;
import at.altin.bikemedcommons.helper.JsonHelper;
import at.altin.bikemeddispatcher.dto.OfficeDataEventDTO;
import at.altin.bikemedoffice.data.OfficeDataDao;
import at.altin.bikemedoffice.data.ProductDao;
import at.altin.bikemedoffice.model.OfficeData;
import at.altin.bikemedoffice.model.Product;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class OfficeDataCollectorService {
    private final RabbitTemplate rabbitTemplate;
    private final OfficeDataDao officeDataDao;
    private final ProductDao productDao;

    @Value("${queue.office.name}")
    private String queueName;

    @Value("${queue.dispatcher.name}")
    private String dispatcherQueueName;

    public OfficeDataCollectorService(RabbitTemplate rabbitTemplate,
                                      OfficeDataDao officeDataDao,
                                      ProductDao productDao) {
        this.rabbitTemplate = rabbitTemplate;
        this.officeDataDao = officeDataDao;
        this.productDao = productDao;
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

            saveOfficeData(JsonHelper.convertJsonToObject(diagnoseEventDTO, OfficeDataEventDTO.class));

        } catch (Exception e){
            log.error("Error handling message", e);
        }
    }


    public void saveOfficeData(OfficeDataEventDTO event) {
        log.info("Saving office data: {}", event);
        OfficeData officeData = officeDataDao.findById(event.getEventId()).orElse(new OfficeData());
        officeData.setId(event.getEventId());
        setWerkstattEventData(event, officeData);
        setLagerEventData(event, officeData);

        officeDataDao.save(officeData);
    }

    private void setLagerEventData(OfficeDataEventDTO event, OfficeData officeData) {
        if (event.getLagerEventDTO() != null && event.getLagerEventDTO().getProducts() != null) {
            event.getLagerEventDTO().getProducts().forEach((name, price) -> {
                Product product = new Product();
                product.setName(name);
                product.setPrice(price);
                product.setOfficeData(officeData);
                productDao.save(product);
            });
        }
    }

    private void setWerkstattEventData(OfficeDataEventDTO event, OfficeData officeData) {
        if(event.getWerkstattEventDTO() != null) {
            officeData.setPlattenReparatur(event.getWerkstattEventDTO().getDiagnoseEventDTO().isPlattenReparatur());
            officeData.setVentil(event.getWerkstattEventDTO().getDiagnoseEventDTO().isVentil());
            officeData.setBremsen(event.getWerkstattEventDTO().getDiagnoseEventDTO().isBremsen());
            officeData.setSchaltung(event.getWerkstattEventDTO().getDiagnoseEventDTO().isSchaltung());
            officeData.setBeleuchtungVorne(event.getWerkstattEventDTO().getDiagnoseEventDTO().isBeleuchtungVorne());
            officeData.setBeleuchtungHinten(event.getWerkstattEventDTO().getDiagnoseEventDTO().isBeleuchtungHinten());
            officeData.setReflector(event.getWerkstattEventDTO().getDiagnoseEventDTO().isReflector());
            officeData.setFederung(event.getWerkstattEventDTO().getDiagnoseEventDTO().isFederung());
            officeData.setRahmen(event.getWerkstattEventDTO().getDiagnoseEventDTO().isRahmen());
            officeData.setGabel(event.getWerkstattEventDTO().getDiagnoseEventDTO().isGabel());
            officeData.setKettenantrieb(event.getWerkstattEventDTO().getDiagnoseEventDTO().isKettenantrieb());
            officeData.setElektrischeKomponenten(event.getWerkstattEventDTO().getDiagnoseEventDTO().isElektrischeKomponenten());
            officeData.setSonstigeProbleme(event.getWerkstattEventDTO().getDiagnoseEventDTO().isSonstigeProbleme());
            officeData.setCustomNote(event.getWerkstattEventDTO().getDiagnoseEventDTO().getCustomNote());
            officeData.setFehlerAnzahl(event.getWerkstattEventDTO().getDiagnoseEventDTO().countFalseBooleans());
            officeData.setWerkstattName(event.getWerkstattEventDTO().getWerkstattName());
            officeData.setAnzahlMitarbeiter(event.getWerkstattEventDTO().getAnzahlMitarbeiter());
            officeData.setStundenSatz(event.getWerkstattEventDTO().getStundenSatz());
        }
    }
}
