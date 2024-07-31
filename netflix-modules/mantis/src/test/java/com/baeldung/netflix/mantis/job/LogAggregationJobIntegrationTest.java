package com.baeldung.netflix.mantis.job;

import com.baeldung.netflix.mantis.model.LogAggregate;
import io.mantisrx.runtime.PortRequest;
import io.mantisrx.runtime.sink.Sinks;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import static java.util.Arrays.asList;
import static java.util.Optional.of;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

class LogAggregationJobIntegrationTest extends MantisJobTestBase<LogAggregate> {

    private final static int PORT = 7382;
    private final static String SINK_URL = "http://localhost:" + PORT;

    @BeforeAll
    static void beforeAll() {
        start(new LogAggregationJob((context, portRequest, logAggregateObservable) -> {
            logAggregateObservable.subscribe();
            Sinks.sse(LogAggregate::toJsonString).call(context, new PortRequest(PORT), logAggregateObservable);
        }));
    }

    @Override
    public String getSinkUrl() {
        return SINK_URL;
    }

    @Override
    public Class<LogAggregate> getEventType() {
        return LogAggregate.class;
    }

    @Test
    void whenReadingFromSink_thenShouldRetrieveCorrectNumberOfLogAggregates() {
        assertEquals(of(5L), sinkStream.take(5).count().blockOptional());
    }

    @Test
    void whenReadingFromSink_thenShouldRetrieveLogAggregate() {
        assertNotNull(sinkStream.take(1).blockFirst());
    }

    @Test
    void whenReadingFromSink_thenShouldRetrieveValidLogAggregate() {
        LogAggregate logAggregate = sinkStream.take(1).blockFirst();

        assertTrue(asList("ERROR", "WARN", "INFO").contains(logAggregate.getLevel()));
        assertTrue(logAggregate.getCount() > 0);
    }

}