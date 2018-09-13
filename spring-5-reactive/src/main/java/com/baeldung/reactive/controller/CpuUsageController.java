package com.baeldung.reactive.controller;

import com.baeldung.reactive.model.CpuUsage;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;
import reactor.util.function.Tuple2;

import java.time.Duration;
import java.util.Random;
import java.util.stream.Stream;

@RestController
public class CpuUsageController {

    private Random random = new Random();

    @GetMapping(produces = MediaType.TEXT_EVENT_STREAM_VALUE, value = "/cpu-usage")
    public Flux<CpuUsage> getCpuUsage() {
        final Flux<CpuUsage> cpuUsageFlux = Flux.fromStream(Stream.generate(() -> currentCpuUsage()));
        final Flux<Long> interval = Flux.interval(Duration.ofSeconds(1));

        return Flux.zip(cpuUsageFlux, interval, (x, y) -> x);
    }

    private CpuUsage currentCpuUsage() {
        int usage = (int) (random.nextDouble()*100);
        String severity = getSeverity(usage);

        CpuUsage cpuUsage = new CpuUsage(usage, severity);
        return cpuUsage;
    }

    private String getSeverity(int usage) {
        if (usage < 50) {
            return "Normal";
        } else if (usage < 80) {
            return "Warning";
        } else {
            return "Critical";
        }
    }

}
