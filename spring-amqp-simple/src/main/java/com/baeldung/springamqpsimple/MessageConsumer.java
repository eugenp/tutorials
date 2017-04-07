package com.baeldung.springamqpsimple;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Component;

@Component
public class MessageConsumer {

    private static final Logger logger = LogManager.getLogger(MessageConsumer.class);

    public void receiveMessage(String message) {
        logger.info("Received Message: " + message);
    }
}
