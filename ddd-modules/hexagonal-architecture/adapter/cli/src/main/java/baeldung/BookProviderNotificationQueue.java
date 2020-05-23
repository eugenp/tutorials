package baeldung;

import lombok.extern.slf4j.Slf4j;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class BookProviderNotificationQueue {

    @JmsListener(destination = "book-provider-queue")
    public void receiveMessage(String message) {
        log.info("Received message from library {}", message);
    }

}
