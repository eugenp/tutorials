package com.baeldung.enginetestkit;

import static java.util.Collections.emptyList;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.platform.engine.discovery.DiscoverySelectors.selectClass;
import static org.junit.platform.engine.discovery.DiscoverySelectors.selectMethod;
import static org.junit.platform.testkit.engine.EventConditions.abortedWithReason;
import static org.junit.platform.testkit.engine.EventConditions.event;
import static org.junit.platform.testkit.engine.EventConditions.finishedWithFailure;
import static org.junit.platform.testkit.engine.EventConditions.skippedWithReason;
import static org.junit.platform.testkit.engine.EventConditions.test;
import static org.junit.platform.testkit.engine.TestExecutionResultConditions.instanceOf;
import static org.junit.platform.testkit.engine.TestExecutionResultConditions.message;

import org.junit.jupiter.api.Test;
import org.junit.platform.testkit.engine.EngineDiscoveryResults;
import org.junit.platform.testkit.engine.EngineTestKit;
import org.junit.platform.testkit.engine.Events;
import org.opentest4j.AssertionFailedError;
import org.opentest4j.TestAbortedException;

public class EngineTestKitDiscoveryUnitTest {

    @Test
    void verifyTestEngineDiscovery() {
        EngineDiscoveryResults results = EngineTestKit.engine("junit-jupiter")
            .selectors(selectClass(DisplayTest.class))
            .discover();

        assertEquals("JUnit Jupiter", results.getEngineDescriptor().getDisplayName());
        assertEquals(emptyList(), results.getDiscoveryIssues());
    }

    @Test
    void verifyVintageDiscovery() {
        EngineDiscoveryResults results = EngineTestKit.engine("junit-vintage")
            .selectors(selectClass(DisplayTest.class))
            .discover();

        assertEquals("JUnit Vintage", results.getEngineDescriptor().getDisplayName());
    }

    @Test
    void verifyHighLevelTestStats() {
        EngineTestKit
            .engine("junit-jupiter")
            .selectors(selectClass(DisplayTest.class))
            .execute()
            .testEvents()
            .assertStatistics(stats ->
                stats.started(3).finished(3).succeeded(1).failed(1).skipped(1).aborted(1));
    }

    @Test
    void verifyTestAbortion() {
        Events testEvents = EngineTestKit
            .engine("junit-jupiter")
            .selectors(selectMethod(DisplayTest.class, "aborts"))
            .execute()
            .testEvents();

        testEvents.assertThatEvents()
            .haveExactly(1, event(test("aborts"),
                    abortedWithReason(instanceOf(TestAbortedException.class),
                        message(message -> message.contains("test only runs for mobile")))));
    }

    @Test
    void verifyTestFailure() {
        Events testEvents = EngineTestKit
            .engine("junit-jupiter")
            .selectors(selectMethod(DisplayTest.class, "fails"))
            .execute()
            .testEvents();

        testEvents.assertThatEvents()
            .haveExactly(1, event(test("fails"),
                finishedWithFailure(instanceOf(AssertionFailedError.class))));
    }

}
