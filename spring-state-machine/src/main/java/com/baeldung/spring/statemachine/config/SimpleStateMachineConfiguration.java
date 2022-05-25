package com.baeldung.spring.statemachine.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.statemachine.action.Action;
import org.springframework.statemachine.config.EnableStateMachine;
import org.springframework.statemachine.config.StateMachineConfigurerAdapter;
import org.springframework.statemachine.config.builders.StateMachineConfigurationConfigurer;
import org.springframework.statemachine.config.builders.StateMachineStateConfigurer;
import org.springframework.statemachine.config.builders.StateMachineTransitionConfigurer;
import org.springframework.statemachine.guard.Guard;

import java.util.Arrays;
import java.util.HashSet;
import java.util.logging.Logger;

@Configuration
@EnableStateMachine
public class SimpleStateMachineConfiguration extends StateMachineConfigurerAdapter<String, String> {

    private static final Logger LOGGER = Logger.getLogger(SimpleStateMachineConfiguration.class.getName());

    @Override
    public void configure(StateMachineConfigurationConfigurer<String, String> config) throws Exception {
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
          .stateEntry("S3", entryAction())
          .stateExit("S3", exitAction())
          .state("S4", executeAction(), errorAction())
          .state("S5", executeAction(), errorAction());

    }

    @Override
    public void configure(StateMachineTransitionConfigurer<String, String> transitions) throws Exception {
        transitions
          .withExternal()
          .source("SI")
          .target("S1")
          .event("E1")
          .action(initAction())
          .and()
          .withExternal()
          .source("S1")
          .target("S2")
          .event("E2")
          .and()
          .withExternal()
          .source("SI")
          .target("S3")
          .event("E3")
          .and()
          .withExternal()
          .source("S3")
          .target("S4")
          .event("E4")
          .and()
          .withExternal()
          .source("S4")
          .target("S5")
          .event("E5")
          .and()
          .withExternal()
          .source("S5")
          .target("SF")
          .event("end")
          .guard(simpleGuard());
    }

    @Bean
    public Guard<String, String> simpleGuard() {
        return ctx -> {
            int approvalCount = (int) ctx
              .getExtendedState()
              .getVariables()
              .getOrDefault("approvalCount", 0);
            return approvalCount > 0;
        };
    }

    @Bean
    public Action<String, String> entryAction() {
        return ctx -> LOGGER.info("Entry " + ctx
          .getTarget()
          .getId());
    }

    @Bean
    public Action<String, String> doAction() {
        return ctx -> LOGGER.info("Do " + ctx
          .getTarget()
          .getId());
    }

    @Bean
    public Action<String, String> executeAction() {
        return ctx -> {
            LOGGER.info("Execute " + ctx
              .getTarget()
              .getId());
            int approvals = (int) ctx
              .getExtendedState()
              .getVariables()
              .getOrDefault("approvalCount", 0);
            approvals++;
            ctx
              .getExtendedState()
              .getVariables()
              .put("approvalCount", approvals);
        };
    }

    @Bean
    public Action<String, String> exitAction() {
        return ctx -> LOGGER.info("Exit " + ctx
          .getSource()
          .getId() + " -> " + ctx
          .getTarget()
          .getId());
    }

    @Bean
    public Action<String, String> errorAction() {
        return ctx -> LOGGER.info("Error " + ctx
          .getSource()
          .getId() + ctx.getException());
    }

    @Bean
    public Action<String, String> initAction() {
        return ctx -> LOGGER.info(ctx
          .getTarget()
          .getId());
    }
}