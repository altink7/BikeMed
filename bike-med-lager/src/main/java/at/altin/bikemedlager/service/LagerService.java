package at.altin.bikemedlager.service;

import at.altin.bikemedapi.dto.DiagnoseDTO;
import at.altin.bikemedapi.helper.JsonHelper;
import at.altin.bikemeddispatcher.dto.DiagnoseEventDTO;
import at.altin.bikemeddispatcher.dto.EventDTO;
import at.altin.bikemeddispatcher.dto.LagerEventDTO;
import at.altin.bikemedlager.data.ProduktDao;
import at.altin.bikemedlager.model.Produkt;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Service
@Slf4j
public class LagerService {

    private final RabbitTemplate rabbitTemplate;
    private final ProduktDao produktDao;

    @Value("${queue.lager.name}")
    private String queueName;

    @Value("${queue.dispatcher.name}")
    private String dispatcherQueueName;

    public LagerService(RabbitTemplate rabbitTemplate, ProduktDao produktDao) {
        this.rabbitTemplate = rabbitTemplate;
        this.produktDao = produktDao;
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

            rabbitTemplate.convertAndSend(queueName, JsonHelper.convertObjectToJson(buildLagerEvent(event)));


        } catch (Exception e){
            log.error("Error handling message", e);
        }
    }

    private LagerEventDTO buildLagerEvent(DiagnoseEventDTO event) {
        LagerEventDTO eventDTO = new LagerEventDTO();
        eventDTO.setEventId(event.getEventId());
        eventDTO.setFrom(queueName);
        eventDTO.setTo(dispatcherQueueName);


        List<Boolean> properties = getDiagnoseBooleans(event);

        Map<String, Double> products = IntStream.range(0, properties.size())
                .filter(properties::get)
                .mapToObj(DiagnoseDTO::getPropertyNameAtIndex)
                .flatMap(propertyName -> produktDao.findByName(propertyName).stream())
                .collect(Collectors.toMap(Produkt::getName, Produkt::getPrice));

        eventDTO.setProducts(products);

        return eventDTO;
    }

    private static List<Boolean> getDiagnoseBooleans(DiagnoseEventDTO event) {
        DiagnoseDTO diagnoseDTO = event.getDiagnoseDTO();
        return Arrays.asList(
                diagnoseDTO.isPlattenReparatur(),
                diagnoseDTO.isVentil(),
                diagnoseDTO.isBremsen(),
                diagnoseDTO.isSchaltung(),
                diagnoseDTO.isBeleuchtungVorne(),
                diagnoseDTO.isBeleuchtungHinten(),
                diagnoseDTO.isReflector(),
                diagnoseDTO.isFederung(),
                diagnoseDTO.isRahmen(),
                diagnoseDTO.isGabel(),
                diagnoseDTO.isKettenantrieb(),
                diagnoseDTO.isElektrischeKomponenten()
        );
    }


}
