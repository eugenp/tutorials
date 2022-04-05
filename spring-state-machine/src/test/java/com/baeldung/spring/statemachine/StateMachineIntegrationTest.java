package com.baeldung.spring.statemachine;

import com.baeldung.spring.statemachine.config.SimpleStateMachineConfiguration;
import org.junit.After;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.statemachine.StateMachine;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = SimpleStateMachineConfiguration.class)
public class StateMachineIntegrationTest {

    @Autowired
    private StateMachine<String, String> stateMachine;

    @Before
    public void setUp() {
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

    @Ignore("Fixing in JAVA-9808")
    @Test
    public void whenSimpleStringMachineActionState_thenActionExecuted() {

        stateMachine.sendEvent("E3");
        assertEquals("S3", stateMachine.getState().getId());

        boolean acceptedE4 = stateMachine.sendEvent("E4");

        assertTrue(acceptedE4);
        assertEquals("S4", stateMachine.getState().getId());

        stateMachine.sendEvent("E5");
        assertEquals("S5", stateMachine.getState().getId());

        stateMachine.sendEvent("end");
        assertEquals("SF", stateMachine.getState().getId());

        assertEquals(2, stateMachine.getExtendedState().getVariables().get("approvalCount"));
    }

    @After
    public void tearDown() {
        stateMachine.stop();
    }
}
