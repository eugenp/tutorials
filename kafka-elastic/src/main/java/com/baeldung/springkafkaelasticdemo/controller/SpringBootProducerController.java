package com.itsnaveenk.springkafkaelasticdemo.controller;


import com.itsnaveenk.springkafkaelasticdemo.model.NotificationModel;
import com.itsnaveenk.springkafkaelasticdemo.service.KafkaProducerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1")
public class SpringBootProducerController {

    @Autowired
    KafkaProducerService kafkaProducerService;
    @GetMapping("/status")
    public String checkMethod() {
        return "working";
    }

    @PostMapping("/send")
    public ResponseEntity<?> send(@RequestBody NotificationModel notificationModel) {
        System.out.println(notificationModel.getMessage() + " received in system");
        try {
            kafkaProducerService.sendMessage(notificationModel);
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body(""+ e.getMessage());
        }
        return ResponseEntity.ok("Message sent successfully");
    }


}
