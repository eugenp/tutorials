package com.baeldung.spring.stateMachine.config;

import java.util.Arrays;
import java.util.HashSet;
import java.util.logging.Logger;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.statemachine.action.Action;
import org.springframework.statemachine.config.EnableStateMachine;
import org.springframework.statemachine.config.StateMachineConfigurerAdapter;
import org.springframework.statemachine.config.builders.StateMachineConfigurationConfigurer;
import org.springframework.statemachine.config.builders.StateMachineStateConfigurer;
import org.springframework.statemachine.config.builders.StateMachineTransitionConfigurer;
import org.springframework.statemachine.guard.Guard;

@Configuration
@EnableStateMachine
public class SimpleStateMachineConfiguration extends StateMachineConfigurerAdapter<String, String> {

    public static final Logger LOGGER = Logger.getLogger(SimpleStateMachineConfiguration.class.getName());

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
                .end("SF")
                .states(new HashSet<>(Arrays.asList("S1", "S2")))
                .state("S4", executeAction(), errorAction())
                .stateEntry("S3", entryAction())
                .stateDo("S3", executeAction())
                .stateExit("S3", exitAction());

    }

    @Override
    public void configure(StateMachineTransitionConfigurer<String, String> transitions) throws Exception {
        transitions.withExternal()
                .source("SI").target("S1").event("E1").action(initAction())
                .and().withExternal()
                .source("S1").target("S2").event("E2")
                .and().withExternal()
                .source("SI").target("S3").event("E3")
                .and().withExternal()
                .source("S3").target("S4").event("E4").and().withExternal().source("S4").target("SF").event("end").guard(simpleGuard())
                .and().withExternal()
                .source("S2").target("SF").event("end");
    }

    @Bean
    public Guard<String, String> simpleGuard() {
        return (ctx) -> {
            int approvalCount = (int) ctx.getExtendedState().getVariables().getOrDefault("approvalCount", 0);
            return approvalCount > 0;
        };
    }

    @Bean
    public Action<String, String> entryAction() {
        return (ctx) -> {
            LOGGER.info("Entry " + ctx.getTarget().getId());
        };
    }

    @Bean
    public Action<String, String> executeAction() {
        return (ctx) -> {
            LOGGER.info("Do " + ctx.getTarget().getId());
            int approvals = (int) ctx.getExtendedState().getVariables().getOrDefault("approvalCount", 0);
            approvals++;
            ctx.getExtendedState().getVariables().put("approvalCount", approvals);
        };
    }

    @Bean
    public Action<String, String> exitAction() {
        return (ctx) -> {
            LOGGER.info("Exit " + ctx.getSource().getId() + " -> " + ctx.getTarget().getId());
        };
    }

    @Bean
    public Action<String, String> errorAction() {
        return (ctx) -> {
            LOGGER.info("Error " + ctx.getSource().getId() + ctx.getException());
        };
    }

    @Bean
    public Action<String, String> initAction() {
        return (ctx) -> {
            LOGGER.info(ctx.getTarget().getId());
        };
    }
}