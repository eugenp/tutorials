package com.baeldung.algorithms.hillclimbing;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

public class State {
    List<Stack<String>> state;
    int heuristics;

    public State() {
    }

    public State(List<Stack<String>> state) {
        this.state = state;
    }

    public State(List<Stack<String>> state, int heuristics) {
        this.state = state;
        this.heuristics = heuristics;
    }

    public State(State state) {
        if (state != null) {
            this.state = new ArrayList<Stack<String>>();
            for (Stack s : state.getState()) {
                Stack s1 = new Stack();
                s1 = (Stack) s.clone();
                this.state.add(s1);
            }
            this.heuristics = state.getHeuristics();
        }
    }

    public List<Stack<String>> getState() {
        return state;
    }

    public void setState(List<Stack<String>> state) {
        this.state = state;
    }

    public int getHeuristics() {
        return heuristics;
    }

    public void setHeuristics(int heuristics) {
        this.heuristics = heuristics;
    }
}
