package com.baeldung.concurrent.anonymousthread;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

public class AnonymousThreadUnitTest {
    static ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
    static PrintStream printStream = new PrintStream(outputStream);
    static List<String> expectedNumbers = new ArrayList<>();

    @BeforeClass
    public static void setupClass() {
        for (int i = 1; i <= 10; i++) {
            expectedNumbers.add(String.valueOf(i));
        }
    }

    @Before
    public void setupMethod() {
        System.setOut(printStream);
    }

    @After
    public void cleanUpMethod() {
        outputStream.reset();
        System.setOut(System.out);
    }

    @AfterClass
    public static void cleanupClass() throws IOException {
        expectedNumbers.clear();
        outputStream.close();
        printStream.close();
    }

    @Test
    public void givenAnonymousThreadSubclassOverridingRunMethod_whenStart_thenRunAsExpected() {
        Thread anonymousThread = new Thread() {
            @Override
            public void run() {
                printNumbersFrom1To10();
            }
        };
        anonymousThread.start();
        waitForThreadToFinish();
        anonymousThread.interrupt();

        String[] printedNumbers = getPrintedNumbers();
        assertEquals(expectedNumbers, List.of(printedNumbers));
    }

    @Test
    public void givenAnonymousThreadWithLambdaExpression_whenStart_thenRunAsExpected() {
        Thread anonymousThread = new Thread(() -> {
            printNumbersFrom1To10();
        });

        anonymousThread.start();
        waitForThreadToFinish();
        anonymousThread.interrupt();

        String[] printedNumbers = getPrintedNumbers();
        assertEquals(expectedNumbers, List.of(printedNumbers));
    }

    @Test
    public void givenAnonymousThreadWithRunnableObject_whenStart_thenRunAsExpected() {
        Thread anonymousThread = new Thread(new Runnable() {
            @Override
            public void run() {
                printNumbersFrom1To10();
            }
        });
        anonymousThread.start();
        waitForThreadToFinish();
        anonymousThread.interrupt();

        String[] printedNumbers = getPrintedNumbers();
        assertEquals(expectedNumbers, List.of(printedNumbers));
    }

    private void printNumbersFrom1To10() {
        for (int i = 1; i <= 10; i++) {
            System.out.println(i);
        }
    }

    private void waitForThreadToFinish() {
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            // ignore
        }
    }

    private String[] getPrintedNumbers() {
        String printedOutput = outputStream.toString()
            .trim();
        return printedOutput.split(System.lineSeparator());
    }
}
