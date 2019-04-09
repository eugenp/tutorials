package com.baeldung.algorithms.automata;

/**
 * Transition in a finite State machine.
 */
public interface Transition {

    /**
     * Is the transition possible with the given character?
     * @param c char.
     * @return true or false.
     */
    boolean isPossible(final CharSequence c);

    /**
     * The state to which this transition leads.
     * @return State.
     */
    State state();
}
