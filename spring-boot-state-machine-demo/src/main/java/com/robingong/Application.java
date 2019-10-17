package com.robingong;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.statemachine.StateMachine;
import org.springframework.statemachine.config.StateMachineFactory;

@SpringBootApplication
public class Application implements CommandLineRunner {

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }

    @Autowired
    private StateMachineFactory<States, Events> stateMachineFactory;

    @Override
    public void run(String... args) throws Exception {
        StateMachine<States, Events> stateMachine = stateMachineFactory.getStateMachine("111");
        stateMachine.start();
        MessageBuilder<Events> messageBuilder = MessageBuilder.withPayload(Events.E1)
                .setHeader("myHeader", "andMyValue");
        stateMachine.sendEvent(messageBuilder.build());
        stateMachine.sendEvent(Events.E2);
    }
}
