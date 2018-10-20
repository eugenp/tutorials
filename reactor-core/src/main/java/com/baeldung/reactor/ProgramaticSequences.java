package com.baeldung.reactor;

import java.util.concurrent.atomic.AtomicInteger;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import reactor.core.publisher.Flux;

public class ProgramaticSequences {

    Logger logger = LoggerFactory.getLogger(ProgramaticSequences.class);

    public void statefullImutableGenerate() {
        Flux<String> flux = Flux.generate(() -> 1, (state, sink) -> {
            sink.next("2 + " + state + " = " + 2 + state);
            if (state == 101)
                sink.complete();
            return state + 1;
        });

        flux.subscribe(logger::info);
    }

    public void statefullMutableGenerate() {
        Flux<String> flux = Flux.generate(AtomicInteger::new, (state, sink) -> {
            int i = state.getAndIncrement();
            sink.next("2 + " + state + " = " + 2 + state);
            if (i == 101)
                sink.complete();
            return state;
        });

        flux.subscribe(logger::info);
    }

    public void handle() {
        Flux<String> elephants = Flux.just(1, 2, 3, 4, 5, 6, 7, 8, 9, 10)
            .handle((i, sink) -> {
                String animal = "Elephant nr " + i;
                if (i % 2 == 0) {
                    sink.next(animal);
                }
            });

        elephants.subscribe(logger::info);
    }

    public static void main(String[] args) {
        ProgramaticSequences ps = new ProgramaticSequences();

        ps.statefullImutableGenerate();
        ps.statefullMutableGenerate();
        ps.handle();

    }

}
