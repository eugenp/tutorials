package com.baeldung.batchreaderproperties;

import java.time.ZonedDateTime;

public interface ContainsJobParameters {
    ZonedDateTime getTriggeredDateTime();
    String getTraceId();

    void setTriggeredDateTime(ZonedDateTime triggeredDateTime);
    void setTraceId(String traceId);
}
