package com.robingong;

/**
 * @author Robin
 * @date 2019/11/6
 */
public interface RabbitConstant {
    String COMMON_PREFIX = "com.robingong:spring-boot-rabbit-demo:";
    String EXCHANGE_PREFIX = COMMON_PREFIX + "exchange:";
    String QUEUE_PREFIX = COMMON_PREFIX + "queue:";

    String HELLO_EXCHANGE = EXCHANGE_PREFIX + "hello";
    String HELLO_QUEUE = QUEUE_PREFIX + "hello";
}
