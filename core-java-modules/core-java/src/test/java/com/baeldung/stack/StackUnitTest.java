package com.baeldung.stack;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.junit.Assert.*;

import java.util.Arrays;
import java.util.List;
import java.util.ListIterator;
import java.util.Stack;

import org.junit.Test;
public class StackUnitTest {

    @Test
    public void whenStackIsCreated_thenItHasSize0() {
        Stack intStack = new Stack();
        assertEquals(0, intStack.size());
    }

    @Test
    public void givenEmptyStack_whenElementIsPushed_thenStackSizeisIncreased() {
        Stack intStack = new Stack();
        intStack.push(1);
        assertEquals(1, intStack.size());
    }

    @Test
    public void givenEmptyStack_whenMultipleElementsArePushed_thenStackSizeisIncreased() {
        Stack intStack = new Stack();
        List<Integer> intList = Arrays.asList(1, 2, 3, 4, 5, 6, 7);
        boolean result = intStack.addAll(intList);
        assertTrue(result);
        assertEquals(7, intList.size());
    }

    @Test
    public void whenElementIsPoppedFromStack_thenElementIsRemovedAndSizeChanges() {
        Stack<Integer> intStack = new Stack();
        intStack.push(5);
        intStack.pop();
        assertTrue(intStack.isEmpty());
    }

    @Test
    public void whenElementIsPeeked_thenElementIsNotRemovedAndSizeDoesNotChange() {
        Stack<Integer> intStack = new Stack();
        intStack.push(5);
        intStack.peek();
        assertEquals(1, intStack.search(5));
        assertEquals(1, intStack.size());
    }

    @Test
    public void whenElementIsOnStack_thenSearchReturnsItsDistanceFromTheTop() {
        Stack<Integer> intStack = new Stack();
        intStack.push(5);
        assertEquals(1, intStack.search(5));
    }

    @Test
    public void whenElementIsOnStack_thenIndexOfReturnsItsIndex() {
        Stack<Integer> intStack = new Stack();
        intStack.push(5);
        int indexOf = intStack.indexOf(5);
        assertEquals(0, indexOf);
    }

    @Test
    public void whenMultipleElementsAreOnStack_thenIndexOfReturnsLastElementIndex() {
        Stack<Integer> intStack = new Stack();
        intStack.push(5);
        intStack.push(5);
        intStack.push(5);
        int lastIndexOf = intStack.lastIndexOf(5);
        assertEquals(2, lastIndexOf);
    }

    @Test
    public void givenElementOnStack_whenRemoveElementIsInvoked_thenElementIsRemoved() {
        Stack<Integer> intStack = new Stack();
        intStack.push(5);
        intStack.push(5);
        intStack.removeElement(5);
        assertEquals(1, intStack.size());
    }

    @Test
    public void givenElementOnStack_whenRemoveElementAtIsInvoked_thenElementIsRemoved() {
        Stack<Integer> intStack = new Stack();
        intStack.push(5);
        intStack.push(7);
        intStack.removeElementAt(1);
        assertEquals(-1, intStack.search(7));
    }

    @Test
    public void givenElementsOnStack_whenRemoveAllElementsIsInvoked_thenAllElementsAreRemoved() {
        Stack<Integer> intStack = new Stack();
        intStack.push(5);
        intStack.push(7);
        intStack.removeAllElements();
        assertTrue(intStack.isEmpty());
    }

    @Test
    public void givenElementsOnStack_whenRemoveAllIsInvoked_thenAllElementsFromCollectionAreRemoved() {
        Stack<Integer> intStack = new Stack();
        List<Integer> intList = Arrays.asList(1, 2, 3, 4, 5, 6, 7);
        intStack.addAll(intList);
        intStack.add(500);
        intStack.removeAll(intList);
        assertEquals(1, intStack.size());
    }

    @Test
    public void givenElementsOnStack_whenRemoveIfIsInvoked_thenAllElementsSatysfyingConditionAreRemoved() {
        Stack<Integer> intStack = new Stack();
        List<Integer> intList = Arrays.asList(1, 2, 3, 4, 5, 6, 7);
        intStack.addAll(intList);
        intStack.removeIf(element -> element < 6);
        assertEquals(2, intStack.size());
    }

    @Test
    public void whenAnotherStackCreatedWhileTraversingStack_thenStacksAreEqual() {
        Stack<Integer> intStack = new Stack<>();
        List<Integer> intList = Arrays.asList(1, 2, 3, 4, 5, 6, 7);
        intStack.addAll(intList);
        ListIterator<Integer> it = intStack.listIterator();
        Stack<Integer> result = new Stack();
        while(it.hasNext()) {
            result.push(it.next());
        }

        assertThat(result, equalTo(intStack));
    }
}
