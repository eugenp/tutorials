package com.baeldung.reports;

import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.IReporter;
import org.testng.ISuite;
import org.testng.ISuiteResult;
import org.testng.ITestContext;
import org.testng.xml.XmlSuite;

public class CustomisedReports implements IReporter {
    private static final Logger LOGGER = LoggerFactory.getLogger("TEST_REPORT");

    @Override
    public void generateReport(List<XmlSuite> xmlSuites, List<ISuite> suites, String outputDirectory) {

        suites.stream()
            .forEach(suite -> {
                String suiteName = suite.getName();
                Map<String, ISuiteResult> suiteResults = suite.getResults();
                suiteResults.values()
                    .stream()
                    .forEach(result -> {
                    ITestContext context 
                      = ((ISuiteResult) result).getTestContext();

                    LOGGER.info("Passed tests for suite '" 
                      + suiteName + "' is:" 
                      + context.getPassedTests()
                        .getAllResults()
                        .size());
                    LOGGER.info("Failed tests for suite '" 
                      + suiteName + "' is:" 
                      + context.getFailedTests()
                      .getAllResults()
                      .size());
                    LOGGER.info("Skipped tests for suite '" 
                      + suiteName + "' is:" 
                      + context.getSkippedTests()
                      .getAllResults()
                      .size());
                });
            });

    }

}
