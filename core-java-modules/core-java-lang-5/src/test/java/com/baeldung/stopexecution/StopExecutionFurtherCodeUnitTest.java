package com.baeldung.stopexecution;

import org.junit.Assert;
import org.junit.jupiter.api.Test;

import java.net.MalformedURLException;

import static org.junit.Assert.*;

public class StopExecutionFurtherCodeUnitTest {


    @Test
    public void stopExecutionIfFlagIsFalse() {
        StopExecutionFurtherCode stopExecution = new StopExecutionFurtherCode();
        stopExecution.stop();
        int performedTask = stopExecution.performTask(10, 20);
        assertNotEquals(30, performedTask);
    }

    @Test
    public void stopExecutionIfFlagIsTrue() {
        StopExecutionFurtherCode stopExecution = new StopExecutionFurtherCode();
        int performedTask = stopExecution.performTask(10, 20);
        assertEquals(30, performedTask);
    }

    @Test
    public void stopExecutionWhenUrlAndPathWrong() throws MalformedURLException {
        StopExecutionFurtherCode stopExecutionFurtherCode = new StopExecutionFurtherCode();
        stopExecutionFurtherCode.download("", "");
    }

    @Test
    public void stopExecutionUsingException() throws MalformedURLException {
        StopExecutionFurtherCode stopExecutionFurtherCode = new StopExecutionFurtherCode();

        String name = "John";
        String result1 = stopExecutionFurtherCode.stopExecutionUsingException(name);
        assertEquals("JOHN", result1);

        try {
            Integer number1 = 10;
            assertThrows(IllegalArgumentException.class, () -> {
                int result = stopExecutionFurtherCode.stopExecutionUsingException(number1);
            });
        }catch (Exception e){
            Assert.fail("Unexpected exception thrown: " + e.getMessage());
        }

    }

    @Test
    public void stopExecutionWhenBaseCaseIfKnown() throws MalformedURLException {
        StopExecutionFurtherCode stopExecutionFurtherCode = new StopExecutionFurtherCode();
        int factorial = stopExecutionFurtherCode.calculateFactorial(1);
        assertEquals(1, factorial);
    }

    @Test
    public void stopExecutionInLoopWhenArrayHasNegative() {
        StopExecutionFurtherCode stopExecutionFurtherCode = new StopExecutionFurtherCode();
        int[] nums = {1, 2, 3, -1, 1, 2, 3};
        int sum = stopExecutionFurtherCode.calculateSum(nums);
        assertEquals(6, sum);
    }

    @Test
    public void stopExecutionInThreadUsingInterrupt() throws InterruptedException {
        InterruptThread stopExecution = new InterruptThread();

        stopExecution.start();
        Thread.sleep(2000);

        stopExecution.interrupt();
        stopExecution.join();

        assertTrue(!stopExecution.isAlive()); ;
    }

    @Test
    public void stopExecutionLabelledLoop() {
        StopExecutionFurtherCode furtherCode = new StopExecutionFurtherCode();
        final String[] lines = {"Line 1", "Line 2", "Line 3", "stop", "Line 4", "Line 5"};
        int statusCode = furtherCode.processLines(lines);
        assertEquals(-1, statusCode);
    }


}
