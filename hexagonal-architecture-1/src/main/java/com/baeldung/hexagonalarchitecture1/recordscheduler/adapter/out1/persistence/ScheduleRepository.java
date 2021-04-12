package com.baeldung.hexagonalarchitecture1.recordscheduler.adapter.out1.persistence;

import com.baeldung.hexagonalarchitecture1.recordscheduler.application.port.out.UpdateRecordSchedule;
import com.baeldung.hexagonalarchitecture1.recordscheduler.domain.RecordSchedule;
import org.springframework.stereotype.Repository;

@Repository
public class ScheduleRepository implements UpdateRecordSchedule {

    @Override
    public void upsertSchedule(RecordSchedule recordSchedule) {
        // save into database
    }
}
