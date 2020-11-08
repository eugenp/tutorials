package com.baeldung.websockets;

import com.github.javafaker.Faker;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;

import java.text.SimpleDateFormat;
import java.time.Duration;
import java.util.Date;

@Service
public class ReactiveScheduledPushMessages implements InitializingBean {

    private final SimpMessagingTemplate simpMessagingTemplate;

    private final Faker faker;

    public ReactiveScheduledPushMessages(SimpMessagingTemplate simpMessagingTemplate) {
        this.simpMessagingTemplate = simpMessagingTemplate;
        this.faker = new Faker();
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        Flux.interval(Duration.ofSeconds(4L))
            .map((n) -> new OutputMessage(faker.backToTheFuture().character(), faker.backToTheFuture().quote(), 
                                            new SimpleDateFormat("HH:mm").format(new Date())))
            .subscribe(message -> simpMessagingTemplate.convertAndSend("/topic/pushmessages", message));
    }
}
