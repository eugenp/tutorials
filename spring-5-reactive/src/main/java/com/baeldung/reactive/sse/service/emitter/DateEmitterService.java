package com.baeldung.reactive.sse.service.emitter;

import com.baeldung.reactive.sse.service.EventSubscriptionsService;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;

import javax.annotation.PostConstruct;
import java.time.Duration;
import java.util.Date;
import java.util.stream.Stream;

@Service
public class DateEmitterService {

    private EventSubscriptionsService eventSubscriptionsService;

    public DateEmitterService(EventSubscriptionsService eventSubscriptionsService) {
        this.eventSubscriptionsService = eventSubscriptionsService;
    }

    @PostConstruct
    public void init() {
        Flux.fromStream(Stream.generate(Date::new))
                .delayElements(Duration.ofSeconds(1))
                .subscribe(data -> eventSubscriptionsService.sendDateEvent(new Date()));
    }
}
