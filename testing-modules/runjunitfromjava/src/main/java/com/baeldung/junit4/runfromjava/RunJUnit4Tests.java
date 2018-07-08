package com.baeldung.junit4.runfromjava;

import junit.extensions.ActiveTestSuite;
import junit.extensions.RepeatedTest;
import org.junit.extensions.cpsuite.ClasspathSuite;
import static org.junit.extensions.cpsuite.SuiteType.*;
import junit.framework.TestSuite;

import org.junit.extensions.cpsuite.ClasspathSuite.SuiteTypes;
import org.junit.runner.JUnitCore;
import org.junit.runner.Result;
import org.junit.runner.RunWith;
import org.junit.runner.notification.Failure;

@RunWith(ClasspathSuite.class)
@SuiteTypes({ TEST_CLASSES })
public class RunJUnit4Tests {

    public static void runOne() {
        junit.textui.TestRunner.run(new TestSuite(MergeListsUnitTest.class));
    }

    public static void runAllClasses() {
        Result result = JUnitCore.runClasses(RunJUnit4Tests.class);

        for (Failure failure : result.getFailures()) {
            System.out.println(failure.toString());
        }

        resultReport(result);
    }

    public static void runSuiteOfClasses() {
        Result result = JUnitCore.runClasses(MyTestSuite.class);

        for (Failure failure : result.getFailures()) {
            System.out.println(failure.toString());
        }

        resultReport(result);

    }

    public static void resultReport(Result result) {
        System.out.println("Finished. Result " + ". Failures: " + 
                result.getFailureCount() + ". Ignored: " + 
                result.getIgnoreCount() + ". Tests runt: " + 
                result.getRunCount() + ". Time: " + 
                result.getRunTime() + "ms.");
    }

    public static void runRepeated() {
        RepeatedTest repeatedTest = new RepeatedTest(new TestSuite(MergeListsUnitTest.class), 5);
        junit.textui.TestRunner.run(repeatedTest);
    }

    public static void runRepeatedTestMethod() {
        TestSuite mySuite = new ActiveTestSuite();

        mySuite.addTest(new RepeatedTest(new MergeListsUnitTest("whenMergingNormalLists_thenGetExpectedString"), 50));
        mySuite.addTest(new RepeatedTest(new MergeListsUnitTest("whenMergingNullLists_thenGetNull"), 10));

        junit.textui.TestRunner.run(mySuite);
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

        System.out.println("\nRunning repeated tests on specific test methods:");
        runRepeatedTestMethod();

    }

}