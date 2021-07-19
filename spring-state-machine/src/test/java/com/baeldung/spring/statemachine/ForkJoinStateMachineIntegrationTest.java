package com.baeldung.spring.statemachine;

import com.baeldung.spring.statemachine.config.ForkJoinStateMachineConfiguration;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.statemachine.StateMachine;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.Arrays;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = ForkJoinStateMachineConfiguration.class)
public class ForkJoinStateMachineIntegrationTest {

    @Autowired
    private StateMachine<String, String> stateMachine;

    @Before
    public void setUp() {
        stateMachine.start();
    }

    @Test
    public void whenForkStateEntered_thenMultipleSubStatesEntered() {
        boolean success = stateMachine.sendEvent("E1");

        assertTrue(success);

        assertTrue(Arrays.asList("SFork", "Sub1-1", "Sub2-1").containsAll(stateMachine.getState().getIds()));
    }

    @Test
    public void whenAllConfiguredJoinEntryStatesAreEntered_thenTransitionToJoinState() {

        boolean success = stateMachine.sendEvent("E1");

        assertTrue(success);

        assertTrue(Arrays.asList("SFork", "Sub1-1", "Sub2-1").containsAll(stateMachine.getState().getIds()));

        assertTrue(stateMachine.sendEvent("sub1"));
        assertTrue(stateMachine.sendEvent("sub2"));
        assertEquals("SJoin", stateMachine.getState().getId());
    }

    @After
    public void tearDown() {
        stateMachine.stop();
    }
}
