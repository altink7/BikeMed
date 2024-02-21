package at.altin.bikemeddispatcher.listener;

import at.altin.bikemedcommons.helper.JsonHelper;
import at.altin.bikemedcommons.listener.CommonEventListener;
import at.altin.bikemeddispatcher.dto.LagerEventDTO;
import at.altin.bikemeddispatcher.publisher.OfficeEventPublisher;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class LagerEventListener implements CommonEventListener {

    private final OfficeEventPublisher officeEventPublisher;

    public LagerEventListener(OfficeEventPublisher officeEventPublisher) {
        this.officeEventPublisher = officeEventPublisher;
    }

    @RabbitListener(queues = "${queue.lager.name}")
    public void handleMessage(String lagerEventDTO) {
        try {
            log.info("Received lager event: {}", lagerEventDTO);
            LagerEventDTO lagerEvent = JsonHelper.convertJsonToObject(lagerEventDTO, LagerEventDTO.class);

            officeEventPublisher.buildAndPublish(null, lagerEvent);

        } catch (Exception e){
            log.error("Error handling message", e);
        }
    }
}
