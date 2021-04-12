package com.baeldung.hexagonalarchitecture1.recordscheduler.application.port.in;

import com.baeldung.hexagonalarchitecture1.recordscheduler.domain.RecordSchedule;

public interface RecordRequest {
    boolean recordScheduleRequest(RecordSchedule recordSchedule);
}
