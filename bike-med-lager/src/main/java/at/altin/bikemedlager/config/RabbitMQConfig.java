package at.altin.bikemedlager.config;

import org.springframework.amqp.core.Queue;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitMQConfig {

    @Value("${queue.lager.name}")
    private String queueName;

    @Bean
    public Queue lagerQueue() {
        return new Queue(queueName);
    }
}

