package at.altin.bikemeddispatcher.listener;

import at.altin.bikemedapi.dto.DiagnoseDTO;
import at.altin.bikemedapi.helper.JsonHelper;
import at.altin.bikemeddispatcher.publisher.ApiEventPublisher;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class ApiEventListener {
    private final ApiEventPublisher apiEventPublisher;

    public ApiEventListener(ApiEventPublisher apiEventPublisher) {
        this.apiEventPublisher = apiEventPublisher;
    }

    @RabbitListener(queues = "${queue.api.name}")
    public void handleMessage(String diagnoseDTO) {
        try {
        log.info("Received diagnose: {}", diagnoseDTO);
        DiagnoseDTO diagnose = JsonHelper.convertJsonToObject(diagnoseDTO, DiagnoseDTO.class);

        apiEventPublisher.buildAndPublish(diagnose);
        } catch (Exception e){
            log.error("Error handling message", e);
        }
    }
}
