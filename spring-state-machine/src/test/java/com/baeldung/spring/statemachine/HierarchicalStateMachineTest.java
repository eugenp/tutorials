package com.baeldung.spring.stateMachine;

import com.baeldung.spring.stateMachine.config.HierarchicalStateMachineConfiguration;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.statemachine.StateMachine;

import java.util.Arrays;

public class HierarchicalStateMachineTest {

    @Test
    public void testHierarchy() {
        AnnotationConfigApplicationContext ctx = new AnnotationConfigApplicationContext(HierarchicalStateMachineConfiguration.class);
        StateMachine stateMachine = ctx.getBean(StateMachine.class);
        stateMachine.start();


        Assert.assertEquals(Arrays.asList("SI", "SUB1"), stateMachine.getState().getIds());

        stateMachine.sendEvent("se1");

        Assert.assertEquals(Arrays.asList("SI", "SUB2"), stateMachine.getState().getIds());

        stateMachine.sendEvent("s-end");

        Assert.assertEquals(Arrays.asList("SI", "SUBEND"), stateMachine.getState().getIds());

        stateMachine.sendEvent("end");

        Assert.assertEquals(1, stateMachine.getState().getIds().size());
        Assert.assertEquals("SF", stateMachine.getState().getId());
    }
}
