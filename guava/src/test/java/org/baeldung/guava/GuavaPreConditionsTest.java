package org.baeldung.guava;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import java.util.Arrays;
import org.junit.Test;
import static com.google.common.base.Preconditions.*;

public class GuavaPreConditionsTest {

    @Test
    public void whenCheckArgumentEvaluatesFalse_throwsException() {
        int age = -18;

        assertThatThrownBy(() -> checkArgument(age > 0))
            .isInstanceOf(IllegalArgumentException.class)
            .hasMessage(null)
            .hasNoCause();
    }

    @Test
    public void givenErrorMessage_whenCheckArgumentEvaluatesFalse_throwsException() {
        final int age = -18;
        final String message = "Age can't be zero or less than zero";

        assertThatThrownBy(() -> checkArgument(age > 0, message))
            .isInstanceOf(IllegalArgumentException.class)
            .hasMessage(message)
            .hasNoCause();
    }

    @Test
    public void givenTemplatedErrorMessage_whenCheckArgumentEvaluatesFalse_throwsException() {
        final int age = -18;
        final String message = "Age can't be zero or less than zero, you supplied %s.";

        assertThatThrownBy(() -> checkArgument(age > 0, message, age))
            .isInstanceOf(IllegalArgumentException.class)
            .hasMessage(message, age)
            .hasNoCause();
    }

    @Test
    public void givenArrayOfIntegers_whenCheckElementIndexEvaluatesFalse_throwsException() {
        final int[] numbers = { 1, 2, 3, 4, 5 };

        assertThatThrownBy(() -> checkElementIndex(6, numbers.length - 1))
            .isInstanceOf(IndexOutOfBoundsException.class)
            .hasNoCause();
    }

    @Test
    public void givenArrayOfIntegersAndMessage_whenCheckElementIndexEvaluatesFalse_throwsException() {
        final int[] numbers = { 1, 2, 3, 4, 5 };
        final String message = "Please check the bound of an array and retry";

        assertThatThrownBy(() -> checkElementIndex(6, numbers.length - 1, message))
            .isInstanceOf(IndexOutOfBoundsException.class)
            .hasMessageStartingWith(message)
            .hasNoCause();
    }

    @Test
    public void givenNullString_whenCheckNotNullCalled_throwsException() {
        final String nullObject = null;

        assertThatThrownBy(() -> checkNotNull(nullObject))
            .isInstanceOf(NullPointerException.class)
            .hasMessage(null)
            .hasNoCause();
    }

    @Test
    public void givenNullString_whenCheckNotNullCalledWithMessage_throwsException() {
        final String nullObject = null;
        final String message = "Please check the Object supplied, its null!";

        assertThatThrownBy(() -> checkNotNull(nullObject, message))
            .isInstanceOf(NullPointerException.class)
            .hasMessage(message)
            .hasNoCause();
    }

    @Test
    public void givenNullString_whenCheckNotNullCalledWithTemplatedMessage_throwsException() {
        final String nullObject = null;
        final String message = "Please check the Object supplied, its %s!";

        assertThatThrownBy(() -> checkNotNull(nullObject, message, nullObject))
            .isInstanceOf(NullPointerException.class)
            .hasMessage(message, nullObject)
            .hasNoCause();
    }

    @Test
    public void givenArrayOfIntegers_whenCheckPositionIndexEvaluatesFalse_throwsException() {
        final int[] numbers = { 1, 2, 3, 4, 5 };

        assertThatThrownBy(() -> checkPositionIndex(6, numbers.length - 1))
            .isInstanceOf(IndexOutOfBoundsException.class)
            .hasNoCause();
    }

    @Test
    public void givenArrayOfIntegersAndMessage_whenCheckPositionIndexEvaluatesFalse_throwsException() {
        final int[] numbers = { 1, 2, 3, 4, 5 };
        final String message = "Please check the bound of an array and retry";

        assertThatThrownBy(() -> checkPositionIndex(6, numbers.length - 1, message))
            .isInstanceOf(IndexOutOfBoundsException.class)
            .hasMessageStartingWith(message)
            .hasNoCause();
    }

    @Test
    public void givenArrayOfIntegers_whenCheckPositionIndexesEvaluatesFalse_throwsException() {
        final int[] numbers = { 1, 2, 3, 4, 5 };

        assertThatThrownBy(() -> checkPositionIndexes(6, 0, numbers.length - 1))
            .isInstanceOf(IndexOutOfBoundsException.class)
            .hasNoCause();
    }

    @Test
    public void givenValidStates_whenCheckStateEvaluatesFalse_throwsException() {
        final int[] validStates = { -1, 0, 1 };
        final int givenState = 10;

        assertThatThrownBy(() -> checkState(Arrays.binarySearch(validStates, givenState) > 0))
            .isInstanceOf(IllegalStateException.class)
            .hasMessage(null)
            .hasNoCause();
    }

    @Test
    public void givenValidStatesAndMessage_whenCheckStateEvaluatesFalse_throwsException() {
        final int[] validStates = { -1, 0, 1 };
        final int givenState = 10;
        final String message = "You have entered an invalid state";

        assertThatThrownBy(() -> checkState(Arrays.binarySearch(validStates, givenState) > 0, message))
            .isInstanceOf(IllegalStateException.class)
            .hasMessageStartingWith(message)
            .hasNoCause();
    }

    @Test
    public void givenValidStatesAndTemplatedMessage_whenCheckStateEvaluatesFalse_throwsException() {
        final int[] validStates = { -1, 0, 1 };
        final int givenState = 10;
        final String message = "State can't be %s, It can be one of %s.";

        assertThatThrownBy(() -> checkState(Arrays.binarySearch(validStates, givenState) > 0, message, givenState, Arrays.toString(validStates)))
            .isInstanceOf(IllegalStateException.class)
            .hasMessage(message, givenState, Arrays.toString(validStates))
            .hasNoCause();
    }
}
