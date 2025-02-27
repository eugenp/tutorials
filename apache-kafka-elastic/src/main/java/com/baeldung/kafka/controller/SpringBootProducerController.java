package com.baeldung.kafka.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.baeldung.kafka.model.NotificationModel;
import com.baeldung.kafka.service.KafkaProducerService;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@RestController
@RequestMapping("/api/v1")
public class SpringBootProducerController {

    @Autowired
    KafkaProducerService kafkaProducerService;

    private static final Logger log = LoggerFactory.getLogger(SpringBootProducerController.class);

    @GetMapping("/status")
    public String checkMethod() {
        return "working";
    }

    @PostMapping("/send")
    public ResponseEntity<?> send(@RequestBody NotificationModel notificationModel) {
        try {
            kafkaProducerService.sendMessage(notificationModel);
        } catch (Exception e) {
            log.error("exception occurred while calling sendMessage ", e);
            return ResponseEntity.internalServerError()
                .body("Error sending message: " + e.getMessage());
        }
        return ResponseEntity.ok("Message sent successfully");
    }

}
