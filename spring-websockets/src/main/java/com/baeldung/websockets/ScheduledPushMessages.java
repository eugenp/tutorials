package com.baeldung.websockets;


import com.github.javafaker.Faker;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.Date;

@Service
public class ScheduledPushMessages {

    private final SimpMessagingTemplate simpMessagingTemplate;
    
    private final Faker faker;
    
    public ScheduledPushMessages(SimpMessagingTemplate simpMessagingTemplate) {
        this.simpMessagingTemplate = simpMessagingTemplate;
        faker = new Faker();
    }

    @Scheduled(fixedRate = 5000)
    public void sendMessage() {
        final String time = new SimpleDateFormat("HH:mm").format(new Date());
        simpMessagingTemplate.convertAndSend("/topic/pushmessages", 
            new OutputMessage("Chuck Norris", faker.chuckNorris().fact(), time));
    }
    
}
