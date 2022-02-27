package com.baeldung.exception.variablemightnothavebeeninitialized;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class VariableMightNotHaveBeenInitializedUnitTest {

    @Test
    public void usingInstanceVariable_returnCount() {
        int[] arr = new int[]{1, 2, 3, 4, 5, 6};
        int value = VariableMightNotHaveBeenInitialized.countEvenUsingInstanceVariable(arr);

        assertEquals(3, value);
    }

    @Test
    public void usingArgumentsAndIfElse_returnCount() {
        int[] arr = new int[]{1, 2, 3, 4, 5, 6};
        int value = VariableMightNotHaveBeenInitialized.countEvenUsingIfElse(arr, 2);

        assertEquals(5, value);
    }
}
