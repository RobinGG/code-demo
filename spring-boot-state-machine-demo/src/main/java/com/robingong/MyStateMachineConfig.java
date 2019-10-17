package com.robingong;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.statemachine.action.Action;
import org.springframework.statemachine.config.EnableStateMachineFactory;
import org.springframework.statemachine.config.EnumStateMachineConfigurerAdapter;
import org.springframework.statemachine.config.builders.StateMachineConfigurationConfigurer;
import org.springframework.statemachine.config.builders.StateMachineStateConfigurer;
import org.springframework.statemachine.config.builders.StateMachineTransitionConfigurer;
import org.springframework.statemachine.listener.StateMachineListener;
import org.springframework.statemachine.listener.StateMachineListenerAdapter;
import org.springframework.statemachine.transition.Transition;

import java.util.EnumSet;

@Configuration
@EnableStateMachineFactory
public class MyStateMachineConfig extends EnumStateMachineConfigurerAdapter<States, Events> {

    @Override
    public void configure(StateMachineStateConfigurer<States, Events> states) throws Exception {
        states.withStates()
                .initial(States.SI)
                .states(EnumSet.allOf(States.class));
    }

    @Override
    public void configure(StateMachineTransitionConfigurer<States, Events> transitions) throws Exception {
        transitions
                .withExternal()
                .source(States.SI).target(States.S1)
                .event(Events.E1).action(to1())
                .and()
                .withExternal()
                .source(States.S1).target(States.S2)
                .event(Events.E2).action(to2());
    }

    @Override
    public void configure(StateMachineConfigurationConfigurer<States, Events> config) throws Exception {
        config.withConfiguration()
                .machineId("myStateMachine")
                .listener(listener());
    }

    public Action<States, Events> to1() {
        return context -> {
            System.out.println("[Action] Status Initiated --> 1");
            System.out.println(context.getMessageHeader("myHeader"));
        };
    }

    public Action<States, Events> to2() {
        return context -> System.out.println("[Action] Status 1 --> 2");
    }

    @Bean
    public StateMachineListener<States, Events> listener() {
        return new StateMachineListenerAdapter<States, Events>() {

            @Override
            public void transition(Transition<States, Events> transition) {
                if (transition.getTarget().getId() == States.SI) {
                    System.out.println("[Listener] Status Initiated");
                    return;
                }
                if (transition.getSource().getId() == States.SI && transition.getTarget().getId() == States.S1) {
                    System.out.println("[Listener] Status Initiated --> 1");
                    return;
                }
                if (transition.getSource().getId() == States.S1 && transition.getTarget().getId() == States.S2) {
                    System.out.println("[Listener] Status 1 --> 2");
                }
            }
        };
    }

}
