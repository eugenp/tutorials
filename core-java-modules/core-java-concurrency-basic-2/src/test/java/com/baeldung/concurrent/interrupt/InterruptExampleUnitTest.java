package com.baeldung.concurrent.interrupt;

import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

public class InterruptExampleUnitTest {

    @Test
    public void whenPropagateException_thenThrowsInterruptedException() {
        assertThrows(InterruptedException.class, () -> InterruptExample.propagateException());
    }

    @Test
    public void whenRestoreTheState_thenReturnsTrue() {
        assertTrue(InterruptExample.restoreTheState());
    }
    
    @Test
    public void whenThrowCustomException_thenContainsExpectedMessage() {
        Exception exception = assertThrows(CustomInterruptedException.class, () -> InterruptExample.throwCustomException());
        String expectedMessage = "This thread was interrupted";
        String actualMessage = exception.getMessage();

        assertTrue(actualMessage.contains(expectedMessage));
    }
   
    @Test
    public void whenHandleWithCustomException_thenReturnsTrue() throws CustomInterruptedException{
        assertTrue(InterruptExample.handleWithCustomException());
    }
}
