package com.baeldung.algorithms.hillclimbing;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
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

    private static void printEachStep(State state) {
        List<Stack<String>> stackList = state.getState();
        System.out.println("----------------");
        stackList.forEach(stack -> {
            while (!stack.isEmpty()) {
                System.out.println(stack.pop());
            }
            System.out.println(" ");
        });
    }

    private Stack<String> getStackWithValues(String[] blocks) {
        Stack<String> stack = new Stack<>();
        for (String block : blocks)
            stack.push(block);
        return stack;
    }

    /**
     * This method prepares path from init state to goal state
     */
    public List<State> getRouteWithHillClimbing(Stack<String> initStateStack, Stack<String> goalStateStack) throws Exception {
        List<Stack<String>> initStateStackList = new ArrayList<>();
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

        return resultPath;
    }

    /**
     * This method finds new state from current state based on goal and
     * heuristics
     */
    public State findNextState(State currentState, Stack<String> goalStateStack) {
        List<Stack<String>> listOfStacks = currentState.getState();
        int currentStateHeuristics = currentState.getHeuristics();

        return listOfStacks.stream()
            .map(stack -> {
                return applyOperationsOnState(listOfStacks, stack, currentStateHeuristics, goalStateStack);
            })
            .filter(Objects::nonNull)
            .findFirst()
          .orElse(null);
    }

    /**
     * This method applies operations on the current state to get a new state
     */
    public State applyOperationsOnState(List<Stack<String>> listOfStacks, Stack<String> stack, int currentStateHeuristics, Stack<String> goalStateStack) {
        State tempState;
        List<Stack<String>> tempStackList = new ArrayList<>(listOfStacks);
        String block = stack.pop();
        if (stack.size() == 0)
            tempStackList.remove(stack);
        tempState = pushElementToNewStack(tempStackList, block, currentStateHeuristics, goalStateStack);
        if (tempState == null) {
            tempState = pushElementToExistingStacks(stack, tempStackList, block, currentStateHeuristics, goalStateStack);
        }
        if (tempState == null)
            stack.push(block);
        return tempState;
    }
    
    /**
     * Operation to be applied on a state in order to find new states. This
     * operation pushes an element into a new stack
     */
    private State pushElementToNewStack(List<Stack<String>> currentStackList, String block, int currentStateHeuristics, Stack<String> goalStateStack) {
        State newState = null;
        Stack<String> newStack = new Stack<>();
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
     * Operation to be applied on a state in order to find new states. This
     * operation pushes an element into one of the other stacks to explore new
     * states
     */
    private State pushElementToExistingStacks(Stack currentStack, List<Stack<String>> currentStackList, String block, int currentStateHeuristics, Stack<String> goalStateStack) {

        Optional<State> newState = currentStackList.stream()
            .filter(stack -> stack != currentStack)
            .map(stack -> {
                return pushElementToStack(stack, block, currentStackList, currentStateHeuristics, goalStateStack);
            })
            .filter(Objects::nonNull)
            .findFirst();

        return newState.orElse(null);
    }
    
    /**
     * This method pushes a block to the stack and returns new state if its closer to goal
     */
    private State pushElementToStack(Stack stack, String block, List<Stack<String>> currentStackList, int currentStateHeuristics, Stack<String> goalStateStack) {
        stack.push(block);
        int newStateHeuristics = getHeuristicsValue(currentStackList, goalStateStack);
        if (newStateHeuristics > currentStateHeuristics) {
            return new State(currentStackList, newStateHeuristics);
        }
        stack.pop();
        return null;
    }

    /**
     * This method returns heuristics value for given state with respect to goal
     * state
     */
    public int getHeuristicsValue(List<Stack<String>> currentState, Stack<String> goalStateStack) {
        Integer heuristicValue;
        heuristicValue = currentState.stream()
            .mapToInt(stack -> {
                return getHeuristicsValueForStack(stack, currentState, goalStateStack);
            })
            .sum();
        return heuristicValue;
    }
    
    /**
     * This method returns heuristics value for a particular stack
     */
    public int getHeuristicsValueForStack(Stack<String> stack, List<Stack<String>> currentState, Stack<String> goalStateStack) {
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
        return stackHeuristics;
    }

}