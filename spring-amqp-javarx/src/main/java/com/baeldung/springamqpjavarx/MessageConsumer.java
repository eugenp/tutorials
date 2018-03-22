package com.baeldung.springamqpjavarx;

import com.baeldung.springamqpjavarx.restaurant.GuestService;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class MessageConsumer {

    private final GuestService guestService;

    @Autowired
    public MessageConsumer(GuestService guestService) {
        this.guestService = guestService;
    }

    @RabbitListener(queues = {SpringAmqpConfig.queueName})
    public void receiveMessage(String message) {
        guestService.receiveOrder(message);
    }
}