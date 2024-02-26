package at.altin.bikemeddispatcher.listener;

import at.altin.bikemed.commons.dto.WerkstattEventDTO;
import at.altin.bikemed.commons.helper.JsonHelper;
import at.altin.bikemed.commons.listener.CommonEventListener;
import at.altin.bikemeddispatcher.publisher.OfficeEventPublisher;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class WerkstattEventListener implements CommonEventListener {

    private final OfficeEventPublisher officeEventPublisher;

    public WerkstattEventListener(OfficeEventPublisher officeEventPublisher) {
        this.officeEventPublisher = officeEventPublisher;
    }

    @RabbitListener(queues = "${queue.werkstatt.name}")
    public void handleMessage(String werkstattEventDTO) {
        try {
            log.info("Received werkstatt event: {}", werkstattEventDTO);
            WerkstattEventDTO werkstattEvent = JsonHelper.convertJsonToObject(werkstattEventDTO, WerkstattEventDTO.class);

            officeEventPublisher.buildAndPublish(werkstattEvent, null);
        } catch (Exception e){
            log.error("Error handling message", e);
        }
    }
}
