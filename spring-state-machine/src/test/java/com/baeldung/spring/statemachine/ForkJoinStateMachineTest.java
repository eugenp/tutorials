package com.baeldung.spring.stateMachine;

import com.baeldung.spring.stateMachine.config.ForkJoinStateMachineConfiguration;
import com.baeldung.spring.stateMachine.config.JunctionStateMachineConfiguration;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.statemachine.StateMachine;

import java.util.Arrays;

public class ForkJoinStateMachineTest {

    @Test
    public void testForkJoin() {
        AnnotationConfigApplicationContext ctx = new AnnotationConfigApplicationContext(ForkJoinStateMachineConfiguration.class);
        StateMachine stateMachine = ctx.getBean(StateMachine.class);
        stateMachine.start();

        boolean success = stateMachine.sendEvent("E1");

        Assert.assertTrue(success);

        Assert.assertTrue(Arrays.asList("SFork", "Sub1-1", "Sub2-1").containsAll(stateMachine.getState().getIds()));

        Assert.assertTrue(stateMachine.sendEvent("sub1"));
        Assert.assertTrue(stateMachine.sendEvent("sub2"));
        Assert.assertEquals("SJoin", stateMachine.getState().getId());
    }
}
