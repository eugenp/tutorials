package com.baeldung.array.operations;

import org.junit.jupiter.api.Test;
import static org.junit.Assert.assertArrayEquals;
import static org.assertj.core.api.Assertions.assertThat;

public class ArrayOperationsUnitTest {

    @Test
    public void givenSourceArrayAndElement_whenAddElementUsingPureJavaIsInvoked_thenNewElementMustBeAdded() {
        Integer[] sourceArray = { 1, 2, 3, 4 };
        int elementToAdd = 5;

        Integer[] destArray = ArrayOperations.addElementUsingPureJava(sourceArray, elementToAdd);

        Integer[] expectedArray = { 1, 2, 3, 4, 5 };
        assertArrayEquals(expectedArray, destArray);
    }

    @Test
    public void whenInsertAnElementAtAGivenIndexCalled_thenShiftTheFollowingElementsAndInsertTheElementInArray() {
        int[] expectedArray = { 1, 4, 2, 3, 0 };
        int[] anArray = new int[4];
        anArray[0] = 1;
        anArray[1] = 2;
        anArray[2] = 3;
        int[] outputArray = ArrayOperations.insertAnElementAtAGivenIndex(anArray, 1, 4);

        assertThat(outputArray).containsExactly(expectedArray);
    }

}
