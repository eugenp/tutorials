package com.baeldung.reactive.realtime.service;

import com.baeldung.reactive.realtime.model.Event;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;

import java.time.Duration;
import java.util.Random;
import java.util.stream.Stream;

@Service
public class RealtimeEventGeneratorService {

    private static final Random rnd = new Random();

    public Flux<Event> flow(){
        return Flux.fromStream(Stream.generate(() -> rnd.nextInt(100)))
                .map(l -> new Event(l))
                .delayElements(Duration.ofSeconds(1));
    }
}
