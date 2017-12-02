package com.baeldung.junit5.bean.test;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import com.baeldung.junit5.bean.NumbersBean;

/**
 * Test class for {@link NumbersBean}.
 * 
 * @author Donato Rimenti
 *
 */
public class NumbersBeanTest {

    /**
     * The bean to test.
     */
    private NumbersBean bean = new NumbersBean();

    /**
     * Tests that when an even number is passed to
     * {@link NumbersBean#isNumberEven(int)}, true is returned.
     */
    @Test
    public void whenEven_thenTrue() {
        boolean result = bean.isNumberEven(8);

        Assertions.assertTrue(result);
    }

    /**
     * Tests that when an odd number is passed to
     * {@link NumbersBean#isNumberEven(int)}, false is returned.
     */
    @Test
    public void whenOdd_thenFalse() {
        boolean result = bean.isNumberEven(3);

        Assertions.assertFalse(result);
    }

}
