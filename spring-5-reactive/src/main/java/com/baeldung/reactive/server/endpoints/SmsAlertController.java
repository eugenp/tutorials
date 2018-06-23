package com.baeldung.reactive.server.endpoints;

import com.baeldung.reactive.events.SmsEvent;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;
import reactor.util.function.Tuple2;

import java.time.Duration;
import java.util.Date;
import java.util.stream.Stream;

@RestController
@RequestMapping(value = "api/short-messages")
public class SmsAlertController {

    @GetMapping(value = "{mobileNumber}", produces = {MediaType.TEXT_EVENT_STREAM_VALUE})
    public Flux<SmsEvent> pushNotifications(@PathVariable("mobileNumber") String mobileNumber) {
        SmsEvent smsEvent = new SmsEvent(mobileNumber, "Message successfully pushed", "143", new Date());
        Flux<SmsEvent> smsEventFlux = Flux.fromStream(Stream.generate( () -> smsEvent));
        Flux<Long> durationFlux = Flux.interval(Duration.ofSeconds(1));
        return Flux.zip(smsEventFlux, durationFlux).map(Tuple2::getT1);
    }
}
