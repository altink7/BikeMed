package at.altin.bikemedlager.listener;

import at.altin.bikemed.commons.config.ExchangeConfig;
import at.altin.bikemed.commons.dto.DiagnoseEventDTO;
import at.altin.bikemed.commons.helper.JsonHelper;
import at.altin.bikemed.commons.listener.CommonEventListener;
import at.altin.bikemedlager.service.api.LagerService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
@Slf4j
public class DispatcherEventListener implements CommonEventListener {
    private final RabbitTemplate rabbitTemplate;
    private final LagerService lagerService;

    @RabbitListener(queues = ExchangeConfig.DISPATCHER_LAGER_EXCHANGE)
    public void handleMessage(String diagnoseEventDTO) {
        try {
            log.info("Received diagnose: {}", diagnoseEventDTO);
            DiagnoseEventDTO event = JsonHelper.convertJsonToObject(diagnoseEventDTO, DiagnoseEventDTO.class);
            rabbitTemplate.convertAndSend(ExchangeConfig.LAGER_DISPATCHER_EXCHANGE,
                    JsonHelper.convertObjectToJson(lagerService.buildLagerEvent(event)));
        } catch (Exception e) {
            log.error("Error handling message", e);
        }
    }
}
