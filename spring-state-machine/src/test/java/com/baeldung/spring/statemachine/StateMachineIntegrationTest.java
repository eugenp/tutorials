package com.baeldung.spring.statemachine;

import com.baeldung.spring.statemachine.config.SimpleStateMachineConfiguration;
import org.junit.Before;
import org.junit.Test;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.statemachine.StateMachine;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class StateMachineIntegrationTest {

    private AnnotationConfigApplicationContext ctx;
    private StateMachine stateMachine;

    @Before
    public void setUp() {
        ctx = new AnnotationConfigApplicationContext(SimpleStateMachineConfiguration.class);
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
    }

    @Test
    public void whenSimpleStringMachineActionState_thenActionExecuted() throws InterruptedException {

        stateMachine.sendEvent("E3");
        assertEquals("S3", stateMachine.getState().getId());

        boolean acceptedE4 = stateMachine.sendEvent("E4");

        assertTrue(acceptedE4);
        assertEquals("S4", stateMachine.getState().getId());
        assertEquals(2, stateMachine.getExtendedState().getVariables().get("approvalCount"));

        stateMachine.sendEvent("end");
        assertEquals("SF", stateMachine.getState().getId());
    }
}
