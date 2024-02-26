package at.altin.bikemeddispatcher.listener;


import at.altin.bikemed.commons.dto.DiagnoseEventDTO;
import at.altin.bikemed.commons.helper.JsonHelper;
import at.altin.bikemed.commons.listener.CommonEventListener;
import at.altin.bikemeddispatcher.publisher.ApiEventPublisher;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class ApiEventListener implements CommonEventListener {
    private final ApiEventPublisher apiEventPublisher;

    public ApiEventListener(ApiEventPublisher apiEventPublisher) {
        this.apiEventPublisher = apiEventPublisher;
    }

    @RabbitListener(queues = "${queue.api.name}")
    public void handleMessage(String diagnoseDTO) {
        try {
        log.info("Received diagnose: {}", diagnoseDTO);
        DiagnoseEventDTO diagnose = JsonHelper.convertJsonToObject(diagnoseDTO, DiagnoseEventDTO.class);

        apiEventPublisher.buildAndPublish(diagnose);
        } catch (Exception e){
            log.error("Error handling message", e);
        }
    }
}
