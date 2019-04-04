package com.baeldung.algorithms.automata;

/**
 * Finite state machine.
 */
public interface FiniteStateMachine {

    /**
     * Follow a transition, switch the state of the machine.
     * @param c Char.
     * @return A new finite state machine with the new state.
     */
    FiniteStateMachine switchState(final CharSequence c);

    /**
     * Is the current state a final one?
     * @return true or false.
     */
    boolean canStop();
}
