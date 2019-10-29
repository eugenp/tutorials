package com.baeldung.charstack;

import static org.junit.Assert.assertEquals;

import org.junit.jupiter.api.Test;

public class CharStackUnitTest {

    @Test
    public void whenCharStackIsCreated_thenItHasSize0() {

        CharStack charStack = new CharStack();

        assertEquals(0, charStack.size());
    }

    @Test
    public void givenEmptyCharStack_whenElementIsPushed_thenStackSizeisIncreased() {

        CharStack charStack = new CharStack();

        charStack.push('A');

        assertEquals(1, charStack.size());
    }

    @Test
    public void givenCharStack_whenElementIsPoppedFromStack_thenElementIsRemovedAndSizeChanges() {

        CharStack charStack = new CharStack();
        charStack.push('A');

        char element = charStack.pop();

        assertEquals('A', element);
        assertEquals(0, charStack.size());
    }

    @Test
    public void givenCharStack_whenElementIsPeeked_thenElementIsNotRemovedAndSizeDoesNotChange() {
        CharStack charStack = new CharStack();
        charStack.push('A');

        char element = charStack.peek();

        assertEquals('A', element);
        assertEquals(1, charStack.size());
    }

}
