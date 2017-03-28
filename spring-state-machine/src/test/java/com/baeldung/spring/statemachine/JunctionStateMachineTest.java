package com.baeldung.spring.statemachine;

import org.junit.Assert;
import org.junit.Test;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.statemachine.StateMachine;


public class JunctionStateMachineTest {

    @Test
    public void whenTransitioningToJunction_thenArriveAtSubJunctionNode() {
        AnnotationConfigApplicationContext ctx = new AnnotationConfigApplicationContext(com.baeldung.spring.statemachine.config.JunctionStateMachineConfiguration.class);
        StateMachine stateMachine = ctx.getBean(StateMachine.class);
        stateMachine.start();

        stateMachine.sendEvent("E1");
        Assert.assertEquals("low", stateMachine.getState().getId());

        stateMachine.sendEvent("end");
        Assert.assertEquals("SF", stateMachine.getState().getId());
    }
}
