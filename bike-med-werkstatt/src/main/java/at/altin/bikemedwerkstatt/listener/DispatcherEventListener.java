package at.altin.bikemedwerkstatt.listener;


import at.altin.bikemed.commons.config.ExchangeConfig;
import at.altin.bikemed.commons.dto.DiagnoseEventDTO;
import at.altin.bikemed.commons.helper.JsonHelper;
import at.altin.bikemed.commons.listener.CommonEventListener;
import at.altin.bikemedwerkstatt.service.api.WerkstattService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Slf4j
@Component
public class DispatcherEventListener implements CommonEventListener {
    private final RabbitTemplate rabbitTemplate;
    private final WerkstattService werkstattService;

    @RabbitListener(queues = ExchangeConfig.DISPATCHER_WERKSTATT_EXCHANGE)
    public void handleMessage(String diagnoseEventDTO) {
        try {
            log.info("Received diagnose: {}", diagnoseEventDTO);
            DiagnoseEventDTO event = JsonHelper.convertJsonToObject(diagnoseEventDTO, DiagnoseEventDTO.class);
            rabbitTemplate.convertAndSend(ExchangeConfig.WERKSTATT_DISPATCHER_EXCHANGE,
                    JsonHelper.convertObjectToJson(werkstattService.buildWerkstattEvent(event)));

        } catch (Exception e) {
            log.error("Error handling message", e);
        }
    }
}
