package com.baeldung.reactive.controller;

import com.baeldung.reactive.model.CpuUsageEvent;
import com.baeldung.reactive.utils.CpuUtils;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.stream.Stream;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;
import reactor.util.function.Tuple2;

@RestController
public class CpuUsageEventProducer {

    @GetMapping(produces = MediaType.TEXT_EVENT_STREAM_VALUE, value = "/events/stream")
    Flux<CpuUsageEvent> events() {
        Flux<CpuUsageEvent> eventFlux = Flux
                .fromStream(Stream.generate(() -> new CpuUsageEvent(CpuUtils.getUsage(), LocalDateTime.now())));
        Flux<Long> durationFlux = Flux.interval(Duration.ofSeconds(5));
        return Flux.zip(eventFlux, durationFlux).map(Tuple2::getT1);
    }
}
