package com.baeldung.hexagonalarchitecture1.recordscheduler.application.port.out;

import com.baeldung.hexagonalarchitecture1.recordscheduler.domain.RecordSchedule;

public interface UpdateRecordSchedule {

    void upsertSchedule(RecordSchedule recordSchedule);
}
