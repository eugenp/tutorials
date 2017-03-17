package com.baeldung.spring.stateMachine;

import com.baeldung.spring.stateMachine.applicationReview.ApplicationReviewEvents;
import com.baeldung.spring.stateMachine.applicationReview.ApplicationReviewStates;
import com.baeldung.spring.stateMachine.config.SimpleEnumStateMachineConfiguration;
import com.baeldung.spring.stateMachine.config.SimpleStateMachineConfiguration;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.statemachine.StateMachine;

public class StateEnumMachineTest {

    private StateMachine stateMachine;

    @Before
    public void setUp() {
        AnnotationConfigApplicationContext ctx = new AnnotationConfigApplicationContext(SimpleEnumStateMachineConfiguration.class);
        stateMachine = ctx.getBean(StateMachine.class);
        stateMachine.start();
    }

    @Test
    public  void testStateMachine() {
        Assert.assertTrue(stateMachine.sendEvent(ApplicationReviewEvents.APPROVE));
        Assert.assertEquals(ApplicationReviewStates.PRINCIPAL_REVIEW, stateMachine.getState().getId());
        Assert.assertTrue(stateMachine.sendEvent(ApplicationReviewEvents.REJECT));
        Assert.assertEquals(ApplicationReviewStates.REJECTED, stateMachine.getState().getId());
    }
}
