package at.altin.bikemedwerkstatt.config;

import at.altin.bikemedcommons.config.CommonRabbitMQConfig;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.support.converter.DefaultClassMapper;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.converter.MappingJackson2MessageConverter;

@Configuration
public class RabbitMQConfig extends CommonRabbitMQConfig {

    @Value("${queue.werkstatt.name}")
    private String queueName;

    @Bean
    public Queue werkstattQueue() {
        return new Queue(queueName);
    }
}

