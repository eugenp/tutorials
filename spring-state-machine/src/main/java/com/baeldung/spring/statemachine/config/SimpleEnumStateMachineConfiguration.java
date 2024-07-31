package com.baeldung.spring.statemachine.config;

import com.baeldung.spring.statemachine.applicationreview.ApplicationReviewEvents;
import com.baeldung.spring.statemachine.applicationreview.ApplicationReviewStates;
import org.springframework.context.annotation.Configuration;
import org.springframework.statemachine.config.EnableStateMachine;
import org.springframework.statemachine.config.StateMachineConfigurerAdapter;
import org.springframework.statemachine.config.builders.StateMachineConfigurationConfigurer;
import org.springframework.statemachine.config.builders.StateMachineStateConfigurer;
import org.springframework.statemachine.config.builders.StateMachineTransitionConfigurer;

@Configuration
@EnableStateMachine
public class SimpleEnumStateMachineConfiguration extends StateMachineConfigurerAdapter<ApplicationReviewStates, ApplicationReviewEvents> {

    @Override
    public void configure(StateMachineConfigurationConfigurer<ApplicationReviewStates, ApplicationReviewEvents> config)
            throws Exception {
        config
                .withConfiguration()
                .autoStartup(true)
                .listener(new StateMachineListener());
    }

    @Override
    public void configure(StateMachineStateConfigurer<ApplicationReviewStates, ApplicationReviewEvents> states) throws Exception {
        states
                .withStates()
                .initial(ApplicationReviewStates.PEER_REVIEW)
                .state(ApplicationReviewStates.PRINCIPAL_REVIEW)
                .end(ApplicationReviewStates.APPROVED)
                .end(ApplicationReviewStates.REJECTED);

    }

    @Override
    public void configure(StateMachineTransitionConfigurer<ApplicationReviewStates, ApplicationReviewEvents> transitions) throws Exception {
        transitions.withExternal()
                .source(ApplicationReviewStates.PEER_REVIEW).target(ApplicationReviewStates.PRINCIPAL_REVIEW).event(ApplicationReviewEvents.APPROVE)
                .and().withExternal()
                .source(ApplicationReviewStates.PRINCIPAL_REVIEW).target(ApplicationReviewStates.APPROVED).event(ApplicationReviewEvents.APPROVE)
                .and().withExternal()
                .source(ApplicationReviewStates.PEER_REVIEW).target(ApplicationReviewStates.REJECTED).event(ApplicationReviewEvents.REJECT)
                .and().withExternal()
                .source(ApplicationReviewStates.PRINCIPAL_REVIEW).target(ApplicationReviewStates.REJECTED).event(ApplicationReviewEvents.REJECT);
    }
}