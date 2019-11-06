package com.robingong;

import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

import static com.robingong.RabbitConstant.HELLO_QUEUE;

/**
 * @author Robin
 * @date 2019/11/6
 */
@Slf4j
@Component
@RabbitListener(queues = HELLO_QUEUE)
public class MessageConsumer {

    @RabbitHandler
    public void consume(String hello) {
        log.info("Message consume: {}", hello);
    }

}
