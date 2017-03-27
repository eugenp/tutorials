package com.baeldung.spring.statemachine.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.statemachine.config.EnableStateMachine;
import org.springframework.statemachine.config.StateMachineConfigurerAdapter;
import org.springframework.statemachine.config.builders.StateMachineConfigurationConfigurer;
import org.springframework.statemachine.config.builders.StateMachineStateConfigurer;
import org.springframework.statemachine.config.builders.StateMachineTransitionConfigurer;
import org.springframework.statemachine.guard.Guard;

@Configuration
@EnableStateMachine
public class ForkJoinStateMachineConfiguration extends StateMachineConfigurerAdapter<String, String> {

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
                .fork("SFork")
                .join("SJoin")
                .end("SF")
                .and()
                .withStates()
                    .parent("SFork")
                    .initial("Sub1-1")
                    .end("Sub1-2")
                .and()
                .withStates()
                    .parent("SFork")
                    .initial("Sub2-1")
                    .end("Sub2-2");
    }

    @Override
    public void configure(StateMachineTransitionConfigurer<String, String> transitions) throws Exception {
        transitions.withExternal()
                .source("SI").target("SFork").event("E1")
                .and().withExternal()
                    .source("Sub1-1").target("Sub1-2").event("sub1")
                .and().withExternal()
                    .source("Sub2-1").target("Sub2-2").event("sub2")
                .and()
                .withFork()
                    .source("SFork")
                    .target("Sub1-1")
                    .target("Sub2-1")
                .and()
                .withJoin()
                    .source("Sub1-2")
                    .source("Sub2-2")
                    .target("SJoin");
    }

    @Bean
    public Guard<String, String> mediumGuard() {
        return (ctx) -> false;
    }

    @Bean
    public Guard<String, String> highGuard() {
        return (ctx) -> false;
    }
}