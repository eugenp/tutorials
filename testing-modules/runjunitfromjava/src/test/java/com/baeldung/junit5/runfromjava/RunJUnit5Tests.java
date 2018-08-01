package com.baeldung.junit5.runfromjava;

import static org.junit.platform.engine.discovery.ClassNameFilter.includeClassNamePatterns;
import static org.junit.platform.engine.discovery.DiscoverySelectors.selectClass;
import static org.junit.platform.engine.discovery.DiscoverySelectors.selectPackage;

import java.io.PrintWriter;

import org.junit.platform.launcher.Launcher;
import org.junit.platform.launcher.LauncherDiscoveryRequest;
import org.junit.platform.launcher.TestPlan;
import org.junit.platform.launcher.core.LauncherDiscoveryRequestBuilder;
import org.junit.platform.launcher.core.LauncherFactory;
import org.junit.platform.launcher.listeners.SummaryGeneratingListener;
import org.junit.platform.launcher.listeners.TestExecutionSummary;

public class RunJUnit5Tests {
    SummaryGeneratingListener listener = new SummaryGeneratingListener();

    public void runOne() {
        LauncherDiscoveryRequest request = LauncherDiscoveryRequestBuilder
          .request()
          .selectors(selectClass(RotateListUnitTest.class))
          .build();
        Launcher launcher = LauncherFactory.create();
        TestPlan testPlan = launcher.discover(request);

        launcher.registerTestExecutionListeners(listener);
        launcher.execute(request);
    }

    public void runAll() {
        LauncherDiscoveryRequest request = LauncherDiscoveryRequestBuilder
          .request()
          .selectors(selectPackage("com.baeldung.junit5.runfromjava"))
          .filters(includeClassNamePatterns(".*Test"))
          .build();
        Launcher launcher = LauncherFactory.create();

        TestPlan testPlan = launcher.discover(request);

        launcher.registerTestExecutionListeners(listener);

        launcher.execute(request);
    }

    public static void main(String[] args) {
        RunJUnit5Tests runner = new RunJUnit5Tests();
        runner.runAll();

        TestExecutionSummary summary = runner.listener.getSummary();
        summary.printTo(new PrintWriter(System.out));

        runner.runOne();

        summary = runner.listener.getSummary();
        summary.printTo(new PrintWriter(System.out));
    }
}
