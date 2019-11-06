package com.robingong;

import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.connection.CorrelationData;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import static com.robingong.RabbitConstant.HELLO_EXCHANGE;
import static com.robingong.RabbitConstant.HELLO_QUEUE;

/**
 * @author Robin
 * @date 2019/11/6
 */
@Slf4j
@Component
public class MessageProducer {

    @Autowired
    private RabbitTemplate rabbitTemplate;

    public void send(String context) {
        log.info("Produce message: {}", context);
        rabbitTemplate.convertAndSend(HELLO_EXCHANGE, HELLO_QUEUE, context,
                message -> {
                    message.getMessageProperties().setHeader("someHeader", "someValue");
                    return message;
                },
                new CorrelationData(String.valueOf(System.currentTimeMillis())));
    }

}
