package com.baeldung.reactive.eventstreaming.service;

import com.baeldung.reactive.eventstreaming.model.Notification;

import java.time.Duration;
import java.time.LocalTime;
import java.util.Random;
import java.util.stream.Stream;

import org.springframework.stereotype.Service;

import reactor.core.publisher.Flux;

@Service
public class NotificationService {

    public Flux<Notification> streamNotification() {
        Flux<Long> interval = Flux.interval(Duration.ofSeconds(1));
         
        Flux<Notification> events = Flux.fromStream(Stream.generate(
                                      ()->new Notification( getText(),
                                                			LocalTime.now())));
                                     
        return Flux.zip(events, interval, (key, value) -> key);
    }
    
    private String getText() {
        int randomNumber = new Random().nextInt(9) + 1; 
    	
        String text = String.format("You have %d new like(s) on your post...", randomNumber);
        
        return text;
    }

}