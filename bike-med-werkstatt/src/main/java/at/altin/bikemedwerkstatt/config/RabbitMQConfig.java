package at.altin.bikemedwerkstatt.config;

import at.altin.bikemed.commons.config.ExchangeConfig;
import at.altin.bikemed.commons.config.QueueTestVariables;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.connection.CachingConnectionFactory;
import org.springframework.amqp.support.converter.DefaultClassMapper;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitMQConfig {

    @Bean
    public Queue werkstattQueue() {
        return new Queue(ExchangeConfig.WERKSTATT_DISPATCHER_EXCHANGE);
    }

    /**
     * This method is used to convert the message to JSON. <br>
     * and trust all packages.
     *
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
        CachingConnectionFactory connectionFactory = new CachingConnectionFactory(QueueTestVariables.HOSTNAME);
        connectionFactory.setUsername(QueueTestVariables.USERNAME);
        connectionFactory.setPassword(QueueTestVariables.PASSWORD);
        connectionFactory.setVirtualHost(QueueTestVariables.VIRTUAL_HOST);
        return connectionFactory;
    }
}

