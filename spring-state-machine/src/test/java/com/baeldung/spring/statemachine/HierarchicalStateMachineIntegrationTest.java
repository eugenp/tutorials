package com.baeldung.spring.statemachine;

import com.baeldung.spring.statemachine.config.HierarchicalStateMachineConfiguration;
import com.baeldung.spring.statemachine.config.JunctionStateMachineConfiguration;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.statemachine.StateMachine;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.annotation.Resource;
import java.util.Arrays;

import static org.junit.Assert.assertEquals;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = HierarchicalStateMachineConfiguration.class)
public class HierarchicalStateMachineIntegrationTest {

    @Autowired
    private StateMachine<String, String> stateMachine;

    @Before
    public void setUp() {
        stateMachine.start();
    }

    @Test
    public void whenTransitionToSubMachine_thenSubStateIsEntered() {

        assertEquals(Arrays.asList("SI", "SUB1"), stateMachine.getState().getIds());

        stateMachine.sendEvent("se1");

        assertEquals(Arrays.asList("SI", "SUB2"), stateMachine.getState().getIds());

        stateMachine.sendEvent("s-end");

        assertEquals(Arrays.asList("SI", "SUBEND"), stateMachine.getState().getIds());

        stateMachine.sendEvent("end");

        assertEquals(1, stateMachine.getState().getIds().size());
        assertEquals("SF", stateMachine.getState().getId());
    }

    @After
    public void tearDown() {
        stateMachine.stop();
    }
}
