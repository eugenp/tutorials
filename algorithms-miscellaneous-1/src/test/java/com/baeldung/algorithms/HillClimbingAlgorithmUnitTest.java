package com.baeldung.algorithms;

import com.baeldung.algorithms.hillclimbing.HillClimbing;
import com.baeldung.algorithms.hillclimbing.State;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

public class HillClimbingAlgorithmUnitTest {
    private Stack<String> initStack;
    private Stack<String> goalStack;

    @Before
    public void initStacks() {
        String blockArr[] = { "B", "C", "D", "A" };
        String goalBlockArr[] = { "A", "B", "C", "D" };
        initStack = new Stack<>();
        for (String block : blockArr)
            initStack.push(block);
        goalStack = new Stack<>();
        for (String block : goalBlockArr)
            goalStack.push(block);
    }

    @Test
    public void givenInitAndGoalState_whenGetPathWithHillClimbing_thenPathFound() {
        HillClimbing hillClimbing = new HillClimbing();

        List<State> path;
        try {
            path = hillClimbing.getRouteWithHillClimbing(initStack, goalStack);
            assertNotNull(path);
            assertEquals(path.get(path.size() - 1)
                .getState()
                .get(0), goalStack);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    public void givenCurrentState_whenFindNextState_thenBetterHeuristics() {
        HillClimbing hillClimbing = new HillClimbing();
        List<Stack<String>> initList = new ArrayList<>();
        initList.add(initStack);
        State currentState = new State(initList);
        currentState.setHeuristics(hillClimbing.getHeuristicsValue(initList, goalStack));
        State nextState = hillClimbing.findNextState(currentState, goalStack);
        assertTrue(nextState.getHeuristics() > currentState.getHeuristics());
    }
}