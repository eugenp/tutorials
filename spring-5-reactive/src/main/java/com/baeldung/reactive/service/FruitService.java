package com.baeldung.reactive.service;

import java.util.Random;
import org.springframework.stereotype.Service;
import com.baeldung.reactive.model.FruitStatus;
import reactor.core.publisher.Flux;

@Service
public class FruitService {

    public Flux<FruitStatus> getStatus(String name) {
        
        String[] statusTypes = new String[] { "Available", "OutOfStock", "Arriving", "Delayed" };

        return Flux.fromStream(java.util.stream.Stream.generate(() -> new FruitStatus(name, null)))
            .doOnEach(s -> {
                s.get()
                    .setStatus(statusTypes[new Random().nextInt(4)]);
            });

    }

}
