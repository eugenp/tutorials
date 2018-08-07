package com.baeldung.runfromjava;

import junit.extensions.ActiveTestSuite;
import junit.extensions.RepeatedTest;
import junit.framework.JUnit4TestAdapter;
import junit.framework.Test;
import junit.framework.TestSuite;
import org.junit.internal.TextListener;
import org.junit.runner.JUnitCore;
import org.junit.runner.Result;
import org.junit.runner.notification.Failure;

public class RunJUnit4TestsFromJava {

    public static void runOne() {
        JUnitCore junit = new JUnitCore();
        junit.addListener(new TextListener(System.out));
        junit.run(FirstUnitTest.class);
    }

    public static void runAllClasses() {
        JUnitCore junit = new JUnitCore();
        junit.addListener(new TextListener(System.out));

        Result result = junit.run(FirstUnitTest.class, SecondUnitTest.class);

        for (Failure failure : result.getFailures()) {
            System.out.println(failure.toString());
        }

        resultReport(result);
    }

    public static void runSuiteOfClasses() {
        JUnitCore junit = new JUnitCore();
        junit.addListener(new TextListener(System.out));
        Result result = junit.run(MyTestSuite.class);

        for (Failure failure : result.getFailures()) {
            System.out.println(failure.toString());
        }

        resultReport(result);
    }

    public static void runRepeated() {
        Test test = new JUnit4TestAdapter(SecondUnitTest.class);
        RepeatedTest repeatedTest = new RepeatedTest(test, 5);

        JUnitCore junit = new JUnitCore();
        junit.addListener(new TextListener(System.out));

        junit.run(repeatedTest);
    }

    public static void runRepeatedSuite() {
        TestSuite mySuite = new ActiveTestSuite();

        JUnitCore junit = new JUnitCore();
        junit.addListener(new TextListener(System.out));

        mySuite.addTest(new RepeatedTest(new JUnit4TestAdapter(FirstUnitTest.class), 5));
        mySuite.addTest(new RepeatedTest(new JUnit4TestAdapter(SecondUnitTest.class), 3));

        junit.run(mySuite);
    }

    public static void resultReport(Result result) {
        System.out.println("Finished. Result: Failures: " +
          result.getFailureCount() + ". Ignored: " +
          result.getIgnoreCount() + ". Tests run: " +
          result.getRunCount() + ". Time: " +
          result.getRunTime() + "ms.");
    }

    public static void main(String[] args) {
        System.out.println("\nRunning one test class:");
        runOne();

        System.out.println("\nRunning all test classes:");
        runAllClasses();

        System.out.println("\nRunning a suite of test classes:");
        runSuiteOfClasses();

        System.out.println("\nRunning repeated tests:");
        runRepeated();

        System.out.println("\nRunning repeated suite tests:");
        runRepeatedSuite();
    }

}