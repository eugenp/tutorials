package com.baeldung.guava.tutorial;

import com.google.common.util.concurrent.Monitor;
import org.junit.Assert;
import org.junit.Test;

public class MonitorUnitTest {

    @Test
    public void whenGaurdConditionIsTrue_IsSuccessful() {
        Monitor monitor = new Monitor();
        boolean enteredInCriticalSection = false;

        Monitor.Guard gaurdCondition = monitor.newGuard(this::returnTrue);

        if (monitor.enterIf(gaurdCondition)) {
            try {
                System.out.println("Entered in critical section");
                enteredInCriticalSection = true;
            } finally {
                monitor.leave();
            }
        }

        Assert.assertTrue(enteredInCriticalSection);

    }

    @Test
    public void whenGaurdConditionIsFalse_IsSuccessful() {
        Monitor monitor = new Monitor();
        boolean enteredInCriticalSection = false;

        Monitor.Guard gaurdCondition = monitor.newGuard(this::returnFalse);

        if (monitor.enterIf(gaurdCondition)) {
            try {
                System.out.println("Entered in critical section");
                enteredInCriticalSection = true;
            } finally {
                monitor.leave();
            }
        }

        Assert.assertFalse(enteredInCriticalSection);
    }

    private boolean returnTrue() {
        return true;
    }

    private boolean returnFalse() {
        return false;
    }
}
