package com.baeldung.spring.stateMachine;

import com.baeldung.spring.stateMachine.config.SimpleStateMachineConfiguration;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.statemachine.StateMachine;

public class StateMachineTest {

    private StateMachine stateMachine;

    @Before
    public void setUp() {
        AnnotationConfigApplicationContext ctx = new AnnotationConfigApplicationContext(SimpleStateMachineConfiguration.class);
        stateMachine = ctx.getBean(StateMachine.class);
        stateMachine.start();
    }

    @Test
    public void testSimpleStringMachine() {
        Assert.assertEquals("SI", stateMachine.getState().getId());

        stateMachine.sendEvent("E1");
        Assert.assertEquals("S1", stateMachine.getState().getId());

        stateMachine.sendEvent("E2");
        Assert.assertEquals("S2", stateMachine.getState().getId());

        stateMachine.sendEvent("end");
        Assert.assertEquals("SF", stateMachine.getState().getId());

    }

    @Test
    public void testSimpleStringMachineAction() {
        stateMachine.sendEvent("E3");
        Assert.assertEquals("S3", stateMachine.getState().getId());

        stateMachine.sendEvent("E4");
        Assert.assertEquals("S4", stateMachine.getState().getId());

    }
}
