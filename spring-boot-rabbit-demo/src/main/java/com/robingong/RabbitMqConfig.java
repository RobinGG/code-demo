package com.robingong;

import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.config.SimpleRabbitListenerContainerFactory;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.rabbit.listener.RabbitListenerContainerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

import static com.robingong.RabbitConstant.HELLO_EXCHANGE;
import static com.robingong.RabbitConstant.HELLO_QUEUE;

/**
 * @author Robin
 * @date 2019/11/6
 */
@Slf4j
@Configuration
public class RabbitMqConfig {

    @Bean
    public RabbitListenerContainerFactory rabbitListenerContainerFactory(ConnectionFactory connectionFactory) {
        SimpleRabbitListenerContainerFactory rabbitListenerContainerFactory =
                new SimpleRabbitListenerContainerFactory();
        rabbitListenerContainerFactory.setConnectionFactory(connectionFactory);
        rabbitListenerContainerFactory.setAcknowledgeMode(AcknowledgeMode.MANUAL);
        int concurrency = Runtime.getRuntime().availableProcessors();
        rabbitListenerContainerFactory.setConcurrentConsumers(concurrency);
        rabbitListenerContainerFactory.setMaxConcurrentConsumers(concurrency * 2);
        rabbitListenerContainerFactory.setPrefetchCount(10);
        return rabbitListenerContainerFactory;
    }

    @Bean
    @Primary
    @Autowired
    public RabbitTemplate rabbitTemplate(ConnectionFactory connectionFactory) {
        RabbitTemplate rabbitTemplate = new RabbitTemplate();
        rabbitTemplate.setConnectionFactory(connectionFactory);
        rabbitTemplate.setConfirmCallback((correlationData, ack, cause) -> {
            if (!ack) {
                // Do something here
                log.warn("Message send to MQ failed with id: {}", correlationData.getId());
            }
        });
        return rabbitTemplate;
    }

    @Bean
    public DirectExchange helloExchange() {
        return new DirectExchange(HELLO_EXCHANGE);
    }

    @Bean
    public Queue helloQueue() {
        return new Queue(HELLO_QUEUE);
    }

    @Bean
    public Binding queueBinding(Queue helloQueue, DirectExchange helloExchange) {
        return BindingBuilder.bind(helloQueue).to(helloExchange).withQueueName();
    }

}
