package com.baeldung.reactive.streaming.annotation;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;
import reactor.util.function.Tuple2;

import java.time.Duration;
import java.util.stream.Stream;

@RestController
public class SequenceExampleController {
    @RequestMapping("/sequenceController")
    public Flux<Integer> stockTransactionEvents(){
        Flux<Long> interval = Flux.interval(Duration.ofSeconds(1));
        Flux<Integer> sequence = Flux.fromStream(Stream.iterate(1, incSeq -> incSeq + 1));
        return Flux.zip(interval, sequence).map(Tuple2::getT2);
    }
}
