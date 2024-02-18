package at.altin.bikemeddispatcher.listener;

import at.altin.bikemeddispatcher.dto.DiagnoseDTO;
import at.altin.bikemeddispatcher.helper.JsonHelper;
import at.altin.bikemeddispatcher.publisher.ApiEventPublisher;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class ApiEventListener {
    private final ApiEventPublisher apiEventPublisher;
    private final JsonHelper jsonHelper;

    public ApiEventListener(ApiEventPublisher apiEventPublisher, JsonHelper jsonHelper) {
        this.apiEventPublisher = apiEventPublisher;
        this.jsonHelper = jsonHelper;
    }

    @RabbitListener(queues = "${queue.api.name}")
    public void handleMessage(String diagnoseDTO) {
        try {
        log.info("Received diagnose: {}", diagnoseDTO);
        apiEventPublisher.buildAndPublish(jsonHelper.convertJsonToObject(diagnoseDTO, DiagnoseDTO.class));
        } catch (Exception e){
            log.error("Error handling message", e);
        }
    }
}
