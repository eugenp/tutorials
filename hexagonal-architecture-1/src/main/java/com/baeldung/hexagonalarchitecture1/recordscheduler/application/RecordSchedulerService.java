package com.baeldung.hexagonalarchitecture1.recordscheduler.application;

import com.baeldung.hexagonalarchitecture1.recordscheduler.application.port.in.RecordRequest;
import com.baeldung.hexagonalarchitecture1.recordscheduler.application.port.out.AuthorizeRecordRequest;
import com.baeldung.hexagonalarchitecture1.recordscheduler.application.port.out.UpdateRecordSchedule;
import com.baeldung.hexagonalarchitecture1.recordscheduler.domain.RecordSchedule;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RecordSchedulerService implements RecordRequest {

    @Autowired
    private AuthorizeRecordRequest authorizeRecordRequest;
    @Autowired
    private UpdateRecordSchedule updateRecordSchedule;

    @Override
    public boolean recordScheduleRequest(RecordSchedule recordSchedule) {
        if (authorizeRecordRequest.isRecordingAllowed(recordSchedule)) {
            updateRecordSchedule.upsertSchedule(recordSchedule);
            return true;
        }
        return false;
    }
}
