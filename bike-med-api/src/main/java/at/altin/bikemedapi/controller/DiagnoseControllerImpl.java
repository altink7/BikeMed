package at.altin.bikemedapi.controller;

import at.altin.bikemed.commons.config.ExchangeConfig;
import at.altin.bikemed.commons.dto.DiagnoseEventDTO;
import at.altin.bikemed.commons.helper.JsonHelper;
import at.altin.bikemedapi.controller.api.DiagnoseController;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RequiredArgsConstructor
@RequestMapping("api/diagnose")
@RestController
@CrossOrigin
@Slf4j
public class DiagnoseControllerImpl implements DiagnoseController {
    private final RabbitTemplate rabbitTemplate;

    @PostMapping
    @Override
    public ResponseEntity<UUID> addDiagnose(@RequestBody DiagnoseEventDTO diagnoseEventDTO) {
        log.info("Diagnose added: {}", diagnoseEventDTO);
        diagnoseEventDTO.setEventId(UUID.randomUUID());
        rabbitTemplate.convertAndSend(ExchangeConfig.API_DISPATCHER_EXCHANGE, JsonHelper.convertObjectToJson(diagnoseEventDTO));
        return ResponseEntity.ok(diagnoseEventDTO.getEventId());
    }

    @GetMapping("/welcome")
    @Override
    public ResponseEntity<String> welcome() {
        return ResponseEntity.ok("Welcome to BikeMed API");
    }
}
