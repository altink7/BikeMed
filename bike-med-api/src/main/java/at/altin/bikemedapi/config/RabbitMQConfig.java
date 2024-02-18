package at.altin.bikemedapi.config;

import org.springframework.amqp.core.Queue;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitMQConfig {

    @Value("${queue.name}")
    public static String queueName;

    @Bean
    public Queue diagnosisQueue() {
        return new Queue(queueName);
    }
}

