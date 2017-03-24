package com.baeldung.spring.stateMachine;

import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.statemachine.StateMachine;

import com.baeldung.spring.stateMachine.config.SimpleStateMachineConfiguration;

public class StateMachineTest {

    private StateMachine stateMachine;

    @Before
    public void setUp() {
        AnnotationConfigApplicationContext ctx = new AnnotationConfigApplicationContext(SimpleStateMachineConfiguration.class);
        stateMachine = ctx.getBean(StateMachine.class);
        stateMachine.start();
    }

    @Test
    public void whenSimpleStringStateMachineEvents_thenEndState() {
        assertEquals("SI", stateMachine.getState().getId());

        stateMachine.sendEvent("E1");
        assertEquals("S1", stateMachine.getState().getId());

        stateMachine.sendEvent("E2");
        assertEquals("S2", stateMachine.getState().getId());

        stateMachine.sendEvent("end");
        assertEquals("SF", stateMachine.getState().getId());

    }

    @Test
    public void whenSimpleStringMachineActionState_thenActionExecuted() {
        stateMachine.sendEvent("E3");
        assertEquals("S3", stateMachine.getState().getId());

        stateMachine.sendEvent("E4");
        assertEquals("S4", stateMachine.getState().getId());
        assertEquals(2, stateMachine.getExtendedState().getVariables().get("approvalCount"));
    }
}
