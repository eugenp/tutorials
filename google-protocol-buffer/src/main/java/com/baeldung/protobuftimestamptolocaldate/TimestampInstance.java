package com.baeldung.protobuftimestamptolocaldate;

import com.google.protobuf.Timestamp;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.Instant;

public class TimestampInstance {

    private static final Logger logger = LoggerFactory.getLogger(TimestampInstance.class);

    public static void main(String[] args) {
        Instant currentTimestamp = Instant.now();
        Timestamp timestamp = Timestamp.newBuilder()
            .setSeconds(currentTimestamp.getEpochSecond())
            .setNanos(currentTimestamp.getNano())
            .build();
        logger.info(timestamp.toString());
    }
}
