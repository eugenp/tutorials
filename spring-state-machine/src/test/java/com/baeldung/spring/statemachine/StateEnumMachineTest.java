package com.baeldung.spring.stateMachine;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.Test;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.statemachine.StateMachine;

import com.baeldung.spring.stateMachine.applicationReview.ApplicationReviewEvents;
import com.baeldung.spring.stateMachine.applicationReview.ApplicationReviewStates;
import com.baeldung.spring.stateMachine.config.SimpleEnumStateMachineConfiguration;

public class StateEnumMachineTest {

    private StateMachine stateMachine;

    @Before
    public void setUp() {
        AnnotationConfigApplicationContext ctx = new AnnotationConfigApplicationContext(SimpleEnumStateMachineConfiguration.class);
        stateMachine = ctx.getBean(StateMachine.class);
        stateMachine.start();
    }

    @Test
    public void whenStateMachineConfiguredWithEnums_thenStateMachineAcceptsEnumEvents() {
        assertTrue(stateMachine.sendEvent(ApplicationReviewEvents.APPROVE));
        assertEquals(ApplicationReviewStates.PRINCIPAL_REVIEW, stateMachine.getState().getId());
        assertTrue(stateMachine.sendEvent(ApplicationReviewEvents.REJECT));
        assertEquals(ApplicationReviewStates.REJECTED, stateMachine.getState().getId());
    }
}
