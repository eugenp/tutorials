package com.baeldung.hexagonalarchitecture1.recordscheduler.adapter.in.web;

import com.baeldung.hexagonalarchitecture1.recordscheduler.application.port.in.RecordRequest;
import com.baeldung.hexagonalarchitecture1.recordscheduler.domain.RecordSchedule;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class RecordSchedulerController {
    @Autowired
    private RecordRequest recordRequest;

    @PostMapping("/schedule")
    String scheduleRecording(@RequestBody RecordSchedule recordSchedule) {
        return recordRequest.recordScheduleRequest(recordSchedule) ? "Success" : "Failed";
    }
}
