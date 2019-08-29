package com.baeldung.spring.statemachine;

import com.baeldung.spring.statemachine.config.JunctionStateMachineConfiguration;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.statemachine.StateMachine;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = JunctionStateMachineConfiguration.class)
public class JunctionStateMachineIntegrationTest {

    @Autowired
    private StateMachine<String, String> stateMachine;

    @Before
    public void setUp() {
        stateMachine.start();
    }

    @Test
    public void whenTransitioningToJunction_thenArriveAtSubJunctionNode() {

        stateMachine.sendEvent("E1");
        Assert.assertEquals("low", stateMachine.getState().getId());

        stateMachine.sendEvent("end");
        Assert.assertEquals("SF", stateMachine.getState().getId());
    }

    @After
    public void tearDown() {
        stateMachine.stop();
    }
}
