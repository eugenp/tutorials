package com.baeldung.compoundoperators;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class CompoundOperatorsUnitTest {

    @Test
    public void whenAssignmentOperatorIsUsed_thenValueIsAssigned() {
        int x = 5;

        assertEquals(5, x);
    }

    @Test
    public void whenCompoundAssignmentUsed_thenSameAsSimpleAssignment() {
        int a = 3, b = 3, c = -2;
        a = a * c; // Simple assignment operator
        b *= c; // Compound assignment operator

        assertEquals(a, b);
    }

    @Test
    public void whenAssignmentOperatorIsUsed_thenValueIsReturned() {
        long x = 1;
        long y = (x+=2);

        assertEquals(3, y);
        assertEquals(y, x);
    }

    @Test
    public void whenCompoundOperatorsAreUsed_thenOperationsArePerformedAndAssigned() {
        //Simple assignment
        int x = 5; //x is 5

        //Incrementation
        x += 5; //x is 10
        assertEquals(10, x);

        //Decrementation
        x -= 2; //x is 8
        assertEquals(8, x);

        //Multiplication
        x *= 2; //x is 16
        assertEquals(16, x);

        //Division
        x /= 4; //x is 4
        assertEquals(4, x);

        //Modulus
        x %= 3; //x is 1
        assertEquals(1, x);


        //Binary AND
        x &= 4; //x is 0
        assertEquals(0, x);

        //Binary exclusive OR
        x ^= 4; //x is 4
        assertEquals(4, x);

        //Binary inclusive OR
        x |= 8; //x is 12
        assertEquals(12, x);


        //Binary Left Shift
        x <<= 2; //x is 48
        assertEquals(48, x);

        //Binary Right Shift
        x >>= 2; //x is 12
        assertEquals(12, x);

        //Shift right zero fill
        x >>>= 1; //x is 6
        assertEquals(6, x);
    }

    @Test(expected = NullPointerException.class)
    public void whenArrayIsNull_thenThrowNullException() {
        int[] numbers = null;

        //Trying Incrementation
        numbers[2] += 5;
    }

    @Test(expected = ArrayIndexOutOfBoundsException.class)
    public void whenArrayIndexNotCorrect_thenThrowArrayIndexException() {
        int[] numbers = {0, 1};

        //Trying Incrementation
        numbers[2] += 5;
    }

    @Test
    public void whenArrayIndexIsCorrect_thenPerformOperation() {
        int[] numbers = {0, 1};

        //Incrementation
        numbers[1] += 5;
        assertEquals(6, numbers[1]);
    }

}
