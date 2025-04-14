package com.baeldung.dapr.pubsub.publisher;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.dapr.spring.messaging.DaprMessagingTemplate;

@RestController
@RequestMapping("/passenger")
public class PassengerRestController {

    private static final Logger logger = LoggerFactory.getLogger(PassengerRestController.class);
    public static final String RIDE_REQUESTS_TOPIC = "ride-requests";

    private DaprMessagingTemplate<RideRequest> messaging;
    
    public PassengerRestController(DaprMessagingTemplate<RideRequest> messagingTemplate) {
        this.messaging = messagingTemplate;
    }

    @PostMapping("/request-ride")
    public String requestRide(@RequestBody RideRequest request) {
        messaging.send(RIDE_REQUESTS_TOPIC, request);

        logger.info("[bael] message sent: {}", request);
        return "looking for drivers";
    }
}
