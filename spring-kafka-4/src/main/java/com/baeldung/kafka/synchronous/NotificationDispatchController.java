package com.baeldung.kafka.synchronous;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.concurrent.ExecutionException;

@RestController
@RequestMapping("/api/v1")
class NotificationDispatchController {

    private final NotificationDispatchService notificationDispatchService;

    NotificationDispatchController(NotificationDispatchService notificationDispatchService) {
        this.notificationDispatchService = notificationDispatchService;
    }

    @PostMapping(value = "/notification")
    ResponseEntity<NotificationDispatchResponse> dispatch(
        @RequestBody NotificationDispatchRequest notificationDispatchRequest
    ) throws ExecutionException, InterruptedException {
        NotificationDispatchResponse response = notificationDispatchService.dispatch(notificationDispatchRequest);
        return ResponseEntity.ok(response);
    }

}