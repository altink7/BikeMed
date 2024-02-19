package at.altin.bikemedlager.service;

import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Service;

@Service
public class LagerService {

    private final RabbitTemplate rabbitTemplate;

    public LagerService(RabbitTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
    }

    @RabbitListener(queues = "${queue.dispatcher.name}")
    public void handleDiagnoseEvent(String diagnoseEventDTO) {
        System.out.println("Received diagnose event: " + diagnoseEventDTO);
    }
}
