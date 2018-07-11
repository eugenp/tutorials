package com.baeldung.service;

import com.baeldung.data.MemoryStats;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.util.function.Tuples;

import java.time.Duration;
import java.util.Date;

/**
 * Service for free memory event streaming.
 */
@Service
public class EventsEmitter {

    private final Runtime runtime = Runtime.getRuntime();

    /**
     * Emits Memory Status objects once in 1 seconds.
     * @return Flux<MemoryStats>
     */
    public Flux<MemoryStats> emitMemoryStats() {
        return Flux.interval(Duration.ofSeconds(1))
                .map(seq -> Tuples.of(new Date(), (runtime.freeMemory() / 1024), (runtime.totalMemory() / 1024)))
                .map(data -> new MemoryStats().setTimeStamp(new Date())
                        .setFreeMemory(data.getT2()).setTotalMemory(data.getT3()));
    }
}
