package com.baeldung.algorithms.hillclimbing;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

public class HillClimbing {
    public static void main(String[] args) {
        HillClimbing hillClimbing = new HillClimbing();
        String blockArr[] = { "B", "C", "D", "A" };
        Stack<String> startState = hillClimbing.getStackWithValues(blockArr);
        String goalBlockArr[] = { "A", "B", "C", "D" };
        Stack<String> goalState = hillClimbing.getStackWithValues(goalBlockArr);
        try {
            List<State> solutionSequence = hillClimbing.getRouteWithHillClimbing(startState, goalState);
            solutionSequence.forEach(HillClimbing::printEachStep);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void printEachStep(State state) {
        List<Stack<String>> stackList = state.getState();
        System.out.println("----------------");
        stackList.forEach(stack -> {
            while (!stack.isEmpty()) {
                System.out.println(stack.pop());
            }
            System.out.println(" ");
        });
    }

    public Stack<String> getStackWithValues(String[] blocks) {
        Stack<String> stack = new Stack<String>();
        for (String block : blocks)
            stack.push(block);
        return stack;
    }

    /**
     * This method prepares path from init state to goal state
     *
     * @param initStateStack
     * @param goalStateStack
     * @return
     * @throws Exception
     */
    public List<State> getRouteWithHillClimbing(Stack<String> initStateStack, Stack<String> goalStateStack) throws Exception {
        List<Stack<String>> initStateStackList = new ArrayList<Stack<String>>();
        initStateStackList.add(initStateStack);
        int initStateHeuristics = getHeuristicsValue(initStateStackList, goalStateStack);
        State initState = new State(initStateStackList, initStateHeuristics);

        List<State> resultPath = new ArrayList<>();
        resultPath.add(new State(initState));

        State currentState = initState;
        boolean noStateFound = false;
        while (!currentState.getState()
            .get(0)
            .equals(goalStateStack) || noStateFound) {
            noStateFound = true;
            State nextState = findNextState(currentState, goalStateStack);
            if (nextState != null) {
                noStateFound = false;
                currentState = nextState;
                resultPath.add(new State(nextState));
            }
        }

        if (noStateFound)
            throw new Exception("No path found");
        return resultPath;
    }

    /**
     * This method finds new state from current state based on goal and heuristics
     *
     * @param currentState
     * @param goalStateStack
     * @return a next state
     */
    public State findNextState(State currentState, Stack<String> goalStateStack) {
        List<Stack<String>> listOfStacks = currentState.getState();
        int currentStateHeuristics = currentState.getHeuristics();
        State newState = null;

        for (Stack<String> stack : listOfStacks) {
            List<Stack<String>> tempStackList = new ArrayList<Stack<String>>(listOfStacks);
            String block = stack.pop();
            if (stack.size() == 0)
                tempStackList.remove(stack);
            newState = pushElementToNewStack(tempStackList, block, currentStateHeuristics, goalStateStack);
            if (newState == null) {
                newState = pushElementToExistingStacks(stack, tempStackList, block, currentStateHeuristics, goalStateStack);
            }
            if (newState != null)
                break;
            stack.push(block);
        }
        return newState;
    }

    /**
     * Operation to be applied on a state in order to find new states.
     * This operation pushes an element into a new stack
     *
     * @param currentStackList
     * @param block
     * @param currentStateHeuristics
     * @param goalStateStack
     * @return a new state
     */
    private State pushElementToNewStack(List<Stack<String>> currentStackList, String block, int currentStateHeuristics, Stack<String> goalStateStack) {
        State newState = null;
        Stack<String> newStack = new Stack<String>();
        newStack.push(block);

        currentStackList.add(newStack);
        int newStateHeuristics = getHeuristicsValue(currentStackList, goalStateStack);
        if (newStateHeuristics > currentStateHeuristics) {
            newState = new State(currentStackList, newStateHeuristics);
        } else {
            currentStackList.remove(newStack);
        }
        return newState;
    }

    /**
     * Operation to be applied on a state in order to find new states.
     * This operation pushes an element into one of the other stacks to explore new states
     *
     * @param stack
     * @param currentStackList
     * @param block
     * @param currentStateHeuristics
     * @param goalStateStack
     * @return a new state
     */
    private State pushElementToExistingStacks(Stack stack, List<Stack<String>> currentStackList, String block, int currentStateHeuristics, Stack<String> goalStateStack) {
        State newState = null;
        for (Stack<String> otherStack : currentStackList) {
            if (!stack.equals(otherStack)) {
                otherStack.push(block);
                int newStateHeuristics = getHeuristicsValue(currentStackList, goalStateStack);
                if (newStateHeuristics > currentStateHeuristics) {
                    newState = new State(currentStackList, newStateHeuristics);
                    break;
                }
                otherStack.pop();
            }
        }
        return newState;
    }

    /**
     * This method returns heuristics value for given state with respect to goal state
     *
     * @param currentState
     * @param goalStateStack
     * @return
     */
    public int getHeuristicsValue(List<Stack<String>> currentState, Stack<String> goalStateStack) {

        int heuristicValue = 0;
        for (Stack<String> stack : currentState) {
            int stackHeuristics = 0;
            boolean isPositioneCorrect = true;
            int goalStartIndex = 0;
            for (String currentBlock : stack) {
                if (isPositioneCorrect && currentBlock.equals(goalStateStack.get(goalStartIndex))) {
                    stackHeuristics += goalStartIndex;
                } else {
                    stackHeuristics -= goalStartIndex;
                    isPositioneCorrect = false;
                }
                goalStartIndex++;
            }
            heuristicValue += stackHeuristics;
        }
        return heuristicValue;
    }

}
