package com.baeldung.netflix.mantis.job;

import com.baeldung.netflix.mantis.model.LogEvent;
import com.baeldung.netflix.mantis.sink.LogSink;
import io.mantisrx.runtime.Context;
import io.mantisrx.runtime.PortRequest;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import rx.Observable;

import static java.util.Arrays.asList;
import static java.util.Optional.of;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

class LogCollectingJobIntegrationTest extends MantisJobTestBase<LogEvent> {

    private final static int PORT = 7381;
    private final static String SINK_URL = "http://localhost:" + PORT;

    @BeforeAll
    static void beforeAll() {

        start(new LogCollectingJob(new LogSink() {

            @Override
            public void call(Context context, PortRequest portRequest, Observable<LogEvent> observable) {
                super.call(context, new PortRequest(PORT), observable);
            }

        }));

    }

    @Override
    public String getSinkUrl() {
        return SINK_URL;
    }

    @Override
    public Class<LogEvent> getEventType() {
        return LogEvent.class;
    }

    @Test
    void whenReadingFromSink_thenShouldRetrieveCorrectNumberOfLogEvents() {
        assertEquals(of(5L), sinkStream.take(5).count().blockOptional());
    }

    @Test
    void whenReadingFromSink_thenShouldRetrieveLogEvent() {
        assertNotNull(sinkStream.take(1).blockFirst());
    }

    @Test
    void whenReadingFromSink_thenShouldRetrieveValidLogEvent() {
        LogEvent logEvent = sinkStream.take(1).blockFirst();

        assertTrue(asList("ERROR", "WARN", "INFO").contains(logEvent.getLevel()));
        assertTrue(asList("login attempt", "user created").contains(logEvent.getMessage()));
    }

    @Test
    void whenReadingFromSink_thenShouldRetrieveFilteredLogEvents() {
        getSinkStream(SINK_URL + "?filter=login")
          .take(7)
          .toStream().forEach(
            logEvent -> assertEquals("login attempt", logEvent.getMessage())
        );
    }

}