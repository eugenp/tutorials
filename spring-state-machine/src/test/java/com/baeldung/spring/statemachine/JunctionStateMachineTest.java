package com.baeldung.spring.stateMachine;

import com.baeldung.spring.stateMachine.config.HierarchicalStateMachineConfiguration;
import com.baeldung.spring.stateMachine.config.JunctionStateMachineConfiguration;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.statemachine.StateMachine;

import java.util.Arrays;

public class JunctionStateMachineTest {

    @Test
    public void testHierarchy() {
        AnnotationConfigApplicationContext ctx = new AnnotationConfigApplicationContext(JunctionStateMachineConfiguration.class);
        StateMachine stateMachine = ctx.getBean(StateMachine.class);
        stateMachine.start();

        stateMachine.sendEvent("E1");
        Assert.assertEquals("low", stateMachine.getState().getId());

        stateMachine.sendEvent("end");
        Assert.assertEquals("SF", stateMachine.getState().getId());

    }
}
