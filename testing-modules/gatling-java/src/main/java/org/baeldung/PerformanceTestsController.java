package org.baeldung;

import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.TimeUnit;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
public class PerformanceTestsController {

    @GetMapping("/api/fast-response")
    public ResponseEntity<String> getFastResponse() {
        return ResponseEntity.ok("was that fast enough?");
    }

    @GetMapping("/api/slow-response")
    public ResponseEntity<String> getSlowResponse() throws InterruptedException {
        int min = 1000;
        int max = 2000;
        TimeUnit.MILLISECONDS.sleep(ThreadLocalRandom.current()
            .nextInt(min, max));

        return ResponseEntity.ok("this took a while");
    }
}
