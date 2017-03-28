package com.baeldung.spring.statemachine;

import com.baeldung.spring.statemachine.config.ForkJoinStateMachineConfiguration;
import org.junit.Test;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.statemachine.StateMachine;

import java.util.Arrays;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class ForkJoinStateMachineTest {

    @Test
    public void whenForkStateEntered_thenMultipleSubStatesEntered() {
        AnnotationConfigApplicationContext ctx = new AnnotationConfigApplicationContext(ForkJoinStateMachineConfiguration.class);
        StateMachine stateMachine = ctx.getBean(StateMachine.class);
        stateMachine.start();

        boolean success = stateMachine.sendEvent("E1");

        assertTrue(success);

        assertTrue(Arrays.asList("SFork", "Sub1-1", "Sub2-1").containsAll(stateMachine.getState().getIds()));
    }

    @Test
    public void whenAllConfiguredJoinEntryStatesAreEntered_thenTransitionToJoinState() {
        AnnotationConfigApplicationContext ctx = new AnnotationConfigApplicationContext(ForkJoinStateMachineConfiguration.class);
        StateMachine stateMachine = ctx.getBean(StateMachine.class);
        stateMachine.start();

        boolean success = stateMachine.sendEvent("E1");

        assertTrue(success);

        assertTrue(Arrays.asList("SFork", "Sub1-1", "Sub2-1").containsAll(stateMachine.getState().getIds()));

        assertTrue(stateMachine.sendEvent("sub1"));
        assertTrue(stateMachine.sendEvent("sub2"));
        assertEquals("SJoin", stateMachine.getState().getId());
    }
}
