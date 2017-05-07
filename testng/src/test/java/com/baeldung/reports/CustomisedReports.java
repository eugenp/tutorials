package com.baeldung.reports;

import org.testng.IReporter;
import org.testng.IResultMap;
import org.testng.ISuite;
import org.testng.ISuiteResult;
import org.testng.ITestContext;
import org.testng.ITestResult;
import org.testng.xml.XmlSuite;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class CustomisedReports implements IReporter {
    private PrintWriter reportWriter;
    private String resultRow = "<tr class=\"%s\"><td>%s</td><td>%s</td><td>%s</td><td>%s</td><td>%s</td></tr>";

    public void generateReport(List<XmlSuite> xmlSuites, List<ISuite> suites, String outputDirectory) {
        new File(outputDirectory).mkdirs();
        try {
            reportWriter = new PrintWriter(new BufferedWriter(new FileWriter(new File(outputDirectory, "my-report.html"))));
        } catch (IOException e) {
            e.printStackTrace();
        }
        initReportTemplate();
        for (ISuite suite : suites) {
            Map<String, ISuiteResult> suiteResults = suite.getResults();
            suiteResults.forEach((testName, suiteResult) -> {
                ITestContext testContext = suiteResult.getTestContext();

                processFailedResults(testContext.getFailedTests(), suite, testName);

                processPassedResult(testContext.getPassedTests(), suite, testName);

                processSkippedResults(testContext.getSkippedTests(), suite, testName);
            });
        }
        finishReportTemplate();
        reportWriter.flush();
        reportWriter.close();
    }

    private void initReportTemplate() {
        reportWriter.println(
            "<html>" + "<head>" + "<title>My Custom Report</title>" + "<meta name=\"viewport\" content=\"width=device-width, initial-scale=1\">" + "<link rel=\"stylesheet\" href=\"https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css\">"
                + "<script src=\"https://ajax.googleapis.com/ajax/libs/jquery/3.2.0/jquery.min.js\"></script>" + "<script src=\"https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/js/bootstrap.min.js\"></script></head>" + "<body><div class=\"container\">");
        reportWriter.println("<table class=\"table\"><thead><tr>" + "<th>Suite</th>" + "<th>Test</th>" + "<th>Method</th>" + "<th>Status</th>" + "<th>Execution Time(ms)</th>" + "</tr></thead> <tbody>");
    }

    private void finishReportTemplate() {
        reportWriter.println(" </tbody></div></body></html>");
    }

    private void processPassedResult(IResultMap passResult, ISuite suite, String testName) {
        Set<ITestResult> testsPassed = passResult.getAllResults();
        for (ITestResult testResult : testsPassed) {
            reportWriter.println(String.format(resultRow, "success", suite.getName(), testName, testResult.getName(), "PASSED", String.valueOf(testResult.getEndMillis() - testResult.getStartMillis())));
        }

    }

    private void processFailedResults(IResultMap failResult, ISuite suite, String testName) {
        Set<ITestResult> testsFailed = failResult.getAllResults();
        for (ITestResult testResult : testsFailed) {
            reportWriter.println(String.format(resultRow, "danger", suite.getName(), testName, testResult.getName(), "FAILED", "NA"));
        }
    }

    private void processSkippedResults(IResultMap skipResult, ISuite suite, String testName) {
        Set<ITestResult> testsSkipped = skipResult.getAllResults();
        for (ITestResult testResult : testsSkipped) {
            reportWriter.println(String.format(resultRow, "warning", suite.getName(), testName, testResult.getName(), "SKIPPED", "NA"));
        }
    }
}
