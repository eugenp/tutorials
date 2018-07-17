package vaibhav.com.server;

import java.time.Duration;
import java.util.Date;
import java.util.stream.Stream;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;
import reactor.util.function.Tuple2;
import vaibhav.com.model.Event;

@SpringBootApplication
@RestController
public class ReactiveServer {

    @GetMapping(produces = MediaType.TEXT_EVENT_STREAM_VALUE, value = "/events")
    public Flux<Event> events() {
        Flux<Event> eventFlux = Flux.fromStream(Stream.generate(() -> new Event(System.currentTimeMillis(), new Date())));
        Flux<Long> durationFlux = Flux.interval(Duration.ofSeconds(1));
        return Flux.zip(eventFlux, durationFlux)
            .map(Tuple2::getT1);
    }

    public static void main(String[] args) {
        SpringApplication.run(ReactiveServer.class, args);
    }

}