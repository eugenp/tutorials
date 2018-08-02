package com.baeldung.reactive.controller;

import com.baeldung.reactive.controller.exception.ImageNotFoundException;
import com.baeldung.reactive.model.Image;
import com.baeldung.reactive.model.ImageEvent;
import com.baeldung.reactive.service.ImageService;
import lombok.AllArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Controller
@AllArgsConstructor
@RequestMapping(path = "images")
public class ImageController {

    private final ImageService imageService;

    @GetMapping("/{id}")
    public Mono<Image> show(@PathVariable String id) {
        return imageService
                .findById(id)
                .switchIfEmpty(Mono.error(new ImageNotFoundException("Image with id " + id + " not found")));
    }

    @GetMapping
    public Flux<Image> list() {
        return imageService.findAll();
    }

    @DeleteMapping
    public Mono<Void> deleteAll() {
        return imageService.deleteAll();
    }

    @GetMapping(value = "/{id}/events", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    public Flux<ImageEvent> events(@PathVariable String id) {
        return imageService.findById(id).flatMapMany(imageService::events);
    }
}
