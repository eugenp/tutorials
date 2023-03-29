package com.baeldung.helperVsUtilityClasses;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

class MyHelperClassUnitTest {

    @Test
    void whenCreatingHelperObject_thenHelperObjectShouldBeCreated() {
        MyHelperClass myHelperClassObject = new MyHelperClass();

        assertNotNull(myHelperClassObject);
    }

    @Test
    void whenHelperObjectIsCreated_thenHelperMethodsAreAccessed() {
        MyHelperClass helperClassObject = new MyHelperClass();

        assertTrue(helperClassObject.isInteger("20"));
    }
}