package com.baeldung.interfaceVsAbstractClass;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

public class InterfaceVsAbstractClassUnitTest {
    @Test
    public void givenAbstractClass_whenValidCircleUsedThenPass() {
        CircleClass redCircle = new ChildCircleClass();
        redCircle.setColor("RED");
        assertTrue(redCircle.isValid());
    }

    @Test
    public void givenAbstractClass_whenInvalidCircleUsedThenFail() {
        CircleClass redCircle = new ChildCircleClass();
        redCircle.setColor("WHITE");
        assertFalse(redCircle.isValid());
    }

    @Test
    public void givenInterface_whenValidCircleWithoutStateUsedThenPass() {
        ChidlCircleInterfaceImpl redCircleWithoutState = new ChidlCircleInterfaceImpl();
        redCircleWithoutState.setColor("RED");
        assertTrue(redCircleWithoutState.isValid(redCircleWithoutState.getColor()));
    }

    @Test
    public void givenInterface_whenInvalidCircleWithoutStateUsedThenFail() {
        ChidlCircleInterfaceImpl redCircleWithoutState = new ChidlCircleInterfaceImpl();
        redCircleWithoutState.setColor("WHITE");
        assertFalse(redCircleWithoutState.isValid(redCircleWithoutState.getColor()));
    }
}
