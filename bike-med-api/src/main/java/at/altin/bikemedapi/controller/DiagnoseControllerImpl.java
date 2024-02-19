package at.altin.bikemedapi.controller;

import at.altin.bikemedapi.controller.api.DiagnoseController;
import at.altin.bikemedapi.dto.DiagnoseDTO;
import at.altin.bikemedapi.helper.JsonHelper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RequestMapping("api/diagnose")
@RestController
@CrossOrigin
@Slf4j
public class DiagnoseControllerImpl implements DiagnoseController {
    private final RabbitTemplate rabbitTemplate;

    @Value("${queue.api.name}")
    private String queueName;

    @Autowired
    public DiagnoseControllerImpl(RabbitTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
        this.rabbitTemplate.setMessageConverter(new Jackson2JsonMessageConverter());
    }

    @PostMapping
    @Override
    public ResponseEntity<UUID> addDiagnose(@RequestBody DiagnoseDTO diagnoseDTO) {
        log.info("Diagnose added: {}", diagnoseDTO);
        diagnoseDTO.setId(UUID.randomUUID());
        rabbitTemplate.convertAndSend(queueName, JsonHelper.convertObjectToJson(diagnoseDTO));
        return ResponseEntity.ok(diagnoseDTO.getId());
    }
}
