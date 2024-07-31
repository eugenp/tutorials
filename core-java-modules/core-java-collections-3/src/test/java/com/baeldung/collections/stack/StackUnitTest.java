package com.baeldung.collections.stack;

import org.junit.Test;

import java.util.Arrays;
import java.util.List;
import java.util.ListIterator;
import java.util.Stack;
import java.util.stream.Collectors;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;
public class StackUnitTest {

    @Test
    public void whenStackIsCreated_thenItHasSizeZero() {
        Stack<Integer> intStack = new Stack<>();

        assertEquals(0, intStack.size());
    }

    @Test
    public void whenElementIsPushed_thenStackSizeIsIncreased() {
        Stack<Integer> intStack = new Stack<>();
        intStack.push(1);

        assertEquals(1, intStack.size());
    }

    @Test
    public void whenMultipleElementsArePushed_thenStackSizeIsIncreased() {
        Stack<Integer> intStack = new Stack<>();
        List<Integer> intList = Arrays.asList(1, 2, 3, 4, 5, 6, 7);

        boolean result = intStack.addAll(intList);

        assertTrue(result);
        assertEquals(7, intList.size());
    }

    @Test
    public void whenElementIsPoppedFromStack_thenElementIsRemovedAndSizeChanges() {
        Stack<Integer> intStack = new Stack<>();
        intStack.push(5);

        Integer element = intStack.pop();

        assertEquals(Integer.valueOf(5), element);
        assertTrue(intStack.isEmpty());
    }

    @Test
    public void whenElementIsPeeked_thenElementIsNotRemovedAndSizeDoesNotChange() {
        Stack<Integer> intStack = new Stack<>();
        intStack.push(5);

        Integer element = intStack.peek();

        assertEquals(Integer.valueOf(5), element);
        assertEquals(1, intStack.search(5));
        assertEquals(1, intStack.size());
    }

    @Test
    public void whenElementIsOnStack_thenSearchReturnsItsDistanceFromTheTop() {
        Stack<Integer> intStack = new Stack<>();
        intStack.push(5);
        intStack.push(8);

        assertEquals(2, intStack.search(5));
    }

    @Test
    public void whenElementIsOnStack_thenIndexOfReturnsItsIndex() {
        Stack<Integer> intStack = new Stack<>();
        intStack.push(5);

        int indexOf = intStack.indexOf(5);

        assertEquals(0, indexOf);
    }

    @Test
    public void whenMultipleElementsAreOnStack_thenIndexOfReturnsLastElementIndex() {
        Stack<Integer> intStack = new Stack<>();
        intStack.push(5);
        intStack.push(5);
        intStack.push(5);

        int lastIndexOf = intStack.lastIndexOf(5);

        assertEquals(2, lastIndexOf);
    }

    @Test
    public void whenRemoveElementIsInvoked_thenElementIsRemoved() {
        Stack<Integer> intStack = new Stack<>();
        intStack.push(5);
        intStack.push(5);

        intStack.removeElement(5);

        assertEquals(1, intStack.size());
    }

    @Test
    public void whenRemoveElementAtIsInvoked_thenElementIsRemoved() {
        Stack<Integer> intStack = new Stack<>();
        intStack.push(5);
        intStack.push(7);

        intStack.removeElementAt(1);

        assertEquals(-1, intStack.search(7));
    }

    @Test
    public void whenRemoveAllElementsIsInvoked_thenAllElementsAreRemoved() {
        Stack<Integer> intStack = new Stack<>();
        intStack.push(5);
        intStack.push(7);

        intStack.removeAllElements();

        assertTrue(intStack.isEmpty());
    }

    @Test
    public void givenElementsOnStack_whenRemoveAllIsInvoked_thenAllElementsFromCollectionAreRemoved() {
        Stack<Integer> intStack = new Stack<>();
        List<Integer> intList = Arrays.asList(1, 2, 3, 4, 5, 6, 7);
        intStack.addAll(intList);
        intStack.add(500);

        intStack.removeAll(intList);

        assertEquals(1, intStack.size());
        assertEquals(1, intStack.search(500));
    }

    @Test
    public void whenRemoveIfIsInvoked_thenAllElementsSatysfyingConditionAreRemoved() {
        Stack<Integer> intStack = new Stack<>();
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

        Stack<Integer> result = new Stack<>();
        while(it.hasNext()) {
            result.push(it.next());
        }

        assertThat(result, equalTo(intStack));
    }

    @Test
    public void whenStackIsFiltered_allElementsNotSatisfyingFilterConditionAreDiscarded() {
        Stack<Integer> intStack = new Stack<>();
        List<Integer> inputIntList = Arrays.asList(1, 2, 3, 4, 5, 6, 7, 9, 10);
        intStack.addAll(inputIntList);

        List<Integer> filtered = intStack
                .stream()
                .filter(element -> element <= 3)
                .collect(Collectors.toList());

        assertEquals(3, filtered.size());
    }
}
