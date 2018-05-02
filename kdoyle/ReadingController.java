package com.baeldung.reactive.kdoyle;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;

import java.util.stream.Stream;

@RestController
public class ReadingController {

    @GetMapping(produces = MediaType.TEXT_EVENT_STREAM_VALUE, value = "/read")
    public Flux<ReadingBook> getWord(){
        return Flux.fromStream(Stream.generate(() -> new ReadingBook()));
    }

}
