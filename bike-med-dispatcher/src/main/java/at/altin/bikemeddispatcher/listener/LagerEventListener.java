package at.altin.bikemeddispatcher.listener;

import at.altin.bikemed.commons.config.ExchangeConfig;
import at.altin.bikemed.commons.dto.LagerEventDTO;
import at.altin.bikemed.commons.helper.JsonHelper;
import at.altin.bikemed.commons.listener.CommonEventListener;
import at.altin.bikemeddispatcher.publisher.OfficeEventPublisher;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
@Slf4j
public class LagerEventListener implements CommonEventListener {
    private final OfficeEventPublisher officeEventPublisher;
    
    @RabbitListener(queues = ExchangeConfig.LAGER_DISPATCHER_EXCHANGE)
    public void handleMessage(String lagerEventDTO) {
        try {
            log.info("Received lager event: {}", lagerEventDTO);
            LagerEventDTO lagerEvent = JsonHelper.convertJsonToObject(lagerEventDTO, LagerEventDTO.class);

            officeEventPublisher.buildAndPublish(null, lagerEvent);

        } catch (Exception e) {
            log.error("Error handling message", e);
        }
    }
}
