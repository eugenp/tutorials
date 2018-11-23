package com.baeldung.adapters;

import com.amazonaws.ClientConfiguration;
import com.amazonaws.services.sns.AmazonSNS;
import com.amazonaws.services.sns.AmazonSNSClient;
import com.amazonaws.services.sns.model.PublishRequest;
import com.baeldung.domain.Booking;
import com.baeldung.ports.NotificationService;
import org.springframework.stereotype.Component;

@Component
public class SNSNotificationService implements NotificationService {

    @Override
    public void notify(Booking booking) {
        String message = "Your booking was confirmed: " + booking.getEmail();
        snsClient().publish(new PublishRequest("booking-confirmed", message));
    }

    private AmazonSNS snsClient() {
        return AmazonSNSClient.builder()
                .withClientConfiguration(new ClientConfiguration())
                .build();
    }
}
