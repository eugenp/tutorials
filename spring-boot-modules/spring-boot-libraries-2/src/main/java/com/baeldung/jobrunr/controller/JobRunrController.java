package com.baeldung.jobrunr.controller;

import com.baeldung.jobrunr.service.SampleJobService;
import org.jobrunr.scheduling.JobScheduler;
import org.springframework.boot.context.properties.bind.DefaultValue;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;

@RestController
@RequestMapping("/jobrunr")
public class JobRunrController {

    private JobScheduler jobScheduler;
    private SampleJobService sampleJobService;

    private JobRunrController(JobScheduler jobScheduler, SampleJobService sampleJobService) {
        this.jobScheduler = jobScheduler;
        this.sampleJobService = sampleJobService;
    }

    @GetMapping(value = "/enqueue/{input}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> enqueue(@PathVariable("input") @DefaultValue("default-input") String input) {
        jobScheduler.enqueue(() -> sampleJobService.executeSampleJob(input));
        return okResponse("job enqueued successfully");
    }

    @GetMapping(value = "/schedule/{input}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> schedule(
            @PathVariable("input") @DefaultValue("default-input") String input,
            @RequestParam("scheduleAt") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime scheduleAt) {
        jobScheduler.schedule(() -> sampleJobService.executeSampleJob(input), scheduleAt);
        return okResponse("job scheduled successfully");
    }

    private ResponseEntity<String> okResponse(String feedback) {
        return new ResponseEntity<>(feedback, HttpStatus.OK);
    }
}
