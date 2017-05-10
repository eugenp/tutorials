package com.baeldung.reports;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.*;
import org.testng.xml.XmlSuite;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.Map;
import java.util.stream.Stream;

public class CustomisedReports implements IReporter {
    private static final Logger LOGGER = LoggerFactory.getLogger(CustomisedReports.class);

    public void generateReport(List<XmlSuite> xmlSuites, List<ISuite> suites, String outputDirectory) {
        String reportTemplate = initReportTemplate();
        String resultRow = "<tr class=\"%s\"><td>%s</td><td>%s</td><td>%s</td><td>%s</td><td>%s</td></tr>";
        StringBuilder rows = new StringBuilder();
        suites.forEach(suite -> {
            Map<String, ISuiteResult> suiteResults = suite.getResults();
            suiteResults.forEach((testName, suiteResult) -> {

                ITestContext testContext = suiteResult.getTestContext();

                Stream<ITestResult> failedTests = testContext.getFailedTests().getAllResults().stream();
                Stream<ITestResult> passedTests = testContext.getPassedTests().getAllResults().stream();
                Stream<ITestResult> skippedTests = testContext.getSkippedTests().getAllResults().stream();

                String suiteName = suite.getName();

                Stream<ITestResult> allTestResults = Stream.concat(Stream.concat(failedTests, passedTests), skippedTests);
                generateReportRows(resultRow, rows, testName, suiteName, allTestResults);
            });
        });
        reportTemplate = reportTemplate.replaceFirst("</tbody>", rows.toString() + "</tbody>");
        saveReportTemplate(outputDirectory, reportTemplate);
    }

    private void generateReportRows(String resultRow, StringBuilder rows, String testName, String suiteName, Stream<ITestResult> allTestResults) {
        allTestResults
            .forEach(testResult -> {
                String testReportRow = "";
                if (testResult.getStatus() == ITestResult.FAILURE) {
                    testReportRow = String.format(resultRow, "danger", suiteName, testName, testResult.getName(), "FAILED", "NA");
                }
                if (testResult.getStatus() == ITestResult.SUCCESS) {
                    testReportRow = String.format(resultRow, "success", suiteName, testName, testResult.getName(), "PASSED", String.valueOf(testResult.getEndMillis() - testResult.getStartMillis()));

                }
                if (testResult.getStatus() == ITestResult.SKIP) {
                    testReportRow = String.format(resultRow, "warning", suiteName, testName, testResult.getName(), "SKIPPED", "NA");
                }
                rows.append(testReportRow);
            });
    }

    private String initReportTemplate() {
        String template = null;
        byte[] reportTemplate = null;
        try {
            reportTemplate = Files.readAllBytes(Paths.get("src/test/resources/reportTemplate.html"));
            template = new String(reportTemplate, "UTF-8");
        } catch (IOException e) {
            LOGGER.error("Problem initializing template", e);
        }
        return template;
    }

    private void saveReportTemplate(String outputDirectory, String reportTemplate) {
        new File(outputDirectory).mkdirs();
        try {
            PrintWriter reportWriter = new PrintWriter(new BufferedWriter(new FileWriter(new File(outputDirectory, "my-report.html"))));
            reportWriter.println(reportTemplate);
            reportWriter.flush();
            reportWriter.close();
        } catch (IOException e) {
            LOGGER.error("Problem saving template", e);
        }
    }
}
