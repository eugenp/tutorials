package com.baeldung.algorithms.automata;

/**
 * Default implementation of a finite state machine.
 * This class is immutable and thread-safe.
 */
public final class RtFiniteStateMachine implements FiniteStateMachine {

    /**
     * Current state.
     */
    private State current;

    /**
     * Ctor.
     * @param initial Initial state of this machine.
     */
    public RtFiniteStateMachine(final State initial) {
        this.current = initial;
    }

    public FiniteStateMachine switchState(final CharSequence c) {
        return new RtFiniteStateMachine(this.current.transit(c));
    }

    public boolean canStop() {
        return this.current.isFinal();
    }

}
