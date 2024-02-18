package at.altin.bikemedapi.controller;

import at.altin.bikemedapi.config.RabbitMQConfig;
import at.altin.bikemedapi.controller.api.DiagnoseController;
import at.altin.bikemedapi.dto.DiagnoseDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequestMapping("api/diagnose")
@RestController
@CrossOrigin
@Slf4j
public class DiagnoseControllerImpl implements DiagnoseController {
    private final RabbitTemplate rabbitTemplate;

    @Autowired
    public DiagnoseControllerImpl(RabbitTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
    }

    @PostMapping
    @Override
    public ResponseEntity<Boolean> addDiagnose(@RequestBody DiagnoseDTO diagnoseDTO) {
        log.info("Diagnose added: {}", diagnoseDTO);
        rabbitTemplate.convertAndSend(RabbitMQConfig.queueName, diagnoseDTO);
        return ResponseEntity.ok(true);
    }
}
