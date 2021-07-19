package com.baeldung.algorithms.automata;

import java.util.ArrayList;
import java.util.List;

/**
 * State in a finite state machine.
 */
public final class RtState implements State {

    private List<Transition> transitions;
    private boolean isFinal;

    public RtState() {
        this(false);
    }
    
    public RtState(final boolean isFinal) {
        this.transitions = new ArrayList<>();
        this.isFinal = isFinal;
    }

    public State transit(final CharSequence c) {
        return transitions
          .stream()
          .filter(t -> t.isPossible(c))
          .map(Transition::state)
          .findAny()
          .orElseThrow(() -> new IllegalArgumentException("Input not accepted: " + c));
    }

    public boolean isFinal() {
        return this.isFinal;
    }

    @Override
    public State with(Transition tr) {
        this.transitions.add(tr);
        return this;
    }

}
