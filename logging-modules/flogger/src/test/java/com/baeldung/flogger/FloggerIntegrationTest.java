package com.baeldung.flogger;

import com.google.common.flogger.FluentLogger;
import com.google.common.flogger.LoggerConfig;
import com.google.common.flogger.StackSize;
import org.junit.Test;

import java.util.concurrent.TimeUnit;
import java.util.logging.Level;
import java.util.stream.IntStream;

import static com.google.common.flogger.LazyArgs.lazy;

public class FloggerIntegrationTest {
    private static final FluentLogger logger = FluentLogger.forEnclosingClass();

    @Test
    public void givenAnInterval_shouldLogAfterEveryInterval() {
        IntStream.range(0, 100).forEach(value -> {
            logger.atInfo().every(40).log("This log shows [every 40 iterations] => %d", value);
        });
    }

    @Test
    public void givenATimeInterval_shouldLogAfterEveryTimeInterval() {
        IntStream.range(0, 1_000_0000).forEach(value -> {
            logger.atInfo().atMostEvery(10, TimeUnit.SECONDS).log("This log shows [every 10 seconds] => %d", value);
        });
    }

    @Test
    public void givenASimpleOperation_shouldLogTheResult() {
        int result = 45 / 3;
        logger.atInfo().log("The result is %d", result);
    }

    @Test
    public void givenCodeThatThrowsAndException_shouldLogTheException() {
        try {
            int result = 45 / 0;
        } catch (RuntimeException re) {
            logger.atInfo().withStackTrace(StackSize.FULL).withCause(re).log("Message");
        }
    }

    @Test
    public void givenALoggingConfiguration_shouldLogAtTheConfiguredLevel() {
        LoggerConfig.of(logger).setLevel(Level.FINE);
        logger.atInfo().log("Info Message");
        logger.atWarning().log("Warning Message");
        logger.atSevere().log("Severe Message");
        logger.atFinest().log("Finest Message");
        logger.atFine().log("Fine Message");
        logger.atFiner().log("Finer Message");
        logger.atConfig().log("Config Message");
    }

    @Test
    public void givenALongRunningMethodForStats_shouldCallTheMethodLazily() {
        //Wrong way of doing it
        logger.atFine().log("stats=%s", collectSummaries());

        // Almost no work done at the log site and structure is preserved.
        logger.atFine().log("stats=%s", lazy(() -> collectSummaries()));
    }

    public static String collectSummaries() {
        //compute summaries in a long-running process
        int items = 110;
        int s = 30;
        return String.format("%d seconds elapsed so far. %d items pending processing", s, items);
    }
}
