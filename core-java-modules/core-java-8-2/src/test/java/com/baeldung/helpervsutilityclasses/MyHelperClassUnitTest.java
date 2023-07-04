package com.baeldung.helpervsutilityclasses;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

class MyHelperClassUnitTest {

    @Test
    void whenCreatingHelperObject_thenHelperObjectShouldBeCreated() {
        MyHelperClass myHelperClassObject = new MyHelperClass(0.10);
        int[] numberArray = {15, 23, 66, 3, 51, 79};

        assertNotNull(myHelperClassObject);

        assertEquals(90, myHelperClassObject.discountedPrice(100.00));
        assertEquals( 79, MyHelperClass.getMaxNumber(numberArray));
        assertEquals(3, MyHelperClass.getMinNumber(numberArray));
    }
}