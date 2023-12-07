package com.baeldung.batch_reader_properties;

import java.time.ZonedDateTime;

public interface ContainsJobParameters {
    ZonedDateTime getTriggeredDateTime();
    String getTraceId();

    void setTriggeredDateTime(ZonedDateTime triggeredDateTime);
    void setTraceId(String traceId);
}
