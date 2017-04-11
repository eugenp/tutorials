package com.baeldung.spring.statemachine.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.statemachine.config.EnableStateMachine;
import org.springframework.statemachine.config.StateMachineConfigurerAdapter;
import org.springframework.statemachine.config.builders.StateMachineConfigurationConfigurer;
import org.springframework.statemachine.config.builders.StateMachineStateConfigurer;
import org.springframework.statemachine.config.builders.StateMachineTransitionConfigurer;

@Configuration
@EnableStateMachine
public class HierarchicalStateMachineConfiguration extends StateMachineConfigurerAdapter<String, String> {

    @Override
    public void configure(StateMachineConfigurationConfigurer<String, String> config)
            throws Exception {
        config
                .withConfiguration()
                .autoStartup(true)
                .listener(new StateMachineListener());
    }

    @Override
    public void configure(StateMachineStateConfigurer<String, String> states) throws Exception {
        states
                .withStates()
                .initial("SI")
                .state("SI")
                .end("SF")
                .and()
                .withStates()
                    .parent("SI")
                    .initial("SUB1")
                    .state("SUB2")
                    .end("SUBEND");
    }

    @Override
    public void configure(StateMachineTransitionConfigurer<String, String> transitions) throws Exception {
        transitions.withExternal()
                .source("SI").target("SF").event("end")
                .and().withExternal()
                .source("SUB1").target("SUB2").event("se1")
                .and().withExternal()
                .source("SUB2").target("SUBEND").event("s-end");
    }
}