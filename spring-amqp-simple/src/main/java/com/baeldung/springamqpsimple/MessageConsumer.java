package com.baeldung.springamqpsimple;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
<<<<<<< HEAD

=======
import org.springframework.amqp.rabbit.annotation.RabbitListener;
>>>>>>> eugenp/master
import org.springframework.stereotype.Component;

@Component
public class MessageConsumer {

    private static final Logger logger = LoggerFactory.getLogger(MessageConsumer.class);

    @RabbitListener(queues = {SpringAmqpConfig.queueName})
    public void receiveMessage(String message) {
        logger.info("Received Message: " + message);
    }
}
