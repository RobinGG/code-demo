package com.robingong;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.time.Instant;

/**
 * @author Robin
 * @date 2019/11/6
 */
@Slf4j
@SpringBootApplication
public class Application implements CommandLineRunner {

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }

    @Autowired
    private MessageProducer messageProducer;

    @Override
    public void run(String... args) throws Exception {
        log.info("Spring-boot integrate with RabbitMQ demo.");
        Instant now = Instant.now();
        log.info("Sending message: {}", now.toString());
        messageProducer.send(now.toString());
    }
}
