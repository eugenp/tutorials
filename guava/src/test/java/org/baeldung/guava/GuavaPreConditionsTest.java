package org.baeldung.guava;

import static org.assertj.core.api.Assertions.assertThat;
import java.util.Arrays;
import org.junit.Test;
import static com.google.common.base.Preconditions.*;

public class GuavaPreConditionsTest {

    @Test
    public void whenCheckArgumentEvaluatesFalse_throwsException() {
        int age = -18;
        try {
            checkArgument(age > 0);
        } catch (Exception exception) {
            assertThat(exception).isInstanceOf(IllegalArgumentException.class);
            assertThat(exception).hasNoCause();
            assertThat(exception).hasMessage(null);
        }
    }

    @Test
    public void givenErrorMessage_whenCheckArgumentEvaluatesFalse_throwsException() {
        final int age = -18;
        final String message = "Age can't be zero or less than zero";
        try {
            checkArgument(age > 0, message);
        } catch (Exception exception) {
            assertThat(exception).isInstanceOf(IllegalArgumentException.class);
            assertThat(exception).hasNoCause();
            assertThat(exception).hasMessage(message);
        }
    }

    @Test
    public void givenTemplatedErrorMessage_whenCheckArgumentEvaluatesFalse_throwsException() {
        final int age = -18;
        final String message = "Age can't be zero or less than zero, you supplied %s.";
        try {
            checkArgument(age > 0, message, age);
        } catch (Exception exception) {
            assertThat(exception).isInstanceOf(IllegalArgumentException.class);
            assertThat(exception).hasNoCause();
            assertThat(exception).hasMessage(message, age);
        }
    }

    @Test
    public void givenArrayOfIntegers_whenCheckElementIndexEvaluatesFalse_throwsException() {
        final int[] numbers = { 1, 2, 3, 4, 5 };
        try {
            checkElementIndex(6, numbers.length - 1);
        } catch (Exception exception) {
            assertThat(exception).isInstanceOf(IndexOutOfBoundsException.class);
            assertThat(exception).hasNoCause();
        }
    }

    @Test
    public void givenArrayOfIntegersAndMessage_whenCheckElementIndexEvaluatesFalse_throwsException() {
        final int[] numbers = { 1, 2, 3, 4, 5 };
        final String message = "Please check the bound of an array and retry";
        try {
            checkElementIndex(6, numbers.length - 1, message);
        } catch (Exception exception) {
            assertThat(exception).isInstanceOf(IndexOutOfBoundsException.class);
            assertThat(exception).hasNoCause();
            assertThat(exception.getMessage()).startsWith(message);
        }
    }

    @Test
    public void givenNullString_whenCheckNotNullCalled_throwsException() {
        final String nullObject = null;
        try {
            checkNotNull(nullObject);
        } catch (Exception exception) {
            assertThat(exception).isInstanceOf(NullPointerException.class);
            assertThat(exception).hasNoCause();
            assertThat(exception).hasMessage(null);
        }
    }

    @Test
    public void givenNullString_whenCheckNotNullCalledWithMessage_throwsException() {
        final String nullObject = null;
        final String message = "Please check the Object supplied, its null!";
        try {
            checkNotNull(nullObject, message);
        } catch (Exception exception) {
            assertThat(exception).isInstanceOf(NullPointerException.class);
            assertThat(exception).hasNoCause();
            assertThat(exception).hasMessage(message);
        }
    }

    @Test
    public void givenNullString_whenCheckNotNullCalledWithTemplatedMessage_throwsException() {
        final String nullObject = null;
        final String message = "Please check the Object supplied, its %s!";
        try {
            checkNotNull(nullObject, message, nullObject);
        } catch (Exception exception) {
            assertThat(exception).isInstanceOf(NullPointerException.class);
            assertThat(exception).hasNoCause();
            assertThat(exception).hasMessage(message, nullObject);
        }
    }

    @Test
    public void givenArrayOfIntegers_whenCheckPositionIndexEvaluatesFalse_throwsException() {
        final int[] numbers = { 1, 2, 3, 4, 5 };
        try {
            checkPositionIndex(6, numbers.length - 1);
        } catch (Exception exception) {
            assertThat(exception).isInstanceOf(IndexOutOfBoundsException.class);
            assertThat(exception).hasNoCause();
        }
    }

    @Test
    public void givenArrayOfIntegersAndMessage_whenCheckPositionIndexEvaluatesFalse_throwsException() {
        final int[] numbers = { 1, 2, 3, 4, 5 };
        final String message = "Please check the bound of an array and retry";
        try {
            checkPositionIndex(6, numbers.length - 1, message);
        } catch (Exception exception) {
            assertThat(exception).isInstanceOf(IndexOutOfBoundsException.class);
            assertThat(exception).hasNoCause();
            assertThat(exception.getMessage()).startsWith(message);
        }
    }

    @Test
    public void givenArrayOfIntegers_whenCheckPositionIndexesEvaluatesFalse_throwsException() {
        final int[] numbers = { 1, 2, 3, 4, 5 };
        try {
            checkPositionIndexes(6, 0, numbers.length - 1);
        } catch (Exception exception) {
            assertThat(exception).isInstanceOf(IndexOutOfBoundsException.class);
            assertThat(exception).hasNoCause();
        }
    }

    @Test
    public void givenValidStates_whenCheckStateEvaluatesFalse_throwsException() {
        final int[] validStates = { -1, 0, 1 };
        final int givenState = 10;
        try {
            checkState(Arrays.binarySearch(validStates, givenState) > 0);
        } catch (Exception exception) {
            assertThat(exception).isInstanceOf(IllegalStateException.class);
            assertThat(exception).hasNoCause();
            assertThat(exception).hasMessage(null);
        }
    }

    @Test
    public void givenValidStatesAndMessage_whenCheckStateEvaluatesFalse_throwsException() {
        final int[] validStates = { -1, 0, 1 };
        final int givenState = 10;
        final String message = "You have entered an invalid state";
        try {
            checkState(Arrays.binarySearch(validStates, givenState) < 0, message);
        } catch (Exception exception) {
            assertThat(exception).isInstanceOf(IllegalStateException.class);
            assertThat(exception).hasNoCause();
            assertThat(exception).hasMessage(message);
        }
    }

    @Test
    public void givenValidStatesAndTemplatedMessage_whenCheckStateEvaluatesFalse_throwsException() {
        final int[] validStates = { -1, 0, 1 };
        final int givenState = 10;
        final String message = "State can't be %s, It can be one of %s.";
        try {
            checkState(Arrays.binarySearch(validStates, givenState) < 0, message, givenState, Arrays.toString(validStates));
        } catch (Exception exception) {
            assertThat(exception).isInstanceOf(IllegalStateException.class);
            assertThat(exception).hasNoCause();
            assertThat(exception).hasMessage(message, givenState, Arrays.toString(validStates));
        }
    }

}