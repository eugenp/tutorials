package com.baeldung.reactive.webflux;

import com.baeldung.reactive.model.EmployeeEvent;
import java.time.Duration;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;

@RestController
@RequestMapping("/employee-monitoring")
class EmployeeMonitoringController {

    static final String CHECK_IN = "Checked In";
    static final String CHECK_OUT = "Checked Out";

    @GetMapping(value = "/events", produces = MediaType.APPLICATION_STREAM_JSON_VALUE)
    private Flux<EmployeeEvent> subscribeToEmployeeEvents() {
        return Flux.interval(Duration.ofSeconds(1L))
            .map(iteration -> new EmployeeEvent(iteration, makeAction(iteration)));
    }

    private String makeAction(Long iteration) {
        return iteration % 2 == 0 ? CHECK_IN : CHECK_OUT;
    }

}
