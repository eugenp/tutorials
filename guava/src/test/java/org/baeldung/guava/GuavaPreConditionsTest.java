package org.baeldung.guava;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import java.util.Arrays;
import org.junit.Test;
import static com.google.common.base.Preconditions.*;

public class GuavaPreConditionsTest {

    @Test(expected = IllegalArgumentException.class)
    public void whenCheckArgumentEvaluatesFalse_throwsException() {
        int age = -18;
        checkArgument(age > 0);
    }

    @Test
    public void givenErrorMessage_whenCheckArgumentEvaluatesFalse_throwsException() {
        final int age = -18;
        final String message = "Age can't be zero or less than zero";
        try {
            checkArgument(age > 0, message);
        } catch (IllegalArgumentException illegalArgumentException) {
            assertEquals(message, illegalArgumentException.getMessage());
        }
    }

    @Test
    public void givenTemplatedErrorMessage_whenCheckArgumentEvaluatesFalse_throwsException() {
        final int age = -18;
        final String message = "Age can't be zero or less than zero, you supplied %s.";
        try {
            checkArgument(age > 0, message, age);
        } catch (IllegalArgumentException illegalArgumentException) {
            final String formattedMessage = String.format(message, age);
            assertEquals(formattedMessage, illegalArgumentException.getMessage());
        }
    }

    @Test(expected = IndexOutOfBoundsException.class)
    public void givenArrayOfIntegers_whenCheckElementIndexEvaluatesFalse_throwsException() {
        final int[] numbers = { 1, 2, 3, 4, 5 };
        checkElementIndex(6, numbers.length - 1);
    }

    @Test
    public void givenArrayOfIntegersAndMessage_whenCheckElementIndexEvaluatesFalse_throwsException() {
        final int[] numbers = { 1, 2, 3, 4, 5 };
        final String message = "Please check the bound of an array and retry";
        try {
            checkElementIndex(6, numbers.length - 1, message);
        } catch (IndexOutOfBoundsException indexOutOfBoundsException) {
            assertTrue(indexOutOfBoundsException.getMessage().startsWith(message));
        }
    }

    @Test(expected = NullPointerException.class)
    public void givenNullString_whenCheckNotNullCalled_throwsException() {
        final String nullObject = null;
        checkNotNull(nullObject);
    }

    @Test
    public void givenNullString_whenCheckNotNullCalledWithMessage_throwsException() {
        final String nullObject = null;
        final String message = "Please check the Object supplied, its null!";
        try {
            checkNotNull(nullObject, message);
        } catch (NullPointerException nullPointerException) {
            assertEquals(message, nullPointerException.getMessage());
        }
    }

    @Test
    public void givenNullString_whenCheckNotNullCalledWithTemplatedMessage_throwsException() {
        final String nullObject = null;
        final String message = "Please check the Object supplied, its %s!";
        try {
            checkNotNull(nullObject, message, nullObject);
        } catch (NullPointerException nullPointerException) {
            final String formattedMessage = String.format(message, nullObject);
            assertEquals(formattedMessage, nullPointerException.getMessage());
        }
    }

    @Test(expected = IndexOutOfBoundsException.class)
    public void givenArrayOfIntegers_whenCheckPositionIndexEvaluatesFalse_throwsException() {
        final int[] numbers = { 1, 2, 3, 4, 5 };
        checkPositionIndex(6, numbers.length - 1);
    }

    @Test
    public void givenArrayOfIntegersAndMessage_whenCheckPositionIndexEvaluatesFalse_throwsException() {
        final int[] numbers = { 1, 2, 3, 4, 5 };
        final String message = "Please check the bound of an array and retry";
        try {
            checkPositionIndex(6, numbers.length - 1, message);
        } catch (IndexOutOfBoundsException indexOutOfBoundsException) {
            assertTrue(indexOutOfBoundsException.getMessage().startsWith(message));
        }
    }

    @Test(expected = IndexOutOfBoundsException.class)
    public void givenArrayOfIntegers_whenCheckPositionIndexesEvaluatesFalse_throwsException() {
        final int[] numbers = { 1, 2, 3, 4, 5 };
        checkPositionIndexes(6, 0, numbers.length - 1);
    }

    @Test(expected = IllegalStateException.class)
    public void givenValidStates_whenCheckStateEvaluatesFalse_throwsException() {
        final int[] validStates = { -1, 0, 1 };
        final int givenState = 10;
        checkState(Arrays.binarySearch(validStates, givenState) > 0);
    }

    @Test
    public void givenValidStatesAndMessage_whenCheckStateEvaluatesFalse_throwsException() {
        final int[] validStates = { -1, 0, 1 };
        final int givenState = 10;
        final String message = "You have entered an invalid state";
        try {
            checkState(Arrays.binarySearch(validStates, givenState) < 0, message);
        } catch (IllegalStateException IllegalStateException) {
            assertEquals(message, IllegalStateException.getMessage());
        }
    }

    @Test
    public void givenValidStatesAndTemplatedMessage_whenCheckStateEvaluatesFalse_throwsException() {
        final int[] validStates = { -1, 0, 1 };
        final int givenState = 10;
        final String message = "State can't be %s, It can be one of %s.";
        try {
            checkState(Arrays.binarySearch(validStates, givenState) < 0, message, givenState, Arrays.toString(validStates));
        } catch (IllegalStateException IllegalStateException) {
            final String formattedMessage = String.format(message, givenState, Arrays.toString(validStates));
            assertEquals(formattedMessage, IllegalStateException.getMessage());
        }
    }

}
