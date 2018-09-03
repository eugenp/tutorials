package com.baeldung.event.streams;

import com.github.javafaker.Faker;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;

import java.time.Duration;
import java.util.UUID;
import java.util.stream.Stream;

@RestController
public class UserController {

    @GetMapping(
        value = "/users",
        produces = MediaType.TEXT_EVENT_STREAM_VALUE
    )
    public Flux<User> getUsers() {
        return Flux
            .fromStream(Stream.generate(() ->
              new User(UUID.randomUUID().toString(),
                Faker.instance().name().fullName())))
            .delayElements(Duration.ofSeconds(1));
    }

}
