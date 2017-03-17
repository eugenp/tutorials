package com.baeldung.spring.stateMachine.config;

import org.springframework.statemachine.listener.StateMachineListenerAdapter;
import org.springframework.statemachine.state.State;

public class StateMachineListener extends StateMachineListenerAdapter {
    @Override
    public void stateChanged(State from, State to) {
        System.out.printf("Transitioned from %s to %s%n", from == null ? "none" : from.getId(), to.getId());
    }
}
