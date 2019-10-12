package com.baeldung.reports;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;

public class CustomisedListener implements ITestListener {
    private static final Logger LOGGER = LoggerFactory.getLogger("CUSTOM_LOGS");

    @Override
    public void onFinish(ITestContext context) {
        LOGGER.info("PASSED TEST CASES");
        context.getPassedTests()
            .getAllResults()
            .forEach(result -> {
                LOGGER.info(result.getName());
            });
        LOGGER.info("FAILED TEST CASES");
        context.getFailedTests()
            .getAllResults()
            .forEach(result -> {
                LOGGER.info(result.getName());
            });
        LOGGER.info("Test completed on: " + context.getEndDate()
            .toString());
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
        LOGGER.info("Testing: " + arg0.getName());

    }

    @Override
    public void onTestSuccess(ITestResult arg0) {
        long timeTaken = ((arg0.getEndMillis() - arg0.getStartMillis()));
        LOGGER.info("Tested: " + arg0.getName() + " Time taken:" + timeTaken + " ms");

    }

}
