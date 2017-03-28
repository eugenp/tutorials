package com.baeldung.spring.statemachine;

import com.baeldung.spring.statemachine.config.HierarchicalStateMachineConfiguration;
import org.junit.Test;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.statemachine.StateMachine;

import java.util.Arrays;

import static org.junit.Assert.assertEquals;


public class HierarchicalStateMachineTest {

    @Test
    public void whenTransitionToSubMachine_thenSubStateIsEntered() {
        AnnotationConfigApplicationContext ctx = new AnnotationConfigApplicationContext(HierarchicalStateMachineConfiguration.class);
        StateMachine stateMachine = ctx.getBean(StateMachine.class);
        stateMachine.start();


        assertEquals(Arrays.asList("SI", "SUB1"), stateMachine.getState().getIds());

        stateMachine.sendEvent("se1");

        assertEquals(Arrays.asList("SI", "SUB2"), stateMachine.getState().getIds());

        stateMachine.sendEvent("s-end");

        assertEquals(Arrays.asList("SI", "SUBEND"), stateMachine.getState().getIds());

        stateMachine.sendEvent("end");

        assertEquals(1, stateMachine.getState().getIds().size());
        assertEquals("SF", stateMachine.getState().getId());
    }
}
