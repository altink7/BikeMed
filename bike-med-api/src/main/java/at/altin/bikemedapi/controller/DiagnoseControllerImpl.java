package at.altin.bikemedapi.controller;

import at.altin.bikemed.commons.config.QueueConfig;
import at.altin.bikemed.commons.dto.DiagnoseEventDTO;
import at.altin.bikemed.commons.helper.JsonHelper;
import at.altin.bikemedapi.controller.api.DiagnoseController;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RequestMapping("api/diagnose")
@RestController
@CrossOrigin
@Slf4j
public class DiagnoseControllerImpl implements DiagnoseController {
    private final RabbitTemplate rabbitTemplate;

    @Autowired
    public DiagnoseControllerImpl(RabbitTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
        this.rabbitTemplate.setMessageConverter(new Jackson2JsonMessageConverter());
    }

    @PostMapping
    @Override
    public ResponseEntity<UUID> addDiagnose(@RequestBody DiagnoseEventDTO diagnoseEventDTO) {
        log.info("Diagnose added: {}", diagnoseEventDTO);
        diagnoseEventDTO.setEventId(UUID.randomUUID());
        rabbitTemplate.convertAndSend(QueueConfig.QUEUE_API, JsonHelper.convertObjectToJson(diagnoseEventDTO));
        return ResponseEntity.ok(diagnoseEventDTO.getEventId());
    }

    @GetMapping("/welcome")
    @Override
    public ResponseEntity<String> welcome() {
        return ResponseEntity.ok("Welcome to BikeMed API");
    }
}
