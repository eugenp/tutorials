package com.baeldung.reactive.service;

import com.baeldung.reactive.model.Image;
import com.baeldung.reactive.model.ImageEvent;
import com.baeldung.reactive.repository.ImageRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.util.function.Tuple2;

import java.time.Duration;
import java.time.LocalDate;
import java.util.stream.Stream;

@Service
@AllArgsConstructor
public class ImageService {

    private final ImageRepository imageRepository;

    public Mono<Image> findById(String id) {
        return imageRepository.findById(id);
    }

    public Flux<Image> findAll() {
        return imageRepository.findAll();
    }

    public Mono<Void> deleteAll() {
        return imageRepository.deleteAll();
    }

    public Flux<ImageEvent> events(Image image) {
        Flux<Long> interval = Flux.interval(Duration.ofSeconds(1));
        Flux<ImageEvent> events = Flux.fromStream(
                Stream.generate(() -> new ImageEvent(image, LocalDate.now()))
        );
        return Flux.zip(interval, events).map(Tuple2::getT2);
    }
}
