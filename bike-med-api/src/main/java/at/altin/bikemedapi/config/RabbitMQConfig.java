package at.altin.bikemedapi.config;

import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.connection.CachingConnectionFactory;
import org.springframework.amqp.support.converter.DefaultClassMapper;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitMQConfig {

    @Value("${queue.api.name}")
    private String queueName;

    @Value("${spring.rabbitmq.address}")
    private String rabbitAddress;

    @Bean
    public Queue apiQueue() {
        return new Queue(queueName);
    }

    /**
     * This method is used to convert the message to JSON. <br>
     * and trust all packages.
     * @return Jackson2JsonMessageConverter
     */
    @Bean
    public MessageConverter jsonToMapMessageConverter() {
        DefaultClassMapper defaultClassMapper = new DefaultClassMapper();
        defaultClassMapper.setTrustedPackages("*");
        Jackson2JsonMessageConverter jackson2JsonMessageConverter = new Jackson2JsonMessageConverter();
        jackson2JsonMessageConverter.setClassMapper(defaultClassMapper);
        return jackson2JsonMessageConverter;
    }

    @Bean
    public CachingConnectionFactory rabbitConnectionFactory() {
        CachingConnectionFactory connectionFactory = new CachingConnectionFactory("goose.rmq2.cloudamqp.com");
        connectionFactory.setUsername("juglmawp");
        connectionFactory.setPassword("bg8I7Qo3zvto1wvrSksMJyRf56xuC7EX");
        connectionFactory.setVirtualHost("juglmawp");
        return connectionFactory;
    }
}

