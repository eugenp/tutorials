package com.baeldung.dapr.pubsub.publisher;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.baeldung.dapr.pubsub.model.RideRequest;

import io.dapr.Topic;
import io.dapr.client.domain.CloudEvent;

@RestController
@RequestMapping("driver")
public class DriverRestController {

    private static final Logger logger = LoggerFactory.getLogger(DriverRestController.class);

    private int drivesAccepted = 0;
    private int drivesRejected = 0;

    @Value("${driver.acceptance.criteria}")
    public String criteria;

    @PostMapping("ride-request")
    @Topic(pubsubName = "ride-hailing", name = PassengerRestController.RIDE_REQUESTS_TOPIC)
    public void onRideRequest(@RequestBody CloudEvent<RideRequest> event) {
        RideRequest request = event.getData();
        logger.info("[bael] Test Event Received: {}", request);

        if (request.getDestination()
            .contains(criteria)) {
            drivesAccepted++;
        } else {
            logger.info("[bael] rejecting Event");
            drivesRejected++;
            throw new UnsupportedOperationException("drive rejected");
        }
    }

    @GetMapping("accepted-rides")
    public int getDrivesAccepted() {
        return drivesAccepted;
    }

    @GetMapping("rejected-rides")
    public int getDrivesRejected() {
        return drivesRejected;
    }
}
