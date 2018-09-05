package com.baeldung.reactive.eventstreaming.endpoint;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;

import com.baeldung.reactive.eventstreaming.model.Notification;
import com.baeldung.reactive.eventstreaming.service.NotificationService;

import reactor.core.publisher.Mono;

@Component
public class RequestHandler {
    
    @Autowired
    private NotificationService notificationService;
    
    public Mono<ServerResponse> streamNotification(ServerRequest request) {
    	
        return ServerResponse.ok()
               .contentType(MediaType.TEXT_EVENT_STREAM)
               .body(notificationService
               .streamNotification(), Notification.class);
    }
    
}