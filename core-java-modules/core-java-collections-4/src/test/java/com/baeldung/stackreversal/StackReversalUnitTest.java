package com.baeldung.stackreversal;

import static org.junit.jupiter.api.Assertions.assertEquals;

import com.baeldung.collections.sorting.Employee;
import com.baeldung.collections.stackreversal.ReverseStackUsingQueue;
import com.baeldung.collections.stackreversal.ReverseStackUsingRecursion;

import org.junit.jupiter.api.Test;

import java.util.*;
import java.util.stream.Collectors;

public class StackReversalUnitTest {
    @Test
    public void whenIntegerStack_thenReturnReversedIntegerStack(){
        ReverseStackUsingQueue reverseStack = new ReverseStackUsingQueue();
        Stack<Integer> originalStack = generateStackFromGivenList(Arrays.stream(new int[]{1, 2, 3, 4, 5, 6, 7, 8, 9, 10}).boxed().collect(Collectors.toList()), new Stack<Integer>());
        Stack<Integer> reverseList = generateStackFromGivenList(Arrays.stream(new int[]{10, 9, 8, 7, 6, 5, 4, 3, 2, 1}).boxed().collect(Collectors.toList()), new Stack<Integer>());
        assertEquals(reverseStack.reverseIntegerStack(originalStack), reverseList);
    }

    @Test
    public void whenStringStack_thenReturnReversedStringStack(){
        ReverseStackUsingQueue stackReversal = new ReverseStackUsingQueue();
        List<String> listOfWords = Arrays.asList(new String[]{"Hello", "I", "am", "reversing", "a", "stack"});
        List<String> listOfWordsReversed = new ArrayList<>(listOfWords);
        Collections.reverse(listOfWordsReversed);
        Stack<String> originalStack = generateStackFromGivenList(listOfWords, new Stack<String>());
        Stack<String> reversedStack = generateStackFromGivenList(listOfWordsReversed, new Stack<String>());
        assertEquals(stackReversal.reverseStringStack(originalStack), reversedStack);
    }

    @Test
    public void whenEmployeeStack_thenReturnReversedEmployeeStack(){
        ReverseStackUsingQueue stackReversal = new ReverseStackUsingQueue();
        Employee employee1 = new Employee("John Doe", new Date());
        Employee employee2 = new Employee("John Nash", new Date());
        Employee employee3 = new Employee("Ryan Howard", new Date());
        List<Employee> employeeList = new ArrayList<>();
        employeeList.add(employee1);
        employeeList.add(employee2);
        employeeList.add(employee3);
        List<Employee> employeeReversed = new ArrayList<>(employeeList);
        Collections.reverse(employeeReversed);
        Stack<Employee> originalStack = generateStackFromGivenList(employeeList, new Stack<Employee>());
        Stack<Employee> reverseStack = generateStackFromGivenList(employeeReversed, new Stack<Employee>());
        assertEquals(stackReversal.reverseEmployeeStack(originalStack), reverseStack);
    }

    @Test
    public void givenIntegerStack_whenStackReversed_thenReturnReversedRecursion(){
        ReverseStackUsingRecursion reverseStack = new ReverseStackUsingRecursion();
        Stack<Integer> originalStack = generateStackFromGivenList(Arrays.stream(new int[]{1, 2, 3, 4, 5, 6, 7, 8, 9, 10}).boxed().collect(Collectors.toList()), new Stack<Integer>());
        Stack<Integer> reversedStack = generateStackFromGivenList(Arrays.stream(new int[]{10, 9, 8, 7, 6, 5, 4, 3, 2, 1}).boxed().collect(Collectors.toList()), new Stack<Integer>());
        assertEquals(reverseStack.reverseIntegerStack(originalStack), reversedStack);
    }

    private Stack generateStackFromGivenList(List elements, Stack stack){
        int start = 0;
        while (start < elements.size()){
            stack.add(elements.get(start++));
        }
        return stack;
    }
}
