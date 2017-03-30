package com.baeldung.reports;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;

public class CustomisedListener implements ITestListener {
    private static final Logger LOGGER = LoggerFactory.getLogger("TEST_REPORT");

    @Override
    public void onFinish(ITestContext context) {
        LOGGER.info("PASSED TEST CASES");
        context.getPassedTests()
                .getAllResults()
                .stream()
                .forEach(result -> {
                    LOGGER.info(result.getName());
                });
        LOGGER.info("FAILED TEST CASES");
        context.getFailedTests()
                .getAllResults()
                .stream()
                .forEach(result -> {
                    LOGGER.info(result.getName());
                });
        LOGGER.info("Test completed on: " + context.getEndDate().toString());
    }

    @Override
    public void onStart(ITestContext arg0) {
        LOGGER.info("Started testing on: " + arg0.getStartDate()
                .toString());
    }

    @Override
    public void onTestFailedButWithinSuccessPercentage(ITestResult arg0) {
        // TODO Auto-generated method stub

    }

    @Override
    public void onTestFailure(ITestResult arg0) {
        LOGGER.info("Failed : " + arg0.getName());

    }

    @Override
    public void onTestSkipped(ITestResult arg0) {
        LOGGER.info("Skipped Test: " + arg0.getName());

    }

    @Override
    public void onTestStart(ITestResult arg0) {
        LOGGER.info("Testing: " + arg0.getName() + " " + arg0.getStartMillis());

    }

    @Override
    public void onTestSuccess(ITestResult arg0) {
        LOGGER.info("Tested: " + arg0.getName() + " " + arg0.getEndMillis());

    }

}
