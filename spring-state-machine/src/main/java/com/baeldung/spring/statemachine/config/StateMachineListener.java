package com.baeldung.spring.statemachine.config;

import org.springframework.statemachine.listener.StateMachineListenerAdapter;
import org.springframework.statemachine.state.State;

import java.util.logging.Logger;

public class StateMachineListener extends StateMachineListenerAdapter {

    public static final Logger LOGGER = Logger.getLogger(StateMachineListener.class.getName());

    @Override
    public void stateChanged(State from, State to) {
        LOGGER.info(String.format("Transitioned from %s to %s%n", from == null ? "none" : from.getId(), to.getId()));
    }
}
