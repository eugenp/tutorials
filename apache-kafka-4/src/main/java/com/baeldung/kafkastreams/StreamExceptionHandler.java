package com.baeldung.kafkastreams;

import org.apache.kafka.streams.errors.StreamsUncaughtExceptionHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class StreamExceptionHandler implements StreamsUncaughtExceptionHandler {

    private static final Logger log = LoggerFactory.getLogger(StreamExceptionHandler.class);

    @Override
    public StreamThreadExceptionResponse handle(Throwable exception) {
        log.error("Stream encountered fatal exception: {}", exception.getMessage(), exception);
        return StreamThreadExceptionResponse.REPLACE_THREAD;
    }
}
