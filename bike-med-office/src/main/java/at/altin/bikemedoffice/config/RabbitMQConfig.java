package at.altin.bikemedoffice.config;

import at.altin.bikemedcommons.config.CommonRabbitMQConfig;
import org.springframework.amqp.core.Queue;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitMQConfig extends CommonRabbitMQConfig {

    @Value("${queue.office.name}")
    private String queueName;

    @Bean
    public Queue officeQueue() {
        return new Queue(queueName);
    }
}

