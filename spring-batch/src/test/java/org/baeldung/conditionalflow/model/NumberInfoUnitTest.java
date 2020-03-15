package org.baeldung.conditionalflow.model;

import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import static org.junit.jupiter.api.Assertions.*;

@RunWith(SpringJUnit4ClassRunner.class)
class NumberInfoUnitTest {

    @Test
    void isPositive() {
        assertTrue(NumberInfo.from(1).isPositive());
        assertTrue(NumberInfo.from(11).isPositive());
        assertFalse(NumberInfo.from(0).isPositive());
        assertFalse(NumberInfo.from(-1).isPositive());
        assertFalse(NumberInfo.from(-10).isPositive());
    }

    @Test
    void isEven() {
        assertTrue(NumberInfo.from(0).isEven());
        assertTrue(NumberInfo.from(-2).isEven());
        assertTrue(NumberInfo.from(2).isEven());
        assertTrue(NumberInfo.from(-22).isEven());
        assertTrue(NumberInfo.from(22).isEven());

        assertFalse(NumberInfo.from(1).isEven());
        assertFalse(NumberInfo.from(-1).isEven());

        assertFalse(NumberInfo.from(13).isEven());
        assertFalse(NumberInfo.from(-13).isEven());
        assertFalse(NumberInfo.from(31).isEven());
        assertFalse(NumberInfo.from(-51).isEven());
    }

    @Test
    void getNumber() {
        for(int i = -100 ; i < 100 ; i++){
            assertEquals(i, NumberInfo.from(i).getNumber());
        }
    }
}